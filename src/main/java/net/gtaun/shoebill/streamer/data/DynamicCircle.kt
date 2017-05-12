package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.Functions

/**
 * Created by Valeriy on 18/4/2016 in project streamer-wrapper.
 * Copyright (c) 2016 Valeriy. All rights reserved.
 */
@AllOpen
class DynamicCircle(id: Int, player: Player?) : DynamicArea(id, player) {

    companion object {
        @JvmOverloads
        @JvmStatic
        fun create(location: Location, size: Float, player: Player? = null, priority: Int = 0): DynamicCircle {
            val playerId = player?.id ?: -1

            val circle = Functions.createDynamicCircle(location, size, playerId, priority)
            areas.add(circle)
            return circle
        }
    }
}
