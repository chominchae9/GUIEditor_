package SwingTest;

import java.io.Serializable;

public class MyPoint implements Serializable {
    int x = 0;
    int y = 0;

    MyPoint() {}

    MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
