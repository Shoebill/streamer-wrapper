package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.entities.Player
import net.gtaun.shoebill.data.Area
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.Functions

/**
 * @author Valeriy
 */
@AllOpen
class DynamicRectangle(id: Int, player: Player?) : DynamicArea(id, player) {
    companion object {
        @JvmOverloads
        @JvmStatic
        fun create(area: Area, worldId: Int = -1, interiorId: Int = -1, player: Player? = null): DynamicRectangle {
            val playerId = player?.id ?: -1

            val rectangle = Functions.createDynamicRectangle(area, worldId, interiorId, playerId)
            areas.add(rectangle)
            return rectangle
        }
    }
}
