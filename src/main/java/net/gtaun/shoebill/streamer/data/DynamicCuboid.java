package net.gtaun.shoebill.streamer.data;

import net.gtaun.shoebill.data.Area3D;
import net.gtaun.shoebill.streamer.Functions;

/**
 * Created by Valeriy on 18/4/2016.
 */
public class DynamicCuboid extends DynamicArea {

    public DynamicCuboid(int id, int playerId) {
        super(id, playerId);
    }

    public static DynamicCuboid create(Area3D area) {
        return create(area, -1, -1, -1);
    }

    public static DynamicCuboid create(Area3D area, int worldId, int interiorId, int playerId) {
        DynamicCuboid cuboid = Functions.createDynamicCuboid(area, worldId, interiorId, playerId);
        areas.add(cuboid);
        return cuboid;
    }
}
