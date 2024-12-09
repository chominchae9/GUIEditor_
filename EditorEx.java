package SwingTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

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

        // 파일 open
        openItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "bmp"));
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    drawPane.loadImage(file); // DrawingPanel에서 이미지 로드
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
                }
            }
        });
        
        saveItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png"));
            int returnValue = fileChooser.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                // 파일 이름에 확장자가 없으면 ".png" 추가
                if (!file.getName().toLowerCase().endsWith(".png")) {
                    file = new File(file.getAbsolutePath() + ".png");
                }

                try {
                    BufferedImage image = new BufferedImage(drawPane.getWidth(), drawPane.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = image.createGraphics();

                    drawPane.paint(g2d); // DrawingPanel의 현재 상태를 이미지로 렌더링
                    g2d.dispose();

                    ImageIO.write(image, "png", file); // PNG 파일로 저장
                    JOptionPane.showMessageDialog(this, "Image saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage());
                }
            }
        });

        // 프로그램 종료
        closeItem.addActionListener(e -> System.exit(0));
        
        // Edit 메뉴
        JMenu editMenu = new JMenu("Settings");
        JMenuItem cleanAllItem = new JMenuItem("Clear All");
        JMenuItem colorSelect = new JMenuItem("Select Color");
        JMenuItem setStroke = new JMenuItem("Set Width");
        
        // Clean All ActionListener
        cleanAllItem.addActionListener(e -> drawPane.cleanAllShapes());

        // Select Color ActionListener
        colorSelect.addActionListener(e -> currentColor = JColorChooser.showDialog(EditorEx.this, "Select Color", Color.BLACK));

        setStroke.addActionListener(e -> {
            // 현재 두께 값을 기본 입력값으로 설정
            String input = JOptionPane.showInputDialog(this, "Enter stroke width:", drawPane.getStrokeWidth());
            try {
                int width = Integer.parseInt(input);
                drawPane.setStrokeWidth(width);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });

        editMenu.add(cleanAllItem);
        editMenu.add(colorSelect);
        editMenu.add(setStroke);
        menubar.add(editMenu);
        menubar.add(editMenu);

        // Draw 메뉴
        JMenu toolsMenu = new JMenu("Draw");
        JMenuItem rectangle = new JMenuItem("Rectangle");
        JMenuItem circle = new JMenuItem("Circle");
        JMenuItem pen = new JMenuItem("Pen");
        JMenuItem eraser = new JMenuItem("Eraser");

        toolsMenu.add(rectangle);
        toolsMenu.add(circle);
        toolsMenu.add(pen);
        toolsMenu.add(eraser);

        rectangle.addActionListener(e -> selectedDrawingTool = 0); // 사각형
        circle.addActionListener(e -> selectedDrawingTool = 1); // 원
        pen.addActionListener(e -> selectedDrawingTool = 2); // 펜
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