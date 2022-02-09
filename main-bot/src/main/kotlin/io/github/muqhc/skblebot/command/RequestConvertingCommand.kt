package io.github.muqhc.skblebot.command

import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.MessageCreateSpec
import io.github.muqhc.skollobletoxml.skollobleToXml
import java.io.StringBufferInputStream

class RequestConvertingCommand: ICommand {
    val regex = "^ *; *(skolloble|skble)( +(xml|html))? *;(.|\n)*$".toRegex()
    val regexHtml = "^ *; *(skolloble|skble) +html *;(.|\n)*$".toRegex()

    override fun checkRequired(event: MessageCreateEvent): Boolean = regex.matches(event.message.content)

    override fun handle(event: MessageCreateEvent) {
        val message = event.message
        val skble = skollobleToXml(message.content)
        var fileFormat = "xml"
        if (message.content.matches(regexHtml)) fileFormat = "html"
        message.channel.block()?.createMessage(
            MessageCreateSpec.builder()
                .content("Here Result!")
                .addFile("result.$fileFormat",StringBufferInputStream(skble))
                .build()
        )?.subscribe()
    }
}