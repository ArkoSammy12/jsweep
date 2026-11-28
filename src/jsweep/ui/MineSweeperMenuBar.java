package jsweep.ui;

import jsweep.game.MineSweeperGameDifficulty;
import jsweep.game.PresetDifficulty;

import javax.swing.*;

final class MineSweeperMenuBar extends JMenuBar {

    private MineSweeperGameDifficulty currentDifficulty = PresetDifficulty.BEGINNER;

    MineSweeperMenuBar(MineSweeperFrame mineSweeperFrame) {
        JMenu gameMenu = new JMenu("Juego");

        JMenuItem newGameItem = new JMenuItem("Nuevo Juego");
        newGameItem.addActionListener(e -> mineSweeperFrame.getMineSweeper().newGame());
        gameMenu.add(newGameItem);

        JMenuItem closeItem = new JMenuItem("Salir");
        closeItem.addActionListener(e -> mineSweeperFrame.close());
        gameMenu.add(closeItem);

        this.add(gameMenu);

        JMenu difficultyMenu = new JMenu("Dificultad");
        ButtonGroup difficultyButtonGroup = new ButtonGroup();
        for (PresetDifficulty presetDifficulty : PresetDifficulty.values()) {
            JRadioButtonMenuItem difficultyButton = new JRadioButtonMenuItem(presetDifficulty.getName());
            difficultyButton.setSelected(presetDifficulty == this.currentDifficulty);
            difficultyButton.addActionListener(l -> this.currentDifficulty = presetDifficulty);
            difficultyButtonGroup.add(difficultyButton);
            difficultyMenu.add(difficultyButton);
        }

        this.add(difficultyMenu);
    }

    MineSweeperGameDifficulty getCurrentDifficulty() {
        return currentDifficulty;
    }

}