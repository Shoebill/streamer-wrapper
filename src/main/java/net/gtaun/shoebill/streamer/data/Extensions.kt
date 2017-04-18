package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.`object`.Player

/**
 * Created by marvin on 17.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */

fun Player.edit(obj: DynamicObject) = obj.edit(this)