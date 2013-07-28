package br.ol.memorygame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import br.ol.memorygame.infra.Music;
import br.ol.memorygame.infra.Sound;
import br.ol.memorygame.view.TitleView;

/**
 * MainActivity.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 13:35)
 */
public class MainActivity extends Activity {
    
    public static Context context;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        // remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        context = this;
        TitleView titleView = new TitleView(this);

        // initialize sound
        if (!Sound.isInitialized()) {
            Sound.initialize(getAssets());
        }
        
        // initialize music
        if (!Music.isInitialized()) {
            Music.initialize(getAssets());
            Music.openDefault();
            Music.play();
        }

        setContentView(titleView);
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
