package io.github.muqhc.skblebot

import com.google.cloud.firestore.Firestore
import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import io.github.muqhc.skblebot.command.*
import io.github.muqhc.skblebot.db.dbAPIInitialize
import io.github.muqhc.skblebot.listener.CommandListener
import java.time.Instant


class SkollobleTesterBot(val token: String, val ownerIDs: List<String>): Thread() {
    lateinit var db: Firestore

    val eventChannelCommand = SelectEventChannelCommand()

    fun initializeData() {
        db = dbAPIInitialize(System.getenv("DATABASE_CAKE").byteInputStream(),System.getenv("DB_URL"))

        val announcingDataAPI = db.collection("guild-channel-pair").document("announcing").get().get()

        announcingDataAPI.data!!.forEach { (key, value) ->
            eventChannelCommand.announceChannelMap[key] = value as String
        }
    }

    fun runDiscordBot() {
        initializeData()

        val client = DiscordClient.create(token)
        val gateway = client.login().block()

        Runtime.getRuntime().addShutdownHook(Thread {
            onKilled(gateway)
        })

        val commandListener = CommandListener(gateway) {
            +RequestConvertingCommand()
            +HelpCommand()
            +AnnounceCommand(ownerIDs, +eventChannelCommand)
            +RequestBackConvertCommand()
        }.apply { register() }

        gateway.onDisconnect().block()
    }

    fun onKilled(gateway: GatewayDiscordClient) {
        val announcingDocRef = db.collection("guild-channel-pair").document("announcing")

        val data = eventChannelCommand.announceChannelMap
        announcingDocRef.set(data as Map<String, Any>)

        println("This bot are disconnected! [${Instant.now()}]")
    }

    override fun run() {
        runDiscordBot()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SkollobleTesterBot(args[0],args[1].split("|")).start()
        }
    }
}
