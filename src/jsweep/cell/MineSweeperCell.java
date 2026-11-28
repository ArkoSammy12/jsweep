package jsweep.cell;

import jsweep.game.MineSweeperBoard;

public abstract sealed class MineSweeperCell permits MineCell, NumberCell {

    protected final MineSweeperBoard board;
    protected final int x;
    protected final int y;
    private boolean isFlagged;

    public MineSweeperCell(MineSweeperBoard board, int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException("La coordenada x '%d' de la celda no puede ser negativa!".formatted(x));
        }
        if (y < 0) {
            throw new IllegalArgumentException("La coordenada y '%d' de la celda no puede ser negativa!".formatted(y));
        }
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public final void toggleFlagged() {
        this.isFlagged = !this.isFlagged;
    }

    public boolean isFlagged() {
        return this.isFlagged;
    }

    public abstract void reveal();

}