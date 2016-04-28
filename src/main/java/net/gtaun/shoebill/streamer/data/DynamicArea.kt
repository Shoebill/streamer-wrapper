package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.`object`.Destroyable
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.`object`.Vehicle
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.streamer.Functions
import java.util.*

/**
 * Created by Valeriy on 18/4/2016.
 */
abstract class DynamicArea internal constructor(id: Int, val player: Player?) : Destroyable {

    var id: Int = id
        get
        private set

    fun isPlayerInRange(player: Player): Boolean {
        return Functions.isPlayerInDynamicArea(player.id, this)
    }

    fun isPointInRange(point: Vector3D): Boolean {
        return Functions.isPointInDynamicArea(this, point)
    }

    fun attachToPlayer(player: Player, offset: Vector3D) {
        Functions.attachDynamicAreaToPlayer(this, player, offset)
    }

    fun attachToObject(`object`: DynamicObject, offset: Vector3D) {
        Functions.attachDynamicAreaToObject(this, `object`, offset)
    }

    fun attachToVehicle(vehicle: Vehicle, offset: Vector3D) {
        Functions.attachDynamicAreaToVehicle(this, vehicle, offset)
    }

    override fun destroy() {
        if (isDestroyed) {
            selfRemove()
            return
        }

        Functions.destroyDynamicArea(this)
        this.id = -1
        selfRemove()
    }

    private fun selfRemove() {
        if (areas.contains(this))
            areas.remove(this)
    }

    override fun isDestroyed(): Boolean {
        return !Functions.isValidDynamicArea(this)
    }

    internal companion object {
        @JvmStatic
        protected var areas = mutableListOf<DynamicArea>()

        @JvmStatic
        fun get(): Set<DynamicArea> {
            return HashSet(areas)
        }

        @JvmStatic
        operator fun get(id: Int): DynamicArea? {
            return areas.find { it.id == id }
        }
    }
}
