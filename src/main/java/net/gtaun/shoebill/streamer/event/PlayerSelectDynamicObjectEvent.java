package net.gtaun.shoebill.streamer.event;

import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.streamer.data.DynamicObject;

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class PlayerSelectDynamicObjectEvent extends DynamicObjectEvent {

    private Player player;
    private int modelid;
    private Vector3D pos;

    public PlayerSelectDynamicObjectEvent(DynamicObject object, Player player, int modelid, Vector3D pos) {
        super(object);
        this.player = player;
        this.modelid = modelid;
        this.pos = pos;
    }

    public Player getPlayer() {
        return player;
    }

    public int getModelid() {
        return modelid;
    }

    public Vector3D getPos() {
        return pos;
    }
}
