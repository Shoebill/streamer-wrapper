package net.gtaun.shoebill.streamer.event;

import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.streamer.data.DynamicObject;

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class PlayerEditDynamicObjectEvent extends DynamicObjectEvent {

    private Player player;
    private int response;
    private Vector3D pos, rot;

    public PlayerEditDynamicObjectEvent(DynamicObject object, Player player, int response, Vector3D pos, Vector3D rot) {
        super(object);
        this.player = player;
        this.response = response;
        this.pos = pos;
        this.rot = rot;
    }

    public Player getPlayer() {
        return player;
    }

    public int getResponse() {
        return response;
    }

    public Vector3D getPos() {
        return pos;
    }

    public Vector3D getRot() {
        return rot;
    }
}
