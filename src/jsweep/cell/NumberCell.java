package jsweep.cell;

import java.util.Optional;

import jsweep.game.MineSweeperBoard;

public final class NumberCell extends MineSweeperCell {

    private NeighborMineQuantity neighborMineQuantity = null;

    public NumberCell(MineSweeperBoard board, int x, int y) {
        super(board, x, y);
    }

    public Optional<NeighborMineQuantity> getNeighborMineQuantity() {
        return Optional.ofNullable(this.neighborMineQuantity);
    }

    @Override
    public boolean isFlagged() {
        return this.neighborMineQuantity == null && super.isFlagged();
    }

    @Override
    public void reveal() {
        if (this.neighborMineQuantity != null || this.isFlagged()) {
            return;
        }

        this.neighborMineQuantity = NeighborMineQuantity.ZERO;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                Optional<MineSweeperCell> cell = this.board.getCellAt(this.x + i, this.y + j);
                if (cell.isPresent() && cell.get() instanceof MineCell) {
                    this.neighborMineQuantity = this.neighborMineQuantity.increment();
                }
            }
        }

        if (this.neighborMineQuantity != NeighborMineQuantity.ZERO) {
            return;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                Optional<MineSweeperCell> cell = this.board.getCellAt(this.x + i, this.y + j);
                if (cell.isPresent() && cell.get() instanceof NumberCell numberCell) {
                    numberCell.reveal();
                }
            }
        }
    }

    public enum NeighborMineQuantity {
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT;

        private NeighborMineQuantity increment() {
            return switch (this) {
                case ZERO -> ONE;
                case ONE -> TWO;
                case TWO -> THREE;
                case THREE -> FOUR;
                case FOUR -> FIVE;
                case FIVE -> SIX;
                case SIX -> SEVEN;
                case SEVEN, EIGHT -> EIGHT;
            };
        }

    }

}
