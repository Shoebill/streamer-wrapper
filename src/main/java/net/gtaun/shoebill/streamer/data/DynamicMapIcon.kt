package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.entities.Destroyable
import net.gtaun.shoebill.entities.Player
import net.gtaun.shoebill.constant.MapIconStyle
import net.gtaun.shoebill.data.Color
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.Functions
import java.util.*

/**
 * Created by marvin on 19.02.16.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
@AllOpen
class DynamicMapIcon(id: Int, val location: Location, val type: Int, val color: Color, val player: Player?,
                     val streamDistance: Float, val style: MapIconStyle) : Destroyable {

    final var id: Int = id
        private set

    override fun destroy() {
        if (isDestroyed)
            return

        Functions.destroyDynamicMapIcon(this)
        id = -1
        removeSelf()
    }

    override val isDestroyed: Boolean
        get() = id == -1

    private fun removeSelf() = objects.remove(this)

    companion object {

        @JvmField val DEFAULT_STREAM_DISTANCE = 200f //From streamer.inc STREAMER_MAP_ICON_SD
        @JvmField val DEFAULT_ICON_STYLE = MapIconStyle.LOCAL //From streamer.inc

        private var objects = mutableListOf<DynamicMapIcon>()

        @JvmStatic fun get(): Set<DynamicMapIcon> = HashSet(objects)

        @JvmStatic
        operator fun get(id: Int): DynamicMapIcon? = objects.find { it.id == id }

        @JvmOverloads
        @JvmStatic
        fun create(location: Location, type: Int, color: Color, streamDistance: Float = DEFAULT_STREAM_DISTANCE,
                   style: MapIconStyle = DEFAULT_ICON_STYLE, priority: Int = 0, player: Player? = null,
                   area: DynamicArea? = null): DynamicMapIcon {
            val playerId = player?.id ?: -1

            val entities = Functions.createDynamicMapIcon(location, type, color, playerId, streamDistance, style, area,
                    priority)

            objects.add(entities)
            return entities
        }
    }
}
