package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.entities.Player
import net.gtaun.shoebill.data.Area3D
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.Functions

/**
 * Created by Valeriy on 18/4/2016 in project streamer-wrapper.
 * Copyright (c) 2016 Valeriy. All rights reserved.
 */
@AllOpen
class DynamicCuboid(id: Int, player: Player?) : DynamicArea(id, player) {
    companion object {
        @JvmOverloads
        @JvmStatic
        fun create(area: Area3D, worldId: Int = -1, interiorId: Int = -1, player: Player? = null,
                   priority: Int = 0): DynamicCuboid {
            val playerId = player?.id ?: -1

            val cuboid = Functions.createDynamicCuboid(area, worldId, interiorId, playerId, priority)
            areas.add(cuboid)
            return cuboid
        }
    }
}
