package com.github.yakov.tictactoe;
import java.awt.Point;
import java.util.Random;


public class Game {

    public static class State {
        // ЭЛЕМЕНТЫ
        public static final int[][] ENON = new int[][] { 
            {} 
        };

        public static final int[][] ROW1 = new int[][] { 
            { 0, 0 }, { 0, 1 }, { 0, 2 } 
        };

        public static final int[][] ROW2 = new int[][] { 
            { 1, 0 }, { 1, 1 }, { 1, 2 } 
        };

        public static final int[][] ROW3 = new int[][] { 
            { 2, 0 }, { 2, 1 }, { 2, 2 } 
        };

        public static final int[][] COL1 = new int[][] { 
            { 0, 0 }, { 1, 0 }, { 2, 0 } 
        };

        public static final int[][] COL2 = new int[][] { 
            { 0, 1 }, { 1, 1 }, { 2, 1 } 
        };

        public static final int[][] COL3 = new int[][] { 
            { 0, 2 }, { 1, 2 }, { 2, 2 } 
        };

        public static final int[][] DIGL = new int[][] { 
            { 0, 0 }, { 1, 1 }, { 2, 2 } 
        };

        public static final int[][] DIGR = new int[][] { 
            { 0, 2 }, { 1, 1 }, { 2, 0 } 
        };

        public static final int[][] WHOL = new int[][] { 
            { 0, 0 }, { 0, 1 }, { 0, 2 }, 
            { 1, 0 }, { 1, 1 }, { 1, 2 },
            { 2, 0 }, { 2, 1 }, { 2, 2 }, 
        };

        public static final int SNON = 0;
        public static final int WIN1 = 1;
        public static final int WIN2 = 2;
        public static final int DRAW = 3;

        public int state = SNON;

        public int[][] which = WHOL;
    }

    // Проверка в ряд
    private static boolean row(String val, int row, Button[][] elem) {
        for (int i = 0; i < 3; ++i) {
            if (!elem[row][i].getText().equalsIgnoreCase(val)) {
                return false;
            }
        }

        return true;
    }

    // Проверка в колонну
    private static boolean col(String val, int col, Button[][] elem) {
        for (int i = 0; i < 3; ++i) {
            if (!elem[i][col].getText().equalsIgnoreCase(val)) {
                return false;
            }
        }

        return true;
    }

    // Проверка диагонали влево
    private static boolean dlf(String val, Button[][] elem) {
        for (int i = 0; i < 3; ++i) {
            if (!elem[i][i].getText().equalsIgnoreCase(val)) {
                return false;
            }
        }

        return true;
    }

    // Проверка диагонали вправо
    private static boolean drt(String val, Button[][] elem) {
        for (int i = 0; i < 3; ++i) {
            if (!elem[i][2 - i].getText().equalsIgnoreCase(val)) {
                return false;
            }
        }

        return true;
    }

    // Проверка на ничью
    private static boolean drw(Button[][] elem) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (elem[i][j].getText().equalsIgnoreCase("")) {
                    return false;
                }
            }
        }

        return true;
    }

    // Состояние ряда
    private static int[][] rowMap(int row) {
        if (row == 0)
            return State.ROW1;
        else if (row == 1)
            return State.ROW2;
        else
            return State.ROW3;
    }

    // Состояние колонны
    private static int[][] colMap(int col) {
        if (col == 0)
            return State.COL1;
        else if (col == 1)
            return State.COL2;
        else
            return State.COL3;
    }

    // Получаем позицию
    public static Point getMove(Button[][] elem) {
        Random rand = new Random();
        Point pos = new Point();

        while (!elem[pos.x][pos.y].getText().equalsIgnoreCase("")) {
            pos.x = rand.nextInt(3);
            pos.y = rand.nextInt(3);
        }

        return pos;
    }

    // Проверка конца
    public static State isEnd(int row, int col, Button[][] elem, Settings settings) {
        State endState = new State();

        if (row(settings.player1, row, elem)) {
            endState.state = State.WIN1;
            endState.which = rowMap(row);
        } else if (col(settings.player1, col, elem)) {
            endState.state = State.WIN1;
            endState.which = colMap(row);
        } else if (dlf(settings.player1, elem)) {
            endState.state = State.WIN1;
            endState.which = State.DIGL;
        } else if (drt(settings.player1, elem)) {
            endState.state = State.WIN1;
            endState.which = State.DIGR;
        } else if (row(settings.player2, row, elem)) {
            endState.state = State.WIN2;
            endState.which = rowMap(row);
        } else if (col(settings.player2, col, elem)) {
            endState.state = State.WIN2;
            endState.which = colMap(row);
        } else if (dlf(settings.player2, elem)) {
            endState.state = State.WIN2;
            endState.which = State.DIGL;
        } else if (drt(settings.player2, elem)) {
            endState.state = State.WIN2;
            endState.which = State.DIGR;
        } else if (drw(elem)) {
            endState.state = State.DRAW;
            endState.which = State.WHOL;
        }

        return endState;
    }
}
