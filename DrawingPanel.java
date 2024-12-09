package SwingTest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

class DrawingPanel extends JPanel {
    private Vector<MyBox> rectList;
    private Vector<MyBox> circleList;
    private Vector<MyPath> penPaths;
    private Image loadedImage = null;
    private int currentStrokeWidth = 1; // 현재 설정된 두께

    MyPoint start = null;
    MyPoint end = null;

    DrawingPanel() {
        rectList = new Vector<>();
        circleList = new Vector<>();
        penPaths = new Vector<>();
        start = new MyPoint();
        end = new MyPoint();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                start.x = e.getX();
                start.y = e.getY();

                if (EditorEx.selectedDrawingTool == 2) { // Pen
                    MyPath path = new MyPath(EditorEx.currentColor, currentStrokeWidth);
                    path.addPoint(new MyPoint(start.x, start.y));
                    penPaths.add(path);
                }
            }

            public void mouseReleased(MouseEvent e) {
                end.x = e.getX();
                end.y = e.getY();

                if (EditorEx.selectedDrawingTool == 0) { // Rectangle
                    MyBox rect = new MyBox(new MyPoint(start.x, start.y), new MyPoint(end.x, end.y));
                    rect.setColor(EditorEx.currentColor); // 색상 설정
                    rect.setStrokeWidth(currentStrokeWidth); // 두께 설정
                    rectList.add(rect);
                } else if (EditorEx.selectedDrawingTool == 1) { // Circle
                    MyBox circle = new MyBox(new MyPoint(start.x, start.y), new MyPoint(end.x, end.y));
                    circle.setColor(EditorEx.currentColor); // 색상 설정
                    circle.setStrokeWidth(currentStrokeWidth); // 두께 설정
                    circleList.add(circle);
                }
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (EditorEx.selectedDrawingTool == 4) { // Eraser
                    eraseShapes(e.getX(), e.getY());
                } else if (EditorEx.selectedDrawingTool == 2) { // Pen
                    MyPath currentPath = penPaths.lastElement();
                    currentPath.addPoint(new MyPoint(e.getX(), e.getY()));
                }
                repaint();
            }
        });
    }

    private void eraseShapes(int x, int y) {
        MyPoint point = new MyPoint(x, y);
        rectList.removeIf(rect -> rect.contains(point));
        circleList.removeIf(circle -> circle.contains(point));
        penPaths.removeIf(path -> path.isNear(point, 5));
    }

    public void loadImage(File file) throws IOException {
        loadedImage = ImageIO.read(file);
        repaint();
    }

    public DrawingData getDrawingData() {
        return new DrawingData(rectList, circleList, penPaths);
    }

    public void cleanAllShapes() {
        rectList.clear();
        circleList.clear();
        penPaths.clear();
        repaint();
        JOptionPane.showMessageDialog(null, "All shapes cleared.");
    }

    public void setStrokeWidth(int width) {
        this.currentStrokeWidth = width; // 두께 업데이트
    }

    public int getStrokeWidth() {
        return currentStrokeWidth;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // 배경 이미지 그리기
        if (loadedImage != null) {
            g2d.drawImage(loadedImage, 0, 0, getWidth(), getHeight(), this);
        }

        // 사각형 그리기
        for (MyBox rect : rectList) {
            g2d.setColor(rect.rectColor); // 저장된 색상 설정
            g2d.setStroke(new BasicStroke(rect.getStrokeWidth())); // 저장된 두께 사용
            g2d.drawRect(
                Math.min(rect.sPos.x, rect.ePos.x),
                Math.min(rect.sPos.y, rect.ePos.y),
                Math.abs(rect.sPos.x - rect.ePos.x),
                Math.abs(rect.sPos.y - rect.ePos.y)
            );
        }

        // 원 그리기
        for (MyBox circle : circleList) {
            g2d.setColor(circle.rectColor); // 저장된 색상 설정
            g2d.setStroke(new BasicStroke(circle.getStrokeWidth())); // 저장된 두께 사용
            g2d.drawOval(
                Math.min(circle.sPos.x, circle.ePos.x),
                Math.min(circle.sPos.y, circle.ePos.y),
                Math.abs(circle.sPos.x - circle.ePos.x),
                Math.abs(circle.sPos.y - circle.ePos.y)
            );
        }

        // 펜 경로 그리기
        for (MyPath path : penPaths) {
            g2d.setColor(path.color); // 펜 색상 설정
            path.draw(g2d); // 두께 적용
        }
    }
}
