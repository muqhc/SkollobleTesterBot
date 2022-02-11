package io.github.muqhc.skblebot.command

import discord4j.core.event.domain.message.MessageCreateEvent
import io.github.muqhc.skblebot.util.Usage

class HelpCommand : ICommand {
    override fun checkRequired(event: MessageCreateEvent): Boolean = Regex("^(.|\n)*; *((help|usage)|(도움말|도움|사용법|헬프)) *;(.|\n)*$").matches(event.message.content)

    val regexHeader = mapOf(
        Regex("^(.|\n)*; *(help|usage) *;(.|\n)*$") to "en",
        Regex("^(.|\n)*; *(도움말|도움|사용법|헬프) *;(.|\n)*$") to "ko"
    )

    override fun handle(event: MessageCreateEvent) {
        event.message.channel.block()?.createMessage(
            "<@${event.message.author.get().userData.id()}>"+Usage[run {
                var result = "en"
                regexHeader.forEach { (key, value) ->
                    if (key.matches(event.message.content)) result = value
                }
                result
            }]
        )?.subscribe()
    }
}