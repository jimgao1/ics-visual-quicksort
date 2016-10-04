package tk.jimgao;

import java.util.HashSet;
import java.util.Set;

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
                window.redrawBar(pIndex);
                window.redrawBar(pIndex + 1);

                window.updateBarGraph(numbers);
                pIndex++;
            }
        }

        swap(pIndex, right);

        return pIndex;
    }

    public static void quickSort(int left, int right){
        if (left > right) return;

        window.setTitle(String.format("left = %d, right = %d", left, right));

        for (int i = 0; i < left; i++){
            if (window.barColor[i] != BarGraphWindow.DEFAULT_COLOR){
                window.barColor[i] = BarGraphWindow.DEFAULT_COLOR;
                window.redrawBar(i);
            }
        }

        for (int i = right + 1; i < numbers.length; i++){
            if (window.barColor[i] != BarGraphWindow.DEFAULT_COLOR){
                window.barColor[i] = BarGraphWindow.DEFAULT_COLOR;
                window.redrawBar(i);
            }
        }

        for (int i = left; i <= right; i++){
            if (window.barColor[i] != BarGraphWindow.RANGE_COLOR){
                window.barColor[i] = BarGraphWindow.RANGE_COLOR;
                window.redrawBar(i);
            }
        }

        if (left == right) return;

        int pivotIndex = partition(left, right);
        window.updateBarGraph(numbers);

        quickSort(left, pivotIndex - 1);
        quickSort(pivotIndex + 1, right);
    }

    public static void main(String[] args){
        Set<Integer> visited = new HashSet<>();

        window = new BarGraphWindow();
        numbers = new int[400];
        for (int i = 0; i < 400; i++) {
            numbers[i] = (int)(Math.random() * 401) + 1;
            if (visited.contains(numbers[i])){
                i--;
                continue;
            }
            visited.add(numbers[i]);
        }
        window.initBarGraph(numbers);

        window.drawBarGraph();
        quickSort(0, 399);

        window.initBarGraph(numbers);
        window.drawBarGraph();
//
//        window = new BarGraphWindow();
//        numbers = new int[]{4, 5, 1, 2, 7, 8, 6, 3};
//        window.initBarGraph(numbers);
//        window.drawBarGraph();
//
//        quickSort(0, 7);
//
//        window.updateBarGraph(numbers);
    }
}
