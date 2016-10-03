package tk.jimgao;

public class QuickSortMain {
    public static void main(String[] args){
        BarGraphWindow window = new BarGraphWindow();
        window.initBarGraph(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        window.drawBarGraph();
    }
}
