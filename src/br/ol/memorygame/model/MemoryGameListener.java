package br.ol.memorygame.model;

/**
 * MemoryGameListener interface.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 16:31)
 */
public interface MemoryGameListener {
    
    public abstract void gameStarted();
    public abstract void gameStartedInitiallyShowingCards();
    public abstract void gameStartedSelectCard1();
    public abstract void card1Selected(Card card);
    public abstract void card2Selected(Card card);
    public abstract void showResult(boolean isCorrect);
    public abstract void nextTryRequested();
    public abstract void gameEnded();
    
}
