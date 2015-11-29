package net.gtaun.shoebill.streamer.event;

import net.gtaun.shoebill.streamer.data.DynamicObject;
import net.gtaun.util.event.Event;

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class DynamicObjectEvent extends Event {

    private DynamicObject object;

    public DynamicObjectEvent(DynamicObject object) {
        this.object = object;
    }

    public DynamicObject getObject() {
        return object;
    }
}
