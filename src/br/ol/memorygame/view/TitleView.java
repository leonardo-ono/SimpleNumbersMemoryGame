package br.ol.memorygame.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import br.ol.memorygame.AboutActivity;
import br.ol.memorygame.GameStartOptionActivity;
import br.ol.memorygame.OptionsActivity;
import br.ol.memorygame.model.MemoryGame;
import br.ol.memorygame.model.Options;

/**
 * TitleView.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 14:55)
 */
public class TitleView extends View {
    
    private static MemoryGame model;
    private int screenWidth;
    private int screenHeight;
    private Paint paint = new Paint();
    private Rect start;
    private Rect options;
    private Rect about;
    private Rect exit;
    
    public TitleView(Context context) {
        super(context);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        
        model = new MemoryGame();
        model.setNumberOfPairOfCards(Options.getInstance().getNumberOfPairsOfCards());
        model.setNumberOfSecondsToInitiallyShowCards(Options.getInstance().getNumberOfSeconds());
    }

    public static MemoryGame getMemoryGameModel() {
        return model;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        
        start = new Rect(w/2-100, 110, w/2+100, 150);
        options = new Rect(w/2-100, 160, w/2+100, 200);
        about = new Rect(w/2-100, 210, w/2+100, 250);
        exit = new Rect(w/2-100, 260, w/2+100, 300);
        
        model.calculateMaxNumberOfCards(w, h);
        invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(20, 20, 80);
        
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Simple Numbers", screenWidth/2, 40, paint);
        canvas.drawText("Memory Game", screenWidth/2, 80, paint);
        
        paint.setTextSize(30);
        paint.setStyle(Paint.Style.STROKE);

        paint.setColor(Color.argb(64, 0, 0, 0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(start, paint);
        canvas.drawRect(options, paint);
        canvas.drawRect(about, paint);
        canvas.drawRect(exit, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(start, paint);
        canvas.drawRect(options, paint);
        canvas.drawRect(about, paint);
        canvas.drawRect(exit, paint);
        
        canvas.drawText("Start", start.left+(start.right-start.left)/2, start.top+(start.bottom-start.top)-10, paint);
        canvas.drawText("Options", options.left+(options.right-start.left)/2, options.top+(options.bottom-options.top)-10, paint);
        canvas.drawText("About", about.left+(about.right-about.left)/2, about.top+(about.bottom-about.top)-10, paint);
        canvas.drawText("Exit", exit.left+(exit.right-exit.left)/2, exit.top+(exit.bottom-exit.top)-10, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (start.contains(x, y)) {
            Intent gameStartOptionActivityIntent = new Intent(getContext(), GameStartOptionActivity.class);
            getContext().startActivity(gameStartOptionActivityIntent);
        }
        else if (options.contains(x, y)) {
            Intent optionsActivityIntent = new Intent(getContext(), OptionsActivity.class);
            getContext().startActivity(optionsActivityIntent);
        }
        else if (about.contains(x, y)) {
            Intent creditsActivityIntent = new Intent(getContext(), AboutActivity.class);
            getContext().startActivity(creditsActivityIntent);
        }
        if (exit.contains(x, y)) {
            ((Activity) getContext()).finish();
        }
        return false;
    }
    
}
