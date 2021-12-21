package com.github.yakov.tictactoe;
import java.awt.Color;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.awt.event.ActionListener;

class WinAction implements ActionListener {

    Button button;


    float deg = 0;


    public WinAction(Button button) {
        this.button = button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(button.getText().equalsIgnoreCase(button.settings.player1)) {
            button.setForeground(Color.getHSBColor(deg / 360, 0.5f, 0.5f));
            deg = deg > 360 ? 0 : deg + 1;
        } else {
            button.setBackground(Color.getHSBColor(deg / 360, 0.5f, 0.5f));
            deg = deg > 360 ? 0 : deg + 1;
        } 
    }
}


public class Panel extends JPanel implements ActionListener {

    Settings settings;

    // Чей ход
    String turn;

    // Счетчик побед 1
    int win1Count;

    // Счетчик побед 2
    int win2Count;

    // Вывод счета
    int drawCount;


    boolean cpu;


    Clip endClip;
    

    Button buttons[][] = new Button[3][3];

    // Загрузка ресурсов
    private void loadResources() {
        try {
            var insclip  = new BufferedInputStream(Button.class.getResourceAsStream("/ending.wav"));
            this.endClip = AudioSystem.getClip();
            this.endClip.open(AudioSystem.getAudioInputStream(insclip));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Элементы выигрыша
    private void setEnded(int[][] elem) {
        this.endClip.setFramePosition(0);
        this.endClip.start();

        for(var points : elem) {
            new Timer(10, new WinAction(buttons[points[0]][points[1]])).start();
        }
    }

    // Эффект нажатия кнопки
    private void pressed(int x, int y) {
        this.buttons[x][y].setText(this.turn);

        if (this.turn == settings.player1) {
            this.turn = settings.player2;
        } else {
            this.turn = settings.player1;
        }

        var state = Game.isEnd(x, y, buttons, settings);

        if (state.state != Game.State.SNON) {
            if (state.state == Game.State.WIN1) {
                ++this.win1Count;
            } else if (state.state == Game.State.WIN2) {
                ++this.win2Count;
            } else if (state.state == Game.State.DRAW) {
                ++this.drawCount;
            }

            this.setEnded(state.which);
            this.showInfo();
            this.reset();
        }
    }

    // Конструктор
    public Panel(Settings settings) {
        this.settings = settings;
        this.setLayout(new GridLayout(3, 3, settings.hashGap, settings.hashGap));
        this.setBackground(settings.viscolor);
        this.loadResources();
        this.reset();
    }

    // Кнопка сброса
    public void reset() {
        this.removeAll();

        this.turn = settings.player1;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.buttons[i][j] = new Button(this.settings);
                this.buttons[i][j].addActionListener(this);
                this.add(buttons[i][j]);
            }
        }

        this.revalidate();
        this.repaint();
    }

    // Информация
    public void showInfo() {
        JOptionPane.showMessageDialog(this,
            "X Wins " + win1Count + "\n" + 
            "O Wins " + win2Count + "\n" + 
            "Draw   " + drawCount + "\n"
        );
    }


    public void againstCpu(boolean b) {
        this.cpu = b;
    }

    // override
    @Override
    public void actionPerformed(ActionEvent e) {
        var button = (Button) e.getSource();

        loop : for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (buttons[i][j] == button) {
                    this.pressed(i, j);
                    break loop;
                }
            }
        }

        if(cpu) {
            var timer = new Timer(0, (event) -> {
                var pos = Game.getMove(buttons);
                this.pressed(pos.x, pos.y);
            });

            timer.setInitialDelay(200);
            timer.setRepeats(false);
            timer.start();
        }

        this.revalidate();
        this.repaint();
    }
}
