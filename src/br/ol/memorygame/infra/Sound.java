package br.ol.memorygame.infra;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import br.ol.memorygame.model.Options;
import java.io.IOException;

/**
 * Sound class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (28/07/2013 14:57)
 */
public class Sound {
    
    public static int ID_CORRECT;
    public static int ID_WRONG;
    
    private static SoundPool soundPool;
    private static boolean initialized = false;
    
    public static boolean isInitialized() {
        return initialized;
    }
    
    public static void initialize(AssetManager assetManager) {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        AssetFileDescriptor descriptor = null;
        try {
            descriptor = assetManager.openFd("correct.ogg");
            ID_CORRECT = soundPool.load(descriptor, 1);        
            descriptor = assetManager.openFd("wrong.ogg");
            ID_WRONG = soundPool.load(descriptor, 1);        
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }        
        initialized = true;
    }
    
    public static void play(int id) {
        if (!Options.getInstance().isSoundEnabled()) {
            return;
        }
        soundPool.play(id, 1.0f, 1.0f, 0, 0, 1);        
    }
    
    public static void release() {
        soundPool.release();
        soundPool = null;
    }
    
}
