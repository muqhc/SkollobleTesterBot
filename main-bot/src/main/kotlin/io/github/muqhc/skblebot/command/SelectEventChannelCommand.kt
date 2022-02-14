package io.github.muqhc.skblebot.command

import discord4j.common.util.Snowflake
import discord4j.core.`object`.component.ActionComponent
import discord4j.core.`object`.component.ActionRow
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.event.domain.interaction.ComponentInteractionEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.MessageCreateSpec
import io.github.muqhc.skblebot.action.IActionComponentManager
import io.github.muqhc.skblebot.action.menu.ChannelMenu

class SelectEventChannelCommand() : AbstractCommand() {
    val announceChannelMap: MutableMap<String,String> = mutableMapOf()
    override fun checkRequired(event: MessageCreateEvent): Boolean = Regex(" *; *dashboard *; *").matches(event.message.content)
    override fun handle(event: MessageCreateEvent) {
        val menu = ChannelMenu(gateway,this,event.guild)
        val spec = MessageCreateSpec.builder()
            .content("Select Channel you want get announce")
            .addComponent(ActionRow.of(menu.component))
            .build()
        menu.publish(event.message.channel,spec)
    }
}