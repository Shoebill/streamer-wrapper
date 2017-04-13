package net.gtaun.shoebill.streamer.event

import net.gtaun.shoebill.event.player.PlayerEvent
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.streamer.AllOpen
import net.gtaun.shoebill.streamer.data.DynamicPickup

/**
 * @author valych
 */
@AllOpen
class PlayerPickUpDynamicPickupEvent(player: Player, val pickup: DynamicPickup) : PlayerEvent(player)
