package net.gtaun.shoebill.streamer.event;

import net.gtaun.shoebill.streamer.data.DynamicObject;

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class DynamicObjectMovedEvent extends DynamicObjectEvent {
    public DynamicObjectMovedEvent(DynamicObject object) {
        super(object);
    }
}
