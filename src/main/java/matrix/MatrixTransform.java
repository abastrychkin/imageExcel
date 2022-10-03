package matrix;

import java.util.Map;
import java.util.TreeMap;

public class MatrixTransform {

    public static int[][] pixelsVectorToMatrix(int width, int height, int[] pixelsVector) {
        int[][]pixelsMatrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelsMatrix[i][j] = pixelsVector[i* width + j];
            }
        }
        return pixelsMatrix;
    }

    public static int[] pixelsMatrixToVector(int width, int height, int[][] pixelsMatrix) {
        int[] pixelsVector = new int[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelsVector[i* width + j] = pixelsMatrix[i][j];
            }
        }
        return pixelsVector;
    }

    public static Map<Integer, int[]> pixelsMatrixToMap(int[][] pixelsMatrix) {
        Map<Integer, int[]> treeMap = new TreeMap<>();
        for (int i = 0; i < pixelsMatrix.length; i++) {
            treeMap.put(i, pixelsMatrix[i]);
        }
        return treeMap;
    }

    public static byte[] intPixelsVectorToByteVector(int[] pixelsVector) {
        byte[] bytePixelsVector = new byte[pixelsVector.length];
        for (int i = 0; i < pixelsVector.length; i++) {
            bytePixelsVector[i] = (byte) pixelsVector[i];
        }
        return bytePixelsVector;
    }

}
