package br.ol.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import br.ol.memorygame.infra.Music;
import br.ol.memorygame.infra.Sound;
import br.ol.memorygame.model.MemoryGame;
import br.ol.memorygame.view.GameView;
import br.ol.memorygame.view.TitleView;

/**
 * GameActivity.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 15:01)
 */
public class GameActivity extends Activity {

    public static final String MEMORY_GAME_MODEL = "MEMORY_GAME_MODEL";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        // remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        MemoryGame gameModel = TitleView.getMemoryGameModel();
        GameView gameView = new GameView(this, gameModel);
        gameModel.clearListeners();
        gameModel.addListener(gameView);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Music.resume();
    }
    
}
