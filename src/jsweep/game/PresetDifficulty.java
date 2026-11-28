package jsweep.game;

public enum PresetDifficulty implements MineSweeperGameDifficulty {
    BEGINNER(9, 9, 10),
    INTERMEDIATE(16, 16, 40),
    EXPERT(30, 16, 99);

    private final int gridWidth;
    private final int gridHeight;
    private final int mineCount;

    PresetDifficulty(int gridWidth, int gridHeight, int mineCount) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.mineCount = mineCount;
    }

    @Override
    public int getGridWidth() {
        return this.gridWidth;
    }

    @Override
    public int getGridHeight() {
        return this.gridHeight;
    }

    @Override
    public int getMineCount() {
        return this.mineCount;
    }

}