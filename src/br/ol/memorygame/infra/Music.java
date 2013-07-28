package br.ol.memorygame.infra;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import br.ol.memorygame.model.Options;
import java.io.IOException;

/**
 * Music class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (28/07/2013 15:22)
 */
public class Music {
    
    private static boolean initialized = false;
    private static MediaPlayer mp;
    private static AssetManager assetManager;
    
    public static void initialize(AssetManager assetManager) {
        Music.assetManager = assetManager;
        initialized = true;
    }

    public static boolean isInitialized() {
        return initialized;
    }
    
    public static void openDefault() {
        open("music.ogg");
    }

    public static void open(String musica) {
        try {
            if (mp!=null) {
                mp.stop();
            }
            mp = new MediaPlayer();
            AssetFileDescriptor afd = assetManager.openFd(musica);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            mp.start();
            mp.pause();
        } catch (IOException ex) {
        }               
    }

    public static void play() {
        if (!Options.getInstance().isMusicEnabled()) {
            return;
        }
        if (mp.isPlaying()) {
            return;
        }
        mp.start();
        mp.setLooping(true);
    }

    public static void pause() {
        if (mp!=null) {
            mp.pause();
        }
    }

    public static void resume() {
        if (!Options.getInstance().isMusicEnabled()) {
            return;
        }
        if (mp!=null) {
            mp.start();
        }
    }

    public static void stop() {
        if (mp!=null) {
            mp.stop();
        }
    }
    
    public static void release() {
        if (mp!=null) {
            try {
                mp.stop();
                mp.release();
            }
            catch (Exception e) {
            }
        }
    }

    public static void setVolume(float volume) {
        if (mp!=null) {
            mp.setVolume(volume, volume);
        }
    }
    
}
