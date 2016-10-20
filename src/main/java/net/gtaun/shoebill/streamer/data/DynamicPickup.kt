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
class DynamicPickup(id: Int, val modelid: Int, val type: Int, val player: Player?, val streamDistance: Float) :
        Destroyable {

    var id: Int = id
        private set

    private var pickupHandlers: MutableList<HandlerEntry> = mutableListOf()

    override fun destroy() {
        if (this.isDestroyed)
            return

        Functions.destroyDynamicPickup(this.id)
        pickupHandlers.forEach { it.cancel() }
        pickupHandlers.clear()
        this.id = -1

        removeSelf()
    }

    private fun removeSelf() = pickups.remove(this)

    fun onPlayerPickup(handler: (PlayerPickUpDynamicPickupEvent) -> Unit): HandlerEntry =
            onPlayerPickup(EventHandler { handler(it) })

    fun onPlayerPickup(handler: EventHandler<PlayerPickUpDynamicPickupEvent>): HandlerEntry {
        val entry = eventManagerNode.registerHandler(PlayerPickUpDynamicPickupEvent::class.java,
                HandlerPriority.NORMAL, Attentions.create().`object`(this), handler)
        pickupHandlers.add(entry)
        return entry
    }


    override fun isDestroyed(): Boolean = !Functions.isValidDynamicPickup(this.id)

    companion object {

        @JvmField
        val DEFAULT_STREAM_DISTANCE = 200f // Corresponds to STREAMER_PICKUP_SD in streamer.inc

        private var pickups = mutableListOf<DynamicPickup>()
        private val eventManagerNode = Streamer.get().eventManager.createChildNode()

        @JvmStatic
        fun get(): Set<DynamicPickup> = HashSet(pickups)

        @JvmStatic
        operator fun get(id: Int): DynamicPickup? = pickups.find { it.id == id }

        @JvmOverloads
        @JvmStatic
        fun create(modelId: Int, type: Int, location: Location,
                   streamDistance: Float = DynamicPickup.DEFAULT_STREAM_DISTANCE, priority: Int = 0,
                   player: Player? = null, area: DynamicArea? = null,
                   pickupHandler: EventHandler<PlayerPickUpDynamicPickupEvent>? = null): DynamicPickup {

            val playerId = if (player == null) -1 else player.id
            val areaId = if (area == null) -1 else area.id

            val pickup = Functions.createDynamicPickup(modelId, type, location, playerId,
                    streamDistance, areaId, priority)

            if (pickupHandler != null)
                pickup.onPlayerPickup(pickupHandler)

            pickups.add(pickup)
            return pickup
        }
    }
}
