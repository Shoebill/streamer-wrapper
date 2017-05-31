package net.gtaun.shoebill.streamer

import net.gtaun.shoebill.entities.Player
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.resource.Plugin
import net.gtaun.shoebill.streamer.autoupdate.StreamerUpdateService
import net.gtaun.shoebill.streamer.data.StreamerType

/**
 * @author Marvin Haschker
 */
class Streamer : Plugin() {

    var tickRate: Int
        get() = Functions.getTickRate()
        set(value) = Functions.setTickRate(value)

    var cellDistance: Float?
        get() = Functions.getCellDistance()
        set(value) {
            if (value != null) Functions.setCellDistance(value)
        }

    var cellSize: Float?
        get() = Functions.getCellSize()
        set(value) {
            if (value != null) Functions.setCellSize(value)
        }

    var isToggleErrorCallback: Boolean
        get() = Functions.isToggleErrorCallback()
        set(value) = Functions.toggleErrorCallback(value)

    @Throws(Throwable::class)
    override fun onEnable() {
        instance = this
        StreamerUpdateService.autoInstall()
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

    @JvmOverloads
    fun getChunckTickRate(type: StreamerType, player: Player? = null) =
            Functions.getChunkTickRate(type, player)

    @JvmOverloads
    fun setChunckTickRate(type: StreamerType, rate: Int, player: Player? = null) =
            Functions.setChunkTickRate(type, rate, player)

    fun getChunckSize(type: StreamerType) =
            Functions.getChunkSize(type)

    fun setChunkSize(type: StreamerType, size: Int) =
            Functions.setChunkSize(type, size)

    fun getMaxItems(type: StreamerType) =
            Functions.getMaxItems(type)

    fun setMaxItems(type: StreamerType, items: Int) =
            Functions.setMaxItems(type, items)

    @JvmOverloads
    fun getVisibleItems(type: StreamerType, player: Player? = null) =
            Functions.getVisibleItems(type, player)

    @JvmOverloads
    fun setVisibleItems(type: StreamerType, amount: Int, player: Player? = null) =
            Functions.setVisibleItems(type, amount, player)

    @JvmOverloads
    fun getRadiusMultiplier(type: StreamerType, player: Player? = null): Float? =
            Functions.getRadiusMultiplier(type, player)

    @JvmOverloads
    fun setRadiusMultiplier(type: StreamerType, multiplier: Float, player: Player? = null) =
            Functions.setRadiusMultiplier(type, multiplier, player)

    fun toggleItemStatic(type: StreamerType, id: Int, toggle: Boolean) =
            Functions.toggleItemStatic(type, id, toggle)

    fun isToggleItemStatic(type: StreamerType, id: Int) =
            Functions.isToggleItemStatic(type, id)

    fun toggleItemInvArea(type: StreamerType, id: Int, toggle: Boolean) =
            Functions.toggleItemInvAreas(type, id, toggle)

    fun isToggleItemInvArea(type: StreamerType, id: Int) =
            Functions.isToggleItemInvAreas(type, id)

    fun toggleItemCallbacks(type: StreamerType, id: Int, toggle: Boolean) =
            Functions.toggleItemCallbacks(type, id, toggle)

    fun isToggleItemCallbacks(type: StreamerType, id: Int) =
            Functions.isToggleItemCallbacks(type, id)

    companion object {
        lateinit private var instance: Streamer

        @JvmStatic
        fun get() = instance
    }
}
