package net.gtaun.shoebill.streamer.event

import net.gtaun.shoebill.streamer.data.DynamicObject
import net.gtaun.util.event.Event

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
open class DynamicObjectEvent(val `object`: DynamicObject) : Event()
