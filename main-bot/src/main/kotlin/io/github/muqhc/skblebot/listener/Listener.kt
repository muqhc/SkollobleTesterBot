package io.github.muqhc.skblebot.listener

import discord4j.core.GatewayDiscordClient

interface Listener {
    val gateway: GatewayDiscordClient

    fun register()
}