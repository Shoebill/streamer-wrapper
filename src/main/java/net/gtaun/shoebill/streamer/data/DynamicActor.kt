package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.`object`.Destroyable
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.constant.SkinModel
import net.gtaun.shoebill.data.AngledLocation
import net.gtaun.shoebill.data.Animation
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.streamer.Functions
import java.util.*

/**
 * Created by marvin on 30.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
class DynamicActor internal constructor(id: Int, val model: SkinModel, val player: Player? = null,
                                        val streamDistance: Float, val area: DynamicArea? = null,
                                        val priority: Int = 0) : Destroyable {

    var id: Int = id
        private set
        get

    var virtualWorld: Int
        get() = Functions.getDynamicActorVirtualWorld(this)
        set(value) = Functions.setDynamicActorVirtualWorld(this, value)

    var angle: Float
        get() = Functions.getDynamicActorFacingAngle(this)
        set(value) = Functions.setDynamicActorFacingAngle(this, value)

    var position: Vector3D
        get() = Functions.getDynamicActorPos(this)
        set(value) = Functions.setDynamicActorPos(this, value)

    var location: AngledLocation
        get() = AngledLocation(position, virtualWorld, angle)
        set(value) {
            position = value
            virtualWorld = value.worldId
            angle = value.angle
        }

    var health: Float
        get() = Functions.getDynamicActorHealth(this)
        set(value) = Functions.setDynamicActorHealth(this, value)

    var isInvulnerable: Boolean
        get() = Functions.isDynamicActorInvulnerable(this)
        set(value) = Functions.setDynamicActorInvulnerable(this, value)

    fun applyAnimation(animation: Animation, delta: Float, loop: Boolean, lockX: Boolean, lockY: Boolean,
                       freeze: Boolean, time: Int) =
            Functions.applyDynamicActorAnimation(this, animation, delta, loop, lockX, lockY, freeze, time)

    fun clearAnimations() =
            Functions.clearDynamicActorAnimations(this)

    fun isStreamedIn(forPlayer: Player): Boolean =
            Functions.isDynamicActorStreamedIn(this, forPlayer)

    override fun isDestroyed() =
            !Functions.isValidDynamicActor(this)

    override fun destroy() {
        if (isDestroyed) return

        Functions.destroyDynamicActor(this)
        objects.remove(this)
        id = -1
    }

    companion object {

        @JvmField val DEFAULT_STREAM_DISTANCE = 200f // Corresponds to STREAMER_ACTOR_SD in streamer.inc

        private var objects = mutableListOf<DynamicActor>()

        @JvmStatic
        fun get(): Set<DynamicActor> = HashSet(objects)

        @JvmStatic
        operator fun get(id: Int): DynamicActor? = objects.find { it.id == id }

        @JvmStatic
        @JvmOverloads
        fun create(model: SkinModel, location: AngledLocation, isInvulnerable: Boolean = true, health: Float = 100f,
                   player: Player? = null, streamDistance: Float = DEFAULT_STREAM_DISTANCE, area: DynamicArea? = null,
                   priority: Int = 0): DynamicActor {
            val actor = Functions.createDynamicActor(model, location, isInvulnerable, health, player,
                    streamDistance, area, priority)
            objects.add(actor)
            return actor
        }

    }

}