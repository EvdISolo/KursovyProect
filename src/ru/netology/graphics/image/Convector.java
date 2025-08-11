package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Convector implements TextGraphicsConverter{
    protected int maxWidth; // Поле Ширины
    protected int maxHeight; // Поле высоты
    protected TextColorSchema colorSchema; // Цветовая схема
    protected int aspectRatio; // Соотношение сторон

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        // Вот так просто мы скачаем картинку из интернета :)
        BufferedImage img = ImageIO.read(new URL(url)); // Скачал в img

        int baseAspectRatio = img.getWidth()/img.getHeight(); // // Нахождение соотношения
        if(baseAspectRatio>aspectRatio){
            throw new BadImageSizeException(baseAspectRatio,aspectRatio);
        }

        int newWidth = img.getWidth(); // Новая ширина
        int newHeight = img.getHeight(); // Новая высота

        double differenceWidth = img.getWidth()/maxWidth; // Разница ширины
        double differenceHeight = img.getHeight()/maxHeight; // Разницы высоты
        double difference = Math.max(differenceWidth,differenceHeight); // Получаю наибольшую разницу
        if(newWidth>maxWidth||newHeight>maxHeight){
            newWidth = (int)(img.getWidth() / difference); // Получаю новую ширину
            newHeight = (int)(img.getHeight()/difference); // Получаю новую высоту
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();

        graphics.drawImage(scaledImage, 0, 0, null);

        RenderedImage imageObject = bwImg;
        ImageIO.write(imageObject, "png", new File("out.png"));

        WritableRaster bwRaster = bwImg.getRaster();
        char[][] symbols = new char[newHeight][newWidth];
        for(int w = 0;w<bwImg.getWidth();w++){
            for(int h = 0;h<bwImg.getHeight();h++){
                int color = bwRaster.getPixel(w,h,new int[3])[0];
                char c = colorSchema.convert(color);
                symbols[h][w] = c;
            }
        }

        StringBuilder text = new StringBuilder();
        for (char[] row : symbols) {
            for (char ch : row) {
                text.append(ch).append(ch);
            }
            text.append("\n");
        }
        return text.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width; // Установка максимально ширины поле через свойство - setMaxWidth
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height; // Установка максимальной высоты поле через свойство - setMaxHeight
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.aspectRatio = maxHeight; // Установка максимальной соотношения сторон через свойство - setMaxRatio
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        colorSchema = schema; // Установка индивидуальной схемы для картинки
    }
}