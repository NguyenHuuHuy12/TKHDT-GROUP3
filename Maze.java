package Project;


import java.util.ArrayList;
import java.util.List;

public class Maze {
    private Cell[][] cells;
    private Player player;
    private List<Enemy> enemies;
    private List<Trap> traps;
    private List<Observer> observers;
    private GameSetting gameSetting;
    private int score;
    private int time;
    private ExitCell exit;

    public Maze(int width, int height, GameSetting gameSetting) {
        this.cells = new Cell[width][height];
        this.enemies = new ArrayList<>();
        this.traps = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.gameSetting = gameSetting;
        this.score = 0;
        this.time = 0;
        this.exit = null;

        // Khởi tạo các cell (ví dụ)
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i, j, false);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void addEnemy(Enemy enemy) {
        if (enemy != null) {
            enemies.add(enemy);
        }
    }

    public List<Trap> getTraps() {
        return traps;
    }

    public void addTrap(Trap trap) {
        if (trap != null) {
            traps.add(trap);
        }
    }

    public ExitCell getExit() {
        return exit;
    }

    public void setExit(ExitCell exit) {
        this.exit = exit;
    }

    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    public void updateCell(int x, int y, int newX, int newY) {
        // Cập nhật di chuyển hoặc trạng thái cell
        notifyObservers();
    }

    public void inScore(int points) {
        score += points;
        notifyObservers();
    }

    public void startGame() {
        score = 0;
        time = 0;
        // Khởi tạo lại game
        notifyObservers();
    }

    public void endGame() {
        // Xử lý kết thúc game
        notifyObservers();
    }

    // Kiểm tra va chạm giữa Player và Enemy/Trap/Item
    public boolean checkCollision() {
        // Kiểm tra từng enemy va chạm với player
        for (Enemy enemy : enemies) {
            if (enemy.getX() == player.getX() && enemy.getY() == player.getY()) {
                return true;
            }
        }

        // Kiểm tra trap
        for (Trap trap : traps) {
            if (trap.getX() == player.getX() && trap.getY() == player.getY() && trap.isActive()) {
                return true;
            }
        }

        return false;
    }

    /**
	 * @param cells
	 * @param player
	 * @param enemies
	 * @param traps
	 * @param observers
	 * @param gameSetting
	 * @param score
	 * @param time
	 * @param exit
	 */
	public Maze(Cell[][] cells, Player player, List<Enemy> enemies, List<Trap> traps, List<Observer> observers,
			GameSetting gameSetting, int score, int time, ExitCell exit) {
		super();
		this.cells = cells;
		this.player = player;
		this.enemies = enemies;
		this.traps = traps;
		this.observers = observers;
		this.gameSetting = gameSetting;
		this.score = score;
		this.time = time;
		this.exit = exit;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	// Lấy danh sách cell có thể dịch chuyển đến từ start cell
    public List<Cell> getValidTeleportCells() {
        List<Cell> validCells = new ArrayList<>();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                Cell c = cells[i][j];
                // Bỏ qua là ExitCell hoặc có enemy/trap
                if (c instanceof ExitCell) continue;

                boolean occupied = false;
                for (Enemy enemy : enemies) {
                    if (enemy.getX() == i && enemy.getY() == j) {
                        occupied = true;
                        break;
                    }
                }
                if (occupied) continue;

                // Thêm cell nếu không phải wall
                if (!c.isWall()) {
                    validCells.add(c);
                }
            }
        }
        return validCells;
    }
    
    public Cell getCell(int x, int y) {
        if (x >= 0 && x < cells.length && y >= 0 && y < cells[0].length) {
            return cells[x][y];
        }
        return null;
    }
    // Kiểm tra có đường đi từ start đến exit
    public boolean isCellReachableFrom(Cell start, ExitCell exitCell) {
        // Thuật toán BFS hoặc DFS để kiểm tra đường đi (giữ lại phương thức này)
        // Ví dụ:
        boolean[][] visited = new boolean[cells.length][cells[0].length];
        return dfs(start.getX(), start.getY(), exitCell.getX(), exitCell.getY(), visited);
    }

    private boolean dfs(int x, int y, int exitX, int exitY, boolean[][] visited) {
        if (x < 0 || y < 0 || x >= cells.length || y >= cells[0].length) return false;
        if (visited[x][y]) return false;
        if (cells[x][y].isWall()) return false;

        if (x == exitX && y == exitY) return true;

        visited[x][y] = true;

        return dfs(x + 1, y, exitX, exitY, visited) ||
               dfs(x - 1, y, exitX, exitY, visited) ||
               dfs(x, y + 1, exitX, exitY, visited) ||
               dfs(x, y - 1, exitX, exitY, visited);
    }
}
