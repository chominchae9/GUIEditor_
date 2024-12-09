package SwingTest;

import javax.swing.*;
import java.awt.*;

public class EditorEx extends JFrame {
    static Color currentColor = Color.BLACK; // 기본 색상
    static int selectedDrawingTool = 0; // 0: 사각형, 1: 원, 2: 펜, 3: 연필, 4: 지우개
    private DrawingPanel drawPane = null;

    public EditorEx() {
        setTitle("Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menubar = new JMenuBar();

        // File 메뉴
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem closeItem = new JMenuItem("Close");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(closeItem);

        menubar.add(fileMenu);

        // Edit 메뉴
        JMenu editMenu = new JMenu("Edit");
        JMenuItem cleanAllItem = new JMenuItem("Clean All");
        JMenuItem colorSelect = new JMenuItem("Select Color");

        // Clean All ActionListener
        cleanAllItem.addActionListener(e -> drawPane.cleanAllShapes());

        // Select Color ActionListener
        colorSelect.addActionListener(e -> currentColor = JColorChooser.showDialog(EditorEx.this, "Select Color", Color.BLACK));

        editMenu.add(cleanAllItem);
        editMenu.add(colorSelect);

        menubar.add(editMenu);

        // Draw 메뉴
        JMenu toolsMenu = new JMenu("Draw");
        JMenuItem rectangle = new JMenuItem("Rectangle");
        JMenuItem circle = new JMenuItem("Circle");
        JMenuItem pen = new JMenuItem("Pen");
        JMenuItem pencil = new JMenuItem("Pencil");
        JMenuItem eraser = new JMenuItem("Eraser");

        toolsMenu.add(rectangle);
        toolsMenu.add(circle);
        toolsMenu.add(pen);
        toolsMenu.add(pencil);
        toolsMenu.add(eraser);

        rectangle.addActionListener(e -> selectedDrawingTool = 0); // 사각형
        circle.addActionListener(e -> selectedDrawingTool = 1); // 원
        pen.addActionListener(e -> selectedDrawingTool = 2); // 펜
        pencil.addActionListener(e -> selectedDrawingTool = 3); // 연필
        eraser.addActionListener(e -> selectedDrawingTool = 4); // 지우개

        menubar.add(toolsMenu);

        setJMenuBar(menubar);

        // Drawing Panel 설정
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        drawPane = new DrawingPanel();
        drawPane.setBackground(Color.WHITE);
        c.add(drawPane, BorderLayout.CENTER);

        pack();
        setSize(1024, 712);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EditorEx();
    }
}
