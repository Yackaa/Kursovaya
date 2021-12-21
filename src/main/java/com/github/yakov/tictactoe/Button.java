package com.github.yakov.tictactoe;
import java.awt.Font;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.sound.sampled.*;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.awt.event.ActionListener;


public class Button extends JButton implements ActionListener {
    // Настройки
    final Settings settings;


    boolean locked = false;


    Clip player1, player2;

    // Загрузка ресурсов
    private void loadResources() {
        try {
            var insplay1 = new BufferedInputStream(Button.class.getResourceAsStream("/player1.wav"));
            var insplay2 = new BufferedInputStream(Button.class.getResourceAsStream("/player2.wav"));
            this.player1 = AudioSystem.getClip();
            this.player2 = AudioSystem.getClip();
            this.player1.open(AudioSystem.getAudioInputStream(insplay1));
            this.player2.open(AudioSystem.getAudioInputStream(insplay2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Конструктор
    public Button(Settings sval) {
        super.setText("");
        this.loadResources();
        this.settings = sval;
        this.setBorder(null);
        this.setFocusPainted(false);
        this.setPreferredSize(settings.buttonsize);
        this.setVerticalTextPosition(SwingConstants.CENTER);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setFont(new Font(settings.fName, settings.fStyle, 0));
    }

    // Задаем текст
    public void setText(String text) {
        if (!locked && (locked = true)) {
            if (text.equalsIgnoreCase(settings.player1)) {
                this.setForeground(settings.viscolor);
                player1.start();
                super.setText(text);
                new Timer(1, this).start();
            } else {
                this.setBackground(settings.viscolor);
                player2.start();
                super.setText(text);
                new Timer(1, this).start();
            }
        }
    }

    // override
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.getFont().getSize() < settings.fSize) {
            this.setFont(new Font(settings.fName, settings.fStyle, this.getFont().getSize() + 5));
            this.revalidate();
            this.repaint();
        } else {
            ((Timer) e.getSource()).stop();
        }
    }
}