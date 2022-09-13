package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainWindow {
    public static final int WINDOW_MARGIN = 75;

    BufferedImage bufferedImage;
    JFrame frame;
    JPanel imagePanel;

    public MainWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(640, 480));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        frame.add(mainPanel);

        JPanel loadButtonsPanel = new JPanel();
        loadButtonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        loadButtonsPanel.setLayout(new BoxLayout(loadButtonsPanel, BoxLayout.Y_AXIS));

        JButton loadImageButton = new JButton("Load image");
        loadImageButton.setSize(100,50);
        loadButtonsPanel.add(loadImageButton);

        JButton loadExcelButton = new JButton("Load excel");
        loadExcelButton.setSize(100,50);
        loadButtonsPanel.add(loadExcelButton);

        mainPanel.add(loadButtonsPanel);

        imagePanel = new JPanel();
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.add(imagePanel);

        JPanel saveButtonsPanel = new JPanel();
        saveButtonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        saveButtonsPanel.setLayout(new BoxLayout(saveButtonsPanel, BoxLayout.Y_AXIS));

        JButton saveImageButton = new JButton("Save image");
        saveImageButton.setSize(100,50);
        saveButtonsPanel.add(saveImageButton);

        JButton saveExcelButton = new JButton("Save excel");
        saveExcelButton.setSize(100,50);
        saveButtonsPanel.add(saveExcelButton);

        mainPanel.add(saveButtonsPanel);

    }

    public void showFrame() {
        frame.setVisible(true);
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }



    public void showBufferedImage() {
        if (bufferedImage != null) {

            JLabel picLabel = new JLabel(new ImageIcon(bufferedImage));
            imagePanel.add(picLabel);

            imagePanel.setSize(new Dimension(bufferedImage.getWidth() + WINDOW_MARGIN, bufferedImage.getHeight() + WINDOW_MARGIN));
            showFrame();
        }
    }
}
