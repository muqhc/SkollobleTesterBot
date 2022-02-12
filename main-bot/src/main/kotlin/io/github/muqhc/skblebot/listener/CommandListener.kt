package io.github.muqhc.skblebot.listener

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import io.github.muqhc.skblebot.command.AbstractCommand


class CommandListener(val gateway: GatewayDiscordClient) {

    constructor(gateway: GatewayDiscordClient, init: CommandListener.() -> Unit) : this(gateway) { init() }

    val commands: MutableList<AbstractCommand> = mutableListOf()

    operator fun AbstractCommand.unaryPlus() = also {
        it.gateway = this@CommandListener.gateway
        commands.add(it)
    }

    fun handle(event: MessageCreateEvent) {
        commands.forEach {
            if (it.checkRequired(event)) it.handle(event)
        }
    }

}