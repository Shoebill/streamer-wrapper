package net.gtaun.shoebill.streamer.event;

import net.gtaun.shoebill.event.player.PlayerEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.streamer.data.DynamicPickup;

/**
 * Created by valych on 11.01.2016 in project streamer-wrapper.
 */
public class PlayerPickUpDynamicPickupEvent extends PlayerEvent {
    Player player;
    DynamicPickup pickup;


    public PlayerPickUpDynamicPickupEvent(Player player, DynamicPickup pickup) {
        super(player);
        this.pickup = pickup;
    }

    public DynamicPickup getPickup() {
        return pickup;
    }

    @Override
    public Player getPlayer() {
        return super.getPlayer();
    }
}
