package Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trap {
    private int x, y;
    private boolean isActive;

    public Trap(int x, int y) {
        this.x = x;
        this.y = y;
        this.isActive = false;
    }

    // Dịch chuyển người chơi khi chạm vào bẫy
    public void triggerTrap(Player player, Maze maze) {
        Cell targetCell = findRandomValidCell(maze, maze.getExit());
        if (targetCell != null) {
            player.setPosition(targetCell.getX(), targetCell.getY());
            System.out.println("Bạn đã bị dịch chuyển đến vị trí mới: (" + targetCell.getX() + ", " + targetCell.getY() + ")");
        } else {
            System.out.println("Không tìm được vị trí hợp lệ để dịch chuyển!");
        }
        isActive = true;
    }

    // Tìm ô ngẫu nhiên có thể dịch chuyển tới
    Cell findRandomValidCell(Maze maze, ExitCell exit) {
        List<Cell> validCells = new ArrayList<>();

        for (Cell[] row : maze.getCells()) {
            for (Cell cell : row) {
                // Ô hợp lệ nếu không phải tường, không phải exit và có đường đến đích
                if (!cell.isWall() && !(cell instanceof ExitCell) && maze.isCellReachableFrom(cell, exit)) {
                    validCells.add(cell);
                }
            }
        }

        // Chọn ngẫu nhiên từ danh sách ô hợp lệ
        if (validCells.isEmpty()) return null;
        return validCells.get(new Random().nextInt(validCells.size()));
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isActive() { return isActive; }
}
