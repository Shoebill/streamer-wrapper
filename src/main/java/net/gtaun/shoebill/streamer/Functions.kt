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
 * @author Marvin Haschker
 */
object Functions {

    private lateinit var eventManagerNode: EventManagerNode
    private val logger = Streamer.get().logger

    private val FUNCTION_LIST = arrayOf(
            "CreateDynamicObject",
            "DestroyDynamicObject",
            "IsValidDynamicObject",
            "SetDynamicObjectPos",
            "GetDynamicObjectPos",
            "SetDynamicObjectRot",
            "GetDynamicObjectRot",
            "MoveDynamicObject",
            "StopDynamicObject",
            "IsDynamicObjectMoving",
            "AttachCameraToDynamicObject",
            "AttachDynamicObjectToObject",
            "AttachDynamicObjectToPlayer",
            "AttachDynamicObjectToVehicle",
            "EditDynamicObject",
            "IsDynamicObjectMaterialUsed",
            "GetDynamicObjectMaterial",
            "SetDynamicObjectMaterial",
            "IsDynamicObjectMaterialTextUsed",
            "GetDynamicObjectMaterialText",
            "SetDynamicObjectMaterialText",
            "CreateDynamicPickup",
            "DestroyDynamicPickup",
            "IsValidDynamicPickup",
            "CreateDynamic3DTextLabel",
            "DestroyDynamic3DTextLabel",
            "IsValidDynamic3DTextLabel",
            "GetDynamic3DTextLabelText",
            "UpdateDynamic3DTextLabelText",
            "CreateDynamicMapIcon",
            "DestroyDynamicMapIcon",
            "IsValidDynamicMapIcon",
            "CreateDynamicCircle",
            "CreateDynamicSphere",
            "CreateDynamicRectangle",
            "CreateDynamicCuboid",
            "DestroyDynamicArea",
            "IsValidDynamicArea",
            "IsPlayerInDynamicArea",
            "IsPlayerInAnyDynamicArea",
            "IsAnyPlayerInDynamicArea",
            "IsAnyPlayerInAnyDynamicArea",
            "IsPointInDynamicArea",
            "IsPointInAnyDynamicArea",
            "AttachDynamicAreaToObject",
            "AttachDynamicAreaToPlayer",
            "AttachDynamicAreaToVehicle",
            "Streamer_Update",
            "Streamer_UpdateEx")

    private var functions: MutableMap<String, AmxCallable> = mutableMapOf()

    fun registerHandlers(eventManager: EventManager) {
        eventManagerNode = eventManager.createChildNode()
        val amxInstance = AmxInstance.getDefault()
        var errors = 0
        FUNCTION_LIST.forEach { functionName ->
            val native = amxInstance.getNative(functionName)
            if(native != null) {
                functions[functionName] = native
            } else {
                errors += 1
                logger.error("Could not find native function $functionName.")
            }
        }
        if(errors > 0) {
            logger.error("$errors native function(s) have not been found. Please make sure that the native streamer" +
                    "plugin will be loaded before Shoebill (check server.cfg).")
        }
    }

    fun unregisterHandlers() {
        eventManagerNode.cancelAll()
        eventManagerNode.destroy()
    }

    @JvmOverloads
    fun createDynamicObject(modelId: Int, location: Location, rotation: Vector3D,
                            streamDistance: Float = DynamicObject.DEFAULT_STREAM_DISTANCE,
                            drawDistance: Float = DynamicObject.DEFAULT_DRAW_DISTANCE, playerId: Int = -1,
                            area: Int = -1, priority: Int = 0): DynamicObject {

        val native = getNative("CreateDynamicObject")

        val id = native.call(modelId, location.x, location.y, location.z, rotation.x, rotation.y, rotation.z,
                        location.worldId, location.interiorId, playerId, streamDistance, drawDistance,
                area, priority) as Int

        return DynamicObject(id, modelId, playerId, streamDistance, drawDistance)
    }

    fun destroyDynamicObject(obj: DynamicObject) = destroyDynamicObject(obj.id)

    fun destroyDynamicObject(id: Int) {
        val native = getNative("DestroyDynamicObject")
        native.call(id)
    }

    fun isValidDynamicObject(obj: DynamicObject) = isValidDynamicObject(obj.id)

    fun isValidDynamicObject(id: Int): Boolean {
        val native = getNative("IsValidDynamicObject")
        return native.call(id) as Int > 0
    }

    fun setDynamicObjectPos(obj: DynamicObject, pos: Vector3D) = setDynamicObjectPos(obj.id, pos)

    fun setDynamicObjectPos(id: Int, pos: Vector3D) = setDynamicObjectPos(id, pos.x, pos.y, pos.z)

    fun setDynamicObjectPos(id: Int, x: Float, y: Float, z: Float) {
        val native = getNative("SetDynamicObjectPos")
        native.call(id, x, y, z)
    }

    fun getDynamicObjectPos(obj: DynamicObject) = getDynamicObjectPos(obj.id)

    fun getDynamicObjectPos(id: Int): Vector3D {
        val native = getNative("GetDynamicObjectPos")

        val refX = ReferenceFloat(0.0f)
        val refY = ReferenceFloat(0.0f)
        val refZ = ReferenceFloat(0.0f)

        native.call(id, refX, refY, refZ)
        return Vector3D(refX.value, refY.value, refZ.value)
    }

    fun setDynamicObjectRot(obj: DynamicObject, rot: Vector3D) = setDynamicObjectRot(obj.id, rot)

    fun setDynamicObjectRot(id: Int, rot: Vector3D) = setDynamicObjectRot(id, rot.x, rot.y, rot.z)

    fun setDynamicObjectRot(id: Int, x: Float, y: Float, z: Float) {
        val native = getNative("SetDynamicObjectRot")
        native.call(id, x, y, z)
    }

    fun getDynamicObjectRot(obj: DynamicObject) = getDynamicObjectRot(obj.id)

    fun getDynamicObjectRot(id: Int): Vector3D {
        val native = getNative("GetDynamicObjectRot")
        val refX = ReferenceFloat(0.0f)
        val refY = ReferenceFloat(0.0f)
        val refZ = ReferenceFloat(0.0f)
        native.call(id, refX, refY, refZ)
        return Vector3D(refX.value, refY.value, refZ.value)
    }

    fun moveDynamicObject(id: Int, newPos: Vector3D, speed: Float, newRot: Vector3D) {
        val native = getNative("MoveDynamicObject")
        native.call(id, newPos.x, newPos.y, newPos.z, speed, newRot.x, newRot.y, newRot.z)
    }

    fun stopDynamicObject(id: Int) {
        val native = getNative("StopDynamicObject")
        native.call(id)
    }

    fun isDynamicObjectMoving(id: Int): Boolean {
        val native = getNative("IsDynamicObjectMoving")
        return native.call(id) as Int > 0
    }

    fun attachCameraToDynamicObject(playerId: Int, objectId: Int) {
        val native = getNative("AttachCameraToDynamicObject")
        native.call(playerId, objectId)
    }

    fun attachDynamicObjectToObject(obj: Int, toObject: Int, offsetX: Float, offsetY: Float, offsetZ: Float,
                                    rotX: Float, rotY: Float, rotZ: Float, syncRotation: Boolean) {
        val native = getNative("AttachDynamicObjectToObject")
        native.call(obj, toObject, offsetX, offsetY, offsetZ, rotX, rotY, rotZ, if (syncRotation) 1 else 0)
    }

    fun attachDynamicObjectToPlayer(obj: Int, playerId: Int, offsetX: Float, offsetY: Float, offsetZ: Float,
                                    rotX: Float, rotY: Float, rotZ: Float) {
        val native = getNative("AttachDynamicObjectToPlayer")
        native.call(obj, playerId, offsetX, offsetY, offsetZ, rotX, rotY, rotZ)
    }

    fun attachDynamicObjectToVehicle(obj: Int, vehicle: Int, offsetX: Float, offsetY: Float, offsetZ: Float,
                                     rotX: Float, rotY: Float, rotZ: Float) {
        val native = getNative("AttachDynamicObjectToVehicle")
        native.call(obj, vehicle, offsetX, offsetY, offsetZ, rotX, rotY, rotZ)
    }

    fun editDynamicObject(playerId: Int, objectId: Int) {
        val native = getNative("EditDynamicObject")
        native.call(playerId, objectId)
    }

    fun isDynamicObjectMaterialUsed(objectid: Int, materialIndex: Int): Boolean {
        val native = getNative("IsDynamicObjectMaterialUsed")
        return native.call(objectid, materialIndex) as Int > 0
    }

    fun getDynamicObjectMaterial(objectId: Int, materialIndex: Int): DynamicObjectMaterial {
        val native = getNative("GetDynamicObjectMaterial")

        val refModel = ReferenceInt(0)
        val refMaterialColor = ReferenceInt(0)
        val refTxdName = ReferenceString("", 128)
        val refTextureName = ReferenceString("", 128)

        native.call(objectId, materialIndex, refModel, refTxdName, refTextureName,
                refMaterialColor, refTxdName.length, refTextureName.length)
        return DynamicObjectMaterial(refModel.value, refMaterialColor.value, refTxdName.value, refTextureName.value)
    }

    fun setDynamicObjectMaterial(objectId: Int, materialIndex: Int, modelId: Int, txdName: String, textureName: String,
                                 materialColor: Int) {
        val native = getNative("SetDynamicObjectMaterial")
        native.call(objectId, materialIndex, modelId, txdName, textureName, materialColor)
    }

    fun isDynamicObjectMaterialTextUsed(objectId: Int, materialIndex: Int): Boolean {
        val native = getNative("IsDynamicObjectMaterialTextUsed")
        return native.call(objectId, materialIndex) as Int > 0
    }

    fun getDynamicObjectMaterialText(objectId: Int, materialIndex: Int): DynamicObjectMaterialText {
        val native = getNative("GetDynamicObjectMaterialText")
        val refText = ReferenceString("", 256)
        val refMaterialSize = ReferenceInt(0)
        val refFontFace = ReferenceString("", 64)
        val refFontSize = ReferenceInt(0)
        val refBold = ReferenceInt(0)
        val refFontColor = ReferenceInt(0)
        val refBackColor = ReferenceInt(0)
        val refTextAlignment = ReferenceInt(0)
        native.call(objectId, materialIndex, refText, refMaterialSize, refFontFace, refFontSize, refBold,
                refFontColor, refBackColor, refTextAlignment, refText.length, refFontFace.length)
        return DynamicObjectMaterialText(refText.value, refFontFace.value, refMaterialSize.value, refFontSize.value,
                refBold.value > 0, refFontColor.value, refBackColor.value, refTextAlignment.value)
    }

    fun setDynamicObjectMaterialText(objectId: Int, materialIndex: Int, text: String, materialSize: ObjectMaterialSize,
                                     fontFace: String, fontSize: Int, bold: Boolean, fontColor: Int, backColor: Int,
                                     textAlignment: Int) {
        val native = getNative("SetDynamicObjectMaterialText")
        native.call(objectId, materialIndex, text, materialSize.value, fontFace,
                fontSize, if (bold) 1 else 0, fontColor, backColor, textAlignment)
    }

    @JvmOverloads
    fun createDynamicPickup(modelId: Int, type: Int, location: Location, playerId: Int = -1, streamDistance: Float = 200f,
                            area: Int = -1, priority: Int = 0): DynamicPickup {
        val native = getNative("CreateDynamicPickup")
        val id = native.call(modelId, type, location.x, location.y, location.z, location.worldId,
                location.interiorId, playerId, streamDistance, area, priority) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return DynamicPickup(id, modelId, type, player, streamDistance)
    }

    fun destroyDynamicPickup(id: Int) {
        val native = getNative("DestroyDynamicPickup")
        native.call(id)
    }

    fun isValidDynamicPickup(id: Int): Boolean {
        val native = getNative("IsValidDynamicPickup")
        return native.call(id) as Int > 0
    }

    @JvmOverloads
    fun createDynamic3DTextLabel(text: String, color: Color, location: Location,
                                 drawDistance: Float = 200f, attachedPlayer: Int = -1, attachedVehicle: Int = -1,
                                 testLOS: Int = 0, playerId: Int = -1, streamDistance: Float = 200f,
                                 area: Int = -1, priority: Int = 0): Dynamic3DTextLabel {
        val native = getNative("CreateDynamic3DTextLabel")
        val id = native.call(text, color.value, location.x, location.y, location.z,
                drawDistance, attachedPlayer, attachedVehicle, testLOS, location.worldId, location.interiorId, playerId,
                streamDistance, area, priority) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return Dynamic3DTextLabel(id, text, player, color, streamDistance, drawDistance)
    }

    fun destroyDynamic3DTextLabel(id: Int) {
        val native = getNative("DestroyDynamic3DTextLabel")
        native.call(id)
    }

    fun isValidDynamic3DTextLabel(id: Int): Boolean {
        val native = getNative("IsValidDynamic3DTextLabel")
        return native.call(id) as Int > 0
    }

    fun getDynamic3DTextLabelText(id: Int): String {
        val native = getNative("GetDynamic3DTextLabelText")
        val refText = ReferenceString("", 1024)
        native.call(id, refText, 1024)
        return refText.value
    }

    fun updateDynamic3DTextLabelText(id: Int, color: Color, text: String) {
        val native = getNative("UpdateDynamic3DTextLabel")
        native.call(id, color.value, text)
    }

    @JvmOverloads
    fun update(player: Player, streamerType: StreamerType = StreamerType.ALL) {
        val native = getNative("Streamer_Update")
        native.call(player.id, streamerType.value)
    }

    @JvmOverloads
    fun updateEx(player: Player, x: Float, y: Float, z: Float, worldId: Int, interiorId: Int,
                 streamerType: StreamerType = StreamerType.ALL, compensatedTime: Int = -1) {
        val native = getNative("Streamer_UpdateEx")
        native.call(player.id, x, y, z, worldId, interiorId, streamerType.value, compensatedTime)
    }

    @JvmOverloads
    fun createDynamicMapIcon(location: Location, type: Int, color: Color, playerId: Int = -1,
                             streamDistance: Float = 200f,
                             style: MapIconStyle = MapIconStyle.LOCAL, area: DynamicArea? = null,
                             priority: Int = 0): DynamicMapIcon {
        val native = getNative("CreateDynamicMapIcon")
        val areaId = area?.id ?: -1

        val id = native.call(location.x, location.y, location.z, type, color.value, location.worldId,
                location.interiorId, playerId, streamDistance, style.value, areaId, priority) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        if (id <= 0) throw CreationFailedException("CreateDynamicMapIcon returned an invalid id.")
        return DynamicMapIcon(id, location, type, color, player, streamDistance, style)
    }

    fun destroyDynamicMapIcon(mapIcon: DynamicMapIcon) {
        val native = getNative("DestroyDynamicMapIcon")
        native.call(mapIcon.id)
    }

    fun isValidDynamicMapIcon(mapIcon: DynamicMapIcon): Boolean {
        val native = getNative("IsValidDynamicMapIcon")
        return native.call(mapIcon.id) as Int == 1
    }

    @JvmOverloads
    fun createDynamicCircle(location: Location, size: Float, playerId: Int = -1): DynamicCircle {
        val native = getNative("CreateDynamicCircle")
        val id = native.call(location.x, location.y, size, location.worldId, location.interiorId, playerId) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return DynamicCircle(id, player)
    }

    @JvmOverloads
    fun createDynamicSphere(location: Location, size: Float, playerId: Int = -1): DynamicSphere {
        val native = getNative("CreateDynamicSphere")
        val id = native.call(location.x, location.y, location.z, size, location.worldId,
                location.interiorId, playerId) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return DynamicSphere(id, player)
    }

    @JvmOverloads
    fun createDynamicRectangle(area: Area, worldId: Int, interiorId: Int, playerId: Int = -1): DynamicRectangle {
        val native = getNative("CreateDynamicRectangle")
        val id = native.call(area.minX, area.minY, area.maxX, area.maxY, worldId, interiorId, playerId) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return DynamicRectangle(id, player)
    }

    @JvmOverloads
    fun createDynamicCuboid(area: Area3D, worldId: Int, interiorId: Int, playerId: Int = -1): DynamicCuboid {
        val native = getNative("CreateDynamicCuboid")
        val id = native.call(area.minX, area.minY, area.minZ, area.maxX, area.maxY, area.maxZ,
                worldId, interiorId, playerId) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return DynamicCuboid(id, player)
    }

    fun destroyDynamicArea(area: DynamicArea) {
        val native = getNative("DestroyDynamicArea")
        native.call(area.id)
    }

    fun isValidDynamicArea(area: DynamicArea): Boolean {
        val native = getNative("IsValidDynamicArea")
        return native.call(area.id) as Int == 1
    }

    @JvmOverloads
    fun isPlayerInDynamicArea(playerId: Int, area: DynamicArea, recheck: Int = 0): Boolean {
        val native = getNative("IsPlayerInDynamicArea")
        return native.call(playerId, area.id, recheck) as Int == 1
    }

    @JvmOverloads
    fun isPlayerInAnyDynamicArea(playerId: Int, recheck: Int = 0): Boolean {
        val native = getNative("IsPlayerInAnyDynamicArea")
        return native.call(playerId, recheck) as Int == 1
    }

    @JvmOverloads
    fun isAnyPlayerInDynamicArea(area: DynamicArea, recheck: Int = 0): Boolean {
        val native = getNative("IsAnyPlayerInDynamicArea")
        return native.call(area.id, recheck) as Int == 1
    }

    @JvmOverloads
    fun isAnyPlayerInAnyDynamicArea(recheck: Int = 0): Boolean {
        val native = getNative("IsAnyPlayerInAnyDynamicArea")
        return native.call(recheck) as Int == 1
    }

    fun isPointInDynamicArea(area: DynamicArea, point: Vector3D): Boolean {
        val native = getNative("IsPointInDynamicArea")
        return native.call(area.id, point.x, point.y, point.z) as Int == 1
    }

    fun isPointInAnyDynamicArea(point: Vector3D): Boolean {
        val native = getNative("IsPointInAnyDynamicArea")
        return native.call(point.x, point.y, point.z) as Int == 1
    }

    @JvmOverloads
    fun attachDynamicAreaToObject(area: DynamicArea, obj: DynamicObject,
                                  objectType: StreamerObjectType = StreamerObjectType.DYNAMIC,
                                  playerId: Int = 0xFFFF, offset: Vector3D) {
        val native = getNative("AttachDynamicAreaToObject")
        native.call(area.id, obj.id, objectType.value, playerId, offset.x, offset.y, offset.z)
    }

    fun attachDynamicAreaToPlayer(area: DynamicArea, player: Player, offset: Vector3D) {
        val native = getNative("AttachDynamicAreaToPlayer")
        native.call(area.id, player.id, offset.x, offset.y, offset.z)
    }

    fun attachDynamicAreaToVehicle(area: DynamicArea, vehicle: Vehicle, offset: Vector3D) {
        val native = getNative("AttachDynamicAreaToVehicle")
        native.call(area.id, vehicle.id, offset.x, offset.y, offset.z)
    }

    private fun getNative(name: String) = functions[name] ?: throw NativeNotFoundException(name)
}