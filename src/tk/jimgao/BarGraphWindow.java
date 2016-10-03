package tk.jimgao;

import javax.swing.*;
import java.awt.*;

public class BarGraphWindow extends JFrame {

    public static final int Y_OFFSET = 50;
    public static final int Y_BOTTOM_OFFSET = 60;
    public static final int X_SPACE_ABS = 2;

    public static final int REDRAW_DELAY = 50;

    public static final Color DEFAULT_COLOR = new Color(93, 173, 226);
    public static final Color RANGE_COLOR = new Color(243, 156, 18);
    public static final Color PIVOT_COLOR = new Color(211, 84, 0);
    public static final Color BORDER_COLOR = new Color(21, 67, 96);

    public static boolean DRAW_BORDER = false;
    public static boolean DRAW_NUMBER = false;

    public int relWidth, relSpace, relHeight;
    public int[] barXLeft, barXRight, barValues;
    public Color[] barColor;

    public BarGraphWindow() {
        this.setSize(1800, 1024);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.repaint();
    }

    public int findMax(int[] values){
        int max = 0;
        for (int i : values) max = Math.max(max, i);
        return max;
    }

    public void initBarGraph(int[] values){
        this.relWidth = (int)((this.getWidth() - values.length) / values.length);
        this.relHeight = (int)((this.getHeight() - Y_OFFSET - Y_BOTTOM_OFFSET) / findMax(values));

        this.barValues = values.clone();
        this.barXLeft = new int[values.length];
        this.barXRight = new int[values.length];
        this.barColor = new Color[values.length];

        int prevX = relSpace;
        for (int i = 0; i < values.length; i++){
            barXLeft[i] = prevX;
            barXRight[i] = barXLeft[i] + relWidth;
            barColor[i] = BarGraphWindow.DEFAULT_COLOR;

            prevX += relWidth + X_SPACE_ABS;
        }
    }

    public void updateBarGraph(int[] values){
        int newHeight = (this.getHeight() - Y_OFFSET - Y_BOTTOM_OFFSET) / findMax(values);

        if (newHeight == this.relHeight){
            for (int i = 0; i < values.length; i++){
                if (this.barValues[i] != values[i]){
                    this.barValues[i] = values[i];
                    redrawBar(i);
                }
            }
        } else {
            this.relHeight = newHeight;

            this.barValues = values.clone();
            int prevX = relSpace;
            for (int i = 0; i < values.length; i++){
                barXLeft[i] = prevX;
                barXRight[i] = barXLeft[i] + relWidth;

                prevX += relWidth + relSpace;
            }
        }

        try {
            Thread.sleep(REDRAW_DELAY);
        } catch(Exception ex){}
    }

    public synchronized void clearBar(int index){
        Graphics g = this.getGraphics();
        g.clearRect(barXLeft[index], 0, relWidth, this.getHeight());
    }

    public synchronized void drawBar(int index){
        Graphics2D g = (Graphics2D) this.getGraphics();
        int leftCornerX = barXLeft[index];
        int leftCornerY = this.getHeight() - Y_BOTTOM_OFFSET - relHeight * barValues[index];
        int barHeight = relHeight * barValues[index];

        g.setColor(barColor[index]);
        g.fillRect(leftCornerX, leftCornerY + Y_OFFSET, relWidth, barHeight);

        if (DRAW_BORDER){
            g.setColor(BarGraphWindow.BORDER_COLOR);
            g.drawRect(leftCornerX, leftCornerY + Y_OFFSET, relWidth, barHeight);
        }

        if (DRAW_NUMBER) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Consolas", Font.BOLD, 14));
            g.drawString(Integer.toString(barValues[index]), leftCornerX + 10, leftCornerY);
        }
    }

    public void redrawBar(int index){
        clearBar(index);
        drawBar(index);
    }

    public synchronized void drawBarGraph(){
        Graphics2D g = (Graphics2D) this.getGraphics();

        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        g.setStroke(new BasicStroke(3));

        for (int i = 0; i < barValues.length; i++){
            int leftCornerX = barXLeft[i];
            int leftCornerY = this.getHeight() - Y_BOTTOM_OFFSET - relHeight * barValues[i];
            int barHeight = relHeight * barValues[i];

            g.setColor(barColor[i]);
            g.fillRect(leftCornerX, leftCornerY + Y_OFFSET, relWidth, barHeight);

            if (DRAW_BORDER){
                g.setColor(BarGraphWindow.BORDER_COLOR);
                g.drawRect(leftCornerX, leftCornerY + Y_OFFSET, relWidth, barHeight);
            }

            if (DRAW_NUMBER) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Consolas", Font.BOLD, 14));
                g.drawString(Integer.toString(barValues[i]), leftCornerX + 10, leftCornerY);
            }
        }

        try {
            Thread.sleep(REDRAW_DELAY);
        } catch(Exception ex){}
    }

    public void paint(Graphics g){
        drawBarGraph();
    }

}
