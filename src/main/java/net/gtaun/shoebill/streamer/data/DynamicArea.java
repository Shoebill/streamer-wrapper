package net.gtaun.shoebill.streamer.data;

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
 * Created by Valeriy on 18/4/2016.
 */
public abstract class DynamicArea implements Destroyable {
    protected static Collection<DynamicArea> areas;

    static {
        areas = new ArrayList<>();
    }

    public static Set<DynamicArea> get() {
        return new HashSet<>(areas);
    }

    public static DynamicArea get(int id) {
        for (DynamicArea area : areas) {
            if(area.getId() == id) {
                return area;
            }
        }
        return null;
    }

    private int id, playerId;

    DynamicArea(int id, int playerId) {
        this.id = id;
        this.playerId = playerId;
    }

    public boolean isPlayerInRange(Player player) {
        return Functions.isPlayerInDynamicArea(player.getId(), this);
    }

    public boolean isPointInRange(Vector3D point) {
        return Functions.isPointInDynamicArea(this, point);
    }

    public void attachToPlayer(Player player, Vector3D offset) {
        Functions.attachDynamicAreaToPlayer(this, player, offset);
    }

    public void attachToObject(DynamicObject object, Vector3D offset) {
        Functions.attachDynamicAreaToObject(this, object, offset);
    }

    public void attachToVehicle(Vehicle vehicle, Vector3D offset) {
        Functions.attachDynamicAreaToVehicle(this, vehicle, offset);
    }

    public int getId() {
        return id;
    }

    public Player getPlayer() {
        return Player.get(playerId);
    }

    @Override
    public void destroy() {
        if(isDestroyed()) {
            selfRemove();
            return;
        }

        Functions.destroyDynamicArea(this);
        this.id = -1;
        selfRemove();
    }

    private void selfRemove() {
        if(areas.contains(this))
            areas.remove(this);
    }

    @Override
    public boolean isDestroyed() {
        return !Functions.isValidDynamicArea(this);
    }
}
