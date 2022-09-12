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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    public static final int WINDOW_MARGIN = 75;

    public static void main(String[] args) {
        System.out.println("Hello!");

        String imagePath = "3_03.jpg";
        try {
            BufferedImage myPicture = ImageIO.read(new File(imagePath));

            Raster pixels = myPicture.getTile(0,0);

            int[] pixelsArr = new int[128*128];

            pixels.getPixels(0,0,128,128, pixelsArr);


            int[][]pixelsTwoDimArr = new int[128][128];
            for (int i = 0; i < 128; i++) {
                for (int j = 0; j < 128; j++) {
                    pixelsTwoDimArr[i][j] = pixelsArr[i*128 + j];
                }
            }

            JLabel picLabel = new JLabel(new ImageIcon(myPicture));

            JPanel jPanel = new JPanel();
            jPanel.add(picLabel);

            JFrame f = new JFrame();
            f.setSize(new Dimension(myPicture.getWidth() + WINDOW_MARGIN, myPicture.getHeight() + WINDOW_MARGIN));
            f.add(jPanel);
            f.setVisible(true);

            // Blank workbook
            XSSFWorkbook workbook = new XSSFWorkbook();

            // Creating a blank Excel sheet
            XSSFSheet sheet
                    = workbook.createSheet("student Details");

            // Creating an empty TreeMap of string and Object][]
            // type
            Map<Integer, int[]> data = new TreeMap<>();

            // Writing data to Object[]
            // using put() method
            for (int i = 0; i < pixelsTwoDimArr.length; i++) {
                data.put(i, pixelsTwoDimArr[i]);
            }

            // Iterating over data and writing it to sheet
            Set<Integer> keyset = data.keySet();

            int rownum = 0;

            for (Integer key : keyset) {

                // Creating a new row in the sheet
                Row row = sheet.createRow(rownum++);

                int[] objArr = data.get(key);

                int cellnum = 0;

                for (int obj : objArr) {

                    // This line creates a cell in the next
                    //  column of that row
                    Cell cell = row.createCell(cellnum++);

                    cell.setCellValue(obj);
                }
            }

            // Try block to check for exceptions
            try {

                // Writing the workbook
                FileOutputStream out = new FileOutputStream(
                        new File("gfgcontribute.xlsx"));
                workbook.write(out);

                // Closing file output connections
                out.close();

                // Console message for successful execution of
                // program
                System.out.println(
                        "gfgcontribute.xlsx written successfully on disk.");
            }

            // Catch block to handle exceptions
            catch (Exception e) {

                // Display exceptions along with line number
                // using printStackTrace() method
                e.printStackTrace();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
