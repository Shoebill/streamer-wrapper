package net.gtaun.shoebill.streamer

import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.resource.Plugin
import net.gtaun.shoebill.streamer.data.StreamerType


/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
class Streamer : Plugin() {

    internal companion object {
        private var instance: Streamer? = null

        @JvmStatic
        fun get(): Streamer {
            return instance!!
        }
    }

    @Throws(Throwable::class)
    override fun onEnable() {
        instance = this
        val eventManager = eventManager
        Functions.registerHandlers(eventManager)
        Callbacks.registerHandlers(eventManager)
    }

    @Throws(Throwable::class)
    override fun onDisable() {
        Functions.unregisterHandlers()
        Callbacks.unregisterHandlers()
    }

    @JvmOverloads fun update(player: Player, streamerType: StreamerType = StreamerType.ALL) {
        Functions.update(player, streamerType)
    }

    @JvmOverloads fun updateEx(player: Player, location: Location, streamerType: StreamerType = StreamerType.ALL) {
        Functions.updateEx(player, location.x, location.y, location.z, location.worldId, location.interiorId, streamerType)
    }
}
