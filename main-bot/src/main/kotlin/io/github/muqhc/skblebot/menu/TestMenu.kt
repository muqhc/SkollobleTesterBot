package io.github.muqhc.skblebot.menu

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.interaction.SelectMenuInteractionEvent
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono

class TestMenu(gateway: GatewayDiscordClient): AbstractMenu(gateway) {
    override fun onPreviousInteract(event: SelectMenuInteractionEvent): InteractionApplicationCommandCallbackReplyMono =
        event.reply("${event.interaction.user.username} selected ${event.values}!").withEphemeral(true)
}