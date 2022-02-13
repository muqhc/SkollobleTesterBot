package io.github.muqhc.skblebot.button

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.component.Button
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.event.domain.interaction.ButtonInteractionEvent
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono
import discord4j.core.spec.MessageCreateSpec
import io.github.muqhc.skblebot.interfaces.IActionComponentManager
import io.netty.handler.timeout.TimeoutException
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant


abstract class AbstractButton(override val gateway: GatewayDiscordClient, val label: String): IActionComponentManager<Button,ButtonInteractionEvent> {
    override val id: String = this.hashCode().toString()
    override val component: Button = Button.primary(id,label)
    override val createdTime = Instant.now().epochSecond

    override val lifeSeconds: Long = 172800L

    override val isTempButton: Boolean = true


    override fun register(channelMono: Mono<MessageChannel>, messageSpec: MessageCreateSpec) =
        registerRaw(channelMono,messageSpec,ButtonInteractionEvent::class.java)
}