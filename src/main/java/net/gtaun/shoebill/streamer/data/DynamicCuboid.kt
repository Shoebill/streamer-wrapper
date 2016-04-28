package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.data.Area3D
import net.gtaun.shoebill.streamer.Functions

/**
 * Created by Valeriy on 18/4/2016.
 */
class DynamicCuboid(id: Int, player: Player?) : DynamicArea(id, player) {
    internal companion object {
        @JvmOverloads
        @JvmStatic
        fun create(area: Area3D, worldId: Int = -1, interiorId: Int = -1, player: Player? = null): DynamicCuboid {
            val playerId = if (player == null) -1 else player.id

            val cuboid = Functions.createDynamicCuboid(area, worldId, interiorId, playerId)
            areas.add(cuboid)
            return cuboid
        }
    }
}
