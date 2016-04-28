package net.gtaun.shoebill.streamer

import net.gtaun.shoebill.Shoebill
import net.gtaun.shoebill.amx.AmxInstanceManager
import net.gtaun.shoebill.constant.WeaponModel
import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.`object`.Player
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

    private var amxInstanceManager: AmxInstanceManager? = null

    fun registerHandlers(eventManager: EventManager) {
        amxInstanceManager = Shoebill.get().amxInstanceManager
        amxInstanceManager!!.hookCallback("OnDynamicObjectMoved", { amxCallEvent ->
            val `object` = DynamicObject.get(amxCallEvent.parameters[0] as Int)
            val event = DynamicObjectMovedEvent(`object`!!)
            eventManager.dispatchEvent(event, `object`)
        }, "i")
        amxInstanceManager!!.hookCallback("OnPlayerEditDynamicObject", { amxCallEvent ->
            val player = Player.get(amxCallEvent.parameters[0] as Int)
            val `object` = DynamicObject.get(amxCallEvent.parameters[1] as Int)
            val event = PlayerEditDynamicObjectEvent(`object`!!, player, amxCallEvent.parameters[2] as Int,
                    Vector3D(amxCallEvent.parameters[3] as Float, amxCallEvent.parameters[4] as Float, amxCallEvent.parameters[5] as Float),
                    Vector3D(amxCallEvent.parameters[6] as Float, amxCallEvent.parameters[7] as Float, amxCallEvent.parameters[8] as Float))
            eventManager.dispatchEvent(event, player, `object`)
        }, "iiiffffff")
        amxInstanceManager!!.hookCallback("OnPlayerSelectDynamicObject", { amxCallEvent ->
            val player = Player.get(amxCallEvent.parameters[0] as Int)
            val `object` = DynamicObject.get(amxCallEvent.parameters[1] as Int)
            val modelid = amxCallEvent.parameters[2] as Int
            val event = PlayerSelectDynamicObjectEvent(`object`!!, player, modelid,
                    Vector3D(amxCallEvent.parameters[3] as Float, amxCallEvent.parameters[4] as Float, amxCallEvent.parameters[5] as Float))
            eventManager.dispatchEvent(event, player, `object`)
        }, "iiifff")
        amxInstanceManager!!.hookCallback("OnPlayerShootDynamicObject", { amxCallEvent ->
            val player = Player.get(amxCallEvent.parameters[0] as Int)
            val weaponModel = WeaponModel.get(amxCallEvent.parameters[1] as Int)
            val dynamicObject = DynamicObject.get(amxCallEvent.parameters[2] as Int)
            val event = PlayerShootDynamicObjectEvent(dynamicObject!!, player, weaponModel,
                    Vector3D(amxCallEvent.parameters[3] as Float, amxCallEvent.parameters[4] as Float, amxCallEvent.parameters[5] as Float))
            eventManager.dispatchEvent(event, player, dynamicObject)
        }, "iiifff")

        amxInstanceManager!!.hookCallback("OnPlayerEnterDynamicArea", { amxCallEvent ->
            val player = Player.get(amxCallEvent.parameters[0] as Int)
            val area = DynamicArea[amxCallEvent.parameters[1] as Int]
            val event = PlayerEnterDynamicAreaEvent(player, area!!)
            eventManager.dispatchEvent(event)
        }, "ii")

        amxInstanceManager!!.hookCallback("OnPlayerLeaveDynamicArea", { amxCallEvent ->
            val player = Player.get(amxCallEvent.parameters[0] as Int)
            val area = DynamicArea[amxCallEvent.parameters[1] as Int]
            val event = PlayerLeaveDynamicAreaEvent(player, area!!)
            eventManager.dispatchEvent(event)
        }, "ii")

    }

    fun unregisterHandlers() {
        amxInstanceManager!!.unhookCallback("OnDynamicObjectMoved")
        amxInstanceManager!!.unhookCallback("OnPlayerEditDynamicObject")
        amxInstanceManager!!.unhookCallback("OnPlayerSelectDynamicObject")
        amxInstanceManager!!.unhookCallback("OnPlayerShootDynamicObject")
        amxInstanceManager!!.unhookCallback("OnPlayerPickUpDynamicPickup")
        amxInstanceManager!!.unhookCallback("OnPlayerEnterDynamicArea")
        amxInstanceManager!!.unhookCallback("OnPlayerLeaveDynamicArea")
    }

}
