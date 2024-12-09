package SwingTest;

import java.io.Serializable;
import java.util.Vector;

public class DrawingData implements Serializable {
    public Vector<MyBox> rectList;
    public Vector<MyBox> circleList;
    public Vector<MyPath> penPaths;
    public Vector<MyPath> pencilPaths;

    public DrawingData(Vector<MyBox> rectList, Vector<MyBox> circleList,
                       Vector<MyPath> penPaths, Vector<MyPath> pencilPaths) {
        this.rectList = rectList;
        this.circleList = circleList;
        this.penPaths = penPaths;
        this.pencilPaths = pencilPaths;
    }
}
