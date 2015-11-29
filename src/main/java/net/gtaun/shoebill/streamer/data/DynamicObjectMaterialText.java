package net.gtaun.shoebill.streamer.data;

/**
 * Created by marvin on 23.07.15 in project streamer-wrapper.
 * Copyright (c) 2015 Marvin Haschker. All rights reserved.
 */
public class DynamicObjectMaterialText {
    private String text, fontFace;
    private int materialSize, fontSize, fontColor, backcolor, textAlignment;
    private boolean bold;

    public DynamicObjectMaterialText(String text, String fontFace, int materialSize, int fontSize, boolean bold, int fontColor, int backcolor, int textAlignment) {
        this.text = text;
        this.fontFace = fontFace;
        this.materialSize = materialSize;
        this.fontSize = fontSize;
        this.bold = bold;
        this.fontColor = fontColor;
        this.backcolor = backcolor;
        this.textAlignment = textAlignment;
    }

    public String getText() {
        return text;
    }

    public String getFontFace() {
        return fontFace;
    }

    public int getMaterialSize() {
        return materialSize;
    }

    public int getFontSize() {
        return fontSize;
    }

    public boolean getBold() {
        return bold;
    }

    public int getFontColor() {
        return fontColor;
    }

    public int getBackcolor() {
        return backcolor;
    }

    public int getTextAlignment() {
        return textAlignment;
    }
}
