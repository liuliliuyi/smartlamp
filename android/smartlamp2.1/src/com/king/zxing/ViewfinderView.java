package com.king.zxing;

/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.parking.smarthome.R;


import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

    private static final long ANIMATION_DELAY = 15L;
    private static final int CURRENT_POINT_OPACITY = 0xA0;
    private static final int MAX_RESULT_POINTS = 20;
    private static final int POINT_SIZE = 20;

    private static final int CORNER_RECT_WIDTH             =   8;  //鎵弿鍖鸿竟瑙掔殑瀹�
    private static final int CORNER_RECT_HEIGHT            =   40; //鎵弿鍖鸿竟瑙掔殑楂�
    private static final int SCANNER_LINE_MOVE_DISTANCE    =   6;  //鎵弿绾跨Щ鍔ㄨ窛绂�
    private static final int SCANNER_LINE_HEIGHT           =   10;  //鎵弿绾垮搴�

    private Paint paint;
    private TextPaint textPaint;
    private int maskColor;
    //鎵弿鍖哄煙杈规棰滆壊
    private int frameColor;
    //鎵弿绾块鑹�
    private int laserColor;
    //鍥涜棰滆壊
    private int cornerColor;
    private int resultPointColor;

    private float labelTextPadding;
    private TextLocation labelTextLocation;
    //鎵弿鍖哄煙鎻愮ず鏂囨湰
    private String labelText;
    //鎵弿鍖哄煙鎻愮ず鏂囨湰棰滆壊
    private int labelTextColor;
    private float labelTextSize;
    public int scannerStart = 0;
    public int scannerEnd = 0;
    private boolean isShowResultPoint;

    private int screenWidth;
    private int screenHeight;
    //鎵爜妗嗗
    private int frameWidth;
    //鎵爜妗嗗
    private int frameHeight;
    //鎵弿婵�鍏夌嚎椋庢牸
    private LaserStyle laserStyle;

    private int gridColumn;
    private int gridHeight;

    private Rect frame;


    private List<ResultPoint> possibleResultPoints;
    private List<ResultPoint> lastPossibleResultPoints;

    public enum LaserStyle{
        NONE(0),LINE(1),GRID(2);
        private int mValue;
        LaserStyle(int value){
            mValue = value;
        }

        private static LaserStyle getFromInt(int value){

            for(LaserStyle style : LaserStyle.values()){
                if(style.mValue == value){
                    return style;
                }
            }

            return LaserStyle.LINE;
        }
    }

    public enum TextLocation {
        TOP(0),BOTTOM(1);

        private int mValue;

        TextLocation(int value){
            mValue = value;
        }

        private static TextLocation getFromInt(int value){

            for(TextLocation location : TextLocation.values()){
                if(location.mValue == value){
                    return location;
                }
            }

            return TextLocation.TOP;
        }


    }

    public ViewfinderView(Context context) {
        this(context,null);
    }

    public ViewfinderView(Context context,AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewfinderView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        //鍒濆鍖栬嚜瀹氫箟灞炴�т俊鎭�
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewfinderView); 
        maskColor = array.getColor(R.styleable.ViewfinderView_maskColor, getResources().getColor(R.color.viewfinder_mask));
        frameColor = array.getColor(R.styleable.ViewfinderView_frameColor, getResources().getColor(R.color.viewfinder_frame));// getResources().getColor(R.color.viewfinder_frame)
        cornerColor = array.getColor(R.styleable.ViewfinderView_cornerColor, getResources().getColor(R.color.viewfinder_corner));// getResources().getColor(R.color.viewfinder_corner
        laserColor = array.getColor(R.styleable.ViewfinderView_laserColor,getResources().getColor(R.color.viewfinder_laser)); // getResources().getColor(R.color.viewfinder_laser
        resultPointColor = array.getColor(R.styleable.ViewfinderView_resultPointColor, getResources().getColor(R.color.viewfinder_result_point_color)); // getResources().getColor(R.color.viewfinder_result_point_color

        labelText = array.getString(R.styleable.ViewfinderView_labelText);
        labelTextColor = array.getColor(R.styleable.ViewfinderView_labelTextColor, getResources().getColor(R.color.viewfinder_text_color));// getResources().getColor(R.color.viewfinder_text_color
        labelTextSize = array.getDimension(R.styleable.ViewfinderView_labelTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,14f,getResources().getDisplayMetrics()));
        labelTextPadding = array.getDimension(R.styleable.ViewfinderView_labelTextPadding,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
        labelTextLocation = TextLocation.getFromInt(array.getInt(R.styleable.ViewfinderView_labelTextLocation,0));

        isShowResultPoint = array.getBoolean(R.styleable.ViewfinderView_showResultPoint,false);

        frameWidth = array.getDimensionPixelSize(R.styleable.ViewfinderView_frameWidth,0);
        frameHeight = array.getDimensionPixelSize(R.styleable.ViewfinderView_frameHeight,0);

        laserStyle = LaserStyle.getFromInt(array.getInt(R.styleable.ViewfinderView_laserStyle,LaserStyle.LINE.mValue));
        gridColumn = array.getInt(R.styleable.ViewfinderView_gridColumn,20);
        gridHeight = (int)array.getDimension(R.styleable.ViewfinderView_gridHeight,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,40,getResources().getDisplayMetrics()));

        array.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        possibleResultPoints = new ArrayList<>(5);
        lastPossibleResultPoints = null;

        screenWidth = getDisplayMetrics().widthPixels;
        screenHeight = getDisplayMetrics().heightPixels;

        int size = (int)(Math.min(screenWidth,screenHeight) * 0.625f);

        if(frameWidth<=0 || frameWidth > screenWidth){
            frameWidth = size;
        }

        if(frameHeight<=0 || frameHeight > screenHeight){
            frameHeight = size;
        }

    }

    private DisplayMetrics getDisplayMetrics(){
        return getResources().getDisplayMetrics();
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public void setLabelTextColor(int color) {
        this.labelTextColor = color;
    }

    public void setLabelTextColorResource(int id){
    	// getResources().getColor(id);
        this.labelTextColor = getResources().getColor(id);
    }

    public void setLabelTextSize(float textSize) {
        this.labelTextSize = textSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //鎵爜妗嗛粯璁ゅ眳涓紝鏀寔鍒╃敤鍐呰窛鍋忕Щ鎵爜妗�
        int leftOffset = (screenWidth - frameWidth) / 2 + getPaddingLeft() - getPaddingRight();
        int topOffset = (screenHeight - frameHeight) / 2 + getPaddingTop() - getPaddingBottom();
        frame = new Rect(leftOffset, topOffset, leftOffset + frameWidth, topOffset + frameHeight);
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {

        if (frame == null) {
            return;
        }

        if(scannerStart == 0 || scannerEnd == 0) {
            scannerStart = frame.top;
            scannerEnd = frame.bottom - SCANNER_LINE_HEIGHT;
        }

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        drawExterior(canvas,frame,width,height);
        // Draw a red "laser scanner" line through the middle to show decoding is active
        drawLaserScanner(canvas,frame);
        // Draw a two pixel solid black border inside the framing rect
        drawFrame(canvas, frame);
        // 缁樺埗杈硅
        drawCorner(canvas, frame);
        //缁樺埗鎻愮ず淇℃伅
        drawTextInfo(canvas, frame);
        //缁樺埗鎵爜缁撴灉鐐�
        drawResultPoint(canvas,frame);
        // Request another update at the animation interval, but only repaint the laser line,
        // not the entire viewfinder mask.
        postInvalidateDelayed(ANIMATION_DELAY,
                frame.left - POINT_SIZE,
                frame.top - POINT_SIZE,
                frame.right + POINT_SIZE,
                frame.bottom + POINT_SIZE);
    }

    /**
     * 缁樺埗鏂囨湰
     * @param canvas
     * @param frame
     */
    private void drawTextInfo(Canvas canvas, Rect frame) {
        if(!TextUtils.isEmpty(labelText)){
            textPaint.setColor(labelTextColor);
            textPaint.setTextSize(labelTextSize);
            textPaint.setTextAlign(Paint.Align.CENTER);
            StaticLayout staticLayout = new StaticLayout(labelText,textPaint,canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL,1.0f,0.0f,true);
            if(labelTextLocation == TextLocation.BOTTOM){
                canvas.translate(frame.left + frame.width() / 2,frame.bottom + labelTextPadding);
                staticLayout.draw(canvas);
            }else{
                canvas.translate(frame.left + frame.width() / 2,frame.top - labelTextPadding - staticLayout.getHeight());
                staticLayout.draw(canvas);
            }
        }

    }

    /**
     * 缁樺埗杈硅
     * @param canvas
     * @param frame
     */
    private void drawCorner(Canvas canvas, Rect frame) {
        paint.setColor(cornerColor);
        //宸︿笂
        canvas.drawRect(frame.left, frame.top, frame.left + CORNER_RECT_WIDTH, frame.top + CORNER_RECT_HEIGHT, paint);
        canvas.drawRect(frame.left, frame.top, frame.left + CORNER_RECT_HEIGHT, frame.top + CORNER_RECT_WIDTH, paint);
        //鍙充笂
        canvas.drawRect(frame.right - CORNER_RECT_WIDTH, frame.top, frame.right, frame.top + CORNER_RECT_HEIGHT, paint);
        canvas.drawRect(frame.right - CORNER_RECT_HEIGHT, frame.top, frame.right, frame.top + CORNER_RECT_WIDTH, paint);
        //宸︿笅
        canvas.drawRect(frame.left, frame.bottom - CORNER_RECT_WIDTH, frame.left + CORNER_RECT_HEIGHT, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - CORNER_RECT_HEIGHT, frame.left + CORNER_RECT_WIDTH, frame.bottom, paint);
        //鍙充笅
        canvas.drawRect(frame.right - CORNER_RECT_WIDTH, frame.bottom - CORNER_RECT_HEIGHT, frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - CORNER_RECT_HEIGHT, frame.bottom - CORNER_RECT_WIDTH, frame.right, frame.bottom, paint);
    }

    /**
     * 缁樺埗婵�鍏夋壂鎻忕嚎
     * @param canvas
     * @param frame
     */
    private void drawLaserScanner(Canvas canvas, Rect frame) {
        if(laserStyle!=null){
            paint.setColor(laserColor);
            switch (laserStyle){
                case LINE://绾�
                    drawLineScanner(canvas,frame);
                    break;
                case GRID://缃戞牸
                    drawGridScanner(canvas,frame);
                    break;
            }
            paint.setShader(null);
        }
    }

    /**
     * 缁樺埗绾挎�у紡鎵弿
     * @param canvas
     * @param frame
     */
    private void drawLineScanner(Canvas canvas,Rect frame){
        //绾挎�ф笎鍙�
        LinearGradient linearGradient = new LinearGradient(
                frame.left, scannerStart,
                frame.left, scannerStart + SCANNER_LINE_HEIGHT,
                shadeColor(laserColor),
                laserColor,
                Shader.TileMode.MIRROR);

        paint.setShader(linearGradient);
        if(scannerStart <= scannerEnd) {
            //妞渾
            RectF rectF = new RectF(frame.left + 2 * SCANNER_LINE_HEIGHT, scannerStart, frame.right - 2 * SCANNER_LINE_HEIGHT, scannerStart + SCANNER_LINE_HEIGHT);
            canvas.drawOval(rectF, paint);
            scannerStart += SCANNER_LINE_MOVE_DISTANCE;
        } else {
            scannerStart = frame.top;
        }
    }

    /**
     * 缁樺埗缃戞牸寮忔壂鎻�
     * @param canvas
     * @param frame
     */
    private void drawGridScanner(Canvas canvas,Rect frame){
        int stroke = 2;
        paint.setStrokeWidth(stroke);
        //璁＄畻Y杞村紑濮嬩綅缃�
        int startY = gridHeight > 0 && scannerStart - frame.top > gridHeight ? scannerStart - gridHeight : frame.top;

        LinearGradient linearGradient = new LinearGradient(frame.left + frame.width()/2, startY, frame.left + frame.width()/2, scannerStart, new int[]{shadeColor(laserColor), laserColor}, new float[]{0,1f}, LinearGradient.TileMode.CLAMP);
        //缁欑敾绗旇缃潃鑹插櫒
        paint.setShader(linearGradient);

        float wUnit = frame.width() * 1.0f/ gridColumn;
        float hUnit = wUnit;
        //閬嶅巻缁樺埗缃戞牸绾电嚎
        for (int i = 1; i < gridColumn; i++) {
            canvas.drawLine(frame.left + i * wUnit, startY,frame.left + i * wUnit, scannerStart,paint);
        }

        int height = gridHeight > 0 && scannerStart - frame.top > gridHeight ? gridHeight : scannerStart - frame.top;

        //閬嶅巻缁樺埗缃戞牸妯嚎
        for (int i = 0; i <= height/hUnit; i++) {
            canvas.drawLine(frame.left, scannerStart - i * hUnit,frame.right, scannerStart - i * hUnit,paint);
        }

        if(scannerStart<scannerEnd){
            scannerStart += SCANNER_LINE_MOVE_DISTANCE;
        } else {
            scannerStart = frame.top;
        }

    }

    /**
     * 澶勭悊棰滆壊妯＄硦
     * @param color
     * @return
     */
    public int shadeColor(int color) {
        String hax = Integer.toHexString(color);
        String result = "01"+hax.substring(2);
        return Integer.valueOf(result, 16);
    }

    /**
     * 缁樺埗鎵弿鍖鸿竟妗�
     * @param canvas
     * @param frame
     */
    private void drawFrame(Canvas canvas, Rect frame) {
        paint.setColor(frameColor);
        canvas.drawRect(frame.left, frame.top, frame.right + 1, frame.top + 2, paint);
        canvas.drawRect(frame.left, frame.top + 2, frame.left + 2, frame.bottom - 1, paint);
        canvas.drawRect(frame.right - 1, frame.top, frame.right + 1, frame.bottom - 1, paint);
        canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1, frame.bottom + 1, paint);
    }

    /**
     * 缁樺埗妯＄硦鍖哄煙
     * @param canvas
     * @param frame
     * @param width
     * @param height
     */
    private void drawExterior(Canvas canvas, Rect frame, int width, int height) {
        paint.setColor(maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);
    }

    /**
     * 缁樺埗鎵爜缁撴灉鐐�
     * @param canvas
     * @param frame
     */
    private void drawResultPoint(Canvas canvas,Rect frame){

        if(!isShowResultPoint){
            return;
        }

        List<ResultPoint> currentPossible = possibleResultPoints;
        List<ResultPoint> currentLast = lastPossibleResultPoints;

        if (currentPossible.isEmpty()) {
            lastPossibleResultPoints = null;
        } else {
            possibleResultPoints = new ArrayList<>(5);
            lastPossibleResultPoints = currentPossible;
            paint.setAlpha(CURRENT_POINT_OPACITY);
            paint.setColor(resultPointColor);
            synchronized (currentPossible) {
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle( point.getX(),point.getY(), radius, paint);
                }
            }
        }
        if (currentLast != null) {
            paint.setAlpha(CURRENT_POINT_OPACITY / 2);
            paint.setColor(resultPointColor);
            synchronized (currentLast) {
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle( point.getX(),point.getY(), radius, paint);
                }
            }
        }
    }

    public void drawViewfinder() {
        invalidate();
    }

    public boolean isShowResultPoint() {
        return isShowResultPoint;
    }

    public void setLaserStyle(LaserStyle laserStyle) {
        this.laserStyle = laserStyle;
    }

    /**
     * 璁剧疆鏄剧ず缁撴灉鐐�
     * @param showResultPoint 鏄惁鏄剧ず缁撴灉鐐�
     */
    public void setShowResultPoint(boolean showResultPoint) {
        isShowResultPoint = showResultPoint;
    }


    public void addPossibleResultPoint(ResultPoint point) {
        if(isShowResultPoint){
            List<ResultPoint> points = possibleResultPoints;
            synchronized (points) {
                points.add(point);
                int size = points.size();
                if (size > MAX_RESULT_POINTS) {
                    // trim it
                    points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
                }
            }
        }

    }



}