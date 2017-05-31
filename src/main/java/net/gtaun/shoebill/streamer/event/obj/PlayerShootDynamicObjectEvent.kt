package net.gtaun.shoebill.streamer.event.obj

/**
 * @author Marvin Haschker
 */
@net.gtaun.shoebill.streamer.AllOpen
class PlayerShootDynamicObjectEvent(obj: net.gtaun.shoebill.streamer.data.DynamicObject, val player: net.gtaun.shoebill.entities.Player, val weaponModel: net.gtaun.shoebill.constant.WeaponModel,
                                    val position: net.gtaun.shoebill.data.Vector3D) : net.gtaun.shoebill.streamer.event.obj.DynamicObjectEvent(obj)
