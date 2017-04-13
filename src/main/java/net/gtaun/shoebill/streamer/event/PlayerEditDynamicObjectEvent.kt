package net.gtaun.shoebill.streamer.event

import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.data.DynamicObject

/**
 * @author Marvin Haschker
 */
@AllOpen
class PlayerEditDynamicObjectEvent(obj: DynamicObject, val player: Player,
                                   val response: Int, val pos: Vector3D,
                                   val rot: Vector3D) : DynamicObjectEvent(obj)
