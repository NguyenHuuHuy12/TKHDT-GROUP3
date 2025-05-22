package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
 private MazePanel mazePanel;
 private Player player;
 private int lives = 3;
 private JLabel heartLabel;

 public Main() {
     setTitle("Maze Game");
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setSize(770, 820);
     setLayout(new BorderLayout());

     // Panel nút chọn độ khó
     JPanel topPanel = new JPanel();
     JButton easyBtn = new JButton("Dễ (18x18)");
     JButton mediumBtn = new JButton("Vừa (21x21)");
     JButton hardBtn = new JButton("Khó (30x30)");

     easyBtn.addActionListener(e -> startGame(18));
     mediumBtn.addActionListener(e -> startGame(21));
     hardBtn.addActionListener(e -> startGame(29)); // set 29 vì set 30 bị khuất mất 1 hàng ở cuối 

     topPanel.add(easyBtn);
     topPanel.add(mediumBtn);
     topPanel.add(hardBtn);
     add(topPanel, BorderLayout.NORTH);

     // Panel trái tim
     heartLabel = new JLabel("❤️ ❤️ ❤️");
     JPanel bottomPanel = new JPanel();
     bottomPanel.add(heartLabel);
     add(bottomPanel, BorderLayout.SOUTH);
     
     
     setVisible(true);
 }

 private void startGame(int size) {
     if (mazePanel != null) remove(mazePanel);
     mazePanel = new MazePanel(size);
     add(mazePanel, BorderLayout.CENTER);
     revalidate();
     repaint();
     mazePanel.requestFocusInWindow();
 }

 public static void main(String[] args) {
     SwingUtilities.invokeLater(Main::new);
 }
}

