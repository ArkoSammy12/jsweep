package jsweep;

import javax.swing.*;

import jsweep.cell.MineSweeperCell;
import jsweep.game.MineSweeperGameDifficulty;
import jsweep.game.MineSweeperBoard;
import jsweep.game.MineSweeperGameState;
import jsweep.game.PresetDifficulty;
import jsweep.ui.MineSweeperFrame;

import java.util.Optional;

public class MineSweeper {

    private final MineSweeperFrame frame;
    private MineSweeperBoard board = new MineSweeperBoard(PresetDifficulty.BEGINNER);

    public MineSweeper() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.frame = new MineSweeperFrame(this);
    }

    public void start() {
        this.frame.show();
        this.frame.onNewGame();
    }

    public void newGame() {
        this.board = new MineSweeperBoard(this.frame.getSelectedDifficulty());
        this.frame.onNewGame();
    }

    public Optional<MineSweeperCell> getCellAt(int x, int y) {
        return this.board.getCellAt(x, y);
    }

    public MineSweeperGameState getGameState() {
        return this.board.getGameState();
    }

    public MineSweeperGameDifficulty getGameDifficulty() {
        return this.board.getGameDifficulty();
    }

    public void revealCellAt(int x, int y) {
        this.board.revealAt(x, y);
        this.frame.onCellRevealed();
    }

    public void toggleFlagAt(int x, int y) {
        this.board.toggleFlagAt(x, y);
        this.frame.onCellFlagged();
    }

}