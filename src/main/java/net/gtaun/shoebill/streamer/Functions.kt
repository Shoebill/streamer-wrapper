@file:JvmName("Functions")

package net.gtaun.shoebill.streamer

import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.`object`.Vehicle
import net.gtaun.shoebill.amx.AmxCallable
import net.gtaun.shoebill.amx.AmxInstance
import net.gtaun.shoebill.amx.types.ReferenceFloat
import net.gtaun.shoebill.amx.types.ReferenceInt
import net.gtaun.shoebill.amx.types.ReferenceString
import net.gtaun.shoebill.constant.MapIconStyle
import net.gtaun.shoebill.constant.ObjectMaterialSize
import net.gtaun.shoebill.data.*
import net.gtaun.shoebill.exception.CreationFailedException
import net.gtaun.shoebill.streamer.data.*
import net.gtaun.util.event.EventManager
import net.gtaun.util.event.EventManagerNode

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
object Functions {

    private var eventManagerNode: EventManagerNode? = null

    //Objects:
    private var createDynamicObject: AmxCallable? = null
    private var destroyDynamicObject: AmxCallable? = null
    private var isValidDynamicObject: AmxCallable? = null
    private var setDynamicObjectPos: AmxCallable? = null
    private var getDynamicObjectPos: AmxCallable? = null
    private var setDynamicObjectRot: AmxCallable? = null
    private var getDynamicObjectRot: AmxCallable? = null
    private var moveDynamicObject: AmxCallable? = null
    private var stopDynamicObject: AmxCallable? = null
    private var isDynamicObjectMoving: AmxCallable? = null
    private var attachCameraToDynamicObject: AmxCallable? = null
    private var attachDynamicObjectToObject: AmxCallable? = null
    private var attachDynamicObjectToPlayer: AmxCallable? = null
    private var attachDynamicObjectToVehicle: AmxCallable? = null
    private var editDynamicObject: AmxCallable? = null
    private var isDynamicObjectMaterialUsed: AmxCallable? = null
    private var getDynamicObjectMaterial: AmxCallable? = null
    private var setDynamicObjectMaterial: AmxCallable? = null
    private var isDynamicObjectMaterialTextUsed: AmxCallable? = null
    private var getDynamicObjectMaterialText: AmxCallable? = null
    private var setDynamicObjectMaterialText: AmxCallable? = null

    //Pickups:
    private var createDynamicPickup: AmxCallable? = null
    private var destroyDynamicPickup: AmxCallable? = null
    private var isValidDynamicPickup: AmxCallable? = null

    //3DTextLabels:
    private var createDynamic3DTextLabel: AmxCallable? = null
    private var destroyDynamic3DTextLabel: AmxCallable? = null
    private var isValidDynamic3DTextLabel: AmxCallable? = null
    private var getDynamic3DTextLabelText: AmxCallable? = null
    private var updateDynamic3DTextLabelText: AmxCallable? = null

    //Mapicons:
    private var createDynamicMapIcon: AmxCallable? = null
    private var destroyDynamicMapIcon: AmxCallable? = null
    private var isValidDynamicMapIcon: AmxCallable? = null

    //Areas:
    private var createDynamicCircle: AmxCallable? = null
    private var createDynamicSphere: AmxCallable? = null
    private var createDynamicRectangle: AmxCallable? = null
    private var createDynamicCuboid: AmxCallable? = null
    private var destroyDynamicArea: AmxCallable? = null
    private var isValidDynamicArea: AmxCallable? = null
    private var isPlayerInDynamicArea: AmxCallable? = null
    private var isPlayerInAnyDynamicArea: AmxCallable? = null
    private var isAnyPlayerInDynamicArea: AmxCallable? = null
    private var isAnyPlayerInAnyDynamicArea: AmxCallable? = null
    private var isPointInDynamicArea: AmxCallable? = null
    private var isPointInAnyDynamicArea: AmxCallable? = null
    private var attachDynamicAreaToObject: AmxCallable? = null
    private var attachDynamicAreaToPlayer: AmxCallable? = null
    private var attachDynamicAreaToVehicle: AmxCallable? = null

    //Streamer:
    private var update: AmxCallable? = null
    private var updateEx: AmxCallable? = null

    fun registerHandlers(eventManager: EventManager) {
        eventManagerNode = eventManager.createChildNode()
        val amxInstance = AmxInstance.getDefault()
        findObjectFunctions(amxInstance)
        findPickupFunctions(amxInstance)
        find3DTextLabelFunctions(amxInstance)
        findStreamerFunctions(amxInstance)
        findAreaFunctions(amxInstance)
    }

    fun unregisterHandlers() {
        eventManagerNode!!.cancelAll()
        eventManagerNode!!.destroy()
        eventManagerNode = null
    }

    private fun findObjectFunctions(instance: AmxInstance) {
        val tickFunc = instance.getNative("Streamer_GetTickRate")
        if (tickFunc != null && createDynamicObject == null) {
            createDynamicObject = instance.getNative("CreateDynamicObject")
            destroyDynamicObject = instance.getNative("DestroyDynamicObject")
            isValidDynamicObject = instance.getNative("IsValidDynamicObject")
            setDynamicObjectPos = instance.getNative("SetDynamicObjectPos")
            getDynamicObjectPos = instance.getNative("GetDynamicObjectPos")
            setDynamicObjectRot = instance.getNative("SetDynamicObjectRot")
            getDynamicObjectRot = instance.getNative("GetDynamicObjectRot")
            moveDynamicObject = instance.getNative("MoveDynamicObject")
            stopDynamicObject = instance.getNative("StopDynamicObject")
            isDynamicObjectMoving = instance.getNative("IsDynamicObjectMoving")
            attachCameraToDynamicObject = instance.getNative("AttachCameraToDynamicObject")
            attachDynamicObjectToObject = instance.getNative("AttachDynamicObjectToObject")
            attachDynamicObjectToPlayer = instance.getNative("AttachDynamicObjectToPlayer")
            attachDynamicObjectToVehicle = instance.getNative("AttachDynamicObjectToVehicle")
            editDynamicObject = instance.getNative("EditDynamicObject")
            isDynamicObjectMaterialUsed = instance.getNative("IsDynamicObjectMaterialUsed")
            getDynamicObjectMaterial = instance.getNative("GetDynamicObjectMaterial")
            setDynamicObjectMaterial = instance.getNative("SetDynamicObjectMaterial")
            isDynamicObjectMaterialTextUsed = instance.getNative("IsDynamicObjectMaterialTextUsed")
            getDynamicObjectMaterialText = instance.getNative("GetDynamicObjectMaterialText")
            setDynamicObjectMaterialText = instance.getNative("SetDynamicObjectMaterialText")
            createDynamicMapIcon = instance.getNative("CreateDynamicMapIcon")
            destroyDynamicMapIcon = instance.getNative("DestroyDynamicMapIcon")
            isValidDynamicMapIcon = instance.getNative("IsValidDynamicMapIcon")
        } else {
            Streamer.get().logger.error("Could not find Streamer functions! Are you sure that the streamer is loading " +
                    "before Shoebill (check server.cfg plugins order)?")
        }
    }

    private fun findPickupFunctions(instance: AmxInstance) {
        if (createDynamicPickup == null) {
            createDynamicPickup = instance.getNative("CreateDynamicPickup")
            destroyDynamicPickup = instance.getNative("DestroyDynamicPickup")
            isValidDynamicPickup = instance.getNative("IsValidDynamicPickup")
        }
    }

    private fun find3DTextLabelFunctions(instance: AmxInstance) {
        if (createDynamic3DTextLabel == null) {
            createDynamic3DTextLabel = instance.getNative("CreateDynamic3DTextLabel")
            destroyDynamic3DTextLabel = instance.getNative("DestroyDynamic3DTextLabel")
            isValidDynamic3DTextLabel = instance.getNative("IsValidDynamic3DTextLabel")
            getDynamic3DTextLabelText = instance.getNative("GetDynamic3DTextLabelText")
            updateDynamic3DTextLabelText = instance.getNative("UpdateDynamic3DTextLabelText")
        }
    }

    private fun findAreaFunctions(instance: AmxInstance) {
        if (createDynamicCircle == null) {
            createDynamicCircle = instance.getNative("CreateDynamicCircle")
            createDynamicSphere = instance.getNative("CreateDynamicSphere")
            createDynamicRectangle = instance.getNative("CreateDynamicRectangle")
            createDynamicCuboid = instance.getNative("CreateDynamicCuboid")
            destroyDynamicArea = instance.getNative("DestroyDynamicArea")
            isValidDynamicArea = instance.getNative("IsValidDynamicArea")
            isPlayerInDynamicArea = instance.getNative("IsPlayerInDynamicArea")
            isPlayerInAnyDynamicArea = instance.getNative("IsPlayerInAnyDynamicArea")
            isAnyPlayerInDynamicArea = instance.getNative("IsAnyPlayerInDynamicArea")
            isAnyPlayerInAnyDynamicArea = instance.getNative("IsAnyPlayerInAnyDynamicArea")
            isPointInDynamicArea = instance.getNative("IsPointInDynamicArea")
            isPointInAnyDynamicArea = instance.getNative("isPointInAnyDynamicArea")
            attachDynamicAreaToObject = instance.getNative("AttachDynamicAreaToObject")
            attachDynamicAreaToPlayer = instance.getNative("AttachDynamicAreaToPlayer")
            attachDynamicAreaToVehicle = instance.getNative("AttachDynamicAreaToVehicle")
        }
    }

    private fun findStreamerFunctions(instance: AmxInstance) {
        update = instance.getNative("Streamer_Update")
        updateEx = instance.getNative("Streamer_UpdateEx")
    }

    @JvmOverloads fun createDynamicObject(modelid: Int, location: Location, rotation: Vector3D,
                                          streamDistance: Float = DynamicObject.DEFAULT_STREAM_DISTANCE,
                                          drawDistance: Float = DynamicObject.DEFAULT_DRAW_DISTANCE, playerId: Int = -1,
                                          area: Int = -1, priority: Int = 0): DynamicObject {
        val id = createDynamicObject!!
                .call(modelid, location.x, location.y, location.z, rotation.x, rotation.y, rotation.z,
                        location.worldId, location.interiorId, playerId, streamDistance, drawDistance, area/*, priority*/) as Int
        return DynamicObject(id, modelid, playerId, streamDistance, drawDistance)
    }

    fun destroyDynamicObject(`object`: DynamicObject) {
        destroyDynamicObject(`object`.id)
    }

    fun destroyDynamicObject(id: Int) {
        destroyDynamicObject!!.call(id)
    }

    fun isValidDynamicObject(`object`: DynamicObject): Boolean {
        return isValidDynamicObject(`object`.id)
    }

    fun isValidDynamicObject(id: Int): Boolean {
        return isValidDynamicObject!!.call(id) as Int > 0
    }

    fun setDynamicObjectPos(`object`: DynamicObject, pos: Vector3D) {
        setDynamicObjectPos(`object`.id, pos)
    }

    fun setDynamicObjectPos(id: Int, pos: Vector3D) {
        setDynamicObjectPos(id, pos.x, pos.y, pos.z)
    }

    fun setDynamicObjectPos(id: Int, x: Float, y: Float, z: Float) {
        setDynamicObjectPos!!.call(id, x, y, z)
    }

    fun getDynamicObjectPos(`object`: DynamicObject): Vector3D {
        return getDynamicObjectPos(`object`.id)
    }

    fun getDynamicObjectPos(id: Int): Vector3D {
        val refX = ReferenceFloat(0.0f)
        val refY = ReferenceFloat(0.0f)
        val refZ = ReferenceFloat(0.0f)
        getDynamicObjectPos!!.call(id, refX, refY, refZ)
        return Vector3D(refX.value, refY.value, refZ.value)
    }

    fun setDynamicObjectRot(`object`: DynamicObject, rot: Vector3D) {
        setDynamicObjectRot(`object`.id, rot)
    }

    fun setDynamicObjectRot(id: Int, rot: Vector3D) {
        setDynamicObjectRot(id, rot.x, rot.y, rot.z)
    }

    fun setDynamicObjectRot(id: Int, x: Float, y: Float, z: Float) {
        setDynamicObjectRot!!.call(id, x, y, z)
    }

    fun getDynamicObjectRot(`object`: DynamicObject): Vector3D {
        return getDynamicObjectRot(`object`.id)
    }

    fun getDynamicObjectRot(id: Int): Vector3D {
        val refX = ReferenceFloat(0.0f)
        val refY = ReferenceFloat(0.0f)
        val refZ = ReferenceFloat(0.0f)
        getDynamicObjectRot!!.call(id, refX, refY, refZ)
        return Vector3D(refX.value, refY.value, refZ.value)
    }

    fun moveDynamicObject(id: Int, newPos: Vector3D, speed: Float, newRot: Vector3D) {
        moveDynamicObject!!.call(id, newPos.x, newPos.y, newPos.z, speed, newRot.x, newRot.y, newRot.z)
    }

    fun stopDynamicObject(id: Int) {
        stopDynamicObject!!.call(id)
    }

    fun isDynamicObjectMoving(id: Int): Boolean {
        return isDynamicObjectMoving!!.call(id) as Int > 0
    }

    fun attachCameraToDynamicObject(playerid: Int, objectId: Int) {
        attachCameraToDynamicObject!!.call(playerid, objectId)
    }

    fun attachDynamicObjectToObject(`object`: Int, toObject: Int, offsetX: Float, offsetY: Float, offsetZ: Float, rotX: Float, rotY: Float, rotZ: Float, syncRotation: Boolean) {
        attachDynamicObjectToObject!!.call(`object`, toObject, offsetX, offsetY, offsetZ, rotX, rotY, rotZ, if (syncRotation) 1 else 0)
    }

    fun attachDynamicObjectToPlayer(`object`: Int, playerid: Int, offsetX: Float, offsetY: Float, offsetZ: Float, rotX: Float, rotY: Float, rotZ: Float) {
        attachDynamicObjectToPlayer!!.call(`object`, playerid, offsetX, offsetY, offsetZ, rotX, rotY, rotZ)
    }

    fun attachDynamicObjectToVehicle(`object`: Int, vehicle: Int, offsetX: Float, offsetY: Float, offsetZ: Float, rotX: Float, rotY: Float, rotZ: Float) {
        attachDynamicObjectToVehicle!!.call(`object`, vehicle, offsetX, offsetY, offsetZ, rotX, rotY, rotZ)
    }

    fun editDynamicObject(playerid: Int, objectId: Int) {
        editDynamicObject!!.call(playerid, objectId)
    }

    fun isDynamicObjectMaterialUsed(objectid: Int, materialindex: Int): Boolean {
        return isDynamicObjectMaterialUsed!!.call(objectid, materialindex) as Int > 0
    }

    fun getDynamicObjectMaterial(objectid: Int, materialindex: Int): DynamicObjectMaterial {
        val refModel = ReferenceInt(0)
        val refMaterialColor = ReferenceInt(0)
        val refTxdName = ReferenceString("", 128)
        val refTextureName = ReferenceString("", 128)
        getDynamicObjectMaterial!!.call(objectid, materialindex, refModel, refTxdName, refTextureName, refMaterialColor, refTxdName.length, refTextureName.length)
        return DynamicObjectMaterial(refModel.value, refMaterialColor.value, refTxdName.value, refTextureName.value)
    }

    fun setDynamicObjectMaterial(objectid: Int, materialindex: Int, modelid: Int, txdname: String, texturename: String, materialcolor: Int) {
        setDynamicObjectMaterial!!.call(objectid, materialindex, modelid, txdname, texturename, materialcolor)
    }

    fun isDynamicObjectMaterialTextUsed(objectid: Int, materialindex: Int): Boolean {
        return isDynamicObjectMaterialTextUsed!!.call(objectid, materialindex) as Int > 0
    }

    fun getDynamicObjectMaterialText(objectid: Int, materialindex: Int): DynamicObjectMaterialText {
        val refText = ReferenceString("", 256)
        val refMaterialSize = ReferenceInt(0)
        val refFontFace = ReferenceString("", 64)
        val refFontSize = ReferenceInt(0)
        val refBold = ReferenceInt(0)
        val refFontColor = ReferenceInt(0)
        val refBackColor = ReferenceInt(0)
        val refTextAlignment = ReferenceInt(0)
        getDynamicObjectMaterialText!!.call(objectid, materialindex, refText, refMaterialSize, refFontFace, refFontSize, refBold, refFontColor, refBackColor, refTextAlignment, refText.length, refFontFace.length)
        return DynamicObjectMaterialText(refText.value, refFontFace.value, refMaterialSize.value, refFontSize.value, refBold.value > 0, refFontColor.value, refBackColor.value, refTextAlignment.value)
    }

    fun setDynamicObjectMaterialText(objectid: Int, materialindex: Int, text: String, materialsize: ObjectMaterialSize, fontFace: String, fontSize: Int, bold: Boolean, fontColor: Int, backColor: Int, textAlignment: Int) {
        setDynamicObjectMaterialText!!.call(objectid, materialindex, text, materialsize.value, fontFace, fontSize, if (bold) 1 else 0, fontColor, backColor, textAlignment)
    }

    //Pickups:

    @JvmOverloads
    fun createDynamicPickup(modelid: Int, type: Int, location: Location, playerId: Int = 0, streamDistance: Float = 200f,
                            area: Int = -1, priority: Int = 0): DynamicPickup {

        val id = createDynamicPickup!!.call(modelid, type, location.x, location.y, location.z, location.worldId,
                location.interiorId, playerId, streamDistance, area/*, priority*/) as Int
        return DynamicPickup(id, modelid, type, Player.get(playerId), streamDistance)
    }

    fun destroyDynamicPickup(id: Int) {
        destroyDynamicPickup!!.call(id)
    }

    fun isValidDynamicPickup(id: Int): Boolean {
        return isValidDynamicPickup!!.call(id) as Int > 0
    }

    @JvmOverloads
    fun createDynamic3DTextLabel(text: String, color: Color, location: Location,
                                 drawDistance: Float = 200f, attachedPlayer: Int = -1, attachedVehicle: Int = -1, testLOS: Int = 0,
                                 playerid: Int = -1, streamDistance: Float = 200f, area: Int = -1, priority: Int = 0): Dynamic3DTextLabel {

        val id = createDynamic3DTextLabel!!.call(text, color.value, location.x, location.y, location.z,
                drawDistance, attachedPlayer, attachedVehicle, testLOS, location.worldId, location.interiorId, playerid,
                streamDistance, area/*, priority*/) as Int
        return Dynamic3DTextLabel(id, text, playerid, color, streamDistance, drawDistance)
    }

    fun destroyDynamic3DTextLabel(id: Int) {
        destroyDynamic3DTextLabel!!.call(id)
    }

    fun isValidDynamic3DTextLabel(id: Int): Boolean {
        return isValidDynamic3DTextLabel!!.call(id) as Int > 0
    }

    fun getDynamic3DTextLabelText(id: Int): String {
        val text = ""
        getDynamic3DTextLabelText!!.call(id, text, 1024) // Hope no-one will have length of a label text greater then 1024 :)
        return text
    }

    fun updateDynamic3DTextLabelText(id: Int, color: Color, text: String) {
        updateDynamic3DTextLabelText!!.call(id, color.value, text)
    }

    @JvmOverloads fun update(player: Player, streamerType: StreamerType = StreamerType.ALL) {
        update!!.call(player.id, streamerType.value)
    }

    @JvmOverloads fun updateEx(player: Player, x: Float, y: Float, z: Float, worldid: Int, interiorid: Int, streamerType: StreamerType = StreamerType.ALL) {
        updateEx!!.call(player.id, x, y, z, worldid, interiorid, streamerType.value)
    }

    //MapIcons:

    @JvmOverloads
    fun createDynamicMapIcon(location: Location, type: Int, color: Color, playerId: Int = -1,
                             streamDistance: Float = 200f,
                             style: MapIconStyle = MapIconStyle.LOCAL, area: DynamicArea? = null,
                             priority: Int = 0): DynamicMapIcon {
        val areaId = if (area == null) -1 else area.id

        val id = createDynamicMapIcon!!.call(location.x, location.y, location.z, type,
                color.value, location.worldId, location.interiorId, playerId, streamDistance, style.value, areaId/*, priority*/) as Int
        if (id <= 0) throw CreationFailedException("CreateDynamicMapIcon returned an invalid id.")
        return DynamicMapIcon(id, location, type, color, Player.get(playerId), streamDistance, style)
    }

    fun destroyDynamicMapIcon(mapIcon: DynamicMapIcon) {
        destroyDynamicMapIcon!!.call(mapIcon.id)
    }

    fun isValidDynamicMapIcon(mapIcon: DynamicMapIcon): Boolean {
        return isValidDynamicMapIcon!!.call(mapIcon.id) as Int == 1
    }

    //Areas:

    fun createDynamicCircle(location: Location, size: Float, playerId: Int): DynamicCircle {
        val id = createDynamicCircle!!.call(location.x, location.y, size, location.worldId, location.interiorId, playerId) as Int
        return DynamicCircle(id, Player.get(playerId))
    }

    fun createDynamicSphere(location: Location, size: Float, playerId: Int): DynamicSphere {
        val id = createDynamicSphere!!.call(location.x, location.y, location.z, size, location.worldId, location.interiorId, playerId) as Int
        return DynamicSphere(id, Player.get(playerId))
    }

    fun createDynamicRectangle(area: Area, worldId: Int, interiorId: Int, playerId: Int): DynamicRectangle {
        val id = createDynamicRectangle!!.call(area.minX, area.minY, area.maxX, area.maxY, worldId, interiorId, playerId) as Int
        return DynamicRectangle(id, Player.get(playerId))
    }

    fun createDynamicCuboid(area: Area3D, worldId: Int, interiorId: Int, playerId: Int): DynamicCuboid {
        val id = createDynamicCuboid!!.call(area.minX, area.minY, area.minZ, area.maxX, area.maxY, area.maxZ, worldId, interiorId, playerId) as Int
        return DynamicCuboid(id, Player.get(playerId))
    }

    fun destroyDynamicArea(area: DynamicArea) {
        destroyDynamicArea!!.call(area.id)
    }

    fun isValidDynamicArea(area: DynamicArea): Boolean {
        return isValidDynamicArea!!.call(area.id) as Int == 1
    }

    fun isPlayerInDynamicArea(playerId: Int, area: DynamicArea): Boolean {
        return isPlayerInDynamicArea!!.call(playerId, area.id, 0) as Int == 1
    }

    fun isPlayerInAnyDynamicArea(playerId: Int): Boolean {
        return isPlayerInAnyDynamicArea!!.call(playerId, 0) as Int == 1
    }

    fun isAnyPlayerInDynamicArea(area: DynamicArea): Boolean {
        return isAnyPlayerInDynamicArea!!.call(area.id, 0) as Int == 1
    }

    fun isAnyPlayerInAnyDynamicArea(): Boolean {
        return isAnyPlayerInAnyDynamicArea!!.call(0) as Int == 1
    }

    fun isPointInDynamicArea(area: DynamicArea, point: Vector3D): Boolean {
        return isPointInDynamicArea!!.call(area.id, point.x, point.y, point.z) as Int == 1
    }

    fun IsPointInAnyDynamicArea(point: Vector3D): Boolean {
        return isPointInAnyDynamicArea!!.call(point.x, point.y, point.z) as Int == 1
    }

    fun attachDynamicAreaToObject(area: DynamicArea, `object`: DynamicObject, offset: Vector3D) {
        attachDynamicAreaToObject!!.call(area.id, `object`.id, 2, 0xFFFF, offset.x, offset.y, offset.z)
    }

    fun attachDynamicAreaToPlayer(area: DynamicArea, player: Player, offset: Vector3D) {
        attachDynamicAreaToPlayer!!.call(area.id, player.id, offset.x, offset.y, offset.z)
    }

    fun attachDynamicAreaToVehicle(area: DynamicArea, vehicle: Vehicle, offset: Vector3D) {
        attachDynamicAreaToVehicle!!.call(area.id, vehicle.id, offset.x, offset.y, offset.z)
    }
}