package net.gtaun.shoebill.streamer

import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.resource.Plugin
import net.gtaun.shoebill.streamer.data.StreamerType
import net.gtaun.util.event.EventManager


/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
class Streamer : Plugin() {

    @Throws(Throwable::class)
    override fun onEnable() {
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
