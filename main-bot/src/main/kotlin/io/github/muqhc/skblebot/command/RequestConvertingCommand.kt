package io.github.muqhc.skblebot.command

import discord4j.core.`object`.component.ActionRow
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.spec.MessageCreateSpec
import discord4j.discordjson.Id
import io.github.muqhc.skblebot.action.button.ContentAndTextFileDMButton
import io.github.muqhc.skollobletoxml.skollobleToXml
import java.time.Instant

class RequestConvertingCommand(): AbstractCommand() {
    val regex = "^ *; *(skolloble|skble)( +(xml|html))? *; *$".toRegex()
    val regexHtml = "^ *; *(skolloble|skble) +html *; *$".toRegex()

    fun genMessageSpec(id: Id, username: String, timestamp: Instant, xml: String, fileFormat: String) =
        MessageCreateSpec.builder()
            .content("<@${id}> Here Result!")
            .addFile(
                "${username}_${timestamp}.$fileFormat",
                xml.byteInputStream()
            ).build()

    override fun checkRequired(event: MessageCreateEvent): Boolean = try { regex.matches(event.message.content.split("\n")[0]) } catch (e: Exception) {
        println("====match fail====")
        false
    }

    fun sendGenerated(event: MessageCreateEvent) {
        try {
            val message = event.message
            message.content.let(::println)
            var fileFormat = "xml"
            if (message.content.split("\n")[0].matches(regexHtml)) fileFormat = "html"
            val xml =
"""<!-- $fileFormat generated by SkollobleTesterBot at ${message.timestamp} -->
${skollobleToXml(message.content).substring(1)}
<!-- ======ORIGINAL======- -!>
${message.content}
; author: ${message.author.get().username} ;
<- -=======================-->"""

            val channel = message.channel.block()

            val dmSpec = genMessageSpec(
                message.author.get().userData.id(),
                message.author.get().username,
                message.timestamp,
                xml, fileFormat
            )

            val dmButton = ContentAndTextFileDMButton(gateway,
                "<@${message.author.get().userData.id()}> Here Result!",
                "${message.author.get().username}_${message.timestamp}.$fileFormat",
                xml
            )

            val messageSpec = dmSpec.withComponents(ActionRow.of(dmButton.component))

            dmButton.publish(message.channel,messageSpec)
        } catch (e: Exception) {
            println("====Match Fail====")
        }
    }

    override fun handle(event: MessageCreateEvent) {
        sendGenerated(event)
    }
}
