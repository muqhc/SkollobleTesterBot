package io.github.muqhc.skblebot.util

object Usage {
    val data = mapOf(
        Regex("^(en|english|eng|en-US)$") to "Usage =>> https://discord.gg/4dfegEQ3k5",
        Regex("^(ko|korean|kor)$") to "도움말 =>> https://discord.gg/eBtQAASJCH"
    )
    operator fun get(key: String): String? = data.run {
        var result: String? = null
        forEach { (regex, value) ->
            if (regex.matches(key)) result = value
        }
        return@run result
    }
}
