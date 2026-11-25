package minesweeper.game;

public enum MineSweeperGameState {
    PLAYING(false),
    LOST(true),
    WON(true);

    private final boolean isFinished;

    MineSweeperGameState(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

}