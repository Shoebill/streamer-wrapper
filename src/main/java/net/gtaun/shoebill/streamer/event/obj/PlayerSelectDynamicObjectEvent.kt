package net.gtaun.shoebill.streamer.event.obj

import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.data.DynamicObject

/**
 * @author Marvin Haschker
 */
@AllOpen
class PlayerSelectDynamicObjectEvent(obj: DynamicObject, val player: Player,
                                     val modelid: Int, val pos: Vector3D) : DynamicObjectEvent(obj)
