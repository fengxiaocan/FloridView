package com.app.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.app.shadow.ElevationComparator;
import com.app.shadow.IShadowView;
import com.app.shadow.Shadow;
import com.app.shadow.ShadowLayout;
import com.app.shadow.ShadowView;
import com.app.view.RenderingMode;
import com.app.view.RenderingModeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.app.shadow.Shadow.IS_LOLLIPOP_OR_HIGHER;

public class FloridLayoutHelper extends FloridViewHelper implements IShadowView {
	
	protected List<View> views = new ArrayList<>();
	protected ViewGroup mRootView;
	
	public FloridLayoutHelper(ViewGroup rootView) {
		super(rootView);
		mRootView = rootView;
	}
	
	@Override
	public void initLayout(AttributeSet attrs) {
		super.initLayout(attrs);
		((ShadowLayout)mRootView).setSuperChildrenDrawingOrderEnabled(true);
		mRootView.setClipToPadding(false);
	}
	
	protected List<View> getViews() {
		views.clear();
		for (int i = 0;i < (mRootView).getChildCount();i++)
		{ views.add((mRootView).getChildAt(i)); }
		return views;
	}
	
	public void dispatchDraw(@NonNull Canvas canvas) {
		boolean c = cornerRadius > 0;
		// draw not called, we have to handle corners here
		if (mRootView.isInEditMode()
		    && !drawCalled
		    && c
		    && mRootView.getWidth() > 0
		    && mRootView.getHeight() > 0)
		{
			Bitmap layer = Bitmap.createBitmap(mRootView.getWidth(),mRootView.getHeight(),
			                                   Bitmap.Config.ARGB_8888);
			Canvas layerCanvas = new Canvas(layer);
			dispatchDrawInternal(layerCanvas);
			
			Bitmap mask = Bitmap.createBitmap(mRootView.getWidth(),mRootView.getHeight(),
			                                  Bitmap.Config.ARGB_8888);
			Canvas maskCanvas = new Canvas(mask);
			Paint maskPaint = new Paint(0xffffffff);
			maskCanvas.drawRoundRect(new RectF(0,0,mRootView.getWidth(),mRootView.getHeight()),
			                         cornerRadius,cornerRadius,maskPaint);
			
			for (int x = 0;x < mRootView.getWidth();x++) {
				for (int y = 0;y < mRootView.getHeight();y++) {
					int maskPixel = mask.getPixel(x,y);
					layer.setPixel(x,y,Color.alpha(maskPixel) > 0 ? layer.getPixel(x,y) : 0);
				}
			}
			canvas.drawBitmap(layer,0,0,paint);
		}
		else if (!drawCalled
		         && c
		         && mRootView.getWidth() > 0
		         && mRootView.getHeight() > 0
		         && (!IS_LOLLIPOP_OR_HIGHER || renderingMode == RenderingMode.Software))
		{
			int saveCount = canvas.saveLayer(0,0,mRootView.getWidth(),mRootView.getHeight(),null,
			                                 Canvas.ALL_SAVE_FLAG);
			
			dispatchDrawInternal(canvas);
			
			paint.setXfermode(Shadow.CLEAR_MODE);
			if (c) { canvas.drawPath(cornersMask,paint); }
			paint.setXfermode(null);
			
			canvas.restoreToCount(saveCount);
		}
		else {
			dispatchDrawInternal(canvas);
		}
		drawCalled = false;
	}
	
	private void dispatchDrawInternal(@NonNull Canvas canvas) {
		Collections.sort(getViews(),new ElevationComparator());
		
		((ShadowLayout)mRootView).superDispatchDraw(canvas);
		if (stroke != null) { drawStroke(canvas); }
	}
	
	
	public void drawChild(@NonNull Canvas canvas,@NonNull View child,long drawingTime) {
		if (child instanceof ShadowView && (!IS_LOLLIPOP_OR_HIGHER
		                                    || ((RenderingModeView)child).getRenderingMode()
		                                       == RenderingMode.Software
		                                    || ((ShadowView)child).getElevationShadowColor()
		                                       != null))
		{
			ShadowView shadowView = (ShadowView)child;
			shadowView.drawShadow(canvas);
		}
	}
	
	public int getChildDrawingOrder(int childCount,int child) {
		if (views.size() != childCount) { getViews(); }
		return (mRootView).indexOfChild(views.get(child));
	}
	
}
