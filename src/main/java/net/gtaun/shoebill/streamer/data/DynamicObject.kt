package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.constant.ObjectMaterialSize
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.`object`.Destroyable
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.`object`.Vehicle
import net.gtaun.shoebill.streamer.Functions

import java.util.ArrayList
import java.util.HashSet

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
class DynamicObject(id: Int, val modelid: Int, val playerid: Int, val streamDistance: Float, val drawDistance: Float)
: Destroyable {
    var id: Int = id
        private set

    var position: Vector3D
        get() = Functions.getDynamicObjectPos(this)
        set(newPos) = Functions.setDynamicObjectPos(this, newPos)

    var rotation: Vector3D
        get() = Functions.getDynamicObjectRot(this)
        set(newRot) = Functions.setDynamicObjectRot(this, newRot)

    fun movePosition(newPos: Vector3D, speed: Float) {
        val rotation = rotation
        move(newPos, speed, rotation)
    }

    fun moveRotation(newRot: Vector3D, speed: Float) {
        val position = position
        move(position, speed, newRot)
    }

    fun move(pos: Vector3D, speed: Float, rot: Vector3D) {
        Functions.moveDynamicObject(id, pos, speed, rot)
    }

    fun stop() {
        Functions.stopDynamicObject(id)
    }

    val isMoving: Boolean
        get() = Functions.isDynamicObjectMoving(id)

    fun attachCamera(player: Player) {
        Functions.attachCameraToDynamicObject(player.id, id)
    }

    @JvmOverloads fun attach(target: DynamicObject, offset: Vector3D, rotation: Vector3D, syncRotation: Boolean = true) {
        Functions.attachDynamicObjectToObject(id, target.id, offset.x, offset.y, offset.z, rotation.x, rotation.y, rotation.z, syncRotation)
    }

    fun attach(target: Player, offset: Vector3D, rotation: Vector3D) {
        Functions.attachDynamicObjectToPlayer(id, target.id, offset.x, offset.y, offset.z, rotation.x, rotation.y, rotation.z)
    }

    fun attach(target: Vehicle, offset: Vector3D, rotation: Vector3D) {
        Functions.attachDynamicObjectToVehicle(id, target.id, offset.x, offset.y, offset.z, rotation.x, rotation.y, rotation.z)
    }

    fun edit(player: Player) {
        Functions.editDynamicObject(player.id, this.id)
    }

    fun isMaterialUsed(materialIndex: Int): Boolean {
        return Functions.isDynamicObjectMaterialUsed(id, materialIndex)
    }

    fun getMaterial(materialindex: Int): DynamicObjectMaterial {
        return Functions.getDynamicObjectMaterial(id, materialindex)
    }

    @JvmOverloads fun setMaterial(materialindex: Int, modelid: Int, txdname: String, textureName: String, materialColor: Int = 0) {
        Functions.setDynamicObjectMaterial(id, materialindex, modelid, txdname, textureName, materialColor)
    }

    fun isMaterialTextUsed(materialindex: Int): Boolean {
        return Functions.isDynamicObjectMaterialTextUsed(id, materialindex)
    }

    fun getMaterialText(materialindex: Int): DynamicObjectMaterialText {
        return Functions.getDynamicObjectMaterialText(id, materialindex)
    }

    @JvmOverloads fun setMaterialText(materialIndex: Int, text: String,
                                      size: ObjectMaterialSize = ObjectMaterialSize.SIZE_256x128,
                                      fontFace: String = "Arial", fontSize: Int = 24,
                                      bold: Boolean = true, fontColor: Int = 0xFFFFFFFF.toInt(),
                                      backColor: Int = 0, textAlignment: Int = 0) {
        Functions.setDynamicObjectMaterialText(id, materialIndex, text, size, fontFace, fontSize, bold,
                fontColor, backColor, textAlignment)
    }

    override fun destroy() {
        if (isDestroyed) {
            removeSelf()
            return
        }
        Functions.destroyDynamicObject(this)
        id = -1
        removeSelf()
    }

    private fun removeSelf() {
        if (objects.contains(this))
            objects.remove(this)
    }

    override fun isDestroyed(): Boolean {
        return !Functions.isValidDynamicObject(id)
    }

    internal companion object {
        @JvmField val DEFAULT_STREAM_DISTANCE = 300f // Corresponds to STREAMER_OBJECT_SD in streamer.inc
        @JvmField val DEFAULT_DRAW_DISTANCE = 0f // Corresponds to STREAMER_OBJECT_DD in streamer.inc

        private var objects = mutableListOf<DynamicObject>()

        @JvmStatic
        fun get(): Set<DynamicObject> {
            return HashSet(objects)
        }

        @JvmStatic
        operator fun get(id: Int): DynamicObject? {
            return objects.find { it.id == id }
        }

        @JvmOverloads
        @JvmStatic
        fun create(modelid: Int, location: Location, rotation: Vector3D = Vector3D(0f, 0f, 0f),
                   streamDistance: Float = DynamicObject.DEFAULT_STREAM_DISTANCE,
                   drawDistance: Float = DynamicObject.DEFAULT_DRAW_DISTANCE,
                   priority: Int = 0, player: Player? = null, area: DynamicArea? = null): DynamicObject {
            val playerId = if (player == null) -1 else player.id
            val areaId = if (area == null) -1 else area.id

            val `object` = Functions.createDynamicObject(modelid, location, rotation, streamDistance, drawDistance,
                    playerId, areaId, priority)
            objects.add(`object`)
            return `object`
        }
    }
}
