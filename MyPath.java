package SwingTest;

import java.awt.*;
import java.util.*;

public class MyPath {
    private ArrayList<MyPoint> points;
    protected Color color;
    private int strokeWidth = 1; // 두께 추가

    MyPath(Color color, int strokeWidth) {
        points = new ArrayList<>();
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public void addPoint(MyPoint point) {
        points.add(point);
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(strokeWidth)); // 저장된 두께로 그리기

        for (int i = 1; i < points.size(); i++) {
            MyPoint p1 = points.get(i - 1);
            MyPoint p2 = points.get(i);
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public boolean isNear(MyPoint point, int threshold) {
        for (MyPoint p : points) {
            if (Math.abs(p.x - point.x) <= threshold && Math.abs(p.y - point.y) <= threshold) {
                return true;
            }
        }
        return false;
    }
}
