package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.`object`.Destroyable
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.streamer.Functions
import net.gtaun.shoebill.streamer.Streamer
import net.gtaun.shoebill.streamer.event.PlayerPickUpDynamicPickupEvent
import net.gtaun.util.event.Attentions
import net.gtaun.util.event.EventHandler
import net.gtaun.util.event.HandlerEntry
import net.gtaun.util.event.HandlerPriority
import java.util.*

/**
 * Created by valych & marvin on 11.01.2016 in project streamer-wrapper in project streamer-wrapper.
 * Copyright (c) 2016 valych & Marvin Haschker. All rights reserved.
 */
class DynamicPickup(id: Int, val modelid: Int, val type: Int, val player: Player?, val streamDistance: Float) : Destroyable {

    var id: Int = id
        private set

    private var pickupHandlerEntry: HandlerEntry? = null
    var pickupHandler: EventHandler<PlayerPickUpDynamicPickupEvent>? = null

    override fun destroy() {
        if (this.isDestroyed) {
            removeSelf()
            return
        }

        Functions.destroyDynamicPickup(this.id)
        this.id = -1
        pickupHandlerEntry?.cancel()

        removeSelf()
    }

    private fun removeSelf() {
        pickups.remove(this)
    }

    override fun isDestroyed(): Boolean {
        return !Functions.isValidDynamicPickup(this.id)
    }

    companion object {

        @JvmField
        val DEFAULT_STREAM_DISTANCE = 200f // Corresponds to STREAMER_PICKUP_SD in streamer.inc

        private var pickups = mutableListOf<DynamicPickup>()

        @JvmStatic fun get(): Set<DynamicPickup> = HashSet(pickups)

        @JvmStatic operator fun get(id: Int): DynamicPickup? = pickups.find { it.id == id }

        @JvmOverloads
        @JvmStatic
        fun create(modelId: Int, type: Int, location: Location,
                   streamDistance: Float = DynamicPickup.DEFAULT_STREAM_DISTANCE,
                   pickupHandler: EventHandler<PlayerPickUpDynamicPickupEvent>? = null, priority: Int = 0,
                   player: Player? = null, area: DynamicArea? = null): DynamicPickup {

            val playerId = if (player == null) -1 else player.id
            val areaId = if (area == null) -1 else area.id
            val eventManager = Streamer.get().eventManager

            val pickup = Functions.createDynamicPickup(modelId, type, location, playerId, streamDistance, areaId, priority)
            if (pickupHandler != null) {
                pickup.pickupHandlerEntry = eventManager.registerHandler(PlayerPickUpDynamicPickupEvent::class.java,
                        HandlerPriority.NORMAL, Attentions.create().`object`(pickup), { pickup.pickupHandler?.handleEvent(it) })
                pickup.pickupHandler = pickupHandler
            }
            pickups.add(pickup)
            return pickup
        }
    }
}
