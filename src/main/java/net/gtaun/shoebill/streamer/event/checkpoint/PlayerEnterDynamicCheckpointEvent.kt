package net.gtaun.shoebill.streamer.event.checkpoint

/**
 * Created by marvin on 29.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
class PlayerEnterDynamicCheckpointEvent(player: net.gtaun.shoebill.`object`.Player, val checkpoint: net.gtaun.shoebill.streamer.data.DynamicCheckpoint) : net.gtaun.shoebill.event.player.PlayerEvent(player)