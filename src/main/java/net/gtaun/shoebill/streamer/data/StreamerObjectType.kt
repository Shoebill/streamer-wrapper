package net.gtaun.shoebill.streamer.data

import net.gtaun.shoebill.streamer.AllOpen

/**
 * Created by marvin on 13.04.17 in project streamer-wrapper.
 * Copyright (c) 2017 Marvin Haschker. All rights reserved.
 */
@AllOpen
enum class StreamerObjectType constructor(val value: Int) {
    GLOBAL(0),
    PLAYER(1),
    DYNAMIC(2);
}
