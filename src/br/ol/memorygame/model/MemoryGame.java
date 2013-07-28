package br.ol.memorygame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MemoryGame model.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 16:07)
 */
public class MemoryGame {
    
    public static enum State { WAITING_START_INITIALLY_SHOWING_CARDS, INITIALLY_SHOWING_CARDS, WAITING_CARD1, WAITING_CARD2, SHOWING_RESULT, ENDED }
    private State state;
    private List<Card> cards = new ArrayList<Card>();
    private List<MemoryGameListener> listeners 
            = new ArrayList<MemoryGameListener>();

    private int numberOfPairOfCards = 10;
    private int numberOfSecondsToInitiallyShowCards = 5;
    
    private int triesCount = 0;
    private int rightCount = 0;
    private int wrongCount = 0;

    private Card selectedCard1;
    private Card selectedCard2;
    private Boolean correct;
    
    private int maxNumberOfCards = 12;
    
    public State getState() {
        return state;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Boolean isCorrect() {
        return correct;
    }

    public void incNumberOfPairOfCards() {
        numberOfPairOfCards++;
        if (numberOfPairOfCards > maxNumberOfCards) {
            numberOfPairOfCards = maxNumberOfCards;
        }
    }

    public void decNumberOfPairOfCards() {
        numberOfPairOfCards--;
        if (numberOfPairOfCards < 3) {
            numberOfPairOfCards = 3;
        }
    }

    public void setNumberOfPairOfCards(int numberOfPairOfCards) {
        this.numberOfPairOfCards = numberOfPairOfCards;
    }
    
    public int getNumberOfPairOfCards() {
        return numberOfPairOfCards;
    }

    public void incNumberOfSecondsToInitiallyShowCards() {
        numberOfSecondsToInitiallyShowCards++;
        if (numberOfSecondsToInitiallyShowCards > 99) {
            numberOfSecondsToInitiallyShowCards = 0;
        }
    }

    public void decNumberOfSecondsToInitiallyShowCards() {
        numberOfSecondsToInitiallyShowCards--;
        if (numberOfSecondsToInitiallyShowCards < 0) {
            numberOfSecondsToInitiallyShowCards = 99;
        }
    }

    public void setNumberOfSecondsToInitiallyShowCards(int numberOfSecondsToInitiallyShowCards) {
        this.numberOfSecondsToInitiallyShowCards = numberOfSecondsToInitiallyShowCards;
    }
    
    public int getNumberOfSecondsToInitiallyShowCards() {
        return numberOfSecondsToInitiallyShowCards;
    }    
    
    public int getRightCount() {
        return rightCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public int getTriesCount() {
        return triesCount;
    }
    
    private void createAllCards() {
        cards.clear();
        for (int n=0; n<=(numberOfPairOfCards-1); n++) {
            cards.add(new Card(n));
        }
        for (int n=0; n<=(numberOfPairOfCards-1); n++) {
            cards.add(new Card(n));
        }
        Collections.shuffle(cards);
    }

    public void positionAllCards(int w, int h) {
        int xProv = 10;
        int yProv = 50;
        for (Card card : cards) {
            card.setX(xProv);
            card.setY(yProv);
            xProv += card.getWidth() + 10;
            if (xProv > (w-50)) {
                xProv = 10;
                yProv += card.getHeight() + 10;
            }
        }
    }
    
    public void calculateMaxNumberOfCards(int w, int h) {
        Card card = new Card(0);
        if (card == null) {
            return;
        }
        int qx1 = w/(card.getWidth()+10);
        int qy1 = (h-50)/(card.getHeight()+10);
        int q1 = qx1 * qy1;

        int qx2 = h/(card.getWidth()+10);
        int qy2 = (w-50)/(card.getHeight()+10);
        int q2 = qx2 * qy2;
        
        int r = q1 / 2;
        if (q2 < q1) {
            r = q2 / 2;
        }
        
        maxNumberOfCards = r;
    }
    
    private void setVisibleAndTurnedTrueAllCards() {
        for (Card card : cards) {
            card.setVisible(true);
            card.setTurned(true);
        }
    }

    private void setTurnedAllCards(boolean turned) {
        for (Card card : cards) {
            card.setTurned(turned);
        }
    }
    
    public void start() {
        correct = null;
        selectedCard1 = null;
        selectedCard2 = null;
        rightCount = 0;
        wrongCount = 0;
        triesCount = 0;
        createAllCards();
        setVisibleAndTurnedTrueAllCards();
        Collections.shuffle(cards);
        state = State.WAITING_START_INITIALLY_SHOWING_CARDS;
        fireGameStarted();
    }

    public void initiallyShowCards() {
        setTurnedAllCards(false);
        state = State.INITIALLY_SHOWING_CARDS;
        fireGameStartedInitiallyShowingCards();
    }
    
    public void startSelectCard1() {
        setTurnedAllCards(true);
        state = State.WAITING_CARD1;
        fireGameStartedSelectCard1();
    }
    
    public void selectCard1(Card card) {
        card.setTurned(false);
        selectedCard1 = card;
        fireCard1Selected(card);
        state = State.WAITING_CARD2;
    }

    public void selectCard2(Card card) {
        if (card == selectedCard1) {
            return;
        }
        card.setTurned(false);
        selectedCard2 = card;
        fireCard2Selected(card);
        state = State.SHOWING_RESULT;
        correct = (selectedCard1.getValue() == selectedCard2.getValue());
        if (correct) {
            rightCount++;
        }
        else {
            wrongCount++;
        }
        triesCount++;
        fireShowResult(correct);
    }
    
    public void nextTry() {
        selectedCard1.setVisible(!correct);
        selectedCard2.setVisible(!correct);
        selectedCard1.setTurned(!correct);
        selectedCard2.setTurned(!correct);
        correct = null;
        selectedCard1 = null;
        selectedCard2 = null;
        if (isEnded()) {
            state = State.ENDED;
            fireGameEnded();
        }
        else {
            fireNextTryRequested();
            state = State.WAITING_CARD1;
        }
    }

    public boolean isEnded() {
        for (Card card : cards) {
            if (card.isVisible()) {
                return false;
            }
        }
        return true;
    }
    
    public void addListener(MemoryGameListener listener) {
        listeners.add(listener);
    }

    public void clearListeners() {
        listeners.clear();
    }
    
    // Fire event to all registered listeners
    
    private void fireGameStarted() {
        for (MemoryGameListener listener : listeners) {
            listener.gameStarted();
        }
    }

    private void fireGameStartedInitiallyShowingCards() {
        for (MemoryGameListener listener : listeners) {
            listener.gameStartedInitiallyShowingCards();
        }
    }

    private void fireGameStartedSelectCard1() {
        for (MemoryGameListener listener : listeners) {
            listener.gameStartedSelectCard1();
        }
    }
    
    private void fireCard1Selected(Card card) {
        for (MemoryGameListener listener : listeners) {
            listener.card1Selected(card);
        }
    }
    
    private void fireCard2Selected(Card card) {
        for (MemoryGameListener listener : listeners) {
            listener.card2Selected(card);
        }
    }
    
    private void fireShowResult(boolean isCorrect) {
        for (MemoryGameListener listener : listeners) {
            listener.showResult(isCorrect);
        }
    }

    private void fireNextTryRequested() {
        for (MemoryGameListener listener : listeners) {
            listener.nextTryRequested();
        }
    }
    
    private void fireGameEnded() {
        for (MemoryGameListener listener : listeners) {
            listener.gameEnded();
        }
    }
    
}
