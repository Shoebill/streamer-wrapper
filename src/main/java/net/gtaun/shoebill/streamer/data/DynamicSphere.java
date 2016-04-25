package net.gtaun.shoebill.streamer.data;

import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.streamer.Functions;

/**
 * Created by Valeriy on 18/4/2016.
 */
public class DynamicSphere extends DynamicArea {
    public DynamicSphere(int id, int playerId) {
        super(id, playerId);
    }

    public static DynamicSphere create(Vector3D location, float size) {
        return create(location, size, -1, -1, -1);
    }

    public static DynamicSphere create(Vector3D location, float size, int worldId, int interiorId, int playerId) {
        DynamicSphere sphere = Functions.createDynamicSphere(location, size, worldId, interiorId, playerId);
        areas.add(sphere);
        return sphere;
    }
}
