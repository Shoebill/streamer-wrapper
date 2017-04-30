package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.streamer.AllOpen

/**
 * Created by valych on 11.01.2016 in project streamer-wrapper.
 * Values of the enum corresponds to values of definitions in streamer.inc:
 * STREAMER_TYPE_OBJECT, STREAMER_TYPE_PICKUP, etc.
 */
@AllOpen
enum class StreamerType constructor(val value: Int) {
    ALL(-1), // There is no such value in streamer.inc definitions but value -1 stands for updating all streamer types.
    OBJECT(0),
    PICKUP(1),
    CP(2),
    RACE_CP(3),
    MAP_ICON(4),
    TEXT3D_LABEL(5),
    AREA(6),
    ACTOR(7);

    companion object {
        @JvmStatic
        operator fun get(value: Int): StreamerType? = values().find { it.value == value }
    }

}
