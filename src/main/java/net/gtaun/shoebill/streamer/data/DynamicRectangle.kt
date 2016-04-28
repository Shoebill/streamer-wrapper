package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.data.Area
import net.gtaun.shoebill.streamer.Functions

/**
 * Created by Valeriy on 18/4/2016.
 */
class DynamicRectangle(id: Int, player: Player?) : DynamicArea(id, player) {
    internal companion object {
        @JvmOverloads
        @JvmStatic
        fun create(area: Area, worldId: Int = -1, interiorId: Int = -1, player: Player? = null): DynamicRectangle {
            val playerId = if (player == null) -1 else player.id

            val rectangle = Functions.createDynamicRectangle(area, worldId, interiorId, playerId)
            areas.add(rectangle)
            return rectangle
        }
    }
}
