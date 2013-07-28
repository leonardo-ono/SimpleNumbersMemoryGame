package br.ol.memorygame.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * AboutView.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 14:57)
 */
public class AboutView extends View {
    
    private Paint paint = new Paint();
    private Rect back;
    
    public AboutView(Context context) {
        super(context);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        back = new Rect(w/2-100, h-60, w/2+100, h-20);
        invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(20, 20, 80);
        
        paint.setTextSize(11);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Simple Numbers Memory Game vs 1.00.00", 10, 30, paint);
        canvas.drawText("Programming by O.L. (ono.leo@gmail.com)", 10, 45, paint);

        canvas.drawText("Music by rezoner", 10, 70, paint);
        canvas.drawText("http://opengameart.org/content/trance-menu", 10, 85, paint);
        canvas.drawText("http://soundcloud.com/rezoner/", 10, 100, paint);

        canvas.drawText("Sound effects by Bertrof", 10, 130, paint);
        canvas.drawText("http://www.freesound.org/people/Bertrof/sounds/131657/", 10, 145, paint);
        canvas.drawText("http://www.freesound.org/people/Bertrof/sounds/131660/", 10, 160, paint);
        
        canvas.drawText("You can get the source code from", 10, 190, paint);
        canvas.drawText("https://github.com/leonardo-ono", 10, 205, paint);
        canvas.drawText("/SimpleNumbersMemoryGame", 10, 220, paint);

        canvas.drawText("Thanks for playing :o) !", 10, 250, paint);
        
        paint.setColor(Color.argb(64, 0, 0, 0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(back, paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(back, paint);
        
        paint.setTextSize(30);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Back", back.left+(back.right-back.left)/2, back.top+(back.bottom-back.top)-10, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (back.contains(x, y)) {
            ((Activity) getContext()).finish();
        }
        return false;
    }
    
}
