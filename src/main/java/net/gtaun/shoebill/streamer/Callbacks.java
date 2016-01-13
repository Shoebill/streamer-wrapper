package net.gtaun.shoebill.streamer;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.amx.AmxInstanceManager;
import net.gtaun.shoebill.constant.WeaponModel;
import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.streamer.data.DynamicObject;
import net.gtaun.shoebill.streamer.data.DynamicPickup;
import net.gtaun.shoebill.streamer.event.*;
import net.gtaun.util.event.EventManager;

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class Callbacks {

    private static AmxInstanceManager amxInstanceManager;

    public static void registerHandlers(EventManager eventManager) {
        amxInstanceManager = Shoebill.get().getAmxInstanceManager();
        amxInstanceManager.hookCallback("OnDynamicObjectMoved", amxCallEvent -> {
            DynamicObject object = DynamicObject.get((int) amxCallEvent.getParameters()[0]);
            DynamicObjectMovedEvent event = new DynamicObjectMovedEvent(object);
            eventManager.dispatchEvent(event, object);
        }, "i");
        amxInstanceManager.hookCallback("OnPlayerEditDynamicObject", amxCallEvent -> {
            Player player = Player.get((int) amxCallEvent.getParameters()[0]);
            DynamicObject object = DynamicObject.get((int) amxCallEvent.getParameters()[1]);
            PlayerEditDynamicObjectEvent event = new PlayerEditDynamicObjectEvent(object, player, (int) amxCallEvent.getParameters()[2],
                    new Vector3D((float) amxCallEvent.getParameters()[3], (float) amxCallEvent.getParameters()[4], (float) amxCallEvent.getParameters()[5]),
                    new Vector3D((float) amxCallEvent.getParameters()[6], (float) amxCallEvent.getParameters()[7], (float) amxCallEvent.getParameters()[8]));
            eventManager.dispatchEvent(event, player, object);
        }, "iiiffffff");
        amxInstanceManager.hookCallback("OnPlayerSelectDynamicObject", amxCallEvent -> {
            Player player = Player.get((int) amxCallEvent.getParameters()[0]);
            DynamicObject object = DynamicObject.get((int) amxCallEvent.getParameters()[1]);
            int modelid = (int) amxCallEvent.getParameters()[2];
            PlayerSelectDynamicObjectEvent event = new PlayerSelectDynamicObjectEvent(object, player, modelid,
                    new Vector3D((float) amxCallEvent.getParameters()[3], (float) amxCallEvent.getParameters()[4], (float) amxCallEvent.getParameters()[5]));
            eventManager.dispatchEvent(event, player, object);
        }, "iiifff");
        amxInstanceManager.hookCallback("OnPlayerShootDynamicObject", amxCallEvent -> {
            Player player = Player.get((int) amxCallEvent.getParameters()[0]);
            WeaponModel weaponModel = WeaponModel.get((int) amxCallEvent.getParameters()[1]);
            DynamicObject dynamicObject = DynamicObject.get((int) amxCallEvent.getParameters()[2]);
            PlayerShootDynamicObjectEvent event = new PlayerShootDynamicObjectEvent(dynamicObject, player, weaponModel,
                    new Vector3D((float) amxCallEvent.getParameters()[3], (float) amxCallEvent.getParameters()[4], (float) amxCallEvent.getParameters()[5]));
            eventManager.dispatchEvent(event, player, dynamicObject);
        }, "iiifff");
        amxInstanceManager.hookCallback("OnPlayerPickUpDynamicPickup", amxCallEvent -> {
            Player player = Player.get((int) amxCallEvent.getParameters()[0]);
            DynamicPickup pickup = DynamicPickup.get((int) amxCallEvent.getParameters()[1]);
            PlayerPickUpDynamicPickupEvent event = new PlayerPickUpDynamicPickupEvent(player, pickup);
            eventManager.dispatchEvent(event);
        }, "ii");
    }

    public static void unregisterHandlers() {
        amxInstanceManager.unhookCallback("OnDynamicObjectMoved");
        amxInstanceManager.unhookCallback("OnPlayerEditDynamicObject");
        amxInstanceManager.unhookCallback("OnPlayerSelectDynamicObject");
        amxInstanceManager.unhookCallback("OnPlayerShootDynamicObject");
        amxInstanceManager.unhookCallback("OnPlayerPickUpDynamicPickup");
    }

}
