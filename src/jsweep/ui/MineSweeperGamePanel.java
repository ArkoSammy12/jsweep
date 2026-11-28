package jsweep.ui;

import javax.swing.*;

import jsweep.game.MineSweeperGameDifficulty;

import java.awt.*;

final class MineSweeperGamePanel extends JPanel {

    private final MineSweeperFrame mineSweeperFrame;
    private MineSweeperCellButton[][] buttons = null;

    MineSweeperGamePanel(MineSweeperFrame mineSweeperFrame) {
        this.mineSweeperFrame = mineSweeperFrame;
    }

    MineSweeperFrame getMineSweeperFrame() {
        return this.mineSweeperFrame;
    }

    void resetGrid() {
        SwingUtilities.invokeLater(() -> {
            this.removeAll();
            MineSweeperGameDifficulty mineSweeperGameDifficulty = this.mineSweeperFrame.getMineSweeper().getGameDifficulty();
            int gridHeight = mineSweeperGameDifficulty.getGridHeight();
            int gridWidth = mineSweeperGameDifficulty.getGridWidth();
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

    void updateGrid() {
        SwingUtilities.invokeLater(() -> {
            if (this.buttons == null) {
                return;
            }
            for (MineSweeperCellButton[] row : this.buttons) {
                for (MineSweeperCellButton button : row) {
                    button.update();
                }
            }
            this.revalidate();
            this.repaint();
        }); 
    }

}
