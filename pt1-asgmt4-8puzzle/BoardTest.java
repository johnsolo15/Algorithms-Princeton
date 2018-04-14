import java.util.Arrays;

public class BoardTest {
    public static void main(String[] args) {
        int n = 3;
        int[] contents = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        int[][] array = new int[n][n];
        int[][] array2 = new int[n][n];
        
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = contents[count];
                count++;
            }
        }
        
        array2 = Arrays.copyOf(array, n);
        
        array[0][0] = 8;
        
        System.out.println("array: " + Arrays.toString(array[0]));
        
        System.out.println("array2: " + Arrays.toString(array2));
    }
}