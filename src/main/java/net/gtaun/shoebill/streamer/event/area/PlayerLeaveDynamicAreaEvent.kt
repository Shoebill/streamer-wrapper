package net.gtaun.shoebill.streamer.event.area

import net.gtaun.shoebill.entities.Player
import net.gtaun.shoebill.event.player.PlayerEvent
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.data.DynamicArea

/**
 * @author Valeriy
 */
@AllOpen
class PlayerLeaveDynamicAreaEvent(player: Player, val dynamicArea: DynamicArea) : PlayerEvent(player)
