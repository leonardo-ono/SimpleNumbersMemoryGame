package br.ol.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import br.ol.memorygame.infra.Music;
import br.ol.memorygame.view.AboutView;

/**
 * AboutActivity.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 15:00)
 */
public class AboutActivity extends Activity {
    
    private AboutView aboutView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        // remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        aboutView = new AboutView(this);
        setContentView(aboutView);
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
