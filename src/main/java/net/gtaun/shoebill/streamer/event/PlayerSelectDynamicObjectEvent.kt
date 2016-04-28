package net.gtaun.shoebill.streamer.event

import net.gtaun.shoebill.data.Vector3D
import net.gtaun.shoebill.`object`.Player
import net.gtaun.shoebill.streamer.data.DynamicObject

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
class PlayerSelectDynamicObjectEvent(`object`: DynamicObject, val player: Player, val modelid: Int, val pos: Vector3D)
        : DynamicObjectEvent(`object`)
