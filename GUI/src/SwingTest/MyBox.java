package SwingTest;

import java.awt.*;

public class MyBox {
    protected MyPoint sPos = null;
    protected MyPoint ePos = null;
    protected Color rectColor = null;
    protected int width = 0;
    protected int height = 0;

    MyBox() {
        sPos = new MyPoint();
        ePos = new MyPoint();
    }

    MyBox(MyPoint s, MyPoint e) {
        sPos = s;
        ePos = e;
        rectUpdate();
    }

    public void setColor(Color c) {
        this.rectColor = c;
    }

    public void rectUpdate() {
        width = Math.abs(ePos.x - sPos.x);
        height = Math.abs(ePos.y - sPos.y);
    }

    public boolean contains(MyPoint point) {
        int x1 = Math.min(sPos.x, ePos.x);
        int x2 = Math.max(sPos.x, ePos.x);
        int y1 = Math.min(sPos.y, ePos.y);
        int y2 = Math.max(sPos.y, ePos.y);

        return point.x >= x1 && point.x <= x2 && point.y >= y1 && point.y <= y2;
    }
}
