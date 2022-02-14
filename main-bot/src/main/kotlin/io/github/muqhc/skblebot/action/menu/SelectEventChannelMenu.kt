package io.github.muqhc.skblebot.action.menu

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.component.SelectMenu
import discord4j.core.`object`.entity.Guild
import discord4j.core.`object`.entity.channel.Channel
import discord4j.core.event.domain.interaction.SelectMenuInteractionEvent
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono
import io.github.muqhc.skblebot.command.SelectEventChannelCommand
import reactor.core.publisher.Mono

class SelectEventChannelMenu(gateway: GatewayDiscordClient, val command: SelectEventChannelCommand, val guild: Mono<Guild>): AbstractMenu(gateway) {
    override val lifeSeconds: Long = 7200L
    override val component: SelectMenu = SelectMenu.of(
        id,guild.block().channels.buffer().blockFirst()
            .filter { when (it.type) {
                Channel.Type.GUILD_TEXT -> true
                Channel.Type.DM -> true
                Channel.Type.GROUP_DM -> true
                else -> false
            } }
            .map { channel -> channel.name pair channel.id.asString() }
    ).withPlaceholder("Select a channel want receive announce...")

    override fun onPreviousInteract(event: SelectMenuInteractionEvent): InteractionApplicationCommandCallbackReplyMono {
        command.announceChannelMap[guild.block().id.asString()] = event.values[0]
        return event.reply("${event.interaction.user.username} selected <#${event.values[0]}> !").withEphemeral(true)
    }
}