package jsweep.game;

public enum PresetDifficulty implements MineSweeperGameDifficulty {
    BEGINNER(9, 9, 10, "Principiante"),
    INTERMEDIATE(16, 16, 40, "Intermedio"),
    EXPERT(30, 16, 99, "Experto");

    private final int gridWidth;
    private final int gridHeight;
    private final int mineCount;
    private final String name;

    PresetDifficulty(int gridWidth, int gridHeight, int mineCount, String name) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.mineCount = mineCount;
        this.name = name;
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

    public String getName() {
        return this.name;
    }

}