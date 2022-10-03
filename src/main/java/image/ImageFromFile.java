package image;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import matrix.ImageMatrix;
import matrix.MatrixTransform;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;


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

    private ImageFromFile(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }


    public static ImageFromFile fromExcelFile(File excelFile) {
        int[][] pixelsMatrix = new int[1][1];
        try {
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);

            pixelsMatrix = getPixelsMatrixFromExcelSheet(datatypeSheet);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

        BufferedImage bufferedImage = new ImageMatrix(pixelsMatrix).toBufferedImage();
        return new ImageFromFile(bufferedImage) ;

    }

    private static int[][] getPixelsMatrixFromExcelSheet(Sheet datatypeSheet) {
        int rowCount = datatypeSheet.getLastRowNum() + 1;
        int colCount =  datatypeSheet.getRow(0).getLastCellNum();

        int[][] pixelsMatrix;
        pixelsMatrix = new int[rowCount][colCount];

        setExcelDataToPixelsMatrix(datatypeSheet, pixelsMatrix);
        return pixelsMatrix;
    }

    private static void setExcelDataToPixelsMatrix(Sheet datatypeSheet, int[][] pixelsMatrix) {
        Iterator<Row> rowIterator = datatypeSheet.iterator();
        int rowCounter = 0;
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            setExcelDataToPixelsMatrixRow(currentRow, pixelsMatrix[rowCounter]);
            rowCounter++;
        }
    }

    private static void setExcelDataToPixelsMatrixRow(Row currentRow, int[] pixelsMatrix) {
        Iterator<Cell> cellIterator = currentRow.iterator();
        int cellCounter = 0;
        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();
            pixelsMatrix[cellCounter] =  (int)currentCell.getNumericCellValue();
            cellCounter++;
        }
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
