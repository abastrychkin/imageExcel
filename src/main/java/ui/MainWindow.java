package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainWindow {
    public static final int WINDOW_MARGIN = 75;

    BufferedImage bufferedImage;

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void showBufferedImage() {
        if (bufferedImage != null) {
            JLabel picLabel = new JLabel(new ImageIcon(bufferedImage));

            JPanel jPanel = new JPanel();
            jPanel.add(picLabel);

            JFrame f = new JFrame();
            f.setSize(new Dimension(bufferedImage.getWidth() + WINDOW_MARGIN, bufferedImage.getHeight() + WINDOW_MARGIN));
            f.add(jPanel);
            f.setVisible(true);
        }
    }
}
