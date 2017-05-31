package net.gtaun.shoebill.streamer.event.area


/**
 * @author Valeriy
 */
@net.gtaun.shoebill.streamer.AllOpen
class PlayerEnterDynamicAreaEvent(player: net.gtaun.shoebill.entities.Player, val dynamicArea: net.gtaun.shoebill.streamer.data.DynamicArea) : net.gtaun.shoebill.event.player.PlayerEvent(player)