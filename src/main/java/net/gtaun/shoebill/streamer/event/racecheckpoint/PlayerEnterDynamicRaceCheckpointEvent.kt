package net.gtaun.shoebill.streamer.event.racecheckpoint

/**
 * Created by marvin on 30.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
class PlayerEnterDynamicRaceCheckpointEvent(player: net.gtaun.shoebill.`object`.Player, dynamicCp: net.gtaun.shoebill.streamer.data.DynamicRaceCheckpoint) : net.gtaun.shoebill.event.player.PlayerEvent(player)