package io.github.muqhc.skblebot.action.button

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.component.Button
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.event.domain.interaction.ButtonInteractionEvent
import discord4j.core.spec.MessageCreateSpec
import io.github.muqhc.skblebot.action.IActionComponentManager
import reactor.core.publisher.Mono
import java.time.Instant


abstract class AbstractButton(override val gateway: GatewayDiscordClient, val label: String):
    IActionComponentManager<Button, ButtonInteractionEvent> {
    override val id: String = this.hashCode().toString()
    override val component: Button = Button.primary(id,label)
    override val createdTime = Instant.now().epochSecond

    override val lifeSeconds: Long = 172800L

    override val isTempCompo: Boolean = true


    override fun publish(channelMono: Mono<MessageChannel>, messageSpec: MessageCreateSpec) =
        publishRaw(channelMono,messageSpec,ButtonInteractionEvent::class.java)
}