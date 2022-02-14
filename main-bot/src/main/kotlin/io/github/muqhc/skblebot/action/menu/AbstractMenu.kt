package io.github.muqhc.skblebot.action.menu

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.component.SelectMenu
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.event.domain.interaction.SelectMenuInteractionEvent
import discord4j.core.spec.MessageCreateSpec
import io.github.muqhc.skblebot.action.IActionComponentManager
import reactor.core.publisher.Mono
import java.time.Instant

abstract class AbstractMenu(override val gateway: GatewayDiscordClient):
    IActionComponentManager<SelectMenu, SelectMenuInteractionEvent> {
    override val id: String = this.hashCode().toString()
    override val createdTime = Instant.now().epochSecond

    infix fun String.pair(other: String): SelectMenu.Option = SelectMenu.Option.of(this,other)

    override val lifeSeconds: Long = 172800L

    override val isTempCompo: Boolean = true

    override fun publish(channelMono: Mono<MessageChannel>, messageSpec: MessageCreateSpec) =
        publishRaw(channelMono,messageSpec,SelectMenuInteractionEvent::class.java)
}