package net.gtaun.shoebill.streamer.event

import net.gtaun.shoebill.constant.WeaponModel
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.data.DynamicObject

/**
 * @author Marvin Haschker
 */
@AllOpen
class PlayerShootDynamicObjectEvent(obj: DynamicObject, val player: Player, val weaponModel: WeaponModel,
                                    val position: Vector3D) : DynamicObjectEvent(obj)
