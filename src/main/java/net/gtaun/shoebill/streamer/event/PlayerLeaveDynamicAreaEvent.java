package net.gtaun.shoebill.streamer.event;

import net.gtaun.shoebill.event.player.PlayerEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.streamer.data.DynamicArea;

/**
 * Created by Valeriy on 18/4/2016.
 */
public class PlayerLeaveDynamicAreaEvent extends PlayerEvent {
    DynamicArea dynamicArea;

    public PlayerLeaveDynamicAreaEvent(Player player, DynamicArea dynamicArea) {
        super(player);
        this.dynamicArea = dynamicArea;
    }

    public DynamicArea getDynamicArea() {
        return dynamicArea;
    }
}
