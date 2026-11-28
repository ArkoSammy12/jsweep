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
        this.statusBar = new MineSweeperStatusBar();
        this.gamePanel = new MineSweeperGamePanel(this);

        this.jFrame.setTitle("Buscaminas");
        this.jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.jFrame.getContentPane().setLayout(new BorderLayout());
        this.jFrame.setJMenuBar(this.menuBar);
        this.jFrame.getContentPane().add(this.statusBar, BorderLayout.NORTH);
        this.jFrame.getContentPane().add(this.gamePanel);

        this.jFrame.setResizable(false);
    }

    public void show() {
        this.jFrame.setVisible(true);
        this.recalculateSize();
    }

    public void onNewGame() {
        this.gamePanel.resetGrid();
        this.recalculateSize();
    }

    public void onCellRevealed() {
        this.gamePanel.updateGrid();
    }

    public void onCellFlagged() {
        this.gamePanel.updateGrid();
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
