package net.gtaun.shoebill.streamer.event

import net.gtaun.shoebill.event.player.PlayerEvent
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.data.DynamicArea
import net.gtaun.util.event.Event


/**
 * @author Valeriy
 */
@AllOpen
class PlayerEnterDynamicAreaEvent(player: Player, val dynamicArea: DynamicArea) : PlayerEvent(player)