package com.app.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.helper.FloridLayoutHelper;
import com.app.shadow.ShadowLayout;
import com.app.shadow.ShadowShape;
import com.app.view.RenderingMode;
import com.app.view.RoundedCornersView;
import com.app.view.SizeView;
import com.app.view.StrokeView;

public class FloridLinearLayout extends LinearLayout implements ShadowLayout, RoundedCornersView, StrokeView, SizeView {
	private FloridLayoutHelper mFloridHelper = new FloridLayoutHelper(this);
	
	public FloridLinearLayout(Context context) {
		super(context);
		mFloridHelper.initLayout(null);
	}
	
	public FloridLinearLayout(Context context,AttributeSet attrs) {
		super(context,attrs);
		mFloridHelper.initLayout(attrs);
	}
	
	public FloridLinearLayout(Context context,AttributeSet attrs,int defStyleAttr) {
		super(context,attrs,defStyleAttr);
		mFloridHelper.initLayout(attrs);
	}
	
	@SuppressLint("NewApi")
	public FloridLinearLayout(
			Context context,AttributeSet attrs,int defStyleAttr,int defStyleRes)
	{
		super(context,attrs,defStyleAttr,defStyleRes);
		mFloridHelper.initLayout(attrs);
	}
	
	@Override
	public void setElevation(float elevation) {
		if (mFloridHelper != null) 				mFloridHelper.setElevation(elevation); 			else setSuperElevation(elevation);
	}
	
	@Override
	public float getElevation() {
		if (mFloridHelper != null) 			return mFloridHelper.getElevation(); 		else return super.getElevation();
	}
	
	@Override
	public void setTranslationZ(float translationZ) {
		if (mFloridHelper != null) 		mFloridHelper.setTranslationZ(translationZ); 		else setSuperTranslationZ(translationZ);
	}
	
	@Override
	public float getTranslationZ() {
		if (mFloridHelper != null) 		return mFloridHelper.getTranslationZ(); 		else return super.getTranslationZ();
	}
	
	@Override
	public void superDispatchDraw(@NonNull Canvas canvas) {
		super.dispatchDraw(canvas);
	}
	
	@Override
	public void setSuperChildrenDrawingOrderEnabled(boolean enabled) {
		super.setChildrenDrawingOrderEnabled(enabled);
	}
	
	@Override
	public void setSuperElevation(float elevation) {
		super.setElevation(elevation);
	}
	
	@Override
	public void setSuperTranslationZ(float translationZ) {
		super.setTranslationZ(translationZ);
	}
	
	@Override
	public void superDraw(Canvas canvas) {
		super.draw(canvas);
	}
	
	@Override
	public ShadowShape getShadowShape() {
		return mFloridHelper.getShadowShape();
	}
	
	@Override
	public boolean hasShadow() {
		return mFloridHelper.hasShadow();
	}
	
	@Override
	public void drawShadow(Canvas canvas) {
		mFloridHelper.drawShadow(canvas);
	}
	
	@Override
	public void setElevationShadowColor(ColorStateList shadowColor) {
		mFloridHelper.setElevationShadowColor(shadowColor);
	}
	
	
	@Override
	public ColorStateList getElevationShadowColor() {
		return mFloridHelper.getElevationShadowColor();
	}
	
	@Override
	public void setElevationShadowColor(int color) {
		mFloridHelper.setElevationShadowColor(color);
	}
	
	@Override
	public void setOutlineAmbientShadowColor(ColorStateList color) {
		mFloridHelper.setOutlineAmbientShadowColor(color);
	}
	
	@Override
	public void setOutlineSpotShadowColor(ColorStateList color) {
		mFloridHelper.setOutlineSpotShadowColor(color);
	}
	
	@Override
	public float getCornerRadius() {
		return mFloridHelper.getCornerRadius();
	}
	
	@Override
	public void setCornerRadius(float cornerRadius) {
		mFloridHelper.setCornerRadius(cornerRadius);
	}
	
	@Override
	public RenderingMode getRenderingMode() {
		return mFloridHelper.getRenderingMode();
	}
	
	@Override
	public void setRenderingMode(RenderingMode mode) {
		mFloridHelper.setRenderingMode(mode);
	}
	
	@Override
	public void setStroke(ColorStateList colorStateList) {
		mFloridHelper.setStroke(colorStateList);
	}
	
	@Override
	public ColorStateList getStroke() {
		return mFloridHelper.getStroke();
	}
	
	@Override
	public void setStroke(int color) {
		mFloridHelper.setStroke(color);
	}
	
	@Override
	public float getStrokeWidth() {
		return mFloridHelper.getStrokeWidth();
	}
	
	@Override
	public void setStrokeWidth(float strokeWidth) {
		mFloridHelper.setStrokeWidth(strokeWidth);
	}
	
	@Override
	public void setWidth(int width) {
		mFloridHelper.setWidth(width);
	}
	
	@Override
	public void setHeight(int height) {
		mFloridHelper.setHeight(height);
	}
	
	@Override
	public void setSize(int width,int height) {
		mFloridHelper.setSize(width,height);
	}
	
	@Override
	public void setBounds(int x,int y,int width,int height) {
		mFloridHelper.setBounds(x,y,width,height);
	}
	
	/********************ViewGroup************************/
	
	@Override
	protected void onLayout(boolean changed,int l,int t,int r,int b) {
		super.onLayout(changed,l,t,r,b);
		if (mFloridHelper != null) mFloridHelper.onLayout(changed);
	}
	
	
	@SuppressLint("MissingSuperCall")
	@Override
	public void draw(@NonNull Canvas canvas) {
		if (mFloridHelper != null) mFloridHelper.draw(canvas);
	}
	

	
	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		if (mFloridHelper != null) mFloridHelper.drawableStateChanged();
	}
	
	@Override
	public void dispatchDraw(@NonNull Canvas canvas) {
		if (mFloridHelper != null) mFloridHelper.dispatchDraw(canvas);
	}
	
	@Override
	public boolean drawChild(@NonNull Canvas canvas,@NonNull View child,long drawingTime) {
		if (mFloridHelper != null) mFloridHelper.drawChild(canvas,child,drawingTime);
		return super.drawChild(canvas,child,drawingTime);
	}
	
	@Override
	protected int getChildDrawingOrder(int childCount,int child) {
		if (mFloridHelper != null) return mFloridHelper.getChildDrawingOrder(childCount,child);
		return super.getChildDrawingOrder(childCount, child);
	}
	
	
	@Override
	public void invalidateDrawable(@NonNull Drawable drawable) {
		super.invalidateDrawable(drawable);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void invalidate(@NonNull Rect dirty) {
		super.invalidate(dirty);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void invalidate(int l,int t,int r,int b) {
		super.invalidate(l,t,r,b);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void postInvalidateDelayed(long delayMilliseconds) {
		super.postInvalidateDelayed(delayMilliseconds);
		if (mFloridHelper != null) mFloridHelper.postInvalidateParentIfNeededDelayed(delayMilliseconds);
	}
	
	@Override
	public void postInvalidateDelayed(
			long delayMilliseconds,int left,int top,int right,int bottom)
	{
		super.postInvalidateDelayed(delayMilliseconds,left,top,right,bottom);
		if (mFloridHelper != null) mFloridHelper.postInvalidateParentIfNeededDelayed(delayMilliseconds);
	}
	
	
	@Override
	public void setRotation(float rotation) {
		super.setRotation(rotation);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void setRotationY(float rotationY) {
		super.setRotationY(rotationY);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void setRotationX(float rotationX) {
		super.setRotationX(rotationX);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void setScaleX(float scaleX) {
		super.setScaleX(scaleX);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void setScaleY(float scaleY) {
		super.setScaleY(scaleY);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void setPivotX(float pivotX) {
		super.setPivotX(pivotX);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void setPivotY(float pivotY) {
		super.setPivotY(pivotY);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void setAlpha(
			@FloatRange(from = 0.0,
					to = 1.0) float alpha)
	{
		super.setAlpha(alpha);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void setTranslationX(float translationX) {
		super.setTranslationX(translationX);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	@Override
	public void setTranslationY(float translationY) {
		super.setTranslationY(translationY);
		if (mFloridHelper != null) mFloridHelper.invalidateParentIfNeeded();
	}
	
	
}
