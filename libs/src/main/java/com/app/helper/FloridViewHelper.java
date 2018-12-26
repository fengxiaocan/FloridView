package com.app.helper;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.app.R;
import com.app.shadow.IShadowView;
import com.app.shadow.Shadow;
import com.app.shadow.ShadowGenerator;
import com.app.shadow.ShadowShape;
import com.app.shadow.ShadowView;
import com.app.view.RenderingMode;
import com.app.view.RoundedCornersView;
import com.app.view.SizeView;
import com.app.view.StrokeView;

import static com.app.shadow.Shadow.IS_LOLLIPOP_OR_HIGHER;

public class FloridViewHelper implements IShadowView, RoundedCornersView, StrokeView, SizeView {
	protected View mRootView;
	protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
	protected boolean drawCalled = false;
	protected float cornerRadius;
	protected Path cornersMask;
	protected float elevation = 0;
	protected float translationZ = 0;
	protected Shadow ambientShadow, spotShadow;
	protected ColorStateList ambientShadowColor, spotShadowColor;
	protected PorterDuffColorFilter ambientShadowColorFilter, spotShadowColorFilter;
	protected RectF shadowMaskRect = new RectF();
	protected ColorStateList stroke;
	protected float strokeWidth;
	protected Paint strokePaint;
	protected RectF strokeRect;
	protected RenderingMode renderingMode = RenderingMode.Auto;
	
	public FloridViewHelper(View rootView) {
		mRootView = rootView;
	}
	
	public void initLayout(AttributeSet attrs) {
		TypedArray a = mRootView.getContext().obtainStyledAttributes(attrs,R.styleable.FloridAttrs);
		Florid.initElevation((ShadowView)mRootView,a);
		Florid.initStroke((StrokeView)mRootView,a);
		setCornerRadius(a.getDimension(R.styleable.FloridAttrs_florid_cornerRadius,0));
		a.recycle();
	}
	
	@Override
	public float getElevation() {
		return elevation;
	}
	
	@Override
	public void setElevation(float elevation) {
		if (IS_LOLLIPOP_OR_HIGHER) {
			if ((ambientShadowColor == null || spotShadowColor == null)
			    && renderingMode == RenderingMode.Auto)
			{
				((ShadowView)mRootView).setSuperElevation(elevation);
				((ShadowView)mRootView).setSuperTranslationZ(translationZ);
			}
			else {
				((ShadowView)mRootView).setSuperElevation(0);
				((ShadowView)mRootView).setSuperTranslationZ(0);
			}
		}
		else if (elevation != this.elevation && mRootView.getParent() != null) {
			((View)mRootView.getParent()).postInvalidate();
		}
		this.elevation = elevation;
	}
	
	@Override
	public float getTranslationZ() {
		return translationZ;
	}
	
	@Override
	public void setTranslationZ(float translationZ) {
		if (translationZ == this.translationZ) { return; }
		if (IS_LOLLIPOP_OR_HIGHER) {
			if ((ambientShadowColor == null || spotShadowColor == null)
			    && renderingMode == RenderingMode.Auto)
			{
				((ShadowView)mRootView).setSuperTranslationZ(translationZ);
			}
			else {
				((ShadowView)mRootView).setSuperTranslationZ(0);
			}
		}
		else if (translationZ != this.translationZ && mRootView.getParent() != null) {
			((View)mRootView.getParent()).postInvalidate();
		}
		this.translationZ = translationZ;
	}
	
	@Override
	public ShadowShape getShadowShape() {
		if (cornerRadius == mRootView.getWidth() / 2 && mRootView.getWidth() == mRootView
				.getHeight())
		{
			return ShadowShape.CIRCLE;
		}
		if (cornerRadius > 0) { return ShadowShape.ROUND_RECT; }
		return ShadowShape.RECT;
	}
	
	@Override
	public boolean hasShadow() {
		return getElevation() + getTranslationZ() >= 0.01f
		       && mRootView.getWidth() > 0
		       && mRootView.getHeight() > 0;
	}
	
	@Override
	public void drawShadow(Canvas canvas) {
		float alpha = mRootView.getAlpha() * Shadow.getDrawableAlpha(mRootView.getBackground())
		              / 255.0f;
		if (alpha == 0) { return; }
		
		if (!hasShadow()) { return; }
		
		float z = getElevation() + getTranslationZ();
		if (ambientShadow == null
		    || ambientShadow.elevation != z
		    || ambientShadow.cornerRadius != cornerRadius)
		{
			ambientShadow = ShadowGenerator.generateShadow(mRootView,z
			                                                         / mRootView.getResources()
			                                                                    .getDisplayMetrics().density
			                                                         / 4);
			spotShadow = ShadowGenerator.generateShadow(mRootView,z / mRootView.getResources()
			                                                                   .getDisplayMetrics().density);
		}
		
		int saveCount = 0;
		boolean maskShadow = mRootView.getBackground() != null && alpha != 1;
		if (maskShadow) {
			saveCount = canvas
					.saveLayer(0,0,canvas.getWidth(),canvas.getHeight(),null,Canvas.ALL_SAVE_FLAG);
		}
		paint.setAlpha((int)(Shadow.ALPHA * alpha));
		
		Matrix matrix = mRootView.getMatrix();
		
		canvas.save();
		canvas.translate(mRootView.getLeft(),mRootView.getTop());
		canvas.concat(matrix);
		ambientShadow.draw(canvas,mRootView,paint,ambientShadowColorFilter);
		canvas.restore();
		
		canvas.save();
		canvas.translate(mRootView.getLeft(),mRootView.getTop() + z / 2);
		canvas.concat(matrix);
		spotShadow.draw(canvas,mRootView,paint,spotShadowColorFilter);
		canvas.restore();
		
		if (saveCount != 0) {
			canvas.translate(mRootView.getLeft(),mRootView.getTop());
			canvas.concat(matrix);
			paint.setXfermode(Shadow.CLEAR_MODE);
		}
		if (maskShadow) {
			shadowMaskRect.set(0,0,mRootView.getWidth(),mRootView.getHeight());
			canvas.drawRoundRect(shadowMaskRect,cornerRadius,cornerRadius,paint);
		}
		if (saveCount != 0) {
			canvas.restoreToCount(saveCount);
			paint.setXfermode(null);
		}
	}
	
	@Override
	public void setElevationShadowColor(ColorStateList shadowColor) {
		ambientShadowColor = spotShadowColor = shadowColor;
		ambientShadowColorFilter = spotShadowColorFilter =
				shadowColor != null ? new PorterDuffColorFilter(
						shadowColor.getColorForState(mRootView.getDrawableState(),
						                             shadowColor.getDefaultColor()),
						PorterDuff.Mode.MULTIPLY) : Shadow.DEFAULT_FILTER;
		setElevation(elevation);
		setTranslationZ(translationZ);
	}
	
	@Override
	public ColorStateList getElevationShadowColor() {
		return ambientShadowColor;
	}
	
	@Override
	public void setElevationShadowColor(int color) {
		ambientShadowColor = spotShadowColor = ColorStateList.valueOf(color);
		ambientShadowColorFilter = spotShadowColorFilter = new PorterDuffColorFilter(color,
		                                                                             PorterDuff.Mode.MULTIPLY);
		setElevation(elevation);
		setTranslationZ(translationZ);
	}
	
	@Override
	public void setOutlineAmbientShadowColor(int color) {
		setOutlineAmbientShadowColor(ColorStateList.valueOf(color));
	}
	
	@Override
	public int getOutlineAmbientShadowColor() {
		return ambientShadowColor.getDefaultColor();
	}
	
	@Override
	public void setOutlineAmbientShadowColor(ColorStateList color) {
		ambientShadowColor = color;
		ambientShadowColorFilter = new PorterDuffColorFilter(
				color.getColorForState(mRootView.getDrawableState(),color.getDefaultColor()),
				PorterDuff.Mode.MULTIPLY);
		setElevation(elevation);
		setTranslationZ(translationZ);
	}
	
	@Override
	public void setOutlineSpotShadowColor(int color) {
		setOutlineSpotShadowColor(ColorStateList.valueOf(color));
	}
	
	@Override
	public int getOutlineSpotShadowColor() {
		return spotShadowColor.getDefaultColor();
	}
	
	@Override
	public void setOutlineSpotShadowColor(ColorStateList color) {
		spotShadowColorFilter = new PorterDuffColorFilter(
				color.getColorForState(mRootView.getDrawableState(),color.getDefaultColor()),
				PorterDuff.Mode.MULTIPLY);
		setElevation(elevation);
		setTranslationZ(translationZ);
	}
	
	@Override
	public float getCornerRadius() {
		return cornerRadius;
	}
	
	@Override
	public void setCornerRadius(float cornerRadius) {
		if (!IS_LOLLIPOP_OR_HIGHER && cornerRadius != this.cornerRadius) {
			mRootView.postInvalidate();
		}
		this.cornerRadius = cornerRadius;
		if (mRootView.getWidth() > 0 && mRootView.getHeight() > 0) { updateCorners(); }
	}
	
	@Override
	public void setStroke(ColorStateList colorStateList) {
		stroke = colorStateList;
		
		if (stroke == null) { return; }
		
		if (strokePaint == null) {
			strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			strokePaint.setStyle(Paint.Style.STROKE);
			strokeRect = new RectF();
		}
	}
	
	@Override
	public ColorStateList getStroke() {
		return stroke;
	}
	
	@Override
	public void setStroke(int color) {
		setStroke(ColorStateList.valueOf(color));
	}
	
	@Override
	public float getStrokeWidth() {
		return strokeWidth;
	}
	
	@Override
	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	
	private void updateCorners() {
		if (cornerRadius > 0) {
			cornerRadius = Math
					.min(cornerRadius,Math.min(mRootView.getWidth(),mRootView.getHeight()) / 2.0f);
			if (cornerRadius < 1) { cornerRadius = 0; }
			if (IS_LOLLIPOP_OR_HIGHER && renderingMode == RenderingMode.Auto) {
				mRootView.setClipToOutline(true);
				mRootView.setOutlineProvider(ShadowShape.viewOutlineProvider);
			}
			else {
				cornersMask = new Path();
				cornersMask.addRoundRect(new RectF(0,0,mRootView.getWidth(),mRootView.getHeight()),
				                         cornerRadius,cornerRadius,Path.Direction.CW);
				cornersMask.setFillType(Path.FillType.INVERSE_WINDING);
			}
		}
	}
	
	@Override
	public RenderingMode getRenderingMode() {
		return renderingMode;
	}
	
	@Override
	public void setRenderingMode(RenderingMode mode) {
		this.renderingMode = mode;
		setElevation(elevation);
		setTranslationZ(translationZ);
		if (mRootView.getWidth() > 0 && mRootView.getHeight() > 0) { updateCorners(); }
	}
	
	
	/***************************View*****************************/
	
	
	public void drawableStateChanged() {
		if (ambientShadow != null && ambientShadowColor != null) {
			ambientShadowColorFilter = new PorterDuffColorFilter(
					ambientShadowColor.getColorForState(mRootView.getDrawableState(),
					                                    ambientShadowColor.getDefaultColor()),
					PorterDuff.Mode.MULTIPLY);
		}
		if (spotShadow != null && spotShadowColor != null) {
			spotShadowColorFilter = new PorterDuffColorFilter(
					spotShadowColor.getColorForState(mRootView.getDrawableState(),
					                                 spotShadowColor.getDefaultColor()),
					PorterDuff.Mode.MULTIPLY);
		}
	}
	
	public void onLayout(boolean changed) {
		if (!changed) { return; }
		if (mRootView.getWidth() == 0 || mRootView.getHeight() == 0) { return; }
		updateCorners();
	}
	
	public void drawInternal(@NonNull Canvas canvas) {
		((ShadowView)mRootView).superDraw(canvas);
		if (stroke != null) { drawStroke(canvas); }
	}
	
	protected void drawStroke(Canvas canvas) {
		strokePaint.setStrokeWidth(strokeWidth * 2);
		strokePaint.setColor(
				stroke.getColorForState(mRootView.getDrawableState(),stroke.getDefaultColor()));
		strokeRect.set(0,0,mRootView.getWidth(),mRootView.getHeight());
		canvas.drawRoundRect(strokeRect,cornerRadius,cornerRadius,strokePaint);
	}
	
	public void draw(@NonNull Canvas canvas) {
		boolean c = cornerRadius > 0;
		if (mRootView.isInEditMode()
		    && c
		    && mRootView.getWidth() > 0
		    && mRootView.getHeight() > 0)
		{
			Bitmap layer = Bitmap.createBitmap(mRootView.getWidth(),mRootView.getHeight(),
			                                   Bitmap.Config.ARGB_8888);
			Canvas layerCanvas = new Canvas(layer);
			drawInternal(layerCanvas);
			
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
		else if (c
		         && mRootView.getWidth() > 0
		         && mRootView.getHeight() > 0
		         && (!IS_LOLLIPOP_OR_HIGHER || renderingMode == RenderingMode.Software))
		{
			int saveCount = canvas.saveLayer(0,0,mRootView.getWidth(),mRootView.getHeight(),null,
			                                 Canvas.ALL_SAVE_FLAG);
			
			drawInternal(canvas);
			
			paint.setXfermode(Shadow.CLEAR_MODE);
			if (c) { canvas.drawPath(cornersMask,paint); }
			paint.setXfermode(null);
			
			canvas.restoreToCount(saveCount);
			paint.setXfermode(null);
		}
		else {
			drawInternal(canvas);
		}
	}
	
	public void invalidateParentIfNeeded() {
		if (mRootView == null || mRootView.getParent() == null || !(mRootView
				.getParent() instanceof View))
		{ return; }
		
		{ ((View)mRootView.getParent()).invalidate(); }
		
		if (elevation > 0 || cornerRadius > 0) { ((View)mRootView.getParent()).invalidate(); }
	}
	
	public void postInvalidateParentIfNeededDelayed(long delayMilliseconds) {
		if (mRootView.getParent() == null || !(mRootView.getParent() instanceof View)) { return; }
		
		{ ((View)mRootView.getParent()).postInvalidateDelayed(delayMilliseconds); }
		
		if (elevation > 0 || cornerRadius > 0) {
			((View)mRootView.getParent()).postInvalidateDelayed(delayMilliseconds);
		}
	}
	
	
	@Override
	public void setWidth(int width) {
		ViewGroup.LayoutParams layoutParams = mRootView.getLayoutParams();
		if (layoutParams == null) {
			mRootView.setLayoutParams(
					new ViewGroup.LayoutParams(width,ViewGroup.LayoutParams.WRAP_CONTENT));
		}
		else {
			layoutParams.width = width;
			mRootView.setLayoutParams(layoutParams);
		}
	}
	
	@Override
	public void setHeight(int height) {
		ViewGroup.LayoutParams layoutParams = mRootView.getLayoutParams();
		if (layoutParams == null) {
			mRootView.setLayoutParams(
					new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,height));
		}
		else {
			layoutParams.height = height;
			mRootView.setLayoutParams(layoutParams);
		}
	}
	
	@Override
	public void setSize(int width,int height) {
		ViewGroup.LayoutParams layoutParams = mRootView.getLayoutParams();
		if (layoutParams == null) {
			mRootView.setLayoutParams(new ViewGroup.LayoutParams(width,height));
		}
		else {
			layoutParams.width = width;
			layoutParams.height = height;
			mRootView.setLayoutParams(layoutParams);
		}
	}
	
	@Override
	public void setBounds(int x,int y,int width,int height) {
		setSize(width,height);
		mRootView.setTranslationX(x);
		mRootView.setTranslationY(y);
	}
}
