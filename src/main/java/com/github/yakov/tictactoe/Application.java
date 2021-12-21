package com.github.yakov.tictactoe;
import javax.imageio.*;
import javax.swing.JFrame;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class Application extends JFrame {

    Settings settings = new Settings();


    Panel panel = new Panel(settings);
    

    Menubar menu = new Menubar(panel);


    BufferedImage image;

    // Загрузка ресурсов
    private void loadResources() {

        try {
            image = ImageIO.read(Application.class.getResourceAsStream("/tic-tac-toe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Конструктор
    public Application() {
        this.loadResources();
        this.setTitle("    ");
        this.setIconImage(image);
        this.setJMenuBar(menu);
        this.setResizable(false);
        this.setContentPane(panel);
        this.pack();
        this.setVisible(true);
    }
}