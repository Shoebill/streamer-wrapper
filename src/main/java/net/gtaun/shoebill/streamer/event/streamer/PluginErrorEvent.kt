package net.gtaun.shoebill.streamer.event.streamer

import net.gtaun.util.event.Event

/**
 * Created by marvin on 30.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
class PluginErrorEvent(val error: String) : Event()