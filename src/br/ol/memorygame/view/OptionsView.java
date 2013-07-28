package br.ol.memorygame.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import br.ol.memorygame.infra.Music;
import br.ol.memorygame.infra.Sound;
import br.ol.memorygame.model.Options;

/**
 * OptionsView.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 14:55)
 */
public class OptionsView extends View {
    
    private Paint paint = new Paint();
    private Rect sound;
    private Rect music;
    private Rect back;
    
    public OptionsView(Context context) {
        super(context);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        sound = new Rect(w/2-100, 20, w/2+100, 60);
        music = new Rect(w/2-100, 70, w/2+100, 110);
        back = new Rect(w/2-100, h-60, w/2+100, h-20);
        invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(20, 20, 80);
        
        paint.setColor(Color.argb(64, 0, 0, 0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(sound, paint);
        canvas.drawRect(music, paint);
        canvas.drawRect(back, paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(sound, paint);
        canvas.drawRect(music, paint);
        canvas.drawRect(back, paint);

        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        String soundOnOff = (Options.getInstance().isSoundEnabled() ? "ON" : "OFF");
        String musicOnOff = (Options.getInstance().isMusicEnabled() ? "ON" : "OFF");
        canvas.drawText("Sound " + soundOnOff, sound.left+(sound.right-sound.left)/2, sound.top+(sound.bottom-sound.top)-10, paint);
        canvas.drawText("Music " + musicOnOff, music.left+(music.right-sound.left)/2, music.top+(music.bottom-music.top)-10, paint);
        canvas.drawText("Back", back.left+(back.right-back.left)/2, back.top+(back.bottom-back.top)-10, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (sound.contains(x, y)) {
            Options.getInstance().setSoundEnabled(!Options.getInstance().isSoundEnabled());
            try {
                Options.save();
            } catch (Exception ex) {
            }
            Sound.play(Sound.ID_CORRECT);
            invalidate();
        }
        else if (music.contains(x, y)) {
            Options.getInstance().setMusicEnabled(!Options.getInstance().isMusicEnabled());
            try {
                Options.save();
            } catch (Exception ex) {
            }
            if (!Options.getInstance().isMusicEnabled()) {
                Music.pause();
            }
            else {
                Music.play();
            }
            invalidate();
        }
        else if (back.contains(x, y)) {
            ((Activity) getContext()).finish();
        }
        return false;
    }
    
}
