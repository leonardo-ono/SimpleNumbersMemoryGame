package br.ol.memorygame.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import br.ol.memorygame.infra.Sound;
import br.ol.memorygame.model.Card;
import br.ol.memorygame.model.MemoryGame;
import br.ol.memorygame.model.MemoryGameListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * GameView.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 14:52)
 */
public class GameView extends View implements MemoryGameListener {

    private int screenWidth;
    private int screenHeight;
    private MemoryGame model;
    private Paint paint = new Paint();
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private Rect retry;
    private Rect back;
    
    public GameView(Context context, MemoryGame model) {
        super(context);
        this.model = model;
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        retry = new Rect(w/2-100, h-110, w/2+100, h-70);
        back = new Rect(w/2-100, h-60, w/2+100, h-20);
        model.positionAllCards(w, h);
        model.calculateMaxNumberOfCards(w, h);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(20, 20, 80);
        
        drawAllCards(canvas);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        
        if (model.getState() == MemoryGame.State.WAITING_START_INITIALLY_SHOWING_CARDS) {
            // do nothing
        }
        else if (model.getState() == MemoryGame.State.INITIALLY_SHOWING_CARDS) {
            paint.setTextSize(15);
            canvas.drawText("Initially showing cards during " + model.getNumberOfSecondsToInitiallyShowCards() + " seconds ...", 10, 30, paint);
        }
        else {
            paint.setTextSize(20);
            canvas.drawText("Number of tries: " + model.getTriesCount(), 10, 30, paint);
        }
        if (model.getState() == MemoryGame.State.ENDED) { 
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(30);
            canvas.drawText("CONGRATULATIONS !", screenWidth/2, screenHeight/2, paint);
            
            paint.setColor(Color.argb(64, 0, 0, 0));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(retry, paint);
            canvas.drawRect(back, paint);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(retry, paint);
            canvas.drawRect(back, paint);

            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Retry", retry.left+(retry.right-retry.left)/2, retry.top+(retry.bottom-retry.top)-10, paint);
            canvas.drawText("Back", back.left+(back.right-back.left)/2, back.top+(back.bottom-back.top)-10, paint);
        }
        if (model.getState() == MemoryGame.State.WAITING_START_INITIALLY_SHOWING_CARDS) {
            canvas.drawARGB(200, 0, 0, 0);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(30);
            canvas.drawText("TAP TO START", screenWidth/2, screenHeight/2, paint);
        }
    }

    private void drawAllCards(Canvas canvas) {
        for (Card card : model.getCards()) {
            if (!card.isVisible()) {
                continue;
            }
            
            // draw card shadow
            paint.setColor(Color.argb(127, 0, 0, 0));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(card.getX()+5, card.getY()+5, card.getX()+card.getWidth()+5, card.getY()+card.getHeight()+5, paint);
            paint.setColor(Color.rgb(20, 20, 80));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(card.getX(), card.getY(), card.getX()+card.getWidth(), card.getY()+card.getHeight(), paint);
            
            if (card.isTurned()) {
                paint.setColor(Color.argb(64, 255, 255, 255));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(card.getX(), card.getY(), card.getX()+card.getWidth(), card.getY()+card.getHeight(), paint);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(card.getX(), card.getY(), card.getX()+card.getWidth(), card.getY()+card.getHeight(), paint);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(30);
                canvas.drawText("?", (float) (card.getX()+card.getWidth()/2.0), (float) (card.getY()+card.getHeight()/2.0)+10, paint);
            }
            else {
                
                if (model.getState() == MemoryGame.State.INITIALLY_SHOWING_CARDS) {
                    paint.setColor(Color.argb(32, 0, 0, 0));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(card.getX(), card.getY(), card.getX()+card.getWidth(), card.getY()+card.getHeight(), paint);
                    paint.setColor(Color.WHITE);
                }
                else if (model.isCorrect()==null) {
                    paint.setColor(Color.argb(32, 0, 0, 0));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(card.getX(), card.getY(), card.getX()+card.getWidth(), card.getY()+card.getHeight(), paint);
                    paint.setColor(Color.WHITE);
                }
                else if (model.isCorrect()) {
                    paint.setColor(Color.argb(64, 0, 127, 0));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(card.getX(), card.getY(), card.getX()+card.getWidth(), card.getY()+card.getHeight(), paint);
                    paint.setColor(Color.GREEN);
                }
                else {
                    paint.setColor(Color.argb(64, 127, 0, 0));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(card.getX(), card.getY(), card.getX()+card.getWidth(), card.getY()+card.getHeight(), paint);
                    paint.setColor(Color.RED);
                }
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(card.getX(), card.getY(), card.getX()+card.getWidth(), card.getY()+card.getHeight(), paint);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(30);
                canvas.drawText(card.getValue() + "", (float) (card.getX()+card.getWidth()/2.0), (float) (card.getY()+card.getHeight()/2.0)+10, paint);
            }
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            
            if (model.getState() == MemoryGame.State.WAITING_START_INITIALLY_SHOWING_CARDS) {
                model.initiallyShowCards();
                return false;
            }
            else if (model.getState() == MemoryGame.State.ENDED && retry.contains(x, y)) {
                model.start();
                model.positionAllCards(screenWidth, screenHeight);
                if (model.getNumberOfSecondsToInitiallyShowCards() == 0) {
                    model.startSelectCard1();
                }
                return false;
            }
            else if (model.getState() == MemoryGame.State.ENDED && back.contains(x, y)) {
                ((Activity) getContext()).finish();
                return false;
            }
            
            Card selectedCard = null;
            for (Card card : model.getCards()) {
                Rect cardRect = new Rect(card.getX(), card.getY(), card.getX()+card.getWidth(), card.getY()+card.getHeight());
                if (card.isVisible() && cardRect.contains((int) event.getX(), (int) event.getY())) {
                    selectedCard = card;
                    break;
                }
            }
            if (selectedCard == null) {
                return false;
            }
            if (model.getState() == MemoryGame.State.WAITING_CARD1) {
                model.selectCard1(selectedCard);
            }
            else if (model.getState() == MemoryGame.State.WAITING_CARD2) {
                model.selectCard2(selectedCard);
            }
        }
        return false;
    }

    // MemoryGameListener implementation

    public void gameStarted() {
        invalidate();
    }
    
    public void gameStartedInitiallyShowingCards() {
        invalidate();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                model.startSelectCard1();
            }
        };
        timer.schedule(timerTask, 1000 * model.getNumberOfSecondsToInitiallyShowCards());
    }

    public void gameStartedSelectCard1() {
        handler.post(new Runnable() {
            public void run() {
                invalidate();
            }
        });
    }
    
    public void card1Selected(Card card) {
        invalidate();
    }

    public void card2Selected(Card card) {
        invalidate();
    }

    public void showResult(boolean isCorrect) {
        if (isCorrect) {
            Sound.play(Sound.ID_CORRECT);
        }
        else {
            Sound.play(Sound.ID_WRONG);
        }
        invalidate();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                model.nextTry();
            }
        };
        timer.schedule(timerTask, 1000);
    }
    
    public void nextTryRequested() {
        handler.post(new Runnable() {
            public void run() {
                invalidate();
            }
        });
    }
    
    public void gameEnded() {
        handler.post(new Runnable() {
            public void run() {
                invalidate();
            }
        });
    }
    
}
