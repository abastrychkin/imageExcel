package matrix;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.image.*;
import java.util.Map;
import java.util.Set;

public class ImageMatrix {
    private int[][] pixelsMatrix;

    public ImageMatrix(int[][] pixelsMatrix) {
        this.pixelsMatrix = pixelsMatrix;
    }

    public BufferedImage toBufferedImage(){
        int width = pixelsMatrix.length;
        int height = pixelsMatrix[0].length;

        BufferedImage bufferedImage = getEmptyBufferedImage(width, height);
        Raster pixels = getRaster(width, height);

        bufferedImage.setData(pixels);
        return bufferedImage;
    }

    private BufferedImage getEmptyBufferedImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    }

    private Raster getRaster(int width, int height) {
        DataBuffer dataBuf = getDataBuffer(width, height);
        SampleModel sampleModel = getSampleModel(width, height);
        Raster raster = Raster.createRaster(sampleModel, dataBuf, null);
        return raster;
    }

    private DataBuffer getDataBuffer(int width, int height) {
        byte[] bytePixelsVector = getBytePixelsVector(width, height);
        DataBuffer dataBuf = new DataBufferByte(bytePixelsVector, bytePixelsVector.length);
        return dataBuf;
    }

    private byte[] getBytePixelsVector(int width, int height) {
        int[] pixelsVector = MatrixTransform.pixelsMatrixToVector(width, height, pixelsMatrix);
        byte[] bytePixelsVector = MatrixTransform.intPixelsVectorToByteVector(pixelsVector);
        return bytePixelsVector;
    }

    private SampleModel getSampleModel(int width, int height) {
        int bitsPerPixel = 8;
        SampleModel sampleModel = new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE, width, height, bitsPerPixel);
        return sampleModel;
    }

    public XSSFWorkbook toExcel() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = getSheet(workbook);

        Map<Integer, int[]> pixelsMap = MatrixTransform.pixelsMatrixToMap(pixelsMatrix);

        setDataToSheet(sheet, pixelsMap);
        return workbook;
    }

    private XSSFSheet getSheet(XSSFWorkbook workbook) {
        String sheetName = "Image";
        XSSFSheet sheet = workbook.createSheet(sheetName);
        return sheet;
    }

    private void setDataToSheet(XSSFSheet sheet, Map<Integer, int[]> pixelsMap) {
        Set<Integer> pixelsRowNumbers = pixelsMap.keySet();
        int sheetRowNumber = 0;
        for (Integer pixelsRowNumber : pixelsRowNumbers) {
            Row row = sheet.createRow(sheetRowNumber++);
            int[] pixelsRow = pixelsMap.get(pixelsRowNumber);
            setSheetRow(row, pixelsRow);
        }
    }

    private void setSheetRow(Row row, int[] pixelsRow) {
        int sheetCellNumberInRow = 0;
        for (int currentPixel : pixelsRow) {
            Cell cell = row.createCell(sheetCellNumberInRow++);
            cell.setCellValue(currentPixel);
        }
    }


}
