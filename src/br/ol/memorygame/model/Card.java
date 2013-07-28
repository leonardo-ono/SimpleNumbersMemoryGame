package br.ol.memorygame.model;

/**
 * Card.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 * @since 1.0 (23/07/2013 15:59)
 */
public class Card {
    
    private int value;
    private int x;
    private int y;
    private int width = 50;
    private int height = 50;
    private boolean turned;
    private boolean visible;

    public Card(int value) {
        this.value = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isTurned() {
        return turned;
    }

    public void setTurned(boolean turned) {
        this.turned = turned;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Card{" + "value=" + value + ", x=" + x + ", y=" + y 
                + ", width=" + width + ", height=" + height + ", turned=" 
                + turned + ", visible=" + visible + '}';
    }
    
}
