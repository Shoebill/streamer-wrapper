package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.entities.Destroyable
import net.gtaun.shoebill.entities.Player
import net.gtaun.shoebill.entities.Vehicle
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.Functions
import net.gtaun.shoebill.streamer.Streamer
import net.gtaun.shoebill.streamer.event.area.PlayerEnterDynamicAreaEvent
import net.gtaun.shoebill.streamer.event.area.PlayerLeaveDynamicAreaEvent
import net.gtaun.util.event.Attentions
import net.gtaun.util.event.EventHandler
import net.gtaun.util.event.HandlerEntry
import net.gtaun.util.event.HandlerPriority
import java.util.*

/**
 * Created by Valeriy on 18/4/2016 in project streamer-wrapper.
 * Copyright (c) 2016 Valeriy. All rights reserved.
 */
@AllOpen
abstract class DynamicArea internal constructor(id: Int, val player: Player?) : Destroyable {

    final var id: Int = id
        get
        private set

    fun isPlayerInRange(player: Player): Boolean =
            Functions.isPlayerInDynamicArea(player.id, this)

    fun isPointInRange(point: Vector3D): Boolean =
            Functions.isPointInDynamicArea(this, point)

    fun attachToPlayer(player: Player, offset: Vector3D) =
            Functions.attachDynamicAreaToPlayer(this, player, offset)

    @JvmOverloads
    fun attachToObject(obj: DynamicObject, objectType: StreamerObjectType = StreamerObjectType.DYNAMIC,
                       forPlayer: Player ? = null, offset: Vector3D) =
            Functions.attachDynamicAreaToObject(this, obj, objectType, forPlayer?.id ?: 0xFFFF, offset)

    fun attachToVehicle(vehicle: Vehicle, offset: Vector3D) =
            Functions.attachDynamicAreaToVehicle(this, vehicle, offset)

    override fun destroy() {
        if (isDestroyed)
            return

        Functions.destroyDynamicArea(this)
        id = -1
        eventHandlers.forEach { it.cancel() }
        eventHandlers.clear()
        selfRemove()
    }

    private var eventHandlers: MutableList<HandlerEntry> = mutableListOf()

    fun playerEnterArea(handler: EventHandler<PlayerEnterDynamicAreaEvent>): HandlerEntry {
        val entry = eventManagerNode.registerHandler(PlayerEnterDynamicAreaEvent::class.java, handler, HandlerPriority.NORMAL,
                Attentions.create().`object`(this))
        eventHandlers.add(entry)
        return entry
    }

    fun playerEnterArea(handler: (PlayerEnterDynamicAreaEvent) -> Unit): HandlerEntry =
            playerEnterArea(EventHandler { handler(it) })

    fun playerLeaveArea(handler: EventHandler<PlayerLeaveDynamicAreaEvent>): HandlerEntry {
        val entry = eventManagerNode.registerHandler(PlayerLeaveDynamicAreaEvent::class.java, handler, HandlerPriority.NORMAL,
                Attentions.create().`object`(this))
        eventHandlers.add(entry)
        return entry
    }

    fun playerLeaveArea(handler: (PlayerLeaveDynamicAreaEvent) -> Unit): HandlerEntry =
            playerLeaveArea(EventHandler { handler(it) })

    private fun selfRemove() = areas.remove(this)

    override val isDestroyed: Boolean
        get() = !Functions.isValidDynamicArea(this)

    companion object {
        @JvmStatic
        protected var areas = mutableListOf<DynamicArea>()

        @JvmStatic
        fun get(): Set<DynamicArea> = HashSet(areas)

        private val eventManagerNode = Streamer.get().eventManager

        @JvmStatic
        operator fun get(id: Int): DynamicArea? = areas.find { it.id == id }
    }
}
