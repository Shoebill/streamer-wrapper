package net.gtaun.shoebill.streamer.event.racecheckpoint

import net.gtaun.shoebill.entities.Player
import net.gtaun.shoebill.event.player.PlayerEvent
import net.gtaun.shoebill.streamer.data.DynamicRaceCheckpoint

/**
 * Created by marvin on 30.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
class PlayerLeaveDynamicRaceCheckpointEvent(player: Player, val dynamicCp: DynamicRaceCheckpoint) : PlayerEvent(player)