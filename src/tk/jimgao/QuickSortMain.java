package tk.jimgao;

import java.awt.*;
import java.util.Arrays;

public class QuickSortMain {
    public static int[] numbers;
    public static BarGraphWindow window;

    public static void swap(int a, int b){
        int tmp = numbers[a];
        numbers[a] = numbers[b];
        numbers[b] = tmp;
    }

    public static int partition(int left, int right){
        int pIndex = left, pValue = numbers[right];

        for (int i = left; i <= right - 1; i++){
            if (numbers[i] < pValue){
                swap(i, pIndex);
                window.barColor[pIndex] = BarGraphWindow.RANGE_COLOR;
                window.barColor[pIndex + 1] = BarGraphWindow.PIVOT_COLOR;
                window.updateBarGraph(numbers);
                window.drawBarGraph();
                pIndex++;
            }
        }

        swap(pIndex, right);

        return pIndex;
    }

    public static void quickSort(int left, int right){
        if (left > right) return;
        System.out.printf("left = %d, right = %d\n", left, right);

        Arrays.fill(window.barColor, BarGraphWindow.DEFAULT_COLOR);
        for (int i = left; i <= right; i++){
            window.barColor[i] = BarGraphWindow.RANGE_COLOR;
        }
        window.drawBarGraph();

        if (left == right) return;

        int pivotIndex = partition(left, right);
        window.updateBarGraph(numbers);
        window.drawBarGraph();

        quickSort(left, pivotIndex - 1);
        quickSort(pivotIndex + 1, right);
    }

    public static void main(String[] args){
        window = new BarGraphWindow();
        numbers = new int[25];
        for (int i = 0; i < 25; i++) numbers[i] = (int)(Math.random() * 50) + 1;
        window.initBarGraph(numbers);

        window.drawBarGraph();

        quickSort(0, 24);
    }
}
