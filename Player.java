package Project;

import java.util.Timer;
import java.util.TimerTask;

public class Player {
	private int startX, startY;
	private int x, y;
	private int score;
	private int lives;
	private boolean hasShield;
	private boolean isSpeedBoosted;
	private boolean isEnemyFrozen;
	private Timer effectTimer;
	private String name;

	public Player(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.score = 0;
		this.lives = 3;
		this.hasShield = false;
		this.isSpeedBoosted = false;
		this.isEnemyFrozen = false;
		this.effectTimer = new Timer();
		
	}

	/**
	 * @return the startX
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * @param startX the startX to set
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * @return the startY
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * @param startY the startY to set
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}

	/**
	 * @return the effectTimer
	 */
	public Timer getEffectTimer() {
		return effectTimer;
	}

	/**
	 * @param effectTimer the effectTimer to set
	 */
	public void setEffectTimer(Timer effectTimer) {
		this.effectTimer = effectTimer;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @param hasShield the hasShield to set
	 */
	public void setHasShield(boolean hasShield) {
		this.hasShield = hasShield;
	}

	/**
	 * @param isSpeedBoosted the isSpeedBoosted to set
	 */
	public void setSpeedBoosted(boolean isSpeedBoosted) {
		this.isSpeedBoosted = isSpeedBoosted;
	}

	/**
	 * @param isEnemyFrozen the isEnemyFrozen to set
	 */
	public void setEnemyFrozen(boolean isEnemyFrozen) {
		this.isEnemyFrozen = isEnemyFrozen;
	}

	/**
	 * @param lives the lives to set
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param name
	 */
	public Player(String name) {
		super();
		this.name = name;
	}

	// di chuyen nguoi choi
	public void move(int dx, int dy) {
		this.x += isSpeedBoosted ? 2 * dx : dx;
		this.y += isSpeedBoosted ? 2 * dy : dy;
	}

	// thu thap item
	public void collectItem(Item item) {
		item.applyEffect(this);

	}

	// mat mang
	public void loseLife() {
		if (!hasShield) {
			lives = Math.max(0, lives - 1);
		}

	}
	
	// dap vao bay 
	public void triggerTrap(Maze maze) {
	    Cell currentCell = maze.getCell(x, y);
	    
	    // Kiểm tra xem cell hiện tại có bẫy không
	    if (currentCell != null && currentCell.hasTrap()) {
	        Trap trap = currentCell.getTrap();
	        Cell targetCell = trap.findRandomValidCell(maze, maze.getExit());
	        
	        // Chỉ dịch chuyển nếu tìm thấy cell hợp lệ
	        if (targetCell != null) {
	            setPosition(targetCell.getX(), targetCell.getY());
	            System.out.println("Bạn đã bị dịch chuyển đến vị trí mới: (" + targetCell.getX() + ", " + targetCell.getY() + ")");
	        } else {
	            System.out.println("Không tìm được vị trí hợp lệ để dịch chuyển!");
	        }
	    }
	}
	// doi vi tri
	public void resetPosition() {
		this.x = startX;
		this.y = startY;
	}
	// THEM DIEM
	public void addScore(int points) {
		this.score += points;
	}
	
	// lay vi tri
	public void setPosition(int x, int y) {
	    this.x = x;
	    this.y = y;
	}

	// active shield
	public void activateShield(int duration) {
		hasShield = true;
		scheduleEffectEnd(() -> hasShield = false, duration);

	}

	// kich hoat tang toc
	public void activeSpeedBoost(int duration) {
		isSpeedBoosted = true;
		scheduleEffectEnd(() -> isSpeedBoosted = false, duration);
	}

	// dong bang ke thu
	public void activeFreezeEnemy(int duration) {
		isEnemyFrozen = true;
	}

	// ket thuc hieu ung sau 1 thoi gian
	private void scheduleEffectEnd(Runnable effectEnd, int duration) {
		effectTimer.schedule(new TimerTask() {
			public void run() {
				effectEnd.run();
			}

		}, duration * 1000);

	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * @return the hasShield
	 */
	public boolean isHasShield() {
		return hasShield;
	}

	/**
	 * @return the isSpeedBoosted
	 */
	public boolean isSpeedBoosted() {
		return isSpeedBoosted;
	}

	/**
	 * @return the isEnemyFrozen
	 */
	public boolean isEnemyFrozen() {
		return isEnemyFrozen;
	}
	/**
	 * @return the effectTimer
	 */

}
