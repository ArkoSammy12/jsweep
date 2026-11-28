package jsweep.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

final class MineSweeperStatusBar extends JPanel {

    private static final Color INNER_PANEL_COLOR = new Color(60, 65, 67);
    private static final String SMILING_EMOJI = "\uD83D\uDE42";
    private static final String SUNGLASSES_EMOJI = "\uD83D\uDE0E";
    private static final String CROSSED_OUT_EYES_EMOJI = "\uD83D\uDE35";
    private static final String OPEN_MOUTH_EMOJI = "\uD83D\uDE2E";

    private final MineSweeperFrame mineSweeperFrame;

    private final JButton faceButton;
    private final NumberDisplayPanel secondsCounterPanel;
    private final Timer secondCounterTimer;

    MineSweeperStatusBar(MineSweeperFrame mineSweeperFrame) {
        super(new BorderLayout());
        this.mineSweeperFrame = mineSweeperFrame;

        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setAlignmentX(CENTER_ALIGNMENT);
        panel.setAlignmentY(CENTER_ALIGNMENT);
        panel.setPreferredSize(new Dimension(100, 60));
        panel.setBackground(INNER_PANEL_COLOR);

        GridBagConstraints c = new GridBagConstraints();

        NumberDisplayPanel numberDisplayPanel1 = new NumberDisplayPanel();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0 / 3.0;
        panel.add(numberDisplayPanel1, c);

        this.faceButton = new JButton(SMILING_EMOJI);
        faceButton.setBorder(new LineBorder(INNER_PANEL_COLOR, 1, false));
        faceButton.setFont(faceButton.getFont().deriveFont(18.30f));
        faceButton.setBackground(INNER_PANEL_COLOR);
        faceButton.setPreferredSize(new Dimension(50, 50));
        faceButton.setFocusPainted(false);
        faceButton.setFocusable(false);
        faceButton.addActionListener(l -> mineSweeperFrame.getMineSweeper().newGame());
        faceButton.setModel(new DefaultButtonModel() {

            @Override
            public boolean isPressed() {
                return false;
            }

            @Override
            public void setRollover(boolean b) {

            }

        });

        c.gridx = 1;
        c.gridy = 0;
        panel.add(faceButton, c);

        this.secondsCounterPanel = new NumberDisplayPanel();
        this.secondCounterTimer = new Timer(1000, e -> {
            if (this.secondsCounterPanel.getValue() < Integer.MAX_VALUE - 1) {
                this.secondsCounterPanel.setValue(this.secondsCounterPanel.getValue() + 1);
            }
        });

        c.gridx = 2;
        c.gridy = 0;
        panel.add(this.secondsCounterPanel, c);

        this.add(panel);
    }

    void reset() {
        this.faceButton.setText(SMILING_EMOJI);
        this.secondsCounterPanel.setValue(0);
        this.secondCounterTimer.start();
    }

    void update() {
        switch (this.mineSweeperFrame.getMineSweeper().getGameState()) {
            case PLAYING -> this.faceButton.setText(SMILING_EMOJI);
            case WON -> {
                this.faceButton.setText(SUNGLASSES_EMOJI);
                this.secondCounterTimer.stop();
            }
            case LOST -> {
                this.faceButton.setText(CROSSED_OUT_EYES_EMOJI);
                this.secondCounterTimer.stop();
            }
        }
    }

    private static class NumberDisplayPanel extends JPanel {

        private final JLabel label;

        private int value;

        private NumberDisplayPanel() {
            super(new GridBagLayout());
            this.setPreferredSize(new Dimension(100, 50));
            this.setBackground(Color.BLACK);

            GridBagConstraints c = new GridBagConstraints();

            this.label = new JLabel("000");
            label.setAlignmentX(CENTER_ALIGNMENT);
            label.setAlignmentY(CENTER_ALIGNMENT);
            label.setFont(label.getFont().deriveFont(40.0f));
            label.setForeground(Color.RED);

            c.gridx = 0;
            c.gridy = 0;
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            this.add(label, c);
        }

        private int getValue() {
            return this.value;
        }

        private void setValue(int value) {
            this.value = value;

            String toDisplay;
            if (value >= 0) {
                toDisplay = "%03d".formatted(Math.min(this.value, 999));
            } else {
                toDisplay = "%02d".formatted(Math.max(this.value, -99));
            }

            SwingUtilities.invokeLater(() -> this.label.setText(String.valueOf(toDisplay)));
        }

    }

}
