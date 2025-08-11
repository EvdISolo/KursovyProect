package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema{
    // : '#', '$', '@', '%', '*', '+', '-'
    protected char One = '.';
    protected char Two = '-';
    protected char Free = '+';
    protected char Four = '%';
    protected char Five = '#';
    protected char Six = '$';
    protected char Seven = '@';

    @Override
    public char convert(int color) {
        if (color > 219) return One;
        else if (color > 183) return Two;
        else if (color > 147) return Free;
        else if (color > 111) return Four;
        else if (color > 75) return Five;
        else if (color > 39) return Six;
        else {
            return Seven;
        }
    }
}
