package net.gtaun.shoebill.streamer

import net.gtaun.shoebill.Shoebill
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.amx.AmxInstanceManager
import net.gtaun.shoebill.constant.WeaponModel
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.streamer.data.*
import net.gtaun.shoebill.streamer.event.actor.DynamicActorStreamInEvent
import net.gtaun.shoebill.streamer.event.actor.DynamicActorStreamOutEvent
import net.gtaun.shoebill.streamer.event.actor.PlayerGiveDamageDynamicActorEvent
import net.gtaun.shoebill.streamer.event.area.PlayerEnterDynamicAreaEvent
import net.gtaun.shoebill.streamer.event.area.PlayerLeaveDynamicAreaEvent
import net.gtaun.shoebill.streamer.event.checkpoint.PlayerEnterDynamicCheckpointEvent
import net.gtaun.shoebill.streamer.event.checkpoint.PlayerLeaveDynamicCheckpointEvent
import net.gtaun.shoebill.streamer.event.obj.DynamicObjectMovedEvent
import net.gtaun.shoebill.streamer.event.obj.PlayerEditDynamicObjectEvent
import net.gtaun.shoebill.streamer.event.obj.PlayerSelectDynamicObjectEvent
import net.gtaun.shoebill.streamer.event.obj.PlayerShootDynamicObjectEvent
import net.gtaun.shoebill.streamer.event.pickup.PlayerPickUpDynamicPickupEvent
import net.gtaun.shoebill.streamer.event.racecheckpoint.PlayerEnterDynamicRaceCheckpointEvent
import net.gtaun.shoebill.streamer.event.racecheckpoint.PlayerLeaveDynamicRaceCheckpointEvent
import net.gtaun.shoebill.streamer.event.streamer.ItemStreamInEvent
import net.gtaun.shoebill.streamer.event.streamer.ItemStreamOutEvent
import net.gtaun.shoebill.streamer.event.streamer.PluginErrorEvent
import net.gtaun.util.event.EventManager


/**
 * @author Marvin Haschker
 */
object Callbacks {

    private val amxInstanceManager: AmxInstanceManager
        get() = Shoebill.get().amxInstanceManager

    fun registerHandlers(eventManager: EventManager) {
        amxInstanceManager.apply {
            hookCallback("OnDynamicObjectMoved", { amxCallEvent ->
                val `object` = DynamicObject[amxCallEvent.parameters[0] as Int] ?: return@hookCallback
                val event = DynamicObjectMovedEvent(`object`)
                eventManager.dispatchEvent(event, `object`)
            }, "i")
            hookCallback("OnPlayerEditDynamicObject", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int) ?: return@hookCallback
                val `object` = DynamicObject[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerEditDynamicObjectEvent(`object`, player, amxCallEvent.parameters[2] as Int,
                        Vector3D(amxCallEvent.parameters[3] as Float, amxCallEvent.parameters[4] as Float, amxCallEvent.parameters[5] as Float),
                        Vector3D(amxCallEvent.parameters[6] as Float, amxCallEvent.parameters[7] as Float, amxCallEvent.parameters[8] as Float))
                eventManager.dispatchEvent(event, player, `object`)
            }, "iiiffffff")
            hookCallback("OnPlayerSelectDynamicObject", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int) ?: return@hookCallback
                val `object` = DynamicObject[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val modelid = amxCallEvent.parameters[2] as Int
                val event = PlayerSelectDynamicObjectEvent(`object`, player, modelid,
                        Vector3D(amxCallEvent.parameters[3] as Float, amxCallEvent.parameters[4] as Float, amxCallEvent.parameters[5] as Float))
                eventManager.dispatchEvent(event, player, `object`, modelid)
            }, "iiifff")
            hookCallback("OnPlayerShootDynamicObject", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int)
                val weaponModel = WeaponModel.get(amxCallEvent.parameters[1] as Int) ?: return@hookCallback
                val dynamicObject = DynamicObject[amxCallEvent.parameters[2] as Int] ?: return@hookCallback
                val event = PlayerShootDynamicObjectEvent(dynamicObject, player, weaponModel,
                        Vector3D(amxCallEvent.parameters[3] as Float, amxCallEvent.parameters[4] as Float, amxCallEvent.parameters[5] as Float))
                eventManager.dispatchEvent(event, player, dynamicObject, weaponModel)
            }, "iiifff")

            hookCallback("OnPlayerEnterDynamicArea", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int) ?: return@hookCallback
                val area = DynamicArea[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerEnterDynamicAreaEvent(player, area)
                eventManager.dispatchEvent(event, player, area)
            }, "ii")

            hookCallback("OnPlayerLeaveDynamicArea", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int) ?: return@hookCallback
                val area = DynamicArea[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerLeaveDynamicAreaEvent(player, area)
                eventManager.dispatchEvent(event, player, area)
            }, "ii")

            hookCallback("OnPlayerPickUpDynamicPickup", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int) ?: return@hookCallback
                val pickup = DynamicPickup[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerPickUpDynamicPickupEvent(player, pickup)
                eventManager.dispatchEvent(event, player, pickup)
            }, "ii")

            hookCallback("OnPlayerEnterDynamicCP", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int) ?: return@hookCallback
                val dynamicCp = DynamicCheckpoint[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerEnterDynamicCheckpointEvent(player, dynamicCp)
                eventManager.dispatchEvent(event, player, dynamicCp)
            }, "ii")

            hookCallback("OnPlayerLeaveDynamicCP", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int) ?: return@hookCallback
                val dynamicCp = DynamicCheckpoint[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerLeaveDynamicCheckpointEvent(player, dynamicCp)
                eventManager.dispatchEvent(event, player, dynamicCp)
            }, "ii")

            hookCallback("OnPlayerEnterDynamicRaceCP", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int) ?: return@hookCallback
                val dynamicCp = DynamicRaceCheckpoint[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerEnterDynamicRaceCheckpointEvent(player, dynamicCp)
                eventManager.dispatchEvent(event, player, dynamicCp)
            }, "ii")

            hookCallback("OnPlayerLeaveDynamicRaceCP", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int) ?: return@hookCallback
                val dynamicCp = DynamicRaceCheckpoint[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerLeaveDynamicRaceCheckpointEvent(player, dynamicCp)
                eventManager.dispatchEvent(event, player, dynamicCp)
            }, "ii")

            hookCallback("OnPlayerGiveDamageDynamicActor", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int) ?: return@hookCallback
                val actor = DynamicActor[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val amount = amxCallEvent.parameters[2] as Float
                val model = WeaponModel.get(amxCallEvent.parameters[3] as Int) ?: return@hookCallback
                val bodyPart = amxCallEvent.parameters[4] as Int
                val event = PlayerGiveDamageDynamicActorEvent(player, actor, amount, model, bodyPart)
                eventManager.dispatchEvent(event, player, actor, amount, model, bodyPart)
            }, "iifii")

            hookCallback("OnDynamicActorStreamIn", { amxCallEvent ->
                val actor = DynamicActor[amxCallEvent.parameters[0] as Int] ?: return@hookCallback
                val player = Player.get(amxCallEvent.parameters[1] as Int) ?: return@hookCallback
                val event = DynamicActorStreamInEvent(actor, player)
                eventManager.dispatchEvent(event, actor, player)
            }, "ii")

            hookCallback("OnDynamicActorStreamOut", { amxCallEvent ->
                val actor = DynamicActor[amxCallEvent.parameters[0] as Int] ?: return@hookCallback
                val player = Player.get(amxCallEvent.parameters[1] as Int) ?: return@hookCallback
                val event = DynamicActorStreamOutEvent(actor, player)
                eventManager.dispatchEvent(event, actor, player)
            }, "ii")

            hookCallback("Streamer_OnItemStreamIn", { amxCallEvent ->
                val type = StreamerType[amxCallEvent.parameters[0] as Int] ?: return@hookCallback
                val id = amxCallEvent.parameters[1] as Int
                val event = ItemStreamInEvent(type, id)
                eventManager.dispatchEvent(event, type, id)
            }, "ii")

            hookCallback("Streamer_OnItemStreamOut", { amxCallEvent ->
                val type = StreamerType[amxCallEvent.parameters[0] as Int] ?: return@hookCallback
                val id = amxCallEvent.parameters[1] as Int
                val event = ItemStreamOutEvent(type, id)
                eventManager.dispatchEvent(event, type, id)
            }, "ii")

            hookCallback("Streamer_OnPluginError", { amxCallEvent ->
                val error = amxCallEvent.parameters[0] as String
                val event = PluginErrorEvent(error)
                eventManager.dispatchEvent(event, error)
            }, "s")
        }
    }

    fun unregisterHandlers() {
        amxInstanceManager.apply {
            unhookCallback("OnDynamicObjectMoved")
            unhookCallback("OnPlayerEditDynamicObject")
            unhookCallback("OnPlayerSelectDynamicObject")
            unhookCallback("OnPlayerShootDynamicObject")
            unhookCallback("OnPlayerPickUpDynamicPickup")
            unhookCallback("OnPlayerEnterDynamicArea")
            unhookCallback("OnPlayerLeaveDynamicArea")
            unhookCallback("OnPlayerPickUpDynamicPickup")
            unhookCallback("OnPlayerEnterDynamicCP")
            unhookCallback("OnPlayerLeaveDynamicCP")
            unhookCallback("OnPlayerEnterDynamicRaceCP")
            unhookCallback("OnPlayerLeaveDynamicRaceCP")
            unhookCallback("OnPlayerGiveDamageDynamicActor")
            unhookCallback("OnDynamicActorStreamIn")
            unhookCallback("OnDynamicActorStreamOut")
            unhookCallback("Streamer_OnItemStreamIn")
            unhookCallback("Streamer_OnItemStreamOut")
            unhookCallback("Streamer_OnPluginError")
        }
    }

}