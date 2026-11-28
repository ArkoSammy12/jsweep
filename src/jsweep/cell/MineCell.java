package jsweep.cell;

import jsweep.game.MineSweeperGameDifficulty;
import jsweep.game.MineSweeperBoard;

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
        MineSweeperGameDifficulty mineSweeperGameDifficulty = this.board.getGameDifficulty();
        int gridHeight = mineSweeperGameDifficulty.getGridHeight();
        int gridWidth = mineSweeperGameDifficulty.getGridWidth();
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