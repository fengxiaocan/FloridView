package com.app.shadow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

public class Shadow {
    public static final boolean IS_LOLLIPOP_OR_HIGHER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    public static final int ALPHA = 47;
//    public static final int ALPHA = 1;

    private final int e;
    private final Bitmap bitmap;
    private Rect src = new Rect();
    private Rect dst = new Rect();
    private int[] xDiv;
    private int[] yDiv;
    private int[] xDivDst;
    private int[] yDivDst;
    public float elevation;
    public int cornerRadius;
    private float scale;

    public static final PorterDuffColorFilter DEFAULT_FILTER = new PorterDuffColorFilter(0xff000000, PorterDuff.Mode.MULTIPLY);

    public Shadow(Bitmap bitmap, float elevation, int cornerRadius, float scale) {
        this.scale = scale;
        this.bitmap = bitmap;
        this.elevation = elevation;

        e = (int) (Math.ceil(elevation));
        this.cornerRadius = cornerRadius;
        xDiv = new int[]{0, e + cornerRadius, bitmap.getWidth() - e - cornerRadius, bitmap.getWidth()};
        yDiv = new int[]{0, e + cornerRadius, bitmap.getHeight() - e - cornerRadius, bitmap.getHeight()};
        xDivDst = new int[]{(int) (-e * scale), (int) (cornerRadius * scale), 0, 0};
        yDivDst = new int[]{(int) (-e * scale), (int) (cornerRadius * scale), 0, 0};
    }

    public void draw(Canvas canvas, View view, Paint paint, ColorFilter colorFilter) {
        xDivDst[1] = (int) Math.min(cornerRadius * scale, view.getWidth() / 2);
        yDivDst[1] = (int) Math.min(cornerRadius * scale, view.getHeight() / 2);
        xDivDst[2] = (int) Math.max(view.getWidth() - cornerRadius * scale, view.getWidth() / 2);
        yDivDst[2] = (int) Math.max(view.getHeight() - cornerRadius * scale, view.getHeight() / 2);
        xDivDst[3] = (int) (view.getWidth() + e * scale);
        yDivDst[3] = (int) (view.getHeight() + e * scale);

        paint.setColorFilter(colorFilter);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (y == 1 && x == 1)
                    continue;
                src.set(xDiv[x], yDiv[y], xDiv[x + 1], yDiv[y + 1]);
                dst.set(xDivDst[x], yDivDst[y], xDivDst[x + 1], yDivDst[y + 1]);
                canvas.drawBitmap(bitmap, src, dst, paint);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Shadow && elevation == ((Shadow) o).elevation && cornerRadius == ((Shadow) o).cornerRadius;
    }
    
    public static PorterDuffXfermode CLEAR_MODE = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    
    public static int getDrawableAlpha(Drawable background) {
        if (background == null)
            return 255;
        background = background.getCurrent();
        if (background instanceof ColorDrawable)
            return ((ColorDrawable) background).getAlpha();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return background.getAlpha();
        }
        return 255;
    }
}
