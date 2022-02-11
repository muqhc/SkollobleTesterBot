package io.github.muqhc.skblebot.command

import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.MessageCreateSpec
import io.github.muqhc.skollobletoxml.skollobleToXml
import java.io.StringBufferInputStream

class RequestConvertingCommand: ICommand {
    val regex = "^ *; *(skolloble|skble)( +(xml|html))? *;(.|\n)*$".toRegex()
    val regexHtml = "^ *; *(skolloble|skble) +html *;(.|\n)*$".toRegex()

    override fun checkRequired(event: MessageCreateEvent): Boolean = regex.matches(event.message.content)

    fun sendGenerated(event: MessageCreateEvent) {
        val message = event.message
        var fileFormat = "xml"
        val xml = """
            <!-- $fileFormat generated by SkollobleTesterBot at ${message.timestamp} -->
            ${skollobleToXml(message.content)}
            <!--------ORIGINAL-------->
            ; author: ${message.author.get().username} ;
            ${message.content}
            <------------------------->
        """.trimIndent()
        if (message.content.matches(regexHtml)) fileFormat = "html"
        listOf(
            message.author.get().privateChannel.block(),
            message.channel.block()
        ).forEach {
            it?.createMessage(
            MessageCreateSpec.builder()
                .content("<@${message.author.get().userData.id()}> Here Result!")
                .addFile(
                    "${message.author.get().username}_${message.timestamp}.$fileFormat",
                    StringBufferInputStream(xml)
                )
                .build()
        )?.subscribe()
        }
    }

    fun deleteMessage(event: MessageCreateEvent) {
        event.message.delete().subscribe()
    }

    override fun handle(event: MessageCreateEvent) {
        sendGenerated(event)
        deleteMessage(event)
    }
}