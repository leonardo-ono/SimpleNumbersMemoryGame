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
import br.ol.memorygame.GameActivity;
import br.ol.memorygame.OptionsActivity;
import br.ol.memorygame.model.MemoryGame;
import br.ol.memorygame.model.Options;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TitleView.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 14:55)
 */
public class GameStartOptionView extends View {
    
    private int screenWidth;
    private int screenHeight;
    private Paint paint = new Paint();
    private Rect subNumberOfCards;
    private Rect addNumberOfCards;

    private Rect subNumberOfSeconds;
    private Rect addNumberOfSeconds;
    
    private Rect play;
    private Rect back;
    
    public GameStartOptionView(Context context) {
        super(context);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        subNumberOfCards = new Rect(w/2-75, 50, w/2-25, 90);
        addNumberOfCards = new Rect(w/2+25, 50, w/2+75, 90);

        subNumberOfSeconds = new Rect(w/2-75, 140, w/2-25, 180);
        addNumberOfSeconds = new Rect(w/2+25, 140, w/2+75, 180);
        
        play = new Rect(w/2-100, 210, w/2+100, 250);
        back = new Rect(w/2-100, 260, w/2+100, 300);
        invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(20, 20, 80);
        
        paint.setColor(Color.argb(64, 0, 0, 0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(subNumberOfCards, paint);
        canvas.drawRect(addNumberOfCards, paint);
        canvas.drawRect(subNumberOfSeconds, paint);
        canvas.drawRect(addNumberOfSeconds, paint);
        canvas.drawRect(play, paint);
        canvas.drawRect(back, paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(subNumberOfCards, paint);
        canvas.drawRect(addNumberOfCards, paint);
        canvas.drawRect(subNumberOfSeconds, paint);
        canvas.drawRect(addNumberOfSeconds, paint);
        canvas.drawRect(play, paint);
        canvas.drawRect(back, paint);
        
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(15);
        canvas.drawText("Number of cards:", screenWidth/2, 35, paint);
        canvas.drawText("Number of seconds to initially show cards:", screenWidth/2, 125, paint);
        
        paint.setTextSize(30);
        canvas.drawText("-", subNumberOfCards.left+(subNumberOfCards.right-subNumberOfCards.left)/2, subNumberOfCards.top+(subNumberOfCards.bottom-subNumberOfCards.top)-10, paint);
        canvas.drawText("+", addNumberOfCards.left+(addNumberOfCards.right-addNumberOfCards.left)/2, addNumberOfCards.top+(addNumberOfCards.bottom-addNumberOfCards.top)-10, paint);
        canvas.drawText("-", subNumberOfSeconds.left+(subNumberOfSeconds.right-subNumberOfSeconds.left)/2, subNumberOfSeconds.top+(subNumberOfSeconds.bottom-subNumberOfSeconds.top)-10, paint);
        canvas.drawText("+", addNumberOfSeconds.left+(addNumberOfSeconds.right-addNumberOfSeconds.left)/2, addNumberOfSeconds.top+(addNumberOfSeconds.bottom-addNumberOfSeconds.top)-10, paint);
        canvas.drawText((TitleView.getMemoryGameModel().getNumberOfPairOfCards()*2) + "", screenWidth/2, 80, paint);
        canvas.drawText((TitleView.getMemoryGameModel().getNumberOfSecondsToInitiallyShowCards()) + "", screenWidth/2, 170, paint);
        canvas.drawText("Play", play.left+(play.right-play.left)/2, play.top+(play.bottom-play.top)-10, paint);
        canvas.drawText("Back", back.left+(back.right-back.left)/2, back.top+(back.bottom-back.top)-10, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (subNumberOfCards.contains(x, y)) {
            TitleView.getMemoryGameModel().decNumberOfPairOfCards();
            Options.getInstance().setNumberOfPairsOfCards(TitleView.getMemoryGameModel().getNumberOfPairOfCards());
            try {
                Options.save();
            } catch (Exception ex) {
            }
            invalidate();
        }
        else if (addNumberOfCards.contains(x, y)) {
            TitleView.getMemoryGameModel().incNumberOfPairOfCards();
            Options.getInstance().setNumberOfPairsOfCards(TitleView.getMemoryGameModel().getNumberOfPairOfCards());
            try {
                Options.save();
            } catch (Exception ex) {
            }
            invalidate();
        }
        else if (subNumberOfSeconds.contains(x, y)) {
            TitleView.getMemoryGameModel().decNumberOfSecondsToInitiallyShowCards();
            Options.getInstance().setNumberOfSeconds(TitleView.getMemoryGameModel().getNumberOfSecondsToInitiallyShowCards());
            try {
                Options.save();
            } catch (Exception ex) {
            }
            invalidate();
        }
        else if (addNumberOfSeconds.contains(x, y)) {
            TitleView.getMemoryGameModel().incNumberOfSecondsToInitiallyShowCards();
            Options.getInstance().setNumberOfSeconds(TitleView.getMemoryGameModel().getNumberOfSecondsToInitiallyShowCards());
            try {
                Options.save();
            } catch (Exception ex) {
            }
            invalidate();
        }
        else if (play.contains(x, y)) {
            TitleView.getMemoryGameModel().start();
            if (TitleView.getMemoryGameModel().getNumberOfSecondsToInitiallyShowCards() == 0) {
                TitleView.getMemoryGameModel().startSelectCard1();
            }
            Intent gameActivityIntent = new Intent(getContext(), GameActivity.class);
            getContext().startActivity(gameActivityIntent);
        }
        if (back.contains(x, y)) {
            ((Activity) getContext()).finish();
        }
        return false;
    }
    
}
