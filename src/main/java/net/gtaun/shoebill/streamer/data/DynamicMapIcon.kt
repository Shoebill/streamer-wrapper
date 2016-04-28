package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.constant.MapIconStyle
import net.gtaun.shoebill.data.Color
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.`object`.Destroyable
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.streamer.Functions

import java.util.ArrayList
import java.util.HashSet

/**
 * Created by marvin on 19.02.16.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
class DynamicMapIcon(id: Int, val location: Location, val type: Int, val color: Color, val player: Player?,
                     val streamDistance: Float, val style: MapIconStyle) : Destroyable {

    var id: Int = id
        private set

    override fun destroy() {
        if (isDestroyed) {
            removeSelf()
            return
        }

        Functions.destroyDynamicMapIcon(this)
        id = -1
        removeSelf()
    }

    override fun isDestroyed(): Boolean {
        return id == -1
    }

    private fun removeSelf() {
        if (objects.contains(this))
            objects.remove(this)
    }

    internal companion object {

        @JvmField val DEFAULT_STREAM_DISTANCE = 200f //From streamer.inc STREAMER_MAP_ICON_SD
        @JvmField val DEFAULT_ICON_STYLE = MapIconStyle.LOCAL //From streamer.inc

        private var objects = mutableListOf<DynamicMapIcon>()

        @JvmStatic fun get(): Set<DynamicMapIcon> {
            return HashSet(objects)
        }

        @JvmStatic
        operator fun get(id: Int): DynamicMapIcon? {
            return objects.find { it.id == id }
        }

        @JvmOverloads
        @JvmStatic
        fun create(location: Location, type: Int, color: Color, streamDistance: Float = DEFAULT_STREAM_DISTANCE,
                   style: MapIconStyle = DEFAULT_ICON_STYLE, priority: Int = 0, player: Player? = null, area: DynamicArea? = null): DynamicMapIcon {
            val playerId = if (player == null) -1 else player.id

            val `object` = Functions.createDynamicMapIcon(location, type, color, playerId, streamDistance, style)
            objects.add(`object`)
            return `object`
        }
    }
}
