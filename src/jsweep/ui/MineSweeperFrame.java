package jsweep.ui;

import jsweep.MineSweeper;
import jsweep.game.MineSweeperGameDifficulty;

import javax.swing.*;
import java.awt.*;

public final class MineSweeperFrame {

    private final MineSweeper mineSweeper;

    private final JFrame jFrame;
    private final MineSweeperMenuBar menuBar;
    private final MineSweeperStatusBar statusBar;
    private final MineSweeperGamePanel gamePanel;

    public MineSweeperFrame(MineSweeper mineSweeper) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        this.mineSweeper = mineSweeper;
        this.jFrame = new JFrame();
        this.menuBar = new MineSweeperMenuBar(this);
        this.statusBar = new MineSweeperStatusBar(this);
        this.gamePanel = new MineSweeperGamePanel(this);

        this.jFrame.setTitle("Buscaminas");
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.jFrame.getContentPane().setLayout(new BorderLayout());
        this.jFrame.setJMenuBar(this.menuBar);
        this.jFrame.getContentPane().add(this.statusBar, BorderLayout.PAGE_START);
        this.jFrame.getContentPane().add(this.gamePanel, BorderLayout.CENTER);

        this.jFrame.getContentPane().setBackground(MineSweeperCellButton.PRESSED_MIDDLE_COLOR);
        this.statusBar.setBackground(MineSweeperCellButton.PRESSED_MIDDLE_COLOR);
        this.gamePanel.setBackground(MineSweeperCellButton.PRESSED_MIDDLE_COLOR);

        this.jFrame.setResizable(false);
    }

    public void show() {
        this.jFrame.setVisible(true);
        this.recalculateSize();
        this.jFrame.setLocationRelativeTo(null);
    }

    public void onNewGame() {
        this.gamePanel.resetGrid();
        this.statusBar.reset();
        this.recalculateSize();
    }

    public void onCellRevealed() {
        this.gamePanel.updateGrid();
        this.statusBar.update();
    }

    public void onCellFlagged(int x, int y) {
        this.gamePanel.updateGrid();
        this.mineSweeper.getCellAt(x, y).ifPresent(cell -> {
            if (cell.isFlagged()) {
                this.statusBar.decrementMineCounter();
            } else {
                this.statusBar.incrementMineCounter();
            }
        });
    }

    public MineSweeper getMineSweeper() {
        return this.mineSweeper;
    }

    public MineSweeperGameDifficulty getSelectedDifficulty() {
        return this.menuBar.getCurrentDifficulty();
    }

    public void close() {
        SwingUtilities.invokeLater(this.jFrame::dispose);
    }

    private void recalculateSize() {
        MineSweeperGameDifficulty gameDifficulty = this.getMineSweeper().getGameDifficulty();
        int gridWidth = gameDifficulty.getGridWidth();
        int gridHeight = gameDifficulty.getGridHeight();

        gridWidth *= 40;
        gridHeight *= 40;

        gridHeight += this.statusBar.getHeight();

        this.jFrame.setSize(new Dimension(gridWidth, gridHeight));
    }

}
