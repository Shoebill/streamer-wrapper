package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.data.Radius
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.streamer.Functions
import java.util.*

/**
 * Created by marvin on 30.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
class DynamicRaceCheckpoint(id: Int, val type: RaceCheckpointType, location: Radius, val next: Vector3D,
                            player: Player?, streamDistance: Float, dynamicArea: DynamicArea?, priority: Int) :
        DynamicCheckpoint(id, location, player, streamDistance, dynamicArea, priority) {

    override fun destroy() {
        if (isDestroyed) return

        Functions.destroyDynamicRaceCp(this)
        objects.remove(this)
        id = -1
    }

    override fun isDestroyed(): Boolean =
            Functions.isValidDynamicRaceCp(this)

    companion object {
        @JvmField val DEFAULT_STREAM_DISTANCE = 200f // Corresponds to STREAMER_RACE_CP_SD in streamer.inc

        private var objects = mutableListOf<DynamicRaceCheckpoint>()

        @JvmStatic
        fun get(): Set<DynamicRaceCheckpoint> = HashSet(objects)

        @JvmStatic
        operator fun get(id: Int): DynamicRaceCheckpoint? = objects.find { it.id == id }

        @JvmStatic
        @JvmOverloads
        fun create(type: RaceCheckpointType, location: Radius, next: Vector3D,
                   player: Player? = null, streamDistance: Float = DEFAULT_STREAM_DISTANCE,
                   area: DynamicArea? = null, priority: Int = 0): DynamicRaceCheckpoint {
            val checkpoint = Functions.createDynamicRaceCp(type, location, next, player,
                    streamDistance, area, priority)
            objects.add(checkpoint)
            return checkpoint
        }
    }
}

enum class RaceCheckpointType constructor(val value: Int) {
    NORMAL(0),
    FINISH(1),
    NOTHING(2),
    AIR_NORMAL(3),
    AIR_FINISH(4)
}