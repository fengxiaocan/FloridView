package com.app.helper;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;

import com.app.R;
import com.app.shadow.ShadowView;
import com.app.view.StrokeView;

public class Florid {
	public static void initElevation(ShadowView view,TypedArray a) {
		float elevation = a.getDimension(R.styleable.FloridAttrs_florid_elevation,0);
		view.setElevation(elevation);
		view.setElevationShadowColor(a.getColorStateList(R.styleable.FloridAttrs_florid_elevationShadowColor));
		if (a.hasValue(R.styleable.FloridAttrs_florid_elevationAmbientShadowColor)) {
			view.setOutlineAmbientShadowColor(a.getColorStateList(R.styleable.FloridAttrs_florid_elevationAmbientShadowColor));
		}
		if (a.hasValue(R.styleable.FloridAttrs_florid_elevationSpotShadowColor)) {
			view.setOutlineSpotShadowColor(a.getColorStateList(R.styleable.FloridAttrs_florid_elevationSpotShadowColor));
		}
	}
	
	public static void initStroke(StrokeView strokeView,TypedArray a) {
		ColorStateList color  = a.getColorStateList(R.styleable.FloridAttrs_florid_stroke_color);
		if (color == null){
			color = ColorStateList.valueOf(Color.TRANSPARENT);
		}
		if (color != null) {
			strokeView.setStroke(color);
		}
		strokeView.setStrokeWidth(a.getDimension(R.styleable.FloridAttrs_florid_strokeWidth,0));
	}
}
