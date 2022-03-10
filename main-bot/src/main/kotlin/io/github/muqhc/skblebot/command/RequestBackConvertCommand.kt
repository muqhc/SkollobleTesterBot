package io.github.muqhc.skblebot.command

import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.MessageCreateFields
import discord4j.core.spec.MessageCreateSpec
import java.net.URL

class RequestBackConvertCommand: AbstractCommand() {
    override fun checkRequired(event: MessageCreateEvent): Boolean =
        event.message.content.matches(Regex("; *xml +to +(skolloble|skble) *; *"))

    override fun handle(event: MessageCreateEvent) {
        val message = event.message
        val skolloble = message.attachments.find { it.filename.matches(Regex("$.[.](xml|html)^")) }!!.url.let {
            URL(it).openStream().readAllBytes().inputStream()
        }
        message.channel.block().createMessage(MessageCreateSpec.builder()
            .content("<@${message.author.get().userData.id()}> Here Result!")
            .addFile(MessageCreateFields.File.of("${message.author.get().username}_${message.timestamp}.skolloble.txt",skolloble))
            .addFile(MessageCreateFields.File.of("${message.author.get().username}_${message.timestamp}.skolloble",skolloble))
            .build())
    }
}
