package io.github.muqhc.skblebot.command

import discord4j.common.util.Snowflake
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.EmbedCreateSpec
import discord4j.core.spec.MessageCreateSpec
import discord4j.rest.util.Color

class AnnounceCommand(val ownerIDs: List<String>, val mappingCommand: SelectEventChannelCommand) : AbstractCommand() {
    val regex = "^ *; *(announce|notice|warning|warn|note|event) *; *\n(.|\n)*$".toRegex()

    fun makeLongSpace(spaceCount: Int): String {
        var result = ""
        for ( i in 0..(spaceCount)) result += "⸻"
        return result
    }

    fun getEmbedTemplate(firstLine: String) = when {
        firstLine.contains("announce")  -> EmbedCreateSpec.builder().color(Color.MAGENTA).title("**!--Announce--!**${makeLongSpace(10)}~")
        firstLine.contains("notice") ->    EmbedCreateSpec.builder().color(Color.RUBY).title("**!--Notice--!**${makeLongSpace(10)}~")
        firstLine.contains("warn") ->      EmbedCreateSpec.builder().color(Color.RED).title("**!--Warning--!**${makeLongSpace(10)}~")
        firstLine.contains("event") ->     EmbedCreateSpec.builder().color(Color.MOON_YELLOW).title("**+++Event+++**${makeLongSpace(10)}~")
        else -> EmbedCreateSpec.builder().color(Color.GRAY).title("**++Note++**${makeLongSpace(10)}~")
    }

    override fun checkRequired(event: MessageCreateEvent): Boolean = event.message.content.matches(regex) &&
            event.message.author.get().userData.id().toString().let(ownerIDs::contains)

    override fun handle(event: MessageCreateEvent) {
        val message = event.message
        val (embed,content) = message.content.split("\n").run { getEmbedTemplate(first()) to drop(1).joinToString("\n") }
        mappingCommand.announceChannelMap.forEach { (guildId, channelId) ->
            gateway.getGuildById(Snowflake.of(guildId)).block()?.getChannelById(Snowflake.of(channelId))?.ofType(MessageChannel::class.java)?.block()
                ?.createMessage(MessageCreateSpec.builder().addEmbed(embed
                    .description("⠀\n$content\n\n\n⠀")
                    .footer(gateway.self.block().username,"https://cdn.discordapp.com/avatars/940828335754334209/efc1bfd6dd576e0728fa80c1a1bf38d3.webp?size=20")
                    .timestamp(message.timestamp)
                    .build()
        ).build())?.subscribe() }
    }
}