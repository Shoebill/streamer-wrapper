package net.gtaun.shoebill.streamer

import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.resource.Plugin
import net.gtaun.shoebill.streamer.data.StreamerType

/**
 * @author Marvin Haschker
 */
class Streamer : Plugin() {

    companion object {
        lateinit private var instance: Streamer

        @JvmStatic
        fun get() = instance
    }

    @Throws(Throwable::class)
    override fun onEnable() {
        instance = this
        Functions.registerHandlers(eventManager)
        Callbacks.registerHandlers(eventManager)
    }

    @Throws(Throwable::class)
    override fun onDisable() {
        Functions.unregisterHandlers()
        Callbacks.unregisterHandlers()
    }

    @JvmOverloads
    fun update(player: Player, streamerType: StreamerType = StreamerType.ALL) =
            Functions.update(player, streamerType)

    @JvmOverloads
    fun updateEx(player: Player, location: Location, streamerType: StreamerType = StreamerType.ALL,
                               compensatedTime: Int = -1) =
            Functions.updateEx(player, location.x, location.y, location.z, location.worldId, location.interiorId,
                    streamerType, compensatedTime)

}
