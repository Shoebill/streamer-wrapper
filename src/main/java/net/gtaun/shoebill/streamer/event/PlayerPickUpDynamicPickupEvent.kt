package net.gtaun.shoebill.streamer.event

import net.gtaun.shoebill.event.player.PlayerEvent
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.streamer.data.DynamicPickup

/**
 * Created by valych on 11.01.2016 in project streamer-wrapper.
 */
class PlayerPickUpDynamicPickupEvent(player: Player, pickup: DynamicPickup) : PlayerEvent(player) {

    internal var player: Player = player
    var pickup: DynamicPickup
        internal set


    init {
        this.pickup = pickup
    }

    override fun getPlayer(): Player {
        return super.getPlayer()
    }
}
