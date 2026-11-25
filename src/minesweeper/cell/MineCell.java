package minesweeper.cell;

import minesweeper.game.GameDifficulty;
import minesweeper.game.MineSweeperBoard;

public final class MineCell extends MineSweeperCell {

    private boolean tripped = false;

    public MineCell(MineSweeperBoard board, int x, int y) {
        super(board, x, y);
    }

    public boolean isTripped() {
        return this.tripped;
    }

    @Override
    public void reveal() {
        if (this.isFlagged()) {
            return;
        }
        this.tripped = true;
        GameDifficulty gameDifficulty = this.board.getGameDifficulty();
        int gridHeight = gameDifficulty.getGridHeight();
        int gridWidth = gameDifficulty.getGridWidth();
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                this.board.getCellAt(x, y).ifPresent(cell -> {
                    if (cell instanceof MineCell mineCell) {
                        mineCell.tripped = true;
                    }
                });
            }
        }
    }
    
}