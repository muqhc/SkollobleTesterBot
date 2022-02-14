package io.github.muqhc.skblebot.action.button

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.interaction.ButtonInteractionEvent
import discord4j.core.spec.MessageCreateSpec

class ContentAndTextFileDMButton(gateway: GatewayDiscordClient, val content: String, val filename: String, val text: String) : AbstractButton(gateway,"Send it DM") {
    override val lifeSeconds: Long = 3600L

    override fun onInteract(event: ButtonInteractionEvent) {
        event.interaction.user.privateChannel.block().createMessage(
            MessageCreateSpec.builder()
            .content(content)
            .addFile(filename, text.byteInputStream())
            .build()
        ).subscribe()
    }
}