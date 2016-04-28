package net.gtaun.shoebill.streamer.event

import net.gtaun.shoebill.event.player.PlayerEvent
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.streamer.data.DynamicArea

/**
 * Created by Valeriy on 18/4/2016.
 */
class PlayerLeaveDynamicAreaEvent(player: Player, dynamicArea: DynamicArea) : PlayerEvent(player) {
    var dynamicArea: DynamicArea
        internal set

    init {
        this.dynamicArea = dynamicArea
    }
}
