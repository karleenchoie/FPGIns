package com.example.fpgins;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class Hexagon extends View {

    private Bitmap mBitmap;
    private Paint mPaint;
    private RectF mOval;
    private float mAngle = 135;
    private Paint mTextPaint;

    public Hexagon(Context context) {
        super(context);
        // use your bitmap insted of R.drawable.ic_launcher
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOval = new RectF();
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(48);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(0xffffaa00);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Matrix m = new Matrix();
        RectF src = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        RectF dst = new RectF(0, 0, w, h);
        m.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);
        Shader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(m);
        mPaint.setShader(shader);
        m.mapRect(mOval, src);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xff0000aa);
        canvas.drawArc(mOval, -90, mAngle, true, mPaint);
        canvas.drawText("click me", getWidth() / 2, getHeight() / 2, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float w2 = getWidth() / 2f;
        float h2 = getHeight() / 2f;
        mAngle = (float) Math.toDegrees(Math.atan2(event.getY() - h2, event.getX() - w2));
        mAngle += 90 + 360;
        mAngle %= 360;
        invalidate();
        return true;
    }
}