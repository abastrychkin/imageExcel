package image;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import matrix.ImageMatrix;
import matrix.MatrixTransform;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;


public class ImageFromFile {
    BufferedImage bufferedImage;

    public static ImageFromFile fromImageFile(File imageFile) {
        return new ImageFromFile(imageFile);
    }

    private ImageFromFile(File imageFile) {
        try {
            bufferedImage = ImageIO.read(imageFile);
            bufferedImage = convertToGrayscale(bufferedImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage convertToGrayscale(BufferedImage bufferedImage) {
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        BufferedImage grayImage = op.filter(bufferedImage, null);
        return grayImage;
    }

    public ImageFromFile(String imagePath) {
        this(new File(imagePath));
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Image toFXImage() {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public ImageMatrix toImageMatrix() {
        Raster pixels = bufferedImage.getTile(0,0);

        int minX = pixels.getMinX();
        int minY = pixels.getMinY();

        int width = pixels.getWidth();
        int height = pixels.getHeight();

        int[] pixelsVector = new int[width * height];
        pixels.getPixels(minX, minY, width, height, pixelsVector);

        int[][] pixelsMatrix = MatrixTransform.pixelsVectorToMatrix(width, height, pixelsVector);

        return new ImageMatrix(pixelsMatrix);
    }


}
