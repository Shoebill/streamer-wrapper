package net.gtaun.shoebill.streamer.data;

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class DynamicObjectMaterial {
    private int modelid, materialColor;
    private String txdName, textureName;

    public DynamicObjectMaterial(int modelid, int materialColor, String txdName, String textureName) {
        this.modelid = modelid;
        this.materialColor = materialColor;
        this.txdName = txdName;
        this.textureName = textureName;
    }

    public int getModelid() {
        return modelid;
    }

    public int getMaterialColor() {
        return materialColor;
    }

    public String getTxdName() {
        return txdName;
    }

    public String getTextureName() {
        return textureName;
    }
}
