package net.gtaun.shoebill.streamer.event.actor

import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.event.player.PlayerEvent
import net.gtaun.shoebill.streamer.data.DynamicActor

/**
 * Created by marvin on 30.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
class DynamicActorStreamInEvent(val actor: DynamicActor, player: Player) : PlayerEvent(player)