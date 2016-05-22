package net.gtaun.shoebill.streamer

import net.gtaun.shoebill.Shoebill
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.amx.AmxInstanceManager
import net.gtaun.shoebill.constant.WeaponModel
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.streamer.data.DynamicArea
import net.gtaun.shoebill.streamer.data.DynamicObject
import net.gtaun.shoebill.streamer.data.DynamicPickup
import net.gtaun.shoebill.streamer.event.*
import net.gtaun.util.event.EventManager

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
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
                val player = Player.get(amxCallEvent.parameters[0] as Int)
                val `object` = DynamicObject[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerEditDynamicObjectEvent(`object`, player, amxCallEvent.parameters[2] as Int,
                        Vector3D(amxCallEvent.parameters[3] as Float, amxCallEvent.parameters[4] as Float, amxCallEvent.parameters[5] as Float),
                        Vector3D(amxCallEvent.parameters[6] as Float, amxCallEvent.parameters[7] as Float, amxCallEvent.parameters[8] as Float))
                eventManager.dispatchEvent(event, player, `object`)
            }, "iiiffffff")
            hookCallback("OnPlayerSelectDynamicObject", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int)
                val `object` = DynamicObject[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val modelid = amxCallEvent.parameters[2] as Int
                val event = PlayerSelectDynamicObjectEvent(`object`, player, modelid,
                        Vector3D(amxCallEvent.parameters[3] as Float, amxCallEvent.parameters[4] as Float, amxCallEvent.parameters[5] as Float))
                eventManager.dispatchEvent(event, player, `object`)
            }, "iiifff")
            hookCallback("OnPlayerShootDynamicObject", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int)
                val weaponModel = WeaponModel.get(amxCallEvent.parameters[1] as Int)
                val dynamicObject = DynamicObject[amxCallEvent.parameters[2] as Int] ?: return@hookCallback
                val event = PlayerShootDynamicObjectEvent(dynamicObject, player, weaponModel,
                        Vector3D(amxCallEvent.parameters[3] as Float, amxCallEvent.parameters[4] as Float, amxCallEvent.parameters[5] as Float))
                eventManager.dispatchEvent(event, player, dynamicObject)
            }, "iiifff")

            hookCallback("OnPlayerEnterDynamicArea", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int)
                val area = DynamicArea[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerEnterDynamicAreaEvent(player, area)
                eventManager.dispatchEvent(event)
            }, "ii")

            hookCallback("OnPlayerLeaveDynamicArea", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int)
                val area = DynamicArea[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerLeaveDynamicAreaEvent(player, area)
                eventManager.dispatchEvent(event)
            }, "ii")

            hookCallback("OnPlayerPickUpDynamicPickup", { amxCallEvent ->
                val player = Player.get(amxCallEvent.parameters[0] as Int)
                val pickup = DynamicPickup[amxCallEvent.parameters[1] as Int] ?: return@hookCallback
                val event = PlayerPickUpDynamicPickupEvent(player, pickup)
                eventManager.dispatchEvent(event)
            }, "ii")
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
        }
    }

}
