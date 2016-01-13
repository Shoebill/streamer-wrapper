package net.gtaun.shoebill.streamer.data;

import net.gtaun.shoebill.constant.ObjectMaterialSize;
import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.object.Destroyable;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.Vehicle;
import net.gtaun.shoebill.streamer.Functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class DynamicObject implements Destroyable {
    public static final float DEFAULT_STREAM_DISTANCE = 300f; // Corresponds to STREAMER_OBJECT_SD in streamer.inc
    public static final float DEFAULT_DRAW_DISTANCE = 0f; // Corresponds to STREAMER_OBJECT_DD in streamer.inc

    private static Collection<DynamicObject> objects;

    static {
        objects = new ArrayList<>();
    }

    private int modelid, playerid, id;
    private float streamDistance, drawDistance;

    public DynamicObject(int id, int modelid, int playerid, float streamDistance, float drawDistance) {
        this.id = id;
        this.modelid = modelid;
        this.playerid = playerid;
        this.streamDistance = streamDistance;
        this.drawDistance = drawDistance;
    }

    public static Set<DynamicObject> get() {
        return new HashSet<>(objects);
    }

    public static DynamicObject get(int id) {
        for(DynamicObject object : objects) {
            if(object.getId() == id)
                return object;
        }
        return null;
    }

    public static DynamicObject create(int modelid, Location location, Vector3D rotation) {
        return create(modelid, location, rotation, DynamicObject.DEFAULT_STREAM_DISTANCE, DynamicObject.DEFAULT_DRAW_DISTANCE);
    }

    public static DynamicObject create(int modelid, Location location, Vector3D rotation, float streamDistance, float drawDistance) {
        return create(modelid, location, rotation, -1, streamDistance, drawDistance);
    }

    public static DynamicObject create(int modelid, Location location, Vector3D rotation, int playerid, float streamDistance, float drawDistance) {
        DynamicObject object = Functions.createDynamicObject(modelid, location, rotation, playerid, streamDistance, drawDistance);
        objects.add(object);
        return object;
    }

    public int getId() {
        return id;
    }

    public int getModelid() {
        return modelid;
    }

    public int getPlayerid() {
        return playerid;
    }

    public Vector3D getPosition() {
        return Functions.getDynamicObjectPos(this);
    }

    public void setPosition(Vector3D newPos) {
        Functions.setDynamicObjectPos(this, newPos);
    }

    public Vector3D getRotation() {
        return Functions.getDynamicObjectRot(this);
    }

    public void setRotation(Vector3D newRot) {
        Functions.setDynamicObjectRot(this, newRot);
    }

    public float getStreamDistance() {
        return streamDistance;
    }

    public float getDrawDistance() {
        return drawDistance;
    }

    public void movePosition(Vector3D newPos, float speed) {
        Vector3D rotation = getRotation();
        move(newPos, speed, rotation);
    }

    public void moveRotation(Vector3D newRot, float speed) {
        Vector3D position = getPosition();
        move(position, speed, newRot);
    }

    public void move(Vector3D pos, float speed, Vector3D rot) {
        Functions.moveDynamicObject(id, pos, speed, rot);
    }

    public void stop() {
        Functions.stopDynamicObject(id);
    }

    public boolean isMoving() {
        return Functions.isDynamicObjectMoving(id);
    }

    public void attachCamera(Player player) {
        Functions.attachCameraToDynamicObject(player.getId(), id);
    }

    public void attach(DynamicObject target, Vector3D offset, Vector3D rotation) {
        attach(target, offset, rotation, true);
    }

    public void attach(DynamicObject target, Vector3D offset, Vector3D rotation, boolean syncRotation) {
        Functions.attachDynamicObjectToObject(id, target.getId(), offset.x, offset.y, offset.z, rotation.x, rotation.y, rotation.z, syncRotation);
    }

    public void attach(Player target, Vector3D offset, Vector3D rotation) {
        Functions.attachDynamicObjectToPlayer(id, target.getId(), offset.x, offset.y, offset.z, rotation.x, rotation.y, rotation.z);
    }

    public void attach(Vehicle target, Vector3D offset, Vector3D rotation) {
        Functions.attachDynamicObjectToVehicle(id, target.getId(), offset.x, offset.y, offset.z, rotation.x, rotation.y, rotation.z);
    }

    public void edit(Player player) {
        Functions.editDynamicObject(player.getId(), this.getId());
    }

    public boolean isMaterialUsed(int materialIndex) {
        return Functions.isDynamicObjectMaterialUsed(id, materialIndex);
    }

    public DynamicObjectMaterial getMaterial(int materialindex) {
        return Functions.getDynamicObjectMaterial(id, materialindex);
    }

    public void setMaterial(int materialindex, int modelid, String txdname, String textureName) {
        setMaterial(materialindex, modelid, txdname, textureName, 0);
    }

    public void setMaterial(int materialindex, int modelid, String txdname, String textureName, int materialColor) {
        Functions.setDynamicObjectMaterial(id, materialindex, modelid, txdname, textureName, materialColor);
    }

    public boolean isMaterialTextUsed(int materialindex) {
        return Functions.isDynamicObjectMaterialTextUsed(id, materialindex);
    }

    public DynamicObjectMaterialText getMaterialText(int materialindex) {
        return Functions.getDynamicObjectMaterialText(id, materialindex);
    }

    public void setMaterialText(int materialIndex, String text) {
        setMaterialText(materialIndex, text, ObjectMaterialSize.SIZE_256x128, "Arial", 24, true, 0xFFFFFFFF, 0, 0);
    }

    public void setMaterialText(int materialIndex, String text, ObjectMaterialSize size, String fontFace, int fontSize, boolean bold, int fontColor, int backColor, int textAlignment) {
        Functions.setDynamicObjectMaterialText(id, materialIndex, text, size, fontFace, fontSize, bold, fontColor, backColor, textAlignment);
    }

    @Override
    public void destroy() {
        if(isDestroyed()) {
            removeSelf();
            return;
        }
        Functions.destroyDynamicObject(this);
        id = -1;
        removeSelf();
    }

    private void removeSelf() {
        if(objects.contains(this))
            objects.remove(this);
    }

    @Override
    public boolean isDestroyed() {
        return !Functions.isValidDynamicObject(id);
    }
}
