package net.gtaun.shoebill.streamer.data;

import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.object.Destroyable;
import net.gtaun.shoebill.streamer.Functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by valych on 11.01.2016 in project streamer-wrapper.
 */
public class DynamicPickup implements Destroyable {
    public static final float DEFAULT_STREAM_DISTANCE = 200f; // Corresponds to STREAMER_PICKUP_SD in streamer.inc

    private static Collection<DynamicPickup> pickups;

    static {
        pickups = new ArrayList<>();
    }

    private int id, modelid, type, playerid;
    private float streamDistance;


    public DynamicPickup(int id, int modelid, int type, int playerid, float streamDistance) {
        this.id = id;
        this.modelid = modelid;
        this.type = type;
        this.playerid = playerid;
        this.streamDistance = streamDistance;
    }

    public static Set<DynamicPickup> get() {
        return new HashSet<>(pickups);
    }

    public static DynamicPickup get(int id) {
        for(DynamicPickup pickup : pickups) {
            if(pickup.getId() == id)
                return pickup;
        }
        return null;
    }

    public static DynamicPickup create(int modelid, int type, Location location) {
        return create(modelid, type, location, -1, DynamicPickup.DEFAULT_STREAM_DISTANCE);
    }

    public static DynamicPickup create(int modelid, int type, Location location, float streamDistance) {
        return create(modelid, type, location, -1, streamDistance);
    }

    public static DynamicPickup create(int modelid, int type, Location location, int playerid, float streamDistance) {
        DynamicPickup pickup = Functions.createDynamicPickup(modelid, type, location, playerid, streamDistance);
        pickups.add(pickup);
        return pickup;
    }

    public int getId() {
        return id;
    }

    public int getModelid() {
        return modelid;
    }

    public int getType() {
        return type;
    }

    public int getPlayerid() {
        return playerid;
    }

    public float getStreamDistance() {
        return streamDistance;
    }

    @Override
    public void destroy() {
        if(this.isDestroyed()) {
            removeSelf();
            return;
        }

        Functions.destroyDynamicPickup(this.id);
        this.id = -1;

        removeSelf();
    }

    private void removeSelf() {
        pickups.remove(this);
    }

    @Override
    public boolean isDestroyed() {
        return !Functions.isValidDynamicPickup(this.id);
    }
}
