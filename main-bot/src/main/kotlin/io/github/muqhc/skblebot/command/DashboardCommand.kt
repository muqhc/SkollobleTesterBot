package io.github.muqhc.skblebot.command

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.component.ActionComponent
import discord4j.core.`object`.component.ActionRow
import discord4j.core.event.domain.interaction.ComponentInteractionEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.MessageCreateSpec
import io.github.muqhc.skblebot.interfaces.IActionComponentManager

class DashboardCommand() : AbstractCommand() {
    val components: MutableList<IActionComponentManager<ActionComponent,ComponentInteractionEvent>> = mutableListOf()
    override fun checkRequired(event: MessageCreateEvent): Boolean = Regex(" *; *dashboard *; *").matches(event.message.content)
    override fun handle(event: MessageCreateEvent) {
        MessageCreateSpec.create().withComponents(
            components.map { ActionRow.of(it.component) }
        )
    }
}