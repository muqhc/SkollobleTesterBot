package io.github.muqhc.skblebot

import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import io.github.muqhc.skblebot.command.AnnounceCommand
import io.github.muqhc.skblebot.command.HelpCommand
import io.github.muqhc.skblebot.command.RequestConvertingCommand
import io.github.muqhc.skblebot.command.SelectEventChannelCommand
import io.github.muqhc.skblebot.listener.CommandListener
import java.time.Instant


class SkollobleTesterBot(val token: String, val ownerIDs: List<String>): Thread() {

    fun runDiscordBot() {

        val client = DiscordClient.create(token)
        val gateway = client.login().block()

        Runtime.getRuntime().addShutdownHook(Thread {
            onKilled(gateway)
        })

        val commandListener = CommandListener(gateway) {
            +RequestConvertingCommand()
            +HelpCommand()
            +AnnounceCommand(ownerIDs, +SelectEventChannelCommand())
        }.apply { register() }

        gateway.onDisconnect().block()
    }

    fun onKilled(gateway: GatewayDiscordClient) {
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