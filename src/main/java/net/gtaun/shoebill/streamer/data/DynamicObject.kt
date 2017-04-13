package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.`object`.Destroyable
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.`object`.Vehicle
import net.gtaun.shoebill.constant.ObjectMaterialSize
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.Functions
import net.gtaun.shoebill.streamer.Streamer
import net.gtaun.shoebill.streamer.event.DynamicObjectMovedEvent
import net.gtaun.shoebill.streamer.event.PlayerEditDynamicObjectEvent
import net.gtaun.shoebill.streamer.event.PlayerSelectDynamicObjectEvent
import net.gtaun.shoebill.streamer.event.PlayerShootDynamicObjectEvent
import net.gtaun.util.event.Attentions
import net.gtaun.util.event.EventHandler
import net.gtaun.util.event.HandlerEntry
import net.gtaun.util.event.HandlerPriority
import java.util.*


/**
 * @author Marvin Haschker
 */
@AllOpen
class DynamicObject(id: Int, val modelid: Int, val playerid: Int, val streamDistance: Float,
                    val drawDistance: Float) : Destroyable {

    final var id: Int = id
        private set

    var position: Vector3D
        get() = Functions.getDynamicObjectPos(this)
        set(newPos) = Functions.setDynamicObjectPos(this, newPos)

    var rotation: Vector3D
        get() = Functions.getDynamicObjectRot(this)
        set(newRot) = Functions.setDynamicObjectRot(this, newRot)

    val isMoving: Boolean
        get() = Functions.isDynamicObjectMoving(id)

    fun movePosition(newPos: Vector3D, speed: Float) = move(newPos, speed, rotation)

    fun moveRotation(newRot: Vector3D, speed: Float) = move(position, speed, newRot)

    fun move(pos: Vector3D, speed: Float, rot: Vector3D) = Functions.moveDynamicObject(id, pos, speed, rot)

    fun stop() = Functions.stopDynamicObject(id)

    fun attachCamera(player: Player) = Functions.attachCameraToDynamicObject(player.id, id)

    @JvmOverloads
    fun attach(target: DynamicObject, offset: Vector3D, rotation: Vector3D, syncRotation: Boolean = true) =
            Functions.attachDynamicObjectToObject(id, target.id, offset.x, offset.y, offset.z, rotation.x,
                    rotation.y, rotation.z, syncRotation)

    fun attach(target: Player, offset: Vector3D, rotation: Vector3D) =
            Functions.attachDynamicObjectToPlayer(id, target.id, offset.x, offset.y, offset.z, rotation.x,
                    rotation.y, rotation.z)

    fun attach(target: Vehicle, offset: Vector3D, rotation: Vector3D) =
            Functions.attachDynamicObjectToVehicle(id, target.id, offset.x, offset.y, offset.z, rotation.x,
                    rotation.y, rotation.z)

    fun edit(player: Player) = Functions.editDynamicObject(player.id, this.id)

    fun isMaterialUsed(materialIndex: Int): Boolean =
            Functions.isDynamicObjectMaterialUsed(id, materialIndex)

    fun getMaterial(materialindex: Int): DynamicObjectMaterial =
            Functions.getDynamicObjectMaterial(id, materialindex)

    @JvmOverloads
    fun setMaterial(materialIndex: Int, modelid: Int, txdname: String, textureName: String, materialColor: Int = 0) =
            Functions.setDynamicObjectMaterial(id, materialIndex, modelid, txdname, textureName, materialColor)

    fun isMaterialTextUsed(materialindex: Int): Boolean =
            Functions.isDynamicObjectMaterialTextUsed(id, materialindex)

    fun getMaterialText(materialindex: Int): DynamicObjectMaterialText =
            Functions.getDynamicObjectMaterialText(id, materialindex)

    @JvmOverloads
    fun setMaterialText(materialIndex: Int, text: String,
                        size: ObjectMaterialSize = ObjectMaterialSize.SIZE_256x128,
                        fontFace: String = "Arial", fontSize: Int = 24,
                        bold: Boolean = true, fontColor: Int = 0xFFFFFFFF.toInt(),
                        backColor: Int = 0, textAlignment: Int = 0) =
            Functions.setDynamicObjectMaterialText(id, materialIndex, text, size, fontFace, fontSize, bold,
                    fontColor, backColor, textAlignment)

    override fun destroy() {
        if (isDestroyed)
            return

        Functions.destroyDynamicObject(this)
        id = -1
        eventHandlers.forEach { it.cancel() }
        eventHandlers.clear()
        removeSelf()
    }

    private fun removeSelf() = objects.remove(this)

    override fun isDestroyed(): Boolean = !Functions.isValidDynamicObject(id)

    private var eventHandlers: MutableList<HandlerEntry> = mutableListOf()

    fun onObjectMoved(handler: (DynamicObjectMovedEvent) -> Unit): HandlerEntry =
            onObjectMoved(EventHandler { handler(it) })

    fun onObjectMoved(handler: EventHandler<DynamicObjectMovedEvent>): HandlerEntry {
        val entry = eventManagerNode.registerHandler(DynamicObjectMovedEvent::class.java, HandlerPriority.NORMAL,
                Attentions.create().`object`(this), handler)
        eventHandlers.add(entry)
        return entry
    }

    fun onPlayerEditObject(handler: (PlayerEditDynamicObjectEvent) -> Unit): HandlerEntry =
            onPlayerEditObject(EventHandler { handler(it) })

    fun onPlayerEditObject(handler: EventHandler<PlayerEditDynamicObjectEvent>): HandlerEntry {
        val entry = eventManagerNode.registerHandler(PlayerEditDynamicObjectEvent::class.java, HandlerPriority.NORMAL,
                Attentions.create().`object`(this), handler)
        eventHandlers.add(entry)
        return entry
    }

    fun onPlayerSelectObject(handler: EventHandler<PlayerSelectDynamicObjectEvent>): HandlerEntry {
        val entry = eventManagerNode.registerHandler(PlayerSelectDynamicObjectEvent::class.java, HandlerPriority.NORMAL,
                Attentions.create().`object`(this), handler)
        eventHandlers.add(entry)
        return entry
    }

    fun onPlayerSelectObject(handler: (PlayerSelectDynamicObjectEvent) -> Unit): HandlerEntry =
            onPlayerSelectObject(EventHandler { handler(it) })

    fun onPlayerShootObject(handler: EventHandler<PlayerShootDynamicObjectEvent>): HandlerEntry {
        val entry = eventManagerNode.registerHandler(PlayerShootDynamicObjectEvent::class.java, HandlerPriority.NORMAL,
                Attentions.create().`object`(this), handler)
        eventHandlers.add(entry)
        return entry
    }

    fun onPlayerShootObject(handler: (PlayerShootDynamicObjectEvent) -> Unit): HandlerEntry =
            onPlayerShootObject(EventHandler { handler(it) })

    companion object {

        @JvmField val DEFAULT_STREAM_DISTANCE = 300f // Corresponds to STREAMER_OBJECT_SD in streamer.inc
        @JvmField val DEFAULT_DRAW_DISTANCE = 0f // Corresponds to STREAMER_OBJECT_DD in streamer.inc

        private var objects = mutableListOf<DynamicObject>()
        private val eventManagerNode = Streamer.get().eventManager

        @JvmStatic
        fun get(): Set<DynamicObject> = HashSet(objects)

        @JvmStatic
        operator fun get(id: Int): DynamicObject? = objects.find { it.id == id }

        @JvmOverloads
        @JvmStatic
        fun create(modelId: Int, location: Location, rotation: Vector3D = Vector3D(0f, 0f, 0f),
                   streamDistance: Float = DynamicObject.DEFAULT_STREAM_DISTANCE,
                   drawDistance: Float = DynamicObject.DEFAULT_DRAW_DISTANCE,
                   priority: Int = 0, player: Player? = null, area: DynamicArea? = null): DynamicObject {
            val playerId = player?.id ?: -1
            val areaId = area?.id ?: -1

            val `object` = Functions.createDynamicObject(modelId, location, rotation, streamDistance, drawDistance,
                    playerId, areaId, priority)



            objects.add(`object`)
            return `object`
        }
    }
}
