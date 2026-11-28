package jsweep;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                MineSweeper mineSweeper = new MineSweeper();
                mineSweeper.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}