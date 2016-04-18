package net.gtaun.shoebill.streamer.data;

import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.object.Destroyable;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.streamer.Functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Valeriy on 18/4/2016.
 */
public class DynamicArea implements Destroyable {
    static Collection<DynamicArea> areas;

    static {
        areas = new ArrayList<>();
    }

    private int id, playerId;

    public DynamicArea(int id, int playerId) {
        this.id = id;
        this.playerId = playerId;
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

    static boolean addArea(DynamicArea area) {
        return areas.add(area);
    }

    public boolean isPlayerInRange(Player player) {
        return Functions.isPlayerInDynamicArea(player.getId(), this);
    }

    public boolean isPointInRange(Vector3D point) {
        return Functions.isPointInDynamicArea(this, point);
    }

    // TODO: implement
    public void attachToPlayer() {
        throw new UnsupportedOperationException();
    }

    // TODO: implement
    public void attachToObject() {
        throw new UnsupportedOperationException();
    }

    // TODO: implement
    public void attachToVehicle() {
        throw new UnsupportedOperationException();
    }

    public int getId() {
        return id;
    }

    public int getPlayerId() {
        return playerId;
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
        areas.remove(this);
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }
}
