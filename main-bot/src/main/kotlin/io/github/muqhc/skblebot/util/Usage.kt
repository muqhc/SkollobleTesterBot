package io.github.muqhc.skblebot.util

object Usage {
    val data = mapOf(
        Regex("^(en|english|eng|en-US)$") to "Usage =>> https://discord.gg/yj9YKDyw",
        Regex("^(ko|korean|kor)$") to "도움말 =>> https://discord.gg/VNzuY5KT"
    )
    operator fun get(key: String): String? = data.run {
        var result: String? = null
        forEach { (regex, value) ->
            if (regex.matches(key)) result = value
        }
        return@run result
    }
}