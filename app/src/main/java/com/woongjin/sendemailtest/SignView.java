package com.woongjin.sendemailtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class SignView extends SurfaceView implements SurfaceHolder.Callback {

    Context context;

    SurfaceHolder mHolder;
    Canvas cacheCanvas;
    Paint paint;

    Bitmap backBuffer;
    Bitmap checkBitmap;

    int width, height;

    int lastX, lastY, currX, currY;

    public SignView(Context context) {
        super(context);
        initSignView(context);
    }

    public SignView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSignView(context);
    }

    public SignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSignView(context);
    }

    private void initSignView(Context context) {

        this.context = context;

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    /**
     * Sign Bitmap 이미지를 반환한다.
     * @return
     */
    public Bitmap getSignToBitmap() {

        return backBuffer;
    }

    /**
     * 사인을 지운다.
     */
    public void clearSign() {

        cacheCanvas.drawColor(getResources().getColor(R.color.white));
        draw();

        checkBitmap.recycle();
        checkBitmap = backBuffer.copy(backBuffer.getConfig(), false);
    }

    /**
     * 빈 여백인지 체크해서 반환
     *
     * @return
     *  : true -> 빈 여백
     *  : false -> 사인 했음
     */
    public boolean hasEmptySignData() {

        return backBuffer.sameAs(checkBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        switch(action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                currX = (int) event.getX();
                currY = (int) event.getY();
                cacheCanvas.drawLine(lastX, lastY, currX, currY, paint);
                lastX = currX;
                lastY = currY;

                break;
        }
        // SurfaceView에 그림 그림
        draw();
        return true;
    }

    protected void draw() {

        Canvas canvas = null;
        try{
            canvas = mHolder.lockCanvas();
            //back buffer에 그려진 비트맵을 스크린 버퍼에 그린다
            canvas.drawBitmap(backBuffer, 0,0, paint);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(mHolder!=null)  mHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        width = context.getResources().getDimensionPixelSize(R.dimen.popup_sign_width);
        height = context.getResources().getDimensionPixelSize(R.dimen.popup_sign_height);
        cacheCanvas = new Canvas();
        backBuffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444); // back buffer
        cacheCanvas.setBitmap(backBuffer);
        cacheCanvas.drawColor(getResources().getColor(R.color.white));

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        draw();
        checkBitmap = backBuffer.copy(backBuffer.getConfig(), false);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        if(backBuffer.isRecycled() == false) {

            backBuffer.recycle();
        }

        if(checkBitmap.isRecycled() == false) {

            checkBitmap.recycle();
        }
    }
}
