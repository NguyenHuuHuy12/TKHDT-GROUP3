package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MazePanel extends JPanel {
    private final int size;
    private final int cellSize = 25;
    private int[][] maze;
    private int playerRow = 1;
    private int playerCol = 1;

    public MazePanel(int size) {
        this.size = size;
        this.maze = generateRandomMaze(size);

        // Đảm bảo điểm bắt đầu và điểm kết thúc là đường đi
        maze[playerRow][playerCol] = 0;
        maze[size - 2][size - 2] = 0;

        setPreferredSize(new Dimension(size * cellSize, size * cellSize));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int newRow = playerRow;
                int newCol = playerCol;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> newRow--;
                    case KeyEvent.VK_DOWN -> newRow++;
                    case KeyEvent.VK_LEFT -> newCol--;
                    case KeyEvent.VK_RIGHT -> newCol++;
                }

                if (canMoveTo(newRow, newCol)) {
                    playerRow = newRow;
                    playerCol = newCol;
                    repaint();
                }
            }
        });
    }

    private boolean canMoveTo(int row, int col) {
        return row >= 0 && col >= 0 && row < size && col < size && maze[row][col] == 0;
    }

    private int[][] generateRandomMaze(int size) {
        int[][] m = new int[size][size];
        Random rand = new Random();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (r == 0 || c == 0 || r == size - 1 || c == size - 1) {
                    m[r][c] = 1; // tường
                } else {
                    m[r][c] = rand.nextDouble() < 0.3 ? 1 : 0; // 30% là tường
                }
            }
        }

        // Vị trí người chơi bắt đầu
        m[playerRow][playerCol] = 0;

        // Ô đích trong mê cung
        m[size - 2][size - 2] = 0;

        // Đảm bảo có một đường dẫn tới đích
        m[size - 3][size - 2] = 0; // từ trên
        m[size - 2][size - 3] = 1; // bên trái

        // Các ô xung quanh đích
        m[size - 2][size - 1] = 1; // phải là tường

        // Chừa lối ra ngoài khung bao quanh
        m[size - 1][size - 2] = 0; // mở tường bên dưới để thoát

        return m;
    }
 
    private int[][] generateMazeDFS(int size) {
        int[][] maze = new int[size][size];
        boolean[][] visited = new boolean[size][size];

        // Khởi tạo toàn bộ là tường
        for (int i = 0; i < size; i++) {
            Arrays.fill(maze[i], 1);
        }

        // Bắt đầu từ vị trí người chơi
        generatePath(maze, visited, playerRow, playerCol);

        // Đặt điểm đích
        maze[size - 2][size - 2] = 0;
        maze[size - 1][size - 2] = 0; // mở lối thoát ngoài cùng

        return maze;
    }

    private void generatePath(int[][] maze, boolean[][] visited, int r, int c) {
        visited[r][c] = true;
        maze[r][c] = 0;

        int[][] directions = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        Collections.shuffle(Arrays.asList(directions)); // để random hướng

        for (int[] dir : directions) {
            int nr = r + dir[0] * 2;
            int nc = c + dir[1] * 2;

            if (inBounds(nr, nc, maze.length) && !visited[nr][nc]) {
                // Mở đường giữa 2 ô
                maze[r + dir[0]][c + dir[1]] = 0;
                generatePath(maze, visited, nr, nc);
            }
        }
    }

    private boolean inBounds(int r, int c, int size) {
        return r > 0 && r < size - 1 && c > 0 && c < size - 1;
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
              
        Graphics2D g2d = (Graphics2D) g;
        
        //vẽ maze
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (maze[row][col] == 1) {
                    g.setColor(Color.DARK_GRAY); // tường
                } else {
                    g.setColor(Color.WHITE); // đường đi
                }
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }

               
        // Vẽ player
        int x = playerCol * cellSize;
        int y = playerRow * cellSize;
        drawPlayer(g2d, x, y, cellSize);

        // Ô thoát
        g.setColor(Color.YELLOW);
        g.fillRect((size - 2) * cellSize, (size - 2) * cellSize, cellSize, cellSize);
        g.setColor(Color.BLACK);
        g.drawRect((size - 2) * cellSize, (size - 2) * cellSize, cellSize, cellSize);

        // Người chơi hình tròn
//        g.setColor(Color.BLUE);
//        g.fillOval(playerCol * cellSize + 5, playerRow * cellSize + 5, cellSize - 10, cellSize - 10);
    }
               
        
    private void drawPlayer(Graphics2D g2d, int x, int y, int size) {
        g2d.setColor(new Color(30, 144, 255)); // màu xanh dương

        int headSize = size / 3;
        int bodyWidth = size / 3;
        int bodyHeight = size / 3;

        // Đầu: hình tròn 
        g2d.fillOval(x + size / 2 - headSize / 2, y, headSize, headSize);

        // Thân: hình elip dài
        g2d.fillOval(x + size / 2 - bodyWidth / 2, y + headSize, bodyWidth, bodyHeight);

        g2d.setStroke(new BasicStroke(3));
        
        // Tay trái: 
        g2d.drawLine(
            x + size / 2 - bodyWidth / 2, 
            y + headSize + bodyHeight / 3,
            x + size / 2 - (int)(bodyWidth * 0.9), 
            y + headSize + bodyHeight / 2);

        // Tay phải:
        g2d.drawLine(
            x + size / 2 + bodyWidth / 2, 
            y + headSize + bodyHeight / 3,
            x + size / 2 + (int)(bodyWidth * 0.9), 
            y + headSize + bodyHeight / 2);

        // Chân trái
        g2d.drawLine(x + size / 2 - bodyWidth / 3, y + headSize + bodyHeight,
                     x + size / 2 - bodyWidth / 3, y + size);

        // Chân phải
        g2d.drawLine(x + size / 2 + bodyWidth / 3, y + headSize + bodyHeight,
                     x + size / 2 + bodyWidth / 3, y + size);
    }
    
    

}

