package io.github.muqhc.skblebot

import discord4j.core.DiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import io.github.muqhc.skblebot.command.HelpCommand
import io.github.muqhc.skblebot.command.RequestConvertingCommand
import io.github.muqhc.skblebot.listener.CommandListener


class SkollobleTesterBot(val token: String): Thread() {

    fun runDiscordBot() {
        val client = DiscordClient.create(token)
        val gateway = client.login().block()

        val commandListener = CommandListener {
            +RequestConvertingCommand()
            +HelpCommand()
        }

        gateway!!
            .on(MessageCreateEvent::class.java)
            .subscribe(commandListener::handle)

        gateway.onDisconnect().block()
    }

    override fun run() {
        runDiscordBot()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SkollobleTesterBot(args[0]).start()
        }
    }
}