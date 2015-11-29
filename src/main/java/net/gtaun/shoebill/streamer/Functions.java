package net.gtaun.shoebill.streamer;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.amx.AmxCallable;
import net.gtaun.shoebill.amx.AmxInstance;
import net.gtaun.shoebill.amx.types.ReferenceFloat;
import net.gtaun.shoebill.amx.types.ReferenceInt;
import net.gtaun.shoebill.amx.types.ReferenceString;
import net.gtaun.shoebill.constant.ObjectMaterialSize;
import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.event.amx.AmxLoadEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.streamer.data.DynamicObject;
import net.gtaun.shoebill.streamer.data.DynamicObjectMaterial;
import net.gtaun.shoebill.streamer.data.DynamicObjectMaterialText;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.EventManagerNode;

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class Functions {

    private static EventManagerNode eventManagerNode;

    //Objects:
    private static AmxCallable createDynamicObject;
    private static AmxCallable destroyDynamicObject;
    private static AmxCallable isValidDynamicObject;
    private static AmxCallable setDynamicObjectPos;
    private static AmxCallable getDynamicObjectPos;
    private static AmxCallable setDynamicObjectRot;
    private static AmxCallable getDynamicObjectRot;
    private static AmxCallable moveDynamicObject;
    private static AmxCallable stopDynamicObject;
    private static AmxCallable isDynamicObjectMoving;
    private static AmxCallable attachCameraToDynamicObject;
    private static AmxCallable attachDynamicObjectToObject;
    private static AmxCallable attachDynamicObjectToPlayer;
    private static AmxCallable attachDynamicObjectToVehicle;
    private static AmxCallable editDynamicObject;
    private static AmxCallable isDynamicObjectMaterialUsed;
    private static AmxCallable getDynamicObjectMaterial;
    private static AmxCallable setDynamicObjectMaterial;
    private static AmxCallable isDynamicObjectMaterialTextUsed;
    private static AmxCallable getDynamicObjectMaterialText;
    private static AmxCallable setDynamicObjectMaterialText;

    //Streamer:
    private static AmxCallable update;
    private static AmxCallable updateEx;

    public static void registerHandlers(EventManager eventManager) {
        eventManagerNode = eventManager.createChildNode();
        AmxInstance amxInstance = AmxInstance.getDefault();
        findObjectFunctions(amxInstance);
        findStreamerFunctions(amxInstance);
    }

    public static void unregisterHandlers() {
        eventManagerNode.cancelAll();
        eventManagerNode.destroy();
        eventManagerNode = null;
    }

    private static void findObjectFunctions(AmxInstance instance) {
        AmxCallable tickFunc = instance.getNative("Streamer_GetTickRate");
        if(tickFunc != null && createDynamicObject == null) {
            createDynamicObject = instance.getNative("CreateDynamicObject");
            destroyDynamicObject = instance.getNative("DestroyDynamicObject");
            isValidDynamicObject = instance.getNative("IsValidDynamicObject");
            setDynamicObjectPos = instance.getNative("SetDynamicObjectPos");
            getDynamicObjectPos = instance.getNative("GetDynamicObjectPos");
            setDynamicObjectRot = instance.getNative("SetDynamicObjectRot");
            getDynamicObjectRot = instance.getNative("GetDynamicObjectRot");
            moveDynamicObject = instance.getNative("MoveDynamicObject");
            stopDynamicObject = instance.getNative("StopDynamicObject");
            isDynamicObjectMoving = instance.getNative("IsDynamicObjectMoving");
            attachCameraToDynamicObject = instance.getNative("AttachCameraToDynamicObject");
            attachDynamicObjectToObject = instance.getNative("AttachDynamicObjectToObject");
            attachDynamicObjectToPlayer = instance.getNative("AttachDynamicObjectToPlayer");
            attachDynamicObjectToVehicle = instance.getNative("AttachDynamicObjectToVehicle");
            editDynamicObject = instance.getNative("EditDynamicObject");
            isDynamicObjectMaterialUsed = instance.getNative("IsDynamicOBjectMaterialUsed");
            getDynamicObjectMaterial = instance.getNative("GetDynamicObjectMaterial");
            setDynamicObjectMaterial = instance.getNative("SetDynamicObjectMaterial");
            isDynamicObjectMaterialTextUsed = instance.getNative("IsDynamicObjectMaterialTextUsed");
            getDynamicObjectMaterialText = instance.getNative("GetDynamicObjectMaterialText");
            setDynamicObjectMaterialText = instance.getNative("SetDynamicObjectMaterialText");
        }
    }

    private static void findStreamerFunctions(AmxInstance instance) {
        update = instance.getNative("Streamer_Update");
        updateEx = instance.getNative("Streamer_UpdateEx");
    }


    public static DynamicObject createDynamicObject(int modelid, Location location, Vector3D rotation) {
        return createDynamicObject(modelid, location, rotation, 200f, 0f);
    }

    public static DynamicObject createDynamicObject(int modelid, Location location, Vector3D rotation, float streamDistance, float drawDistance) {
        return createDynamicObject(modelid, location, rotation, -1, streamDistance, drawDistance);
    }

    public static DynamicObject createDynamicObject(int modelid, Location location, Vector3D rotation, int playerid, float streamDistance, float drawDistance) {
        return createDynamicObject(modelid, location.x, location.y, location.z, rotation.x, rotation.y, rotation.z, location.worldId, location.interiorId, playerid, streamDistance, drawDistance);
    }

    public static DynamicObject createDynamicObject(int modelid, float x, float y, float z, float rX, float rY, float rZ, int worldId, int interiorId, int playerId, float streamDistance, float drawDistance) {
        createDynamicObject = Shoebill.get().getAmxInstanceManager().getAmxInstances().iterator().next().getNative("CreateDynamicObject");
        int id = (int)createDynamicObject.call(modelid, x, y, z, rX, rY, rZ, worldId, interiorId, playerId, streamDistance, drawDistance);
        return new DynamicObject(id, modelid, playerId, streamDistance, drawDistance);
    }

    public static void destroyDynamicObject(DynamicObject object) {
        destroyDynamicObject(object.getId());
    }

    public static void destroyDynamicObject(int id) {
        destroyDynamicObject.call(id);
    }

    public static boolean isValidDynamicObject(DynamicObject object) {
        return isValidDynamicObject(object.getId());
    }

    public static boolean isValidDynamicObject(int id) {
        return (int) isValidDynamicObject.call(id) > 0;
    }

    public static void setDynamicObjectPos(DynamicObject object, Vector3D pos) {
        setDynamicObjectPos(object.getId(), pos);
    }

    public static void setDynamicObjectPos(int id, Vector3D pos) {
        setDynamicObjectPos(id, pos.x, pos.y, pos.z);
    }

    public static void setDynamicObjectPos(int id, float x, float y, float z) {
        setDynamicObjectPos.call(id, x, y, z);
    }

    public static Vector3D getDynamicObjectPos(DynamicObject object) {
        return getDynamicObjectPos(object.getId());
    }

    public static Vector3D getDynamicObjectPos(int id) {
        ReferenceFloat refX = new ReferenceFloat(0.0f);
        ReferenceFloat refY = new ReferenceFloat(0.0f);
        ReferenceFloat refZ = new ReferenceFloat(0.0f);
        getDynamicObjectPos.call(id, refX, refY, refZ);
        return new Vector3D(refX.getValue(), refY.getValue(), refZ.getValue());
    }

    public static void setDynamicObjectRot(DynamicObject object, Vector3D rot) {
        setDynamicObjectRot(object.getId(), rot);
    }

    public static void setDynamicObjectRot(int id, Vector3D rot) {
        setDynamicObjectRot(id, rot.x, rot.y, rot.z);
    }

    public static void setDynamicObjectRot(int id, float x, float y, float z) {
        setDynamicObjectRot.call(id, x, y, z);
    }

    public static Vector3D getDynamicObjectRot(DynamicObject object) {
        return getDynamicObjectRot(object.getId());
    }

    public static Vector3D getDynamicObjectRot(int id) {
        ReferenceFloat refX = new ReferenceFloat(0.0f);
        ReferenceFloat refY = new ReferenceFloat(0.0f);
        ReferenceFloat refZ = new ReferenceFloat(0.0f);
        getDynamicObjectRot.call(id, refX, refY, refZ);
        return new Vector3D(refX.getValue(), refY.getValue(), refZ.getValue());
    }

    public static void moveDynamicObject(int id, Vector3D newPos, float speed, Vector3D newRot) {
        moveDynamicObject.call(id, newPos.x, newPos.y, newPos.z, speed, newRot.x, newRot.y, newRot.z);
    }

    public static void stopDynamicObject(int id) {
        stopDynamicObject.call(id);
    }

    public static boolean isDynamicObjectMoving(int id) {
        return (int)isDynamicObjectMoving.call(id) > 0;
    }

    public static void attachCameraToDynamicObject(int playerid, int objectId) {
        attachCameraToDynamicObject.call(playerid, objectId);
    }

    public static void attachDynamicObjectToObject(int object, int toObject, float offsetX, float offsetY, float offsetZ, float rotX, float rotY, float rotZ, boolean syncRotation) {
        attachDynamicObjectToObject.call(object, toObject, offsetX, offsetY, offsetZ, rotX, rotY, rotZ, syncRotation ? 1 : 0);
    }

    public static void attachDynamicObjectToPlayer(int object, int playerid, float offsetX, float offsetY, float offsetZ, float rotX, float rotY, float rotZ) {
        attachDynamicObjectToPlayer.call(object, playerid, offsetX, offsetY, offsetZ, rotX, rotY, rotZ);
    }

    public static void attachDynamicObjectToVehicle(int object, int vehicle, float offsetX, float offsetY, float offsetZ, float rotX, float rotY, float rotZ) {
        attachDynamicObjectToVehicle.call(object, vehicle, offsetX, offsetY, offsetZ, rotX, rotY, rotZ);
    }

    public static void editDynamicObject(int playerid, int objectId) {
        editDynamicObject.call(playerid, objectId);
    }

    public static boolean isDynamicObjectMaterialUsed(int objectid, int materialindex) {
        return (int) isDynamicObjectMaterialUsed.call(objectid, materialindex) > 0;
    }

    public static DynamicObjectMaterial getDynamicObjectMaterial(int objectid, int materialindex) {
        ReferenceInt refModel = new ReferenceInt(0);
        ReferenceInt refMaterialColor = new ReferenceInt(0);
        ReferenceString refTxdName = new ReferenceString("", 128);
        ReferenceString refTextureName = new ReferenceString("", 128);
        getDynamicObjectMaterial.call(objectid, materialindex, refModel, refTxdName, refTextureName, refMaterialColor, refTxdName.getLength(), refTextureName.getLength());
        return new DynamicObjectMaterial(refModel.getValue(), refMaterialColor.getValue(), refTxdName.getValue(), refTextureName.getValue());
    }

    public static void setDynamicObjectMaterial(int objectid, int materialindex, int modelid, String txdname, String texturename, int materialcolor) {
        setDynamicObjectMaterial.call(objectid, materialindex, modelid, txdname, texturename, materialcolor);
    }

    public static boolean isDynamicobjectMaterialTextUsed(int objectid, int materialindex) {
        return (int) isDynamicObjectMaterialTextUsed.call(objectid, materialindex) > 0;
    }

    public static DynamicObjectMaterialText getDynamicObjectMaterialText(int objectid, int materialindex) {
        ReferenceString refText = new ReferenceString("", 256);
        ReferenceInt refMaterialSize = new ReferenceInt(0);
        ReferenceString refFontFace = new ReferenceString("", 64);
        ReferenceInt refFontSize = new ReferenceInt(0);
        ReferenceInt refBold = new ReferenceInt(0);
        ReferenceInt refFontColor = new ReferenceInt(0);
        ReferenceInt refBackColor = new ReferenceInt(0);
        ReferenceInt refTextAlignment = new ReferenceInt(0);
        getDynamicObjectMaterialText.call(objectid, materialindex, refText, refMaterialSize, refFontFace, refFontSize, refBold, refFontColor, refBackColor, refTextAlignment, refText.getLength(), refFontFace.getLength());
        return new DynamicObjectMaterialText(refText.getValue(), refFontFace.getValue(), refMaterialSize.getValue(), refFontSize.getValue(), refBold.getValue() > 0, refFontColor.getValue(), refBackColor.getValue(), refTextAlignment.getValue());
    }

    public static void setDynamicObjectMaterialText(int objectid, int materialindex, String text, ObjectMaterialSize materialsize, String fontFace, int fontSize, boolean bold, int fontColor, int backColor, int textAlignment) {
        setDynamicObjectMaterialText.call(objectid, materialindex, text, materialsize.getValue(), fontFace, fontSize, bold ? 1 : 0, fontColor, backColor, textAlignment);
    }

    public static void update(Player player) {
        update.call(player.getId(), -1);
    }

    public static void updateEx(Player player, float x, float y, float z, int worldid, int interiorid) {
        updateEx.call(player.getId(), x, y, z, worldid, interiorid, -1);
    }
}
