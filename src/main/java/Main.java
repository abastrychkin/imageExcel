import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import image.ImageFromFile;
import matrix.ImageMatrix;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ui.MainWindow;

public class Main {


    public static void main(String[] args) {
        System.out.println("Hello!");

        String imagePath = "3_03.jpg";

        ImageFromFile imageFromFile = new ImageFromFile(imagePath);

        BufferedImage bufferedImage = imageFromFile.getBufferedImage();

        MainWindow mainWindow = new MainWindow();
        mainWindow.setBufferedImage(bufferedImage);
        mainWindow.showBufferedImage();

        ImageMatrix matrix = imageFromFile.toImageMatrix();

        XSSFWorkbook workbook = matrix.toExcel();
        try (FileOutputStream out = new FileOutputStream("gfgcontribute.xlsx")) {
                workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("gfgcontribute.xlsx written successfully on disk.");
    }
}
