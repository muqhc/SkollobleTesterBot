package io.github.muqhc.skblebot.action.menu

import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.component.SelectMenu
import discord4j.core.`object`.entity.Guild
import discord4j.core.event.domain.interaction.SelectMenuInteractionEvent
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono
import io.github.muqhc.skblebot.command.SelectEventChannelCommand
import reactor.core.publisher.Mono

class ChannelMenu(gateway: GatewayDiscordClient, val command: SelectEventChannelCommand, val guild: Mono<Guild>): AbstractMenu(gateway) {
    override val component: SelectMenu = SelectMenu.of(
        id,guild.block().channels.buffer().blockFirst().map { channel -> channel.name pair channel.id.asString() }
    )

    override fun onPreviousInteract(event: SelectMenuInteractionEvent): InteractionApplicationCommandCallbackReplyMono {
        command.announceChannelMap[guild.block().id] = Snowflake.of(event.values[0])
        return event.reply("${event.interaction.user.username} selected ${event.values}!").withEphemeral(true)
    }
}