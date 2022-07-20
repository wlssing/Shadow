package com.tencent.shadow.sample.plugin.app.lib.gallery;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.shadow.sample.plugin.app.lib.base.R;


public class StrokeShadowTextView extends AppCompatTextView {
    protected AppCompatTextView borderText;
    public StrokeShadowTextView(@NonNull Context context) {
        this(context, null);
    }

    public StrokeShadowTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StrokeShadowTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        borderText = new AppCompatTextView(context, attrs);
        try {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StrokeShadowTextView);
            float strokeWidth = array.getDimension(R.styleable.StrokeShadowTextView_strokeWidth_, 0);
            int strokeColor = array.getColor(R.styleable.StrokeShadowTextView_strokeColor_, Color.WHITE);

            boolean isNeedShade = array.getBoolean(R.styleable.StrokeShadowTextView_isNeedShade_, false);
            int shadeStartColor = array.getColor(R.styleable.StrokeShadowTextView_shadeStartColor_, Color.WHITE);
            int shadeEndColor = array.getColor(R.styleable.StrokeShadowTextView_shadeEndColor_, Color.WHITE);
            float gradientHeight = array.getDimension(R.styleable.StrokeShadowTextView_gradientHeight_, 0);

            boolean isNeedStrokeShade = array.getBoolean(R.styleable.StrokeShadowTextView_isNeedStrokeShade_, false);
            int strokeShadeStartColor = array.getColor(R.styleable.StrokeShadowTextView_strokeShadeStartColor_, Color.WHITE);
            int strokeShadeEndColor = array.getColor(R.styleable.StrokeShadowTextView_strokeShadeEndColor_, Color.WHITE);
            float strokeGradientHeight = array.getDimension(R.styleable.StrokeShadowTextView_strokeGradientHeight_, 0);

            Shader shade = null;
            if (isNeedShade) {
                shade = new LinearGradient(0, 0, 0, gradientHeight,
                        new int[]{shadeStartColor, shadeEndColor}, null, TileMode.CLAMP);
            }

            Shader strokeShade = null;
            if (isNeedStrokeShade) {
                strokeShade = new LinearGradient(0, 0, 0, strokeGradientHeight,
                        new int[]{strokeShadeStartColor, strokeShadeEndColor}, null, TileMode.CLAMP);
            }
            array.recycle();
            init(shade, strokeWidth, strokeColor, strokeShade);
        }catch (Exception e){
        }
    }

    private void init(Shader shader, float strokeWidth, int strokeColor, Shader strokeShader) {
        if (shader != null) {
            getPaint().setShader(shader);
        }
        borderText.setGravity(getGravity());
        borderText.getPaint().setStyle(Paint.Style.STROKE);
        borderText.getPaint().setStrokeWidth(strokeWidth);
        if (strokeShader != null) {
            borderText.getPaint().setShader(strokeShader);
        } else {
            borderText.setTextColor(strokeColor);
        }
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        if (borderText != null) {
            borderText.setLayoutParams(params);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(borderText != null) {
            CharSequence tt = borderText.getText();
            if (tt == null || !tt.equals(this.getText())) {
                borderText.setText(getText().toString());
                this.postInvalidate();
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            borderText.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(borderText != null){
            borderText.layout(left, top, right, bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(borderText != null){
            borderText.draw(canvas);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        if(borderText != null){
            borderText.setLineSpacing(add,mult);
        }
    }

    public void setTextSkewX(float skewX) {
        getPaint().setTextSkewX(skewX);
        if(borderText != null){
            borderText.getPaint().setTextSkewX(skewX);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (borderText != null) {
            borderText.setText(getText().toString(), type);
        }
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        if (borderText != null) {
            borderText.setTextSize(size);
        }
    }

    @Override
    public void setTypeface(@Nullable Typeface tf) {
        super.setTypeface(tf);
        if(borderText != null){
            borderText.setTypeface(tf);
        }
    }

    @Override
    public void setGravity(int gravity) {
        super.setGravity(gravity);
        if(borderText != null){
            borderText.setGravity(gravity);
        }
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        if (borderText != null) {
            borderText.setTextSize(unit, size);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (borderText != null) {
            borderText.setBackgroundDrawable(background);
        }
    }

    @Override
    public void postInvalidate() {
        if(borderText != null){
            borderText.setText(getText().toString());
        }
        super.postInvalidate();
    }

    public void setStroke(float strokeWidth, int strokeColor){
        if (borderText != null) {
            borderText.getPaint().setStrokeWidth(strokeWidth);
            borderText.setTextColor(strokeColor);
        }
    }

    public void setStrokeShade(int strokeShadeStartColor, int strokeShadeEndColor){
        if(borderText != null) {
            Shader strokeShade = new LinearGradient(0, 0, 0, getTextSize(), new int[]{strokeShadeStartColor, strokeShadeEndColor}, null, TileMode.CLAMP);
            borderText.getPaint().setShader(strokeShade);
        }
    }

    public void setStrokeShade(int[] color){
        if(borderText != null) {
            Shader strokeShade = new LinearGradient(0, 0, 0, getTextSize(), color, null, TileMode.CLAMP);
            borderText.getPaint().setShader(strokeShade);
        }
    }

    public void setStrokeWidth(float strokeWidth){
        if(borderText != null) {
            borderText.getPaint().setStrokeWidth(strokeWidth);
        }
    }

    public void setShade(int shadeStartColor, int shadeEndColor){
        Shader shade = new LinearGradient(0, 0, 0, getTextSize(), new int[]{shadeStartColor, shadeEndColor}, null, TileMode.CLAMP);
        getPaint().setShader(shade);
    }

    public void setShade(int[] colors){
        Shader shade = new LinearGradient(0, 0, 0, getTextSize(), colors, null, TileMode.CLAMP);
        getPaint().setShader(shade);
    }


    public void setShadeV2(int shadeStartColor, int shadeEndColor){

        String s = (String) getText();
        Rect bounds = new Rect();
        Paint textPaint = getPaint();
        textPaint.getTextBounds(s, 0, s.length(), bounds);
        int baseline = getBaseline();
        bounds.top = baseline + bounds.top;
        bounds.bottom = baseline + bounds.bottom;
        int startPadding = getPaddingStart();
        bounds.left += startPadding;
        bounds.right = (int) textPaint.measureText(s, 0, s.length()) + startPadding;


        Shader shade = new LinearGradient(0,  bounds.top, 0, bounds.bottom, new int[]{shadeStartColor, shadeEndColor}, null, TileMode.CLAMP);
        textPaint.setShader(shade);
    }


    public void setStrokeShadeV2(int shadeStartColor, int shadeEndColor){
        if(borderText != null) {
            String s = (String) borderText.getText();
            Rect bounds = new Rect();
            Paint textPaint = borderText.getPaint();
            textPaint.getTextBounds(s, 0, s.length(), bounds);
            int baseline = borderText.getBaseline();
            bounds.top = baseline + bounds.top;
            bounds.bottom = baseline + bounds.bottom;
            int startPadding = borderText.getPaddingStart();
            bounds.left += startPadding;
            bounds.right = (int) textPaint.measureText(s, 0, s.length()) + startPadding;

            Shader shade = new LinearGradient(0,  bounds.top, 0, bounds.bottom, new int[]{shadeStartColor, shadeEndColor}, null, TileMode.CLAMP);
            textPaint.setShader(shade);
        }
    }

    public void addStroke(float strokeWidth){
        TextPaint paint = getPaint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(strokeWidth);
        invalidate();
    }
}
