package net.gtaun.shoebill.streamer.data;

import net.gtaun.shoebill.data.Color;
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
 * Created by valych on 11.01.2016 in project streamer-wrapper.
 *
 */
public class Dynamic3DTextLabel implements Destroyable {
    public static final float DEFAULT_STREAM_DISTANCE = 200f; // Corresponds to STREAMER_3D_TEXT_LABEL_SD in streamer.inc

    private static Collection<Dynamic3DTextLabel> textLabels;

    static {
        textLabels = new ArrayList<>();
    }

    private int playerid, id;
    private float streamDistance, drawDistance;


    public Dynamic3DTextLabel(int id, int playerid, float streamDistance, float drawDistance) {
        this.id = id;
        this.playerid = playerid;
        this.streamDistance = streamDistance;
        this.drawDistance = drawDistance;
    }

    public static Set<Dynamic3DTextLabel> get() {
        return new HashSet<>(textLabels);
    }

    public static Dynamic3DTextLabel get(int id) {
        for(Dynamic3DTextLabel textLabel : textLabels) {
            if(textLabel.getId() == id)
                return textLabel;
        }
        return null;
    }

    public static Dynamic3DTextLabel create(String text, Color color, Location location, float drawDistance) {
        return create(text, color, location, drawDistance, 0, Dynamic3DTextLabel.DEFAULT_STREAM_DISTANCE);
    }

    public static Dynamic3DTextLabel create(String text, Color color, Location location, float drawDistance, int testLOS, float streamDistance) {
        return create(text, color, location, drawDistance, 0xFFFF, 0xFFFF, testLOS, -1, streamDistance);
    }

    public static Dynamic3DTextLabel create(String text, Color color, Vector3D offset, float drawDistance, Player attachedPlayer, int testLOS, float streamDistance) {
        return create(text, color, new Location(offset.x, offset.y, offset.z, attachedPlayer.getLocation().interiorId, attachedPlayer.getLocation().worldId), drawDistance, attachedPlayer.getId(), 0xFFFF, testLOS, -1, streamDistance);
    }

    public static Dynamic3DTextLabel create(String text, Color color, Vector3D offset, float drawDistance, Vehicle attachedVehicle, int testLOS, float streamDistance) {
        return create(text, color, new Location(offset.x, offset.y, offset.z, attachedVehicle.getLocation().interiorId, attachedVehicle.getLocation().worldId), drawDistance, 0xFFFF, attachedVehicle.getId(), testLOS, -1, streamDistance);
    }

    public static Dynamic3DTextLabel create(String text, Color color, Location location, float drawDistance, int attachedPlayer, int attachedVehicle, int testLOS, int playerid, float streamDistance) {
        Dynamic3DTextLabel textLabel = Functions.createDynamic3DTextLabel(text, color, location.x, location.y, location.z, drawDistance, attachedPlayer, attachedVehicle, testLOS, location.worldId, location.interiorId, playerid, streamDistance);
        textLabels.add(textLabel);
        return textLabel;
    }

    public String getString() {
        return Functions.getDynamic3DTextLabelText(this.id);
    }

    public void updateString(String string, Color color) {
        Functions.updateDynamic3DTextLabelText(this.id, color, string);
    }

    public int getPlayerid() {
        return playerid;
    }

    public int getId() {
        return id;
    }

    public float getStreamDistance() {
        return streamDistance;
    }

    public float getDrawDistance() {
        return drawDistance;
    }

    @Override
    public void destroy() {
        if(this.isDestroyed()) {
            removeSelf();
            return;
        }

        Functions.destroyDynamic3DTextLabel(this.id);
        this.id = -1;

        removeSelf();
    }

    private void removeSelf() {
        textLabels.remove(this);
    }

    @Override
    public boolean isDestroyed() {
        return !Functions.isValidDynamic3DTextLabel(this.id);
    }
}
