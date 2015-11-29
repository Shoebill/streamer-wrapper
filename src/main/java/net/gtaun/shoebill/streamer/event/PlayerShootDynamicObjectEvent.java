package net.gtaun.shoebill.streamer.event;

import net.gtaun.shoebill.constant.WeaponModel;
import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.streamer.data.DynamicObject;

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class PlayerShootDynamicObjectEvent extends DynamicObjectEvent {

    private Player player;
    private WeaponModel weaponModel;
    private Vector3D pos;

    public PlayerShootDynamicObjectEvent(DynamicObject object, Player player, WeaponModel weaponModel, Vector3D pos) {
        super(object);
        this.player = player;
        this.weaponModel = weaponModel;
        this.pos = pos;
    }

    public Player getPlayer() {
        return player;
    }

    public WeaponModel getWeaponModel() {
        return weaponModel;
    }

    public Vector3D getPos() {
        return pos;
    }
}
