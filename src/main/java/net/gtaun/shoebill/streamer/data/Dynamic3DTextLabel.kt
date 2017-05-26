package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.entities.Destroyable
import net.gtaun.shoebill.entities.Player
import net.gtaun.shoebill.entities.Vehicle
import net.gtaun.shoebill.data.Color
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.Functions
import java.util.*

/**
 * @author valych
 * @author Marvin Haschker
 */
@AllOpen
class Dynamic3DTextLabel internal constructor(id: Int, val player: Player?, val location: Location, var color: Color,
                                              val streamDistance: Float, val drawDistance: Float) : Destroyable {

    final var id: Int = id
        private set
        get

    final var text: String
        get() = Functions.getDynamic3DTextLabelText(this.id)
        set(newValue) = Functions.updateDynamic3DTextLabelText(id, color, newValue)

    override fun destroy() {
        if (isDestroyed) {
            removeSelf()
            return
        }

        Functions.destroyDynamic3DTextLabel(id)
        id = -1

        removeSelf()
    }

    private fun removeSelf() {
        if (textLabels.contains(this))
            textLabels.remove(this)
    }

    override val isDestroyed: Boolean
        get() = !Functions.isValidDynamic3DTextLabel(id)

    companion object {

        @JvmField
        val DEFAULT_STREAM_DISTANCE = 200f // Corresponds to STREAMER_3D_TEXT_LABEL_SD in streamer.inc

        private var textLabels: MutableList<Dynamic3DTextLabel> = mutableListOf()

        @JvmStatic
        fun get(): Set<Dynamic3DTextLabel> = HashSet(textLabels)

        @JvmStatic
        operator fun get(id: Int): Dynamic3DTextLabel? = textLabels.find { it.id == id }

        @JvmStatic
        @JvmOverloads
        fun create(text: String, color: Color, location: Location, drawDistance: Float = 200f, testLOS: Int = 0,
                   streamDistance: Float = DEFAULT_STREAM_DISTANCE, priority: Int = 0, attachedPlayer: Player? = null,
                   attachedVehicle: Vehicle? = null, player: Player? = null,
                   area: DynamicArea? = null): Dynamic3DTextLabel {

            val attachedVehicleId = attachedVehicle?.id ?: 0xFFFF
            val attachedPlayerId = attachedPlayer?.id ?: 0xFFFF
            val playerId = player?.id ?: 0xFFFF
            val areaId = area?.id ?: -1

            val textLabel = Functions.createDynamic3DTextLabel(text, color, location, drawDistance, attachedPlayerId,
                    attachedVehicleId, testLOS, playerId, streamDistance, areaId, priority)
            textLabels.add(textLabel)
            return textLabel
        }
    }
}
