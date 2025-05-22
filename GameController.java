import java.util.Scanner;

public class GameController {
    private Maze maze;
    private LeaderBoard entries;
    private GameSetting gameSetting;
    private Player player;

    public GameController(GameSetting gameSetting) {
        
        this.maze = new Maze(0, 0, gameSetting);
        this.entries = new LeaderBoard();
        this.gameSetting = gameSetting;
        this.player = PlayerFactory.createEntity();
        if (this.player == null) {
            throw new IllegalStateException("PlayerFactory.createEntity() returned null");
        }
        this.player.setPosition(0, 0); // Đặt vị trí ban đầu
    }

    public void startGame() {
        player.setLives(3);
        System.out.println("Game Started! You have " + player.getLives() + " lives");
        try (Scanner scanner = new Scanner(System.in)) {
            while (player.getLives() > 0) {
                System.out.println("Enter move (UP, DOWN, LEFT, RIGHT): ");
                String direction = scanner.nextLine();
                move(direction);

                if (checkWin()) {
                    endGame();
                    return;
                }
                if (maze.checkCollision()) { // Sửa: Dùng checkCollision thay hasPlayerLost
                    player.loseLife(); // Sửa: loseLife thay loserLife
                    System.out.println("Bạn mất 1 mạng!");

                    if (player.getLives() == 0) {
                        System.out.println("Game Over!");
                        break;
                    }
                    player.setPosition(0, 0); // Reset vị trí
                }
            }
        }
    }

    public void move(String direction) {
        if (direction == null || !direction.matches("UP|DOWN|LEFT|RIGHT")) {
            System.out.println("Hướng không hợp lệ! Vui lòng nhập UP, DOWN, LEFT hoặc RIGHT.");
            return;
        }
        // Chuyển hướng thành dx, dy
        int dx = 0, dy = 0;
        switch (direction.toUpperCase()) {
            case "UP": dy = -1; break;
            case "DOWN": dy = 1; break;
            case "LEFT": dx = -1; break;
            case "RIGHT": dx = 1; break;
        }
        player.move(dx, dy); // Sửa: Dùng move(dx, dy) của Player
        maze.checkCollision();
        player.triggerTrap(maze); // Thêm: Kích hoạt bẫy
    }

    public boolean checkWin() {
        ExitCell exit = maze.getExit();
        return exit != null && exit.isReached(player); // Sửa: Kiểm tra null
    }

    public void endGame() {
        int timeElapsed = maze.getTime();
        int bonusScore = Math.max(0, 1000 - timeElapsed);
        player.addScore(bonusScore);

        System.out.println("Chúc mừng! Bạn đã thoát khỏi mê cung!");
        System.out.println("Thời gian hoàn thành: " + timeElapsed);
        System.out.println("Bonus Score: " + bonusScore);
        System.out.println("Tổng điểm của bạn: " + player.getScore());

        entries.addEntry(player.getName(), player.getScore(), gameSetting.getDifficulty());

        try {
            saveGame();
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu trò chơi: " + e.getMessage());
        }
    }

    public void saveGame() throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Nhập tên file để lưu game: ");
            String fileName = scanner.nextLine();
            if (!fileName.endsWith(".dat")) {
                fileName += ".dat";
            }
            GameState state = new GameState(
                    maze.getCell(player.getX(), player.getY()), // Sửa: Lấy Cell hiện tại
                    maze.getEnemies(), // Sửa: Lấy vị trí kẻ thù
                    player.getItemWithDuration(), // Sửa: Lấy vật phẩm
                    player.getScore(),
                    maze.getTime(),
                    player.getLives(),
                    gameSetting.getDifficulty());

            state.saveToFile(fileName);
            System.out.println("Game đã lưu vào file: " + fileName);
        }
    }

    public void loadGame() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Nhập tên file để load game: ");
            String fileName = scanner.nextLine();
            if (!fileName.endsWith(".dat")) {
                fileName += ".dat";
            }
            try {
                GameState state = GameState.loadFromFile(fileName);
                Cell position = state.getPosition();
                if (position != null) {
                    player.setPosition(position.getX(), position.getY());
                }
                maze.setEnemyPositions(state.getEnemyPositions()); // Sửa: Đặt vị trí kẻ thù
                player.setItemWithDuration(state.getPlayerItems()); // Sửa: Đặt vật phẩm
                player.setScore(state.getScore());
                maze.setTime(state.getTime());
                player.setLives(state.getLives());
                gameSetting.setDifficulty(state.getDifficulty());

                System.out.println("Tải game từ " + fileName + " thành công!");
            } catch (Exception e) {
                System.out.println("Không thể tải game từ " + fileName + ": " + e.getMessage());
            }
        }
    }

    public boolean isGameOver() {
        if (player.getLives() == 0) {
            player.setPosition(0, 0); // Sửa: Reset vị trí
            return true;
        }
        return false;
    }
}