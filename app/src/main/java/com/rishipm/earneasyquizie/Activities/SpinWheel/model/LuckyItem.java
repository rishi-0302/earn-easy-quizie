package com.rishipm.earneasyquizie.Activities.SpinWheel.model;

/**
 * Created by kiennguyen on 11/5/16.
 */

public class LuckyItem {
    public String topText;
    public String secondaryText;
    public int secondaryTextOrientation;
    public int icon;
    public int color;
    public int textColor;

    public LuckyItem(String topText, String secondaryText, int color, int textColor) {
        this.topText = topText;
        this.secondaryText = secondaryText;
        this.color = color;
        this.textColor = textColor;
    }
}
