package jsweep.game;

import java.util.Optional;
import java.util.Random;

import jsweep.cell.MineCell;
import jsweep.cell.MineSweeperCell;
import jsweep.cell.NumberCell;

public final class MineSweeperBoard {

    private final MineSweeperGameDifficulty mineSweeperGameDifficulty;
    private MineSweeperCell[][] cells = null;
    private MineSweeperGameState gameState = MineSweeperGameState.PLAYING;

    public MineSweeperBoard(MineSweeperGameDifficulty mineSweeperGameDifficulty) {
        this.mineSweeperGameDifficulty = mineSweeperGameDifficulty;
        int gridHeight = this.mineSweeperGameDifficulty.getGridHeight();
        int gridWidth = this.mineSweeperGameDifficulty.getGridWidth();
        int mineCount = this.mineSweeperGameDifficulty.getMineCount();

        if (gridHeight < 1) {
            throw new IllegalArgumentException("La cantidad de filas de la cuadricula no puede ser negativa!");
        }

        if (gridWidth < 8) {
            throw new IllegalArgumentException("La cantidad de columnas de la cuadricula no puede ser menor que 8!");
        }

        if (mineCount < 1) {
            throw new IllegalArgumentException("La cantidad de minas no puede ser menor que 1!");
        }

        int totalCells = gridHeight * gridWidth;
        if (totalCells - 1 - mineCount < 0) {
            throw new IllegalArgumentException("La cantidad de minas '%d' no puede ser mayor que la cantidad total de celdas menos uno!".formatted(this.mineSweeperGameDifficulty.getMineCount()));
        }
    }

    public MineSweeperGameDifficulty getGameDifficulty() {
        return this.mineSweeperGameDifficulty;
    }

    public MineSweeperGameState getGameState() {
        return this.gameState;
    }

    public Optional<MineSweeperCell> getCellAt(int x, int y) {
        if (this.cells == null || x < 0 || x >= this.mineSweeperGameDifficulty.getGridWidth() || y < 0 || y >= this.mineSweeperGameDifficulty.getGridHeight()) {
            return Optional.empty();
        }
        return Optional.of(this.cells[y][x]);
    }

    public void toggleFlagAt(int x, int y) {
        if (this.gameState.isFinished() || x < 0 || x >= this.mineSweeperGameDifficulty.getGridWidth() || y < 0 || y >= this.mineSweeperGameDifficulty.getGridHeight()) {
            return;
        }

        if (this.cells == null) {
            this.generateGrid(x, y);
        }

        this.cells[y][x].toggleFlagged();
    }

    public void revealAt(int x, int y) {
        if (this.gameState.isFinished() || x < 0 || x >= this.mineSweeperGameDifficulty.getGridWidth() || y < 0 || y >= this.mineSweeperGameDifficulty.getGridHeight()) {
            return;
        }

        if (this.cells == null) {
            this.generateGrid(x, y);
        }

        this.cells[y][x].reveal();
        this.updateState();
    } 

    private void generateGrid(int x, int y) {
        int gridHeight = this.mineSweeperGameDifficulty.getGridHeight();
        int gridWidth = this.mineSweeperGameDifficulty.getGridWidth();
        int totalCells = gridHeight * gridWidth;
        int mineCount = this.mineSweeperGameDifficulty.getMineCount();
        Random random = new Random();
        double mineProbability = ((double) mineCount) / ((double) totalCells - 1);
        this.cells = new MineSweeperCell[gridHeight][gridWidth];
        while (mineCount > 0) {
            for (int i = 0; i < this.cells.length; i++) {
                for (int j = 0; j < this.cells[i].length; j++) {
                    MineSweeperCell existing = this.cells[i][j];
                    if (random.nextDouble(1.0) >= mineProbability) {
                        if (existing == null) {
                            this.cells[i][j] = new NumberCell(this, j, i);
                        }
                    } else {
                        if (mineCount > 0 && !(existing instanceof MineCell) && !(j == x && i == y)) {
                            this.cells[i][j] = new MineCell(this, j, i);
                            mineCount--;
                        } else {
                            this.cells[i][j] = new NumberCell(this, j, i);
                        }
                    }
                }
            } 
        }
    }

    private void updateState() {
        boolean allNumberCellsRevealed = true;
        for (MineSweeperCell[] row : this.cells) {
            for (MineSweeperCell cell : row) {
                switch (cell) {
                    case MineCell mineCell -> {
                        if (mineCell.isTripped()) {
                            this.gameState = MineSweeperGameState.LOST;
                            return;
                        }
                    }
                    case NumberCell numberCell -> {
                        if (numberCell.getNeighborMineQuantity().isEmpty()) {
                            allNumberCellsRevealed = false;
                        }
                    }
                }
            }
        }
        if (allNumberCellsRevealed) {
            this.gameState = MineSweeperGameState.WON;
        }
    }

}
