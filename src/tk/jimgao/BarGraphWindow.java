package tk.jimgao;

import javax.swing.*;
import java.awt.*;

public class BarGraphWindow extends JFrame {

    public static final int Y_OFFSET = 50;
    public static final int Y_BOTTOM_OFFSET = 60;
    public static final double Y_SPACE = 0.05;

    public static final int REDRAW_DELAY = 2000;

    public static boolean DRAW_BORDER = true;

    public int relWidth, relSpace, relHeight;
    public int[] barXLeft, barXRight, barValues;
    public Color[] barColor;

    public BarGraphWindow() {
        this.setSize(800, 600);
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
        this.relWidth = (int)(this.getWidth() / (values.length * (1 + Y_SPACE * 2)));
        this.relSpace = (int)(relWidth * Y_SPACE);
        this.relHeight = (int)((this.getHeight() - Y_OFFSET - Y_BOTTOM_OFFSET) / findMax(values) * (1 - Y_SPACE));

        this.barValues = values.clone();
        this.barXLeft = new int[values.length];
        this.barXRight = new int[values.length];
        this.barColor = new Color[values.length];

        int prevX = relSpace;
        for (int i = 0; i < values.length; i++){
            barXLeft[i] = prevX;
            barXRight[i] = barXLeft[i] + relWidth;
            barColor[i] = new Color(93, 173, 226);

            prevX += relWidth + relSpace;
        }
    }

    public void updateBarGraph(int[] values){
        this.relHeight = (int)((this.getHeight() - Y_OFFSET - Y_BOTTOM_OFFSET) / findMax(values) * (1 - Y_SPACE));
        this.barValues = values.clone();
        int prevX = relSpace;
        for (int i = 0; i < values.length; i++){
            barXLeft[i] = prevX;
            barXRight[i] = barXLeft[i] + relWidth;

            prevX += relWidth + relSpace;
        }
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
                g.setColor(new Color(21, 67, 96));

                g.drawRect(leftCornerX, leftCornerY + Y_OFFSET, relWidth, barHeight);
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Consolas", Font.BOLD, 14));
            g.drawString(Integer.toString(barValues[i]), leftCornerX + 10, leftCornerY);
        }

        try {
            Thread.sleep(REDRAW_DELAY);
        } catch(Exception ex){}
    }



    public void paint(Graphics g){
        drawBarGraph();
    }

}
