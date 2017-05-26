package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.entities.Player
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.Functions

/**
 * @author Valeriy
 */
@AllOpen
class DynamicSphere(id: Int, player: Player?) : DynamicArea(id, player) {
    companion object {
        @JvmOverloads
        @JvmStatic
        fun create(location: Location, size: Float, player: Player? = null): DynamicSphere {
            val playerId = player?.id ?: -1

            val sphere = Functions.createDynamicSphere(location, size, playerId)
            areas.add(sphere)
            return sphere
        }
    }
}
