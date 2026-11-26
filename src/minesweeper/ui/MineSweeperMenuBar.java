package minesweeper.ui;

import javax.swing.*;

import minesweeper.MineSweeper;

public class MineSweeperMenuBar extends JMenuBar {

    public MineSweeperMenuBar(MineSweeper mineSweeper) {

        JMenu gameMenu = new JMenu("Juego");

        JMenuItem newGameItem = new JMenuItem("Nuevo Juego");
        newGameItem.addActionListener(e -> mineSweeper.newGame());
        gameMenu.add(newGameItem);

        JMenuItem closeItem = new JMenuItem("Salir");
        closeItem.addActionListener(e -> mineSweeper.close());
        gameMenu.add(closeItem);

        this.add(gameMenu);
    }

}