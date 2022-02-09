package io.github.muqhc.skblebot

import discord4j.core.DiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import io.github.muqhc.skblebot.command.RequestConvertingCommand
import io.github.muqhc.skblebot.listener.CommandListener


class SkollobleTesterBot {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val token = args[0]
            val client = DiscordClient.create(token)
            val gateway = client.login().block()

            val commandListener = CommandListener {
                +RequestConvertingCommand()
            }

            gateway!!
                .on(MessageCreateEvent::class.java)
                .subscribe(commandListener::handle)

            gateway.onDisconnect().block()
        }
    }
}