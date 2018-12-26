package com.app.shadow;

import android.graphics.Canvas;

public interface ShadowView extends IShadowView {
    void setSuperElevation(float elevation);
    
    void setSuperTranslationZ(float translationZ);
    
    void superDraw(Canvas canvas);
}
