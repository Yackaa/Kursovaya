package com.github.yakov.tictactoe;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class Menubar extends JMenuBar {
    Panel panel;

    // Добавляет счет
    private void addScore() {
        var score = new JMenuItem("Score");

        score.addActionListener((event) -> {
            panel.showInfo();
        });

        this.add(score);
    }

    // Против компьютера
    private void addAgainstCpu() {
        var opponent = new JMenu("Opponent");

        var friend = new JMenuItem("Friend");

        friend.addActionListener((event) -> {
            panel.againstCpu(false);
        });

        opponent.add(friend);

        var computer = new JMenuItem("Computer");

        computer.addActionListener((event) -> {
            panel.againstCpu(true);
        });

        opponent.add(computer);

        this.add(opponent);
    }

    // Кнопка сброса
    private void addReset() {
        var reset = new JMenuItem("Reset");

        reset.addActionListener((event) -> {
            panel.reset();
        });

        reset.setHorizontalAlignment(SwingConstants.RIGHT);

        this.add(reset);
    }

    // Конструктор
    public Menubar(Panel panel) {
        this.panel = panel;
        this.addScore();
        this.addAgainstCpu();
        this.addReset();
    }
}
