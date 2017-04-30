package net.gtaun.shoebill.streamer.event.obj

/**
 * @author Marvin Haschker
 */
@net.gtaun.shoebill.streamer.AllOpen
class PlayerEditDynamicObjectEvent(obj: net.gtaun.shoebill.streamer.data.DynamicObject, val player: net.gtaun.shoebill.`object`.Player,
                                   val response: Int, val pos: net.gtaun.shoebill.data.Vector3D,
                                   val rot: net.gtaun.shoebill.data.Vector3D) : DynamicObjectEvent(obj)
