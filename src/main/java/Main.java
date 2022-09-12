import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static final int WINDOW_MARGIN = 100;

    public static void main(String[] args) {
        System.out.println("Hello!");

        String imagePath = "3_03.jpg";
        try {
            BufferedImage myPicture = ImageIO.read(new File(imagePath));

            Raster pixels = myPicture.getTile(0,0);


            JLabel picLabel = new JLabel(new ImageIcon(myPicture));

            JPanel jPanel = new JPanel();
            jPanel.add(picLabel);

            JFrame f = new JFrame();
            f.setSize(new Dimension(myPicture.getWidth() + WINDOW_MARGIN, myPicture.getHeight() + WINDOW_MARGIN));
            f.add(jPanel);
            f.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
