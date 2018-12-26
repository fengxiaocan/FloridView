package com.app.shadow;

import android.content.res.ColorStateList;
import android.graphics.Canvas;

import com.app.view.RenderingModeView;

public interface IShadowView extends RenderingModeView {
    float getElevation();

    void setElevation(float elevation);

    float getTranslationZ();

    void setTranslationZ(float translationZ);

    ShadowShape getShadowShape();

    boolean hasShadow();

    void drawShadow(Canvas canvas);

    void setElevationShadowColor(ColorStateList shadowColor);

    void setElevationShadowColor(int color);

    ColorStateList getElevationShadowColor();
    
    void setOutlineAmbientShadowColor(int color);
    
    void setOutlineAmbientShadowColor(ColorStateList color);
    
    int getOutlineAmbientShadowColor();
    
    void setOutlineSpotShadowColor(int color);
    
    void setOutlineSpotShadowColor(ColorStateList color);
    
    int getOutlineSpotShadowColor();
}
