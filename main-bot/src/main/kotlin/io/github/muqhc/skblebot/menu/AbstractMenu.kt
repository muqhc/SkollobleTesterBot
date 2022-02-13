package io.github.muqhc.skblebot.menu

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.component.Button
import discord4j.core.`object`.component.SelectMenu
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.event.domain.interaction.ButtonInteractionEvent
import discord4j.core.event.domain.interaction.SelectMenuInteractionEvent
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono
import discord4j.core.spec.MessageCreateSpec
import io.github.muqhc.skblebot.interfaces.IActionComponentManager
import io.netty.handler.timeout.TimeoutException
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant

abstract class AbstractMenu(override val gateway: GatewayDiscordClient): IActionComponentManager<SelectMenu, SelectMenuInteractionEvent> {
    override val id: String = this.hashCode().toString()
    override val component: SelectMenu = SelectMenu.of(id)
    override val createdTime = Instant.now().epochSecond

    constructor(gateway: GatewayDiscordClient,init: AbstractMenu.() -> Unit): this(gateway) { init() }

    infix fun String.pair(other: String): SelectMenu.Option = SelectMenu.Option.of(this,other).also { component.options.add(it) }

    override val lifeSeconds: Long = 172800L

    override val isTempButton: Boolean = true

    override fun register(channelMono: Mono<MessageChannel>, messageSpec: MessageCreateSpec) =
        registerRaw(channelMono,messageSpec,SelectMenuInteractionEvent::class.java)
}