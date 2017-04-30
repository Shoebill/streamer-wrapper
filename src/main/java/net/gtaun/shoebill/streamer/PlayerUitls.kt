@file:JvmName("PlayerUtils")

package net.gtaun.shoebill.streamer

import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.streamer.data.DynamicCheckpoint
import net.gtaun.shoebill.streamer.data.DynamicObject
import net.gtaun.shoebill.streamer.data.DynamicRaceCheckpoint

/**
 * Created by marvin on 29.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
fun Player.isInDynamicCp(checkpoint: DynamicCheckpoint) =
        Functions.isPlayerInDynamicCp(this, checkpoint)

fun Player.toggleDynamicCp(checkpoint: DynamicCheckpoint, toggle: Boolean) =
        Functions.togglePlayerDynamicCp(this, checkpoint, toggle)

fun Player.toggleAllDynamicCps(toggle: Boolean) =
        Functions.togglePlayerAllDynamicCps(this, toggle)

val Player.visibleDynamicCp: DynamicCheckpoint?
    get() = Functions.getPlayerVisibleDynamicCp(this)

fun Player.isInDynamicRaceCp(raceCheckpoint: DynamicRaceCheckpoint) =
        Functions.isPlayerInDynamicRaceCp(this, raceCheckpoint)

fun Player.toggleDynamicRaceCp(raceCheckpoint: DynamicRaceCheckpoint, toggle: Boolean) =
        Functions.togglePlayerDynamicRaceCp(this, raceCheckpoint, toggle)

fun Player.toggleAllDynamicRaceCps(toggle: Boolean) =
        Functions.togglePlayerAllDynamicRaceCPs(this, toggle)

val Player.visibleDynamicRaceCp: DynamicRaceCheckpoint?
    get() = Functions.getPlayerVisibleDynamicRaceCp(this)

fun Player.edit(obj: DynamicObject) = obj.edit(this)

var Player.tickRate: Int
    get() = Functions.getPlayerTickRate(this)
    set(value) = Functions.setPlayerTickRate(this, value)