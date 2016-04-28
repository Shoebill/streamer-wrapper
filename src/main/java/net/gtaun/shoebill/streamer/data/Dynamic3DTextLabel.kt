@file:JvmName("Dynamic3DTextLabel")

package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.data.Color
import net.gtaun.shoebill.data.Location
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.`object`.Destroyable
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.`object`.Vehicle
import net.gtaun.shoebill.streamer.Functions

import java.util.ArrayList
import java.util.HashSet

/**
 * Created by valych and marvin on 11.01.2016 in project streamer-wrapper.
 *
 */
class Dynamic3DTextLabel internal constructor(id: Int, text: String, val playerId: Int, var color: Color,
                                              val streamDistance: Float, val drawDistance: Float) : Destroyable {

    var id: Int = id
        private set
        get

    var text: String
        get() = Functions.getDynamic3DTextLabelText(this.id)
        set(newValue) = Functions.updateDynamic3DTextLabelText(id, color, newValue)

    init {
        this.text = text
    }

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

    override fun isDestroyed(): Boolean {
        return !Functions.isValidDynamic3DTextLabel(id)
    }

    protected companion object {

        @JvmField
        val DEFAULT_STREAM_DISTANCE = 200f // Corresponds to STREAMER_3D_TEXT_LABEL_SD in streamer.inc

        private var textLabels: MutableList<Dynamic3DTextLabel> = mutableListOf()

        @JvmStatic fun get(): Set<Dynamic3DTextLabel> {
            return HashSet(textLabels)
        }

        @JvmStatic operator fun get(id: Int): Dynamic3DTextLabel? {
            return textLabels.find { it.id == id }
        }

        @JvmStatic
        @JvmOverloads
        fun create(text: String, color: Color, location: Location, drawDistance: Float = 200f, testLOS: Int = 0,
                   streamDistance: Float = DEFAULT_STREAM_DISTANCE, priority: Int = 0, attachedPlayer: Player? = null,
                   attachedVehicle: Vehicle? = null, player: Player? = null,
                   area: DynamicArea? = null): Dynamic3DTextLabel {

            val attachedVehicleId = if (attachedVehicle == null) 0xFFFF else attachedVehicle.id
            val attachedPlayerId = if (attachedPlayer == null) 0xFFFF else attachedPlayer.id
            val playerId = if (player == null) 0xFFFF else player.id
            val areaId = if (area == null) -1 else area.id

            val textLabel = Functions.createDynamic3DTextLabel(text, color, location, drawDistance, attachedPlayerId,
                    attachedVehicleId, testLOS, playerId, streamDistance, areaId, priority)
            textLabels.add(textLabel)
            return textLabel
        }
    }
}
