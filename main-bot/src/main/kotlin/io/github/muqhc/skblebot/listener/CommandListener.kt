package io.github.muqhc.skblebot.listener

import discord4j.core.event.domain.message.MessageCreateEvent
import io.github.muqhc.skblebot.command.ICommand


class CommandListener() {

    constructor(init: CommandListener.() -> Unit) : this() { init() }

    val commands: MutableList<ICommand> = mutableListOf()

    operator fun ICommand.unaryPlus() = also(commands::add)

    fun handle(event: MessageCreateEvent) {
        commands.forEach {
            if (it.checkRequired(event)) it.handle(event)
        }
    }

}