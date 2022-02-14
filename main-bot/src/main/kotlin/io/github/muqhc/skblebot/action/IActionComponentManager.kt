package io.github.muqhc.skblebot.action

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.component.ActionComponent
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.event.domain.interaction.ComponentInteractionEvent
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono
import discord4j.core.spec.MessageCreateSpec
import io.netty.handler.timeout.TimeoutException
import reactor.core.publisher.Mono
import java.time.Duration

interface IActionComponentManager<ComponentType : ActionComponent,EventType : ComponentInteractionEvent> {
    val gateway: GatewayDiscordClient

    val id: String
    val component: ComponentType
    val createdTime: Long

    val lifeSeconds: Long

    val isTempCompo: Boolean

    fun publishRaw(channelMono: Mono<MessageChannel>, messageSpec: MessageCreateSpec, eventTypeClass: Class<EventType>) {
        val listener = gateway.on(eventTypeClass) { event ->
            if (check(event)) onPreviousInteract(event)
            else Mono.empty()
        }.run { if (isTempCompo) timeout(Duration.ofSeconds(lifeSeconds)) else this }
            .onErrorResume(TimeoutException::class.java) { Mono.empty() }.then()

        channelMono.flatMap { channel ->
            return@flatMap channel.createMessage(messageSpec).then(listener)
        }.subscribe()
    }

    fun publish(channelMono: Mono<MessageChannel>, messageSpec: MessageCreateSpec)

    fun check(event: EventType): Boolean {
        return id == event.customId
    }

    fun onPreviousInteract(event: EventType): InteractionApplicationCommandCallbackReplyMono =
        event.reply().withEphemeral(runCatching { onInteract(event) }.onFailure { throw it }.isSuccess)

    fun onInteract(event: EventType) {}
}