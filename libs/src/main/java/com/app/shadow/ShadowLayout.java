package com.app.shadow;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

public interface ShadowLayout extends ShadowView{
    void superDispatchDraw(@NonNull Canvas canvas);
    void setSuperChildrenDrawingOrderEnabled(boolean enabled);
}
