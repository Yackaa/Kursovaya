package com.github.yakov.tictactoe;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;


class RandColor {
    // Рандомный цвет
    private static Random rand = new Random();

    // Рандомный цвет
    public static Color getColor(){
        return Color.getHSBColor(rand.nextFloat(), 0.5f, 0.5f);
    }
}


public class Settings {

    public Dimension buttonsize = new Dimension(130, 150);

    // Цвет для игры
    public Color viscolor = RandColor.getColor();

    // Первый игрок
    public String player1 = "X";

    // Второй игрок
    public String player2 = "O";

    public int hashGap = 10;

    // Шрифт
    public String fName = "Arial";

    // Стиль шрифта
    public int fStyle = Font.BOLD;

    // Размер шрифта
    public int fSize = 100;
}