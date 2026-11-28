package jsweep.ui;

import javax.swing.*;

final class MineSweeperMenuBar extends JMenuBar {

    MineSweeperMenuBar(MineSweeperFrame mineSweeperFrame) {
        JMenu gameMenu = new JMenu("Juego");

        JMenuItem newGameItem = new JMenuItem("Nuevo Juego");
        newGameItem.addActionListener(e -> mineSweeperFrame.getMineSweeper().newGame());
        gameMenu.add(newGameItem);

        JMenuItem closeItem = new JMenuItem("Salir");
        closeItem.addActionListener(e -> mineSweeperFrame.close());
        gameMenu.add(closeItem);

        this.add(gameMenu);
    }

}