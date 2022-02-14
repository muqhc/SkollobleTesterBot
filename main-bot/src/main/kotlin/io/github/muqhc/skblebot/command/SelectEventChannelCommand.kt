package io.github.muqhc.skblebot.command

import discord4j.core.`object`.component.ActionRow
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.MessageCreateSpec
import io.github.muqhc.skblebot.action.menu.SelectEventChannelMenu

class SelectEventChannelCommand() : AbstractCommand() {
    val announceChannelMap: MutableMap<String,String> = mutableMapOf()
    override fun checkRequired(event: MessageCreateEvent): Boolean = Regex(" *; *channel +event *; *").matches(event.message.content)
    override fun handle(event: MessageCreateEvent) {
        val menu = SelectEventChannelMenu(gateway,this,event.guild)
        val spec = MessageCreateSpec.builder()
            .content("Select Channel you want to receive announce")
            .addComponent(ActionRow.of(menu.component))
            .build()
        menu.publish(event.message.channel,spec)
    }
}