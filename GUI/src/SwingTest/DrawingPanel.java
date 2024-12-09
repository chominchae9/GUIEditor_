package SwingTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class DrawingPanel extends JPanel {
    private Vector<MyBox> rectList;
    private Vector<MyBox> circleList;
    private Vector<MyPath> penPaths;
    private Vector<MyPath> pencilPaths;

    MyPoint start = null;
    MyPoint end = null;

    DrawingPanel() {
        rectList = new Vector<>();
        circleList = new Vector<>();
        penPaths = new Vector<>();
        pencilPaths = new Vector<>();
        start = new MyPoint();
        end = new MyPoint();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                start.x = e.getX();
                start.y = e.getY();

                if (EditorEx.selectedDrawingTool == 2 || EditorEx.selectedDrawingTool == 3) {
                    MyPath path = new MyPath(EditorEx.currentColor);
                    path.addPoint(new MyPoint(start.x, start.y));
                    if (EditorEx.selectedDrawingTool == 2) penPaths.add(path);
                    if (EditorEx.selectedDrawingTool == 3) pencilPaths.add(path);
                }
            }

            public void mouseReleased(MouseEvent e) {
                end.x = e.getX();
                end.y = e.getY();

                if (EditorEx.selectedDrawingTool == 0) { // Rectangle
                    MyBox rect = new MyBox(new MyPoint(start.x, start.y), new MyPoint(end.x, end.y));
                    rect.setColor(EditorEx.currentColor);
                    rectList.add(rect);
                } else if (EditorEx.selectedDrawingTool == 1) { // Circle
                    MyBox circle = new MyBox(new MyPoint(start.x, start.y), new MyPoint(end.x, end.y));
                    circle.setColor(EditorEx.currentColor);
                    circleList.add(circle);
                }
                repaint(); // Always repaint after adding a shape
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (EditorEx.selectedDrawingTool == 4) { // Eraser
                    eraseShapes(e.getX(), e.getY());
                } else if (EditorEx.selectedDrawingTool == 2 || EditorEx.selectedDrawingTool == 3) {
                    MyPath currentPath = EditorEx.selectedDrawingTool == 2 ? penPaths.lastElement() : pencilPaths.lastElement();
                    currentPath.addPoint(new MyPoint(e.getX(), e.getY()));
                }
                repaint();
            }
        });
    }

    private void eraseShapes(int x, int y) {
        MyPoint point = new MyPoint(x, y);

        // Remove shapes overlapping with the eraser point
        rectList.removeIf(rect -> rect.contains(point));
        circleList.removeIf(circle -> circle.contains(point));

        // Remove pen or pencil paths that overlap
        penPaths.removeIf(path -> path.isNear(point, 5));
        pencilPaths.removeIf(path -> path.isNear(point, 5));
    }

    public void cleanAllShapes() {
        rectList.clear(); // 사각형 리스트 초기화
        circleList.clear(); // 원 리스트 초기화
        penPaths.clear(); // 펜 경로 초기화
        pencilPaths.clear(); // 연필 경로 초기화
        repaint(); // 화면 업데이트
        JOptionPane.showMessageDialog(null, "All shapes cleared.");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (MyBox rect : rectList) {
            g.setColor(rect.rectColor);
            rect.rectUpdate();
            g.drawRect(rect.sPos.x, rect.sPos.y, rect.width, rect.height);
        }

        for (MyBox circle : circleList) {
            g.setColor(circle.rectColor);
            circle.rectUpdate();
            g.drawOval(circle.sPos.x, circle.sPos.y, circle.width, circle.height);
        }

        for (MyPath path : penPaths) {
            g.setColor(path.color);
            path.draw(g, 3);
        }

        for (MyPath path : pencilPaths) {
            g.setColor(path.color);
            path.draw(g, 1);
        }
    }
}
