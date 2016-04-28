package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.`object`.Destroyable
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.streamer.Functions

import java.util.ArrayList
import java.util.HashSet

/**
 * Created by valych on 11.01.2016 in project streamer-wrapper.
 */
class DynamicPickup(id: Int, val modelid: Int, val type: Int, val player: Player?, val streamDistance: Float) : Destroyable {

    var id: Int = id
        private set

    override fun destroy() {
        if (this.isDestroyed) {
            removeSelf()
            return
        }

        Functions.destroyDynamicPickup(this.id)
        this.id = -1

        removeSelf()
    }

    private fun removeSelf() {
        pickups.remove(this)
    }

    override fun isDestroyed(): Boolean {
        return !Functions.isValidDynamicPickup(this.id)
    }

    internal companion object {

        @JvmField
        val DEFAULT_STREAM_DISTANCE = 200f // Corresponds to STREAMER_PICKUP_SD in streamer.inc

        private var pickups = mutableListOf<DynamicPickup>()

        @JvmStatic fun get(): Set<DynamicPickup> {
            return HashSet(pickups)
        }

        @JvmStatic operator fun get(id: Int): DynamicPickup? {
            return pickups.find { it.id == id }
        }

        @JvmOverloads
        @JvmStatic
        fun create(modelId: Int, type: Int, location: Location,
                   streamDistance: Float = DynamicPickup.DEFAULT_STREAM_DISTANCE, priority: Int = 0,
                   player: Player? = null, area: DynamicArea? = null): DynamicPickup {
            val playerId = if (player == null) -1 else player.id
            val areaId = if (area == null) -1 else area.id

            val pickup = Functions.createDynamicPickup(modelId, type, location, playerId, streamDistance, areaId, priority)
            pickups.add(pickup)
            return pickup
        }
    }
}
