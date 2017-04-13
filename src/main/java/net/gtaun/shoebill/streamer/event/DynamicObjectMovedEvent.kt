package net.gtaun.shoebill.streamer.event

import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.data.DynamicObject

/**
 * @author Marvin Haschker
 */
@AllOpen
class DynamicObjectMovedEvent(obj: DynamicObject) : DynamicObjectEvent(obj)
