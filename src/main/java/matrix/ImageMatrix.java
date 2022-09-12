package matrix;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ImageMatrix {
    private int[][] imageMatrix;

    public ImageMatrix(int[][] imageMatrix) {
        this.imageMatrix = imageMatrix;
    }

    public BufferedImage toImage(){
        return new BufferedImage(1,1, BufferedImage.TYPE_BYTE_GRAY);
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
