package net.gtaun.shoebill.streamer.data;

import net.gtaun.shoebill.data.Vector2D;
import net.gtaun.shoebill.streamer.Functions;

/**
 * Created by Valeriy on 18/4/2016.
 */
public class DynamicCircle extends DynamicArea {

    public DynamicCircle(int id, int playerId) {
        super(id, playerId);
    }

    public static DynamicCircle create(Vector2D location, float size) {
        return create(location, size, -1, -1, -1);
    }

    public static DynamicCircle create(Vector2D location, float size, int worldId, int interiorId, int playerId) {
        DynamicCircle circle = Functions.createDynamicCircle(location, size, worldId, interiorId, playerId);
        areas.add(circle);
        return circle;
    }
}
