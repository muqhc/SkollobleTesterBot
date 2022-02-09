package io.github.muqhc.skblebot.command

import discord4j.core.event.domain.message.MessageCreateEvent

interface ICommand {
    fun checkRequired(event: MessageCreateEvent): Boolean
    fun handle(event: MessageCreateEvent)
}