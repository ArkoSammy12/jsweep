package minesweeper.ui;

import javax.swing.*;

import minesweeper.MineSweeper;
import minesweeper.game.GameDifficulty;
import minesweeper.game.MineSweeperBoard;

import java.awt.*;

public class MineSweeperGamePanel extends JPanel {

    private final MineSweeper mineSweeper;
    private MineSweeperCellButton[][] buttons = null;

    public MineSweeperGamePanel(MineSweeper mineSweeper) {
        this.mineSweeper = mineSweeper;
    }

    public MineSweeper getMineSweeper() {
        return this.mineSweeper;
    }

    public void onNewGame() {
        SwingUtilities.invokeLater(() -> {
            this.removeAll();
            MineSweeperBoard board = this.mineSweeper.getBoard();
            GameDifficulty gameDifficulty = board.getGameDifficulty();
            int gridHeight = gameDifficulty.getGridHeight();
            int gridWidth = gameDifficulty.getGridWidth();
            this.buttons = new MineSweeperCellButton[gridHeight][gridWidth];
            this.setLayout(new GridLayout(gridHeight, gridWidth, 0, 0));
            for (int y = 0; y < gridHeight; y++) {
                for (int x = 0; x < gridWidth; x++) {
                    MineSweeperCellButton button = new MineSweeperCellButton(this, x, y);
                    this.buttons[y][x] = button;
                    this.add(button);
                }
            }
            this.revalidate();
            this.repaint();
        });
    }

    public void refresh() {
        SwingUtilities.invokeLater(() -> {
            if (this.buttons == null) {
                return;
            }
            for (int y = 0; y < this.buttons.length; y++) {
                for (int x = 0; x < this.buttons[y].length; x++) {
                    this.buttons[y][x].refresh();
                }
            }
            this.revalidate();
            this.repaint();
        }); 
    }

}
