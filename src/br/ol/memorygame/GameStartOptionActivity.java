package br.ol.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import br.ol.memorygame.infra.Music;
import br.ol.memorygame.view.GameStartOptionView;

/**
 * GameStartOptionActivity.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (28/07/2013 15:55)
 */
public class GameStartOptionActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        // remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        GameStartOptionView gameStartOptionView = new GameStartOptionView(this);
        setContentView(gameStartOptionView);
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
