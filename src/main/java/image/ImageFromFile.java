package image;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import matrix.ImageMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;


public class ImageFromFile {
    BufferedImage bufferedImage;

    public ImageFromFile(String imagePath) {
        this(new File(imagePath));
    }

    public ImageFromFile(File imageFile) {
        try {
            this.bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Image getFXImage() {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public ImageMatrix toImageMatrix() {
        Raster pixels = bufferedImage.getTile(0,0);

        int minX = pixels.getMinX();
        int minY = pixels.getMinY();

        int width = pixels.getWidth();
        int height = pixels.getHeight();

        int[] pixelsArr = new int[width * height];
        pixels.getPixels(minX, minY, width, height, pixelsArr);

        int[][]pixelsTwoDimArr = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelsTwoDimArr[i][j] = pixelsArr[i*width + j];
            }
        }

        return new ImageMatrix(pixelsTwoDimArr);
    }
}
