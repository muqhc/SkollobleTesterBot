package io.github.muqhc.skblebot.command

import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.MessageCreateFields
import discord4j.core.spec.MessageCreateSpec
import java.net.URL

class RequestBackConvertCommand: AbstractCommand() {
    override fun checkRequired(event: MessageCreateEvent): Boolean =
        event.message.content.matches(Regex("; *xml +to +(skolloble|skble) *; *"))

    override fun handle(event: MessageCreateEvent) {
        val skolloble = event.message.attachments.find { it.filename.matches(Regex("$.[.](xml|html)^")) }!!.url.let {
            URL(it).openStream().readAllBytes().inputStream()
        }
        event.message.channel.block().createMessage(MessageCreateSpec.builder()
            .addFile(MessageCreateFields.File.of("a",skolloble))
            .build())
    }
}