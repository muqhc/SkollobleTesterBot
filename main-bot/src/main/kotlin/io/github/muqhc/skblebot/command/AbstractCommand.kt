package io.github.muqhc.skblebot.command

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent

abstract class AbstractCommand {
    lateinit var gateway: GatewayDiscordClient
    abstract fun checkRequired(event: MessageCreateEvent): Boolean
    abstract fun handle(event: MessageCreateEvent)
}