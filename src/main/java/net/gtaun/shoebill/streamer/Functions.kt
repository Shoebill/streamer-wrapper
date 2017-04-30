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
import net.gtaun.shoebill.constant.SkinModel
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
            "Streamer_GetTickRate",
            "Streamer_SetTickRate",
            "Streamer_GetPlayerTickRate",
            "Streamer_SetPlayerTickRate",
            "Streamer_GetChunkTickRate",
            "Streamer_SetChunkTickRate",
            "Streamer_GetChunkSize",
            "Streamer_SetChunkSize",
            "Streamer_GetVisibleItems",
            "Streamer_SetVisibleItems",
            "Streamer_GetRadiusMultiplier",
            "Streamer_SetRadiusMultiplier",
            "Streamer_GetTypePriority",
            "Streamer_SetTypePriority",
            "Streamer_GetCellDistance",
            "Streamer_SetCellDistance",
            "Streamer_GetCellSize",
            "Streamer_SetCellSize",
            "Streamer_ToggleItemStatic",
            "Streamer_IsToggleItemStatic",
            "Streamer_ToggleItemInvAreas",
            "Streamer_IsToggleItemInvAreas",
            "Streamer_ToggleItemCallbacks",
            "Streamer_IsToggleItemCallbacks",
            "Streamer_ToggleErrorCallback",
            "Streamer_IsToggleErrorCallback",
            "Streamer_ProcessActiveItems",
            "Streamer_ToggleIdleUpdate",
            "Streamer_IsToggleIdleUpdate",
            "Streamer_ToggleCameraUpdate",
            "Streamer_IsToggleCameraUpdate",
            "Streamer_ToggleItemUpdate",
            "Streamer_IsToggleItemUpdate",
            "Streamer_GetLastUpdateTime",
            "Streamer_Update",
            "Streamer_UpdateEx",
            "Streamer_GetFloatData",
            "Streamer_SetFloatData",
            "Streamer_GetIntData",
            "Streamer_SetIntData",
            "Streamer_GetArrayData",
            "Streamer_SetArrayData",
            "Streamer_IsInArrayData",
            "Streamer_AppendArrayData",
            "Streamer_RemoveArrayData",
            "Streamer_GetUpperBound",
            "Streamer_GetDistanceToItem",
            "Streamer_ToggleItem",
            "Streamer_IsToggleItem",
            "Streamer_ToggleAllItems",
            "Streamer_GetItemInternalID",
            "Streamer_GetItemStreamerID",
            "Streamer_IsItemVisible",
            "Streamer_DestroyAllVisibleItems",
            "Streamer_CountVisibleItems",
            "Streamer_DestroyAllItems",
            "Streamer_CountItems",
            "Streamer_GetNearbyItems",
            "Streamer_GetAllVisibleItems",
            "Streamer_GetItemOffset",
            "Streamer_SetItemOffset",
            "Streamer_SetMaxItems",
            "Streamer_GetMaxItems",
            "CreateDynamicObject",
            "DestroyDynamicObject",
            "IsValidDynamicObject",
            "GetDynamicObjectPos",
            "SetDynamicObjectPos",
            "GetDynamicObjectRot",
            "SetDynamicObjectRot",
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
            "GetPlayerCameraTargetDynObject",
            "CreateDynamicPickup",
            "DestroyDynamicPickup",
            "IsValidDynamicPickup",
            "CreateDynamicCP",
            "DestroyDynamicCP",
            "IsValidDynamicCP",
            "IsPlayerInDynamicCP",
            "GetPlayerVisibleDynamicCP",
            "CreateDynamicRaceCP",
            "DestroyDynamicRaceCP",
            "IsValidDynamicRaceCP",
            "IsPlayerInDynamicRaceCP",
            "GetPlayerVisibleDynamicRaceCP",
            "CreateDynamicMapIcon",
            "DestroyDynamicMapIcon",
            "IsValidDynamicMapIcon",
            "CreateDynamic3DTextLabel",
            "DestroyDynamic3DTextLabel",
            "IsValidDynamic3DTextLabel",
            "GetDynamic3DTextLabelText",
            "UpdateDynamic3DTextLabelText",
            "CreateDynamicCircle",
            "CreateDynamicCylinder",
            "CreateDynamicSphere",
            "CreateDynamicRectangle",
            "CreateDynamicCuboid",
            "CreateDynamicPolygon",
            "DestroyDynamicArea",
            "IsValidDynamicArea",
            "GetDynamicPolygonPoints",
            "GetDynamicPolygonNumberPoints",
            "IsPlayerInDynamicArea",
            "IsPlayerInAnyDynamicArea",
            "IsAnyPlayerInDynamicArea",
            "IsAnyPlayerInAnyDynamicArea",
            "GetPlayerDynamicAreas",
            "GetPlayerNumberDynamicAreas",
            "IsPointInDynamicArea",
            "IsPointInAnyDynamicArea",
            "IsLineInDynamicArea",
            "IsLineInAnyDynamicArea",
            "GetDynamicAreasForPoint",
            "GetNumberDynamicAreasForPoint",
            "GetDynamicAreasForLine",
            "GetNumberDynamicAreasForLine",
            "AttachDynamicAreaToObject",
            "AttachDynamicAreaToPlayer",
            "AttachDynamicAreaToVehicle",
            "ToggleDynAreaSpectateMode",
            "IsToggleDynAreaSpectateMode",
            "CreateDynamicActor",
            "DestroyDynamicActor",
            "IsValidDynamicActor",
            "IsDynamicActorStreamedIn",
            "GetDynamicActorVirtualWorld",
            "SetDynamicActorVirtualWorld",
            "ApplyDynamicActorAnimation",
            "ClearDynamicActorAnimations",
            "GetDynamicActorFacingAngle",
            "SetDynamicActorFacingAngle",
            "GetDynamicActorPos",
            "SetDynamicActorPos",
            "GetDynamicActorHealth",
            "SetDynamicActorHealth",
            "SetDynamicActorInvulnerable",
            "IsDynamicActorInvulnerable",
            "GetPlayerTargetDynamicActor",
            "GetPlayerCameraTargetDynActor",
            "CreateDynamicObjectEx",
            "CreateDynamicPickupEx",
            "CreateDynamicCPEx",
            "CreateDynamicRaceCPEx",
            "CreateDynamicMapIconEx",
            "CreateDynamic3DTextLabelEx",
            "CreateDynamicCircleEx",
            "CreateDynamicCylinderEx",
            "CreateDynamicSphereEx",
            "CreateDynamicRectangleEx",
            "CreateDynamicCuboidEx",
            "CreateDynamicPolygonEx",
            "CreateDynamicActorEx").distinct()

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
        } else if (errors == 0) {
            logger.info("Successfully found all ${FUNCTION_LIST.size} native functions from streamer native plugin.")
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

        val native = get("CreateDynamicObject")

        val id = native.call(modelId, location.x, location.y, location.z, rotation.x, rotation.y, rotation.z,
                        location.worldId, location.interiorId, playerId, streamDistance, drawDistance,
                area, priority) as Int

        return DynamicObject(id, modelId, playerId, streamDistance, drawDistance)
    }

    fun destroyDynamicObject(obj: DynamicObject) = destroyDynamicObject(obj.id)

    fun destroyDynamicObject(id: Int): Any =
            get("DestroyDynamicObject").call(id)

    fun isValidDynamicObject(obj: DynamicObject) = isValidDynamicObject(obj.id)

    fun isValidDynamicObject(id: Int) =
            get("IsValidDynamicObject").call(id) as Int == 1

    fun setDynamicObjectPos(obj: DynamicObject, pos: Vector3D) = setDynamicObjectPos(obj.id, pos)

    fun setDynamicObjectPos(id: Int, pos: Vector3D) = setDynamicObjectPos(id, pos.x, pos.y, pos.z)

    fun setDynamicObjectPos(id: Int, x: Float, y: Float, z: Float) {
        get("SetDynamicObjectPos").call(id, x, y, z)
    }

    fun getDynamicObjectPos(obj: DynamicObject) = getDynamicObjectPos(obj.id)

    fun getDynamicObjectPos(id: Int): Vector3D {
        val native = get("GetDynamicObjectPos")

        val refX = ReferenceFloat(0.0f)
        val refY = ReferenceFloat(0.0f)
        val refZ = ReferenceFloat(0.0f)

        native.call(id, refX, refY, refZ)
        return Vector3D(refX.value, refY.value, refZ.value)
    }

    fun setDynamicObjectRot(obj: DynamicObject, rot: Vector3D) = setDynamicObjectRot(obj.id, rot)

    fun setDynamicObjectRot(id: Int, rot: Vector3D) = setDynamicObjectRot(id, rot.x, rot.y, rot.z)

    fun setDynamicObjectRot(id: Int, x: Float, y: Float, z: Float) {
        get("SetDynamicObjectRot").call(id, x, y, z)
    }

    fun getDynamicObjectRot(obj: DynamicObject) = getDynamicObjectRot(obj.id)

    fun getDynamicObjectRot(id: Int): Vector3D {
        val native = get("GetDynamicObjectRot")
        val refX = ReferenceFloat(0.0f)
        val refY = ReferenceFloat(0.0f)
        val refZ = ReferenceFloat(0.0f)
        native.call(id, refX, refY, refZ)
        return Vector3D(refX.value, refY.value, refZ.value)
    }

    fun moveDynamicObject(id: Int, newPos: Vector3D, speed: Float, newRot: Vector3D): Any =
            get("MoveDynamicObject").call(id, newPos.x, newPos.y, newPos.z, speed, newRot.x, newRot.y, newRot.z)

    fun stopDynamicObject(id: Int): Any =
            get("StopDynamicObject").call(id)

    fun isDynamicObjectMoving(id: Int) =
            get("IsDynamicObjectMoving").call(id) as Int == 1

    fun attachCameraToDynamicObject(playerId: Int, objectId: Int): Any =
            get("AttachCameraToDynamicObject").call(playerId, objectId)

    fun attachDynamicObjectToObject(obj: Int, toObject: Int, offsetX: Float, offsetY: Float, offsetZ: Float,
                                    rotX: Float, rotY: Float, rotZ: Float, syncRotation: Boolean): Any =
            get("AttachDynamicObjectToObject")
                    .call(obj, toObject, offsetX, offsetY, offsetZ, rotX, rotY, rotZ, if (syncRotation) 1 else 0)

    fun attachDynamicObjectToPlayer(obj: Int, playerId: Int, offsetX: Float, offsetY: Float, offsetZ: Float,
                                    rotX: Float, rotY: Float, rotZ: Float): Any =
            get("AttachDynamicObjectToPlayer").call(obj, playerId, offsetX, offsetY, offsetZ, rotX, rotY, rotZ)

    fun attachDynamicObjectToVehicle(obj: Int, vehicle: Int, offsetX: Float, offsetY: Float, offsetZ: Float,
                                     rotX: Float, rotY: Float, rotZ: Float): Any =
            get("AttachDynamicObjectToVehicle").call(obj, vehicle, offsetX, offsetY, offsetZ, rotX, rotY, rotZ)

    fun editDynamicObject(playerId: Int, objectId: Int): Any =
            get("EditDynamicObject").call(playerId, objectId)

    fun isDynamicObjectMaterialUsed(objectid: Int, materialIndex: Int) =
            get("IsDynamicObjectMaterialUsed").call(objectid, materialIndex) as Int == 1

    fun getDynamicObjectMaterial(objectId: Int, materialIndex: Int): DynamicObjectMaterial {
        val native = get("GetDynamicObjectMaterial")

        val refModel = ReferenceInt(0)
        val refMaterialColor = ReferenceInt(0)
        val refTxdName = ReferenceString("", 128)
        val refTextureName = ReferenceString("", 128)

        native.call(objectId, materialIndex, refModel, refTxdName, refTextureName,
                refMaterialColor, refTxdName.length, refTextureName.length)
        return DynamicObjectMaterial(refModel.value, refMaterialColor.value, refTxdName.value, refTextureName.value)
    }

    fun setDynamicObjectMaterial(objectId: Int, materialIndex: Int, modelId: Int, txdName: String, textureName: String,
                                 materialColor: Int): Any =
            get("SetDynamicObjectMaterial").call(objectId, materialIndex, modelId, txdName, textureName, materialColor)

    fun isDynamicObjectMaterialTextUsed(objectId: Int, materialIndex: Int) =
            get("IsDynamicObjectMaterialTextUsed").call(objectId, materialIndex) as Int == 1

    fun getDynamicObjectMaterialText(objectId: Int, materialIndex: Int): DynamicObjectMaterialText {
        val native = get("GetDynamicObjectMaterialText")
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
        val native = get("SetDynamicObjectMaterialText")
        native.call(objectId, materialIndex, text, materialSize.value, fontFace,
                fontSize, if (bold) 1 else 0, fontColor, backColor, textAlignment)
    }

    @JvmOverloads
    fun createDynamicPickup(modelId: Int, type: Int, location: Location, playerId: Int = -1, streamDistance: Float = 200f,
                            area: Int = -1, priority: Int = 0): DynamicPickup {
        val native = get("CreateDynamicPickup")
        val id = native.call(modelId, type, location.x, location.y, location.z, location.worldId,
                location.interiorId, playerId, streamDistance, area, priority) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return DynamicPickup(id, modelId, type, player, streamDistance)
    }

    fun destroyDynamicPickup(id: Int): Any =
            get("DestroyDynamicPickup").call(id)

    fun isValidDynamicPickup(id: Int) =
            get("IsValidDynamicPickup").call(id) as Int == 1

    @JvmOverloads
    fun createDynamic3DTextLabel(text: String, color: Color, location: Location,
                                 drawDistance: Float = 200f, attachedPlayer: Int = -1, attachedVehicle: Int = -1,
                                 testLOS: Int = 0, playerId: Int = -1, streamDistance: Float = 200f,
                                 area: Int = -1, priority: Int = 0): Dynamic3DTextLabel {
        val native = get("CreateDynamic3DTextLabel")
        val id = native.call(text, color.value, location.x, location.y, location.z,
                drawDistance, attachedPlayer, attachedVehicle, testLOS, location.worldId, location.interiorId, playerId,
                streamDistance, area, priority) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return Dynamic3DTextLabel(id, player, location, color, streamDistance, drawDistance)
    }

    fun destroyDynamic3DTextLabel(id: Int): Any =
            get("DestroyDynamic3DTextLabel").call(id)

    fun isValidDynamic3DTextLabel(id: Int) =
            get("IsValidDynamic3DTextLabel").call(id) as Int == 1

    fun getDynamic3DTextLabelText(id: Int): String {
        val native = get("GetDynamic3DTextLabelText")
        val refText = ReferenceString("", 1024)
        native.call(id, refText, 1024)
        return refText.value
    }

    fun updateDynamic3DTextLabelText(id: Int, color: Color, text: String) {
        get("UpdateDynamic3DTextLabel").call(id, color.value, text)
    }

    @JvmOverloads
    fun update(player: Player, streamerType: StreamerType = StreamerType.ALL): Any =
            get("Streamer_Update").call(player.id, streamerType.value)

    @JvmOverloads
    fun updateEx(player: Player, x: Float, y: Float, z: Float, worldId: Int, interiorId: Int,
                 streamerType: StreamerType = StreamerType.ALL, compensatedTime: Int = -1): Any =
            get("Streamer_UpdateEx").call(player.id, x, y, z, worldId, interiorId, streamerType.value, compensatedTime)

    @JvmOverloads
    fun createDynamicMapIcon(location: Location, type: Int, color: Color, playerId: Int = -1,
                             streamDistance: Float = 200f,
                             style: MapIconStyle = MapIconStyle.LOCAL, area: DynamicArea? = null,
                             priority: Int = 0): DynamicMapIcon {
        val native = get("CreateDynamicMapIcon")
        val areaId = area?.id ?: -1

        val id = native.call(location.x, location.y, location.z, type, color.value, location.worldId,
                location.interiorId, playerId, streamDistance, style.value, areaId, priority) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        if (id <= 0) throw CreationFailedException("CreateDynamicMapIcon returned an invalid id.")
        return DynamicMapIcon(id, location, type, color, player, streamDistance, style)
    }

    fun destroyDynamicMapIcon(mapIcon: DynamicMapIcon): Any =
            get("DestroyDynamicMapIcon").call(mapIcon.id)

    fun isValidDynamicMapIcon(mapIcon: DynamicMapIcon) =
            get("IsValidDynamicMapIcon").call(mapIcon.id) as Int == 1

    @JvmOverloads
    fun createDynamicCircle(location: Location, size: Float, playerId: Int = -1): DynamicCircle {
        val native = get("CreateDynamicCircle")
        val id = native.call(location.x, location.y, size, location.worldId, location.interiorId, playerId) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return DynamicCircle(id, player)
    }

    @JvmOverloads
    fun createDynamicSphere(location: Location, size: Float, playerId: Int = -1): DynamicSphere {
        val native = get("CreateDynamicSphere")
        val id = native.call(location.x, location.y, location.z, size, location.worldId,
                location.interiorId, playerId) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return DynamicSphere(id, player)
    }

    @JvmOverloads
    fun createDynamicRectangle(area: Area, worldId: Int, interiorId: Int, playerId: Int = -1): DynamicRectangle {
        val native = get("CreateDynamicRectangle")
        val id = native.call(area.minX, area.minY, area.maxX, area.maxY, worldId, interiorId, playerId) as Int
        val player = if(playerId != -1) Player.get(playerId) else null
        return DynamicRectangle(id, player)
    }

    @JvmOverloads
    fun createDynamicCuboid(area: Area3D, worldId: Int, interiorId: Int, playerId: Int = -1): DynamicCuboid {
        val native = get("CreateDynamicCuboid")
        val id = native.call(area.minX, area.minY, area.minZ, area.maxX, area.maxY, area.maxZ,
                worldId, interiorId, playerId) as Int
        val player = Player.get(playerId)
        return DynamicCuboid(id, player)
    }

    @JvmOverloads
    fun createDynamicCp(location: Radius, playerId: Int = -1, streamDistance: Float,
                        areaId: Int = -1, priority: Int = 0): DynamicCheckpoint {
        val native = get("CreateDynamicCP")
        val id = native.call(location.x, location.y, location.z, location.radius, location.worldId, location.interiorId,
                playerId, streamDistance, areaId, priority) as Int
        val player = Player.get(playerId)
        return DynamicCheckpoint(id, location, player, streamDistance, DynamicArea[areaId], priority)
    }

    fun createDynamicRaceCp(type: RaceCheckpointType, location: Radius, next: Vector3D, player: Player? = null,
                            streamDistance: Float = 200f, area: DynamicArea? = null,
                            priority: Int = 0): DynamicRaceCheckpoint {
        val native = get("CreateDynamicRaceCP")
        val playerId = player?.id ?: -1
        val areaId = area?.id ?: -1
        val id = native.call(type.value, location.x, location.y, location.z, next.x, next.y, next.z, location.radius,
                location.worldId, location.interiorId, playerId, streamDistance, areaId, priority) as Int
        return DynamicRaceCheckpoint(id, type, location, next, player, streamDistance, area, priority)
    }

    fun destroyDynamicRaceCp(raceCheckpoint: DynamicRaceCheckpoint) =
            get("DestroyDynamicRaceCP").call(raceCheckpoint.id) as Int == 1

    fun isValidDynamicRaceCp(raceCheckpoint: DynamicRaceCheckpoint) =
            get("IsValidDynamicRaceCP").call(raceCheckpoint.id) as Int == 1

    fun togglePlayerDynamicRaceCp(player: Player, raceCheckpoint: DynamicRaceCheckpoint, toggle: Boolean) =
            get("TogglePlayerDynamicRaceCP").call(player.id, raceCheckpoint.id, if (toggle) 1 else 0) as Int == 1

    fun togglePlayerAllDynamicRaceCPs(player: Player, toggle: Boolean) =
            get("TogglePlayerAllDynamicRaceCPs").call(player.id, if (toggle) 1 else 0) as Int == 1

    fun isPlayerInDynamicRaceCp(player: Player, raceCheckpoint: DynamicRaceCheckpoint) =
            get("IsPlayerInDynamicRaceCP").call(player.id, raceCheckpoint.id) as Int == 1

    fun getPlayerVisibleDynamicRaceCp(player: Player): DynamicRaceCheckpoint? =
            DynamicRaceCheckpoint[get("GetPlayerVisibleDynamicRaceCP").call(player.id) as Int]

    fun destroyDynamicCp(checkpoint: DynamicCheckpoint) =
            get("DestroyDynamicCP").call(checkpoint.id) as Int == 1

    fun isValidDynamicCp(checkpoint: DynamicCheckpoint) =
            get("IsValidDynamicCP").call(checkpoint.id) as Int == 1

    fun togglePlayerDynamicCp(player: Player, checkpoint: DynamicCheckpoint, toggle: Boolean): Any =
            get("TogglePlayerDynamicCP").call(player.id, checkpoint.id, if (toggle) 1 else 0)

    fun togglePlayerAllDynamicCps(player: Player, toggle: Boolean): Any =
            get("TogglePlayerAllDynamicCPs").call(player.id, if (toggle) 1 else 0)

    fun isPlayerInDynamicCp(player: Player, checkpoint: DynamicCheckpoint) =
            get("IsPlayerInDynamicCP").call(player.id, checkpoint.id) as Int == 1

    fun getPlayerVisibleDynamicCp(player: Player): DynamicCheckpoint? =
            DynamicCheckpoint[get("GetPlayerVisibleDynamicCP").call(player.id) as Int]

    fun destroyDynamicArea(area: DynamicArea): Any = get("DestroyDynamicArea").call(area.id)

    fun isValidDynamicArea(area: DynamicArea) = get("IsValidDynamicArea").call(area.id) as Int == 1

    @JvmOverloads
    fun isPlayerInDynamicArea(playerId: Int, area: DynamicArea, recheck: Int = 0) =
            get("IsPlayerInDynamicArea").call(playerId, area.id, recheck) as Int == 1

    @JvmOverloads
    fun isPlayerInAnyDynamicArea(playerId: Int, recheck: Int = 0) =
            get("IsPlayerInAnyDynamicArea").call(playerId, recheck) as Int == 1


    @JvmOverloads
    fun isAnyPlayerInDynamicArea(area: DynamicArea, recheck: Int = 0) =
            get("IsAnyPlayerInDynamicArea").call(area.id, recheck) as Int == 1

    @JvmOverloads
    fun isAnyPlayerInAnyDynamicArea(recheck: Int = 0) = get("IsAnyPlayerInAnyDynamicArea").call(recheck) as Int == 1

    fun isPointInDynamicArea(area: DynamicArea, point: Vector3D) = get("IsPointInDynamicArea")
            .call(area.id, point.x, point.y, point.z) as Int == 1

    fun isPointInAnyDynamicArea(point: Vector3D) = get("IsPointInAnyDynamicArea")
            .call(point.x, point.y, point.z) as Int == 1

    @JvmOverloads
    fun attachDynamicAreaToObject(area: DynamicArea, obj: DynamicObject,
                                  objectType: StreamerObjectType = StreamerObjectType.DYNAMIC,
                                  playerId: Int = 0xFFFF, offset: Vector3D): Any =
            get("AttachDynamicAreaToObject")
                    .call(area.id, obj.id, objectType.value, playerId, offset.x, offset.y, offset.z)

    fun attachDynamicAreaToPlayer(area: DynamicArea, player: Player, offset: Vector3D): Any =
            get("AttachDynamicAreaToPlayer").call(area.id, player.id, offset.x, offset.y, offset.z)

    fun attachDynamicAreaToVehicle(area: DynamicArea, vehicle: Vehicle, offset: Vector3D): Any =
            get("AttachDynamicAreaToVehicle").call(area.id, vehicle.id, offset.x, offset.y, offset.z)

    fun getTickRate() = get("Streamer_GetTickRate").call() as Int

    @JvmOverloads
    fun setTickRate(rate: Int = 50) {
        get("Streamer_SetTickRate").call(rate)
    }

    fun getPlayerTickRate(player: Player) = get("Streamer_GetPlayerTickRate").call(player.id) as Int

    @JvmOverloads
    fun setPlayerTickRate(player: Player, rate: Int = 50) {
        get("Streamer_SetPlayerTickRate").call(player.id, rate)
    }

    @JvmOverloads
    fun getChunkTickRate(type: StreamerType, player: Player? = null): Int {
        val playerId = player?.id ?: -1
        val native = get("Streamer_GetChunkTickRate")
        return native.call(type.value, playerId) as Int
    }

    @JvmOverloads
    fun setChunkTickRate(type: StreamerType, rate: Int, player: Player? = null): Boolean {
        val playerId = player?.id ?: -1
        val native = get("Streamer_SetChunkTickRate")
        return native.call(type.value, rate, playerId) as Int > 0
    }

    fun getChunkSize(type: StreamerType) = get("Streamer_GetChunkSize").call(type.value) as Int

    fun setChunkSize(type: StreamerType, size: Int) = get("Streamer_SetChunkSize").call(type.value, size) as Int == 1

    fun getMaxItems(type: StreamerType) = get("Streamer_GetMaxItems").call(type.value) as Int

    @JvmOverloads
    fun setMaxItems(type: StreamerType, items: Int = -1) = get("Streamer_SetMaxItems")
            .call(type.value, items) as Int == 1

    @JvmOverloads
    fun getVisibleItems(type: StreamerType, player: Player? = null): Int {
        val native = get("Streamer_GetVisibleItems")
        val playerId = player?.id ?: -1
        return native.call(type.value, playerId) as Int
    }

    @JvmOverloads
    fun setVisibleItems(type: StreamerType, items: Int, player: Player? = null): Boolean {
        val native = get("Streamer_SetVisibleItems")
        val playerId = player?.id ?: -1
        return native.call(type.value, items, playerId) as Int > 0
    }

    @JvmOverloads
    fun getRadiusMultiplier(type: StreamerType, player: Player? = null): Float? {
        val native = get("Streamer_GetRadiusMultiplier")
        val playerId = player?.id ?: -1
        val refFloat = ReferenceFloat(0f)
        val result = native.call(type.value, refFloat, playerId) as Int > 0
        if (result)
            return refFloat.value
        else
            return null
    }

    @JvmOverloads
    fun setRadiusMultiplier(type: StreamerType, multiplier: Float, player: Player? = null): Boolean {
        val native = get("Streamer_SetRadiusMultiplier")
        val playerId = player?.id ?: -1
        return native.call(type.value, multiplier, playerId) as Int > 0
    }

    //TODO: Implement Streamer_GetTypePriority and Streamer_SetTypePriority (array trouble).

    fun getCellDistance(): Float? {
        val native = get("Streamer_GetCellDistance")
        val refFloat = ReferenceFloat(0f)
        return if (native.call(refFloat) as Int == 1) refFloat.value else null
    }

    @JvmOverloads
    fun setCellDistance(distance: Float = 600f) {
        get("Streamer_SetCellDistance").call(distance)
    }

    fun getCellSize(): Float? {
        val native = get("Streamer_GetCellSize")
        val refFloat = ReferenceFloat(0f)
        return if (native.call(refFloat) as Int == 1) refFloat.value else null
    }

    @JvmOverloads
    fun setCellSize(size: Float = 300f) = get("Streamer_SetCellSize").call(size) as Int == 1

    fun toggleItemStatic(type: StreamerType, id: Int, toggle: Boolean) = get("Streamer_ToggleItemStatic")
            .call(type.value, id, if (toggle) 1 else 0) as Int == 1

    fun isToggleItemStatic(type: StreamerType, id: Int) = get("Streamer_IsToggleItemStatic")
            .call(type.value, id) as Int == 1

    fun toggleItemInvAreas(type: StreamerType, id: Int, toggle: Boolean) = get("Streamer_ToggleItemInvAreas")
            .call(type.value, id, if (toggle) 1 else 0) as Int == 1

    fun isToggleItemInvAreas(type: StreamerType, id: Int) = get("Streamer_IsToggleItemInvAreas")
            .call(type.value, id) as Int == 1

    fun toggleItemCallbacks(type: StreamerType, id: Int, toggle: Boolean) = get("Streamer_ToggleItemCallbacks")
            .call(type.value, id, if (toggle) 1 else 0) as Int == 1

    fun isToggleItemCallbacks(type: StreamerType, id: Int) = get("Streamer_IsToggleItemCallbacks")
            .call(type.value, id) as Int == 1

    fun toggleErrorCallback(toggle: Boolean) {
        get("Streamer_ToggleErrorCallback").call(if (toggle) 1 else 0)
    }

    fun isToggleErrorCallback() = get("Streamer_IsToggleErrorCallback").call() as Int == 1

    @JvmOverloads
    fun createDynamicActor(model: SkinModel, location: AngledLocation, invulnerable: Boolean = true, health: Float = 100f,
                           player: Player? = null, streamDistance: Float = 200f, area: DynamicArea? = null,
                           priority: Int = 0): DynamicActor {
        val native = get("CreateDynamicActor")
        val playerId = player?.id ?: -1
        val areaId = area?.id ?: -1

        val id = native.call(model.id, location.x, location.y, location.z, location.angle, if (invulnerable) 1 else 0,
                health, location.worldId, location.interiorId, playerId, streamDistance, areaId, priority) as Int
        return DynamicActor(id, model, player, streamDistance, area, priority)
    }

    fun destroyDynamicActor(actor: DynamicActor) =
            get("DestroyDynamicActor").call(actor.id) as Int == 1

    fun isValidDynamicActor(actor: DynamicActor) =
            get("IsValidDynamicActor").call(actor.id) as Int == 1

    fun isDynamicActorStreamedIn(actor: DynamicActor, forPlayer: Player) =
            get("IsDynamicActorStreamedIn").call(actor.id, forPlayer.id) as Int == 1

    fun getDynamicActorVirtualWorld(actor: DynamicActor) =
            get("GetDynamicActorVirtualWorld").call(actor.id) as Int

    fun setDynamicActorVirtualWorld(actor: DynamicActor, worldId: Int) {
        get("SetDynamicActorVirtualWorld").call(actor.id, worldId)
    }

    fun applyDynamicActorAnimation(actor: DynamicActor, animation: Animation, fDelta: Float, loop: Boolean, lockX: Boolean,
                                   lockY: Boolean, freeze: Boolean, time: Int): Any =
            get("ApplyDynamicActorAnimation").call(actor.id, animation.library, animation.name, fDelta, if (loop) 1 else 0,
                    if (lockX) 1 else 0, if (lockY) 1 else 0, if (freeze) 1 else 0, time)

    fun clearDynamicActorAnimations(actor: DynamicActor): Any =
            get("ClearDynamicActorAnimations").call(actor.id)

    fun getDynamicActorFacingAngle(actor: DynamicActor): Float {
        val native = get("GetDynamicActorFacingAngle")
        val refFloat = ReferenceFloat(0f)
        native.call(actor.id, refFloat)
        return refFloat.value
    }

    fun setDynamicActorFacingAngle(actor: DynamicActor, angle: Float) {
        get("SetDynamicActorFacingAngle").call(actor.id, angle)
    }

    fun getDynamicActorPos(actor: DynamicActor): Vector3D {
        val native = get("GetDynamicActorPos")
        val refX = ReferenceFloat(0f)
        val refY = ReferenceFloat(0f)
        val refZ = ReferenceFloat(0f)
        native.call(actor.id, refX, refY, refZ)
        return Vector3D(refX.value, refY.value, refZ.value)
    }

    fun setDynamicActorPos(actor: DynamicActor, position: Vector3D) {
        get("SetDynamicActorPos").call(actor.id, position.x, position.y, position.z)
    }

    fun getDynamicActorHealth(actor: DynamicActor): Float {
        val native = get("GetDynamicActorHealth")
        val refHealth = ReferenceFloat(0f)
        native.call(actor.id, refHealth)
        return refHealth.value
    }

    fun setDynamicActorHealth(actor: DynamicActor, health: Float) {
        get("SetDynamicActorHealth").call(actor.id, health)
    }

    fun setDynamicActorInvulnerable(actor: DynamicActor, isInvulnerable: Boolean) {
        get("SetDynamicActorInvulnerable").call(actor.id, if (isInvulnerable) 1 else 0)
    }

    fun isDynamicActorInvulnerable(actor: DynamicActor) =
            get("IsDynamicActorInvulnerable").call(actor.id) as Int == 1

    fun getPlayerTargetDynamicActor(player: Player): DynamicActor? =
            DynamicActor[get("GetPlayerTargetDynamicActor").call(player.id) as Int]

    fun getPlayerCameraTargetDynamicActor(player: Player): DynamicActor? =
            DynamicActor[get("GetPlayerCameraTargetDynActor").call(player.id) as Int]

    private fun get(name: String) = functions[name] ?: throw NativeNotFoundException(name)
}
