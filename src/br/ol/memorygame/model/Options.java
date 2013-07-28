package br.ol.memorygame.model;

import android.content.Context;
import br.ol.memorygame.MainActivity;
import java.io.FileNotFoundException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Options class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (26/07/2013 12:34)
 */
public class Options implements Serializable { 
     
    private static Options instance = null;
    private static final String fileName = "simple_numbers_memory_game.opt";
    private boolean saved = false;
    private boolean musicEnabled = false;
    private boolean soundEnabled = true;
    private int numberOfPairsOfCards = 10;
    private int numberOfSeconds = 5;
    
    private Options() {
    }
    
    public static Options getInstance() {
        if (instance == null) {
            try {
                getInstanceFirstAcess();
            }
            catch (Exception e) {
                throw new RuntimeException("Could not restore the options information !", e);
            }
        }
        return instance;
    }
    
    private synchronized static Options getInstanceFirstAcess() throws Exception {
        if (instance == null) {
            try {
                instance = new Options();
                restore();
            }
            catch (FileNotFoundException e) {
                // Options still doesn't exist
            }
            catch (InvalidClassException e) {
                // Options still doesn't exist
            }
        }
        return instance;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public int getNumberOfPairsOfCards() {
        return numberOfPairsOfCards;
    }

    public void setNumberOfPairsOfCards(int numberOfPairsOfCards) {
        this.numberOfPairsOfCards = numberOfPairsOfCards;
    }

    public int getNumberOfSeconds() {
        return numberOfSeconds;
    }

    public void setNumberOfSeconds(int numberOfSeconds) {
        this.numberOfSeconds = numberOfSeconds;
    }
    
    public static void restore() throws Exception {
        Context context = MainActivity.context;
        ObjectInput input = new ObjectInputStream(
                context.openFileInput(fileName));
        Options originalOptions = (Options) input.readObject();
        input.close();
        instance.soundEnabled = originalOptions.soundEnabled;
        instance.musicEnabled = originalOptions.musicEnabled;
        instance.numberOfPairsOfCards = originalOptions.numberOfPairsOfCards;
        instance.numberOfSeconds = originalOptions.numberOfSeconds;
        instance.saved = originalOptions.saved;
    }
    
    public static void save() throws Exception {
        Context context = MainActivity.context;
        try {
            instance.setSaved(true);
            ObjectOutput output = new ObjectOutputStream(
                    context.openFileOutput(fileName
                    , Context.MODE_PRIVATE));
            
            output.writeObject(instance);
            output.close();
        }
        catch (Exception e) {
            instance.setSaved(false);
            throw e;
        }
    }

    @Override
    public String toString() {
        return "Options{" + "saved=" + saved + ", musicEnabled=" + musicEnabled 
                + ", soundEnabled=" + soundEnabled + ", numberOfCards=" + numberOfPairsOfCards 
                + ", numberOfSeconds=" + numberOfSeconds + '}';
    }
    
}
