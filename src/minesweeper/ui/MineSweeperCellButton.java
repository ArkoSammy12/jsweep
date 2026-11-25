package minesweeper.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.DefaultButtonModel;
import javax.swing.JButton;

import minesweeper.cell.MineCell;
import minesweeper.cell.MineSweeperCell;
import minesweeper.cell.NumberCell;
import minesweeper.cell.NumberCell.NeighborMineQuantity;
import minesweeper.game.MineSweeperBoard;
import minesweeper.game.MineSweeperGameState;

class MineSweeperCellButton extends JButton {

    public static final Color UNPRESSED_MIDDLE_COLOR = new Color(189, 189, 189);
    public static final Color UNPRESSED_BORDER_COLOR = new Color(123, 123, 123);
    
    public static final Color PRESSED_MIDDLE_COLOR = new Color(189, 189, 189);
    public static final Color PRESSED_BORDER_COLOR = new Color(123, 123, 123);

    private static final Color COLOR_BOMB = Color.BLACK;
    private static final Color COLOR_FLAG = new Color(254, 0, 0);

    private static final Color COLOR_ONE = new Color(1, 1, 249);
    private static final Color COLOR_TWO = new Color(0, 126, 2);
    private static final Color COLOR_THREE = new Color(253, 1, 0);
    private static final Color COLOR_FOUR = new Color(1, 0, 128);
    private static final Color COLOR_FIVE = new Color(128, 1, 2);
    private static final Color COLOR_SIX = new Color(2, 128, 127);
    private static final Color COLOR_SEVEN = new Color(0, 0, 0);
    private static final Color COLOR_EIGHT = new Color(128, 128, 128);

    private static final String BOMB_EMOJI = "\uD83D\uDCA3";
    private static final String FLAG_EMOJI = "\uD83D\uDEA9";

    private final MineSweeperGamePanel panel;
    private final int x;
    private final int y;

    private State state = State.UNPRESSED_UNFLAGGED;
    private boolean shouldMarkLost;

    MineSweeperCellButton(MineSweeperGamePanel panel, int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException("La posición x del botón de celda no puede ser negativo!");
        }
        if (y < 0) {
            throw new IllegalArgumentException("La posición y del botón de celda no puede ser negativo!");
        } 
        this.x = x;
        this.y = y;
        this.panel = panel;
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setFocusPainted(false);
        this.setFocusable(false);
        this.setFont(getFont().deriveFont(20.0f));
        this.setModel(new DefaultButtonModel() {
            @Override
            public boolean isPressed() {
                return false;
            }

            @Override
            public boolean isArmed() {
                return false;
            }

            @Override
            public boolean isRollover() {
                return false;
            }
            
            @Override
            public void setRollover(boolean b) {

            }
        });
        this.addMouseListener(new MouseAdapter() {

            private boolean pressed;

            @Override 
            public void mousePressed(MouseEvent e) {
                this.pressed = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                this.pressed = false;
            }

            @Override 
            public void mouseReleased(MouseEvent e) {
                if (!this.pressed) {
                    return;
                }
                this.pressed = false;
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1 -> { // Left click
                        MineSweeperBoard board = panel.getMineSweeper().getBoard();
                        if (board.getGameState().isFinished()) {
                            return;
                        }
                        panel.getMineSweeper().getBoard().revealAt(x, y);
                        if (board.getGameState() == MineSweeperGameState.LOST) {
                            shouldMarkLost = true;
                        }
                        panel.refresh();
                    }
                    case MouseEvent.BUTTON3 -> { // Right click
                        MineSweeperBoard board = panel.getMineSweeper().getBoard();
                        if (board.getGameState().isFinished()) {
                            return;
                        }
                        board.toggleFlagAt(x, y);
                        panel.refresh();
                    }
                }
            }

        });
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        switch (this.state) {
            case UNPRESSED_UNFLAGGED, UNPRESSED_FLAGGED -> {
                g.setColor(UNPRESSED_BORDER_COLOR);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());

                final double scale = 0.90;

                int scaledWidth = (int) (this.getWidth() * scale);
                int scaledHeight = (int) (this.getHeight() * scale);

                int centeredX = (this.getWidth() - scaledWidth) / 2;
                int centeredY = (this.getHeight() - scaledHeight) / 2;

                g.setColor(UNPRESSED_MIDDLE_COLOR);
                g.fillRect(centeredX, centeredY, scaledWidth, scaledHeight);
            }
            case PRESSED -> {
                g.setColor(PRESSED_MIDDLE_COLOR);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());

                g.setColor(PRESSED_BORDER_COLOR);
                g.drawRect(0, 0, this.getWidth(), this.getHeight());
            }
            case PRESSED_LOSING -> {
                g.setColor(Color.RED);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());

                g.setColor(PRESSED_BORDER_COLOR);
                g.drawRect(0, 0, this.getWidth(), this.getHeight());
            }
        }
        
        FontMetrics metrics = g.getFontMetrics();

        int textWidth = metrics.stringWidth(this.getText());
        int textHeight = metrics.getHeight();

        int x = (this.getWidth() - textWidth) / 2;
        int y = ((this.getHeight() - textHeight) / 2) + metrics.getAscent();

        g.setColor(this.getForeground());
        g.drawString(this.getText(), x, y);
    }
    

    public void refresh() {
        Optional<MineSweeperCell> cell = this.panel.getMineSweeper().getBoard().getCellAt(this.x, this.y);
        if (cell.isEmpty()) {
            this.state = State.UNPRESSED_UNFLAGGED;
            this.setText("");
            this.setForeground(UNPRESSED_MIDDLE_COLOR);
            return;
        }
        switch (cell.get()) {
            case NumberCell numberCell -> {
                Optional<NeighborMineQuantity> neighborMineQuantity = numberCell.getNeighborMineQuantity();
                if (neighborMineQuantity.isEmpty()) {
                    if (numberCell.isFlagged()) {
                        this.state = State.UNPRESSED_FLAGGED;
                        this.setText(FLAG_EMOJI);
                        this.setForeground(COLOR_FLAG);
                    } else {
                        this.state = State.UNPRESSED_UNFLAGGED;
                        this.setText("");
                        this.setForeground(UNPRESSED_MIDDLE_COLOR);
                    }
                } else {
                    this.state = State.PRESSED;
                    switch (neighborMineQuantity.get()) {
                        case ZERO -> {
                            this.setText("0");
                            this.setForeground(PRESSED_MIDDLE_COLOR);
                        }
                        case ONE -> {
                            this.setText("1");
                            this.setForeground(COLOR_ONE);
                        }
                        case TWO -> {
                            this.setText("2");
                            this.setForeground(COLOR_TWO);
                        }
                        case THREE -> {
                            this.setText("3");
                            this.setForeground(COLOR_THREE);
                        }
                        case FOUR -> {
                            this.setText("4");
                            this.setForeground(COLOR_FOUR);
                        }
                        case FIVE -> {
                            this.setText("5");
                            this.setForeground(COLOR_FIVE);
                        }
                        case SIX -> {
                            this.setText("6");
                            this.setForeground(COLOR_SIX);
                        }
                        case SEVEN -> {
                            this.setText("7");
                            this.setForeground(COLOR_SEVEN);
                        }
                        case EIGHT -> {
                            this.setText("8");
                            this.setForeground(COLOR_EIGHT);
                        }
                    } 
                }
            }
            case MineCell mineCell -> {
                if (mineCell.isTripped()) {
                    if (this.shouldMarkLost) {
                        this.state = State.PRESSED_LOSING;
                    } else {
                        this.state = State.PRESSED;
                    }
                    this.setText(BOMB_EMOJI);
                    this.setForeground(COLOR_BOMB);
                } else {
                    if (mineCell.isFlagged()) {
                        this.state = State.UNPRESSED_FLAGGED;
                        this.setText(FLAG_EMOJI);
                        this.setForeground(COLOR_FLAG);
                    } else {
                        this.state = State.UNPRESSED_UNFLAGGED;
                        this.setText("");
                        this.setForeground(UNPRESSED_MIDDLE_COLOR);
                    }
                }
            }
        }
    }

    private enum State {
        UNPRESSED_UNFLAGGED,
        UNPRESSED_FLAGGED,
        PRESSED,
        PRESSED_LOSING;
    }

}
