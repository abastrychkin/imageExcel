package matrix;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ImageMatrix {
    private int[][] imageMatrix;

    public ImageMatrix(int[][] imageMatrix) {
        this.imageMatrix = imageMatrix;
    }

    public BufferedImage toBufferedImage(){
        int width = imageMatrix.length;
        int height = imageMatrix[0].length;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        int[] pixelsArr = new int[width * height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelsArr[i*width + j] = imageMatrix[i][j];
            }
        }

        byte[] byteArray = new byte[pixelsArr.length];
        for (int i = 0; i < pixelsArr.length; i++) {
            byteArray[i] = (byte) pixelsArr[i];
        }

        DataBuffer dataBuf = new DataBufferByte(byteArray, byteArray.length);
        int bitsPerPixel = 8;
        Raster raster = Raster.createRaster(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE, width, height, bitsPerPixel),
                dataBuf,
                null);

        bufferedImage.setData(raster);

        return bufferedImage;
    }

    public XSSFWorkbook toExcel() {
        XSSFWorkbook workbook = new XSSFWorkbook();

        String sheetName = "Image";
        XSSFSheet sheet = workbook.createSheet(sheetName);

        Map<Integer, int[]> data = new TreeMap<>();
        for (int i = 0; i < imageMatrix.length; i++) {
            data.put(i, imageMatrix[i]);
        }
        
        Set<Integer> keySet = data.keySet();
        int rowNum = 0;
        for (Integer key : keySet) {
            Row row = sheet.createRow(rowNum++);
            int[] imageRow = data.get(key);

            int cellNum = 0;
            for (int currentPixel : imageRow) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(currentPixel);
            }
        }

        return workbook;
    }

}
