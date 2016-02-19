package net.gtaun.shoebill.streamer.data;

import net.gtaun.shoebill.constant.MapIconStyle;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.object.Destroyable;
import net.gtaun.shoebill.streamer.Functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by marvin on 19.02.16.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class DynamicMapIcon implements Destroyable {

    public static final float DEFAULT_STREAM_DISTANCE = 200f; //From streamer.inc STREAMER_MAP_ICON_SD
    public static final MapIconStyle DEFAULT_ICON_STYLE = MapIconStyle.LOCAL; //From streamer.inc

    private static Collection<DynamicMapIcon> objects;

    static {
        objects = new ArrayList<>();
    }

    public static Set<DynamicMapIcon> get() {
        return new HashSet<>(objects);
    }

    public static DynamicMapIcon get(int id) {
        for (DynamicMapIcon object : objects) {
            if (object.getId() == id)
                return object;
        }
        return null;
    }

    public static DynamicMapIcon create(Location location, int type, Color color, MapIconStyle style) {
        return create(location, type, color, DEFAULT_STREAM_DISTANCE, style);
    }

    public static DynamicMapIcon create(Location location, int type, Color color, float streamDistance, MapIconStyle style) {
        return create(location, type, color, -1, streamDistance, style);
    }

    public static DynamicMapIcon create(Location location, int type, Color color, int playerid, float streamDistance, MapIconStyle style) {
        DynamicMapIcon object = Functions.createDynamicMapIcon(location, type, color, playerid, streamDistance, style);
        objects.add(object);
        return object;
    }

    private int id, playerid, type;
    private Location location;
    private float streamDistance;
    private MapIconStyle style;
    private Color color;

    public DynamicMapIcon(int id, Location pos, int type, Color color, int playerId, float streamdistance, MapIconStyle style) {
        this.id = id;
        this.playerid = playerId;
        this.location = pos;
        this.streamDistance = streamdistance;
        this.style = style;
        this.type = type;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public int getPlayerid() {
        return playerid;
    }

    public int getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public float getStreamDistance() {
        return streamDistance;
    }

    public MapIconStyle getStyle() {
        return style;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void destroy() {
        if (isDestroyed()) {
            removeSelf();
            return;
        }

        Functions.destroyDynamicMapIcon(this);
        id = -1;
        removeSelf();
    }

    @Override
    public boolean isDestroyed() {
        return id == -1;
    }

    private void removeSelf() {
        if (objects.contains(this))
            objects.remove(this);
    }
}
