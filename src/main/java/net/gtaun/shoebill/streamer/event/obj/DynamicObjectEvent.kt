package net.gtaun.shoebill.streamer.event.obj

import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.data.DynamicObject
import net.gtaun.util.event.Event

/**
 * @author Marvin Haschker
 */
@AllOpen
class DynamicObjectEvent(val obj: DynamicObject) : Event()
