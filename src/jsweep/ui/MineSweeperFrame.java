package jsweep.ui;

import jsweep.MineSweeper;

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

        this.jFrame.setPreferredSize(new Dimension(400, 500));
        this.jFrame.pack();
    }

    public void show() {
        this.jFrame.setVisible(true);
    }

    public void onNewGame() {
        this.gamePanel.resetGrid();
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

    public void close() {
        SwingUtilities.invokeLater(this.jFrame::dispose);
    }

}
