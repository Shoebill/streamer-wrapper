package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.entities.Destroyable
import net.gtaun.shoebill.entities.Player
import net.gtaun.shoebill.data.Radius
import net.gtaun.shoebill.streamer.Functions
import java.util.*

/**
 * Created by marvin on 29.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
open class DynamicCheckpoint internal constructor(id: Int, val location: Radius, val player: Player?,
                                                  val streamDistance: Float, val dynamicArea: DynamicArea?,
                                                  val priority: Int) : Destroyable {

    var id: Int = id
        internal set
        get

    override fun destroy() {
        if (isDestroyed) return

        Functions.destroyDynamicCp(this)
        objects.remove(this)
        id = -1
    }

    override val isDestroyed: Boolean
        get() = !Functions.isValidDynamicCp(this)

    companion object {

        @JvmField val DEFAULT_STREAM_DISTANCE = 200f // Corresponds to STREAMER_CP_SD in streamer.inc

        private var objects = mutableListOf<DynamicCheckpoint>()

        @JvmStatic
        fun get(): Set<DynamicCheckpoint> = HashSet(objects)

        @JvmStatic
        operator fun get(id: Int): DynamicCheckpoint? = objects.find { it.id == id }

        @JvmStatic
        @JvmOverloads
        fun create(location: Radius, player: Player? = null, streamDistance: Float = DEFAULT_STREAM_DISTANCE,
                   area: DynamicArea? = null, priority: Int = 0): DynamicCheckpoint {
            val playerId = player?.id ?: -1
            val areaId = area?.id ?: -1
            val checkpoint = Functions.createDynamicCp(location, playerId, streamDistance, areaId, priority)
            objects.add(checkpoint)
            return checkpoint
        }

    }

}