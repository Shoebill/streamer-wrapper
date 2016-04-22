package net.gtaun.shoebill.streamer.event;

import net.gtaun.shoebill.event.player.PlayerEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.streamer.data.DynamicArea;
import net.gtaun.util.event.Event;

/**
 * Created by Valeriy on 18/4/2016.
 */
public class PlayerEnterDynamicAreaEvent extends PlayerEvent {
    DynamicArea dynamicArea;

    public PlayerEnterDynamicAreaEvent(Player player, DynamicArea dynamicArea) {
        super(player);
        this.dynamicArea = dynamicArea;
    }

    public DynamicArea getDynamicArea() {
        return dynamicArea;
    }
}
