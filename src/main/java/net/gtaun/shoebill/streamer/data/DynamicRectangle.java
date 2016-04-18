package net.gtaun.shoebill.streamer.data;

import net.gtaun.shoebill.data.Area;
import net.gtaun.shoebill.streamer.Functions;

/**
 * Created by Valeriy on 18/4/2016.
 */
public class DynamicRectangle extends DynamicArea {
    public DynamicRectangle(int id, int playerId) {
        super(id, playerId);
    }

    public static DynamicRectangle create(Area area, int worldId, int interiorId, int playerId) {
        DynamicRectangle rectangle = Functions.createDynamicRectangle(area, worldId, interiorId, playerId);
        DynamicArea.addArea(rectangle);
        return rectangle;
    }
}
