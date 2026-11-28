package minesweeper;

import javax.swing.*;

import minesweeper.game.MineSweeperBoard;
import minesweeper.game.PresetDifficulty;
import minesweeper.ui.MineSweeperGamePanel;
import minesweeper.ui.MineSweeperMenuBar;
import minesweeper.ui.MineSweeperStatusBar;

import java.awt.*;

public class MineSweeper {

    private final JFrame jFrame;
    private final MineSweeperMenuBar menuBar;
    private final MineSweeperStatusBar statusBar;
    private final MineSweeperGamePanel gamePanel;

    private MineSweeperBoard board = new MineSweeperBoard(PresetDifficulty.INTERMEDIATE);

    public MineSweeper() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

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

    public void start() {
        this.jFrame.setVisible(true);
        //this.jFrame.setResizable(false);
        this.gamePanel.onNewGame();
    }

    public void newGame() {
        this.board = new MineSweeperBoard(this.getBoard().getGameDifficulty());
        this.gamePanel.onNewGame();
    }

    public MineSweeperBoard getBoard() {
        return this.board;
    }

    public void close() {
        SwingUtilities.invokeLater(this.jFrame::dispose);
    }

}