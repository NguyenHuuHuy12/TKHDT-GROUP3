package Project;

import java.util.ArrayList;
import java.util.List;
public class GameSetting implements Observer {
    private DifficultLevel difficulty;
    private int mazeSize;
    private int enemyCount;
    private List<Observer> observers = new ArrayList<>();
    
    
    public void setDifficulty(DifficultLevel difficulty) {
        this.difficulty = difficulty;
        notifyObservers();
    }
    /**
	 * @return the mazeSize
	 */
	public int getMazeSize() {
		return mazeSize;
	}
	/**
	 * @param mazeSize the mazeSize to set
	 */
	public void setMazeSize(int mazeSize) {
		this.mazeSize = mazeSize;
	}
	/**
	 * @return the enemyCount
	 */
	public int getEnemyCount() {
		return enemyCount;
	}
	/**
	 * @param enemyCount the enemyCount to set
	 */
	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}
	/**
	 * @return the observers
	 */
	public List<Observer> getObservers() {
		return observers;
	}
	/**
	 * @param observers the observers to set
	 */
	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}
	/**
	 * @return the difficulty
	 */
	public DifficultLevel getDifficulty() {
		return difficulty;
	}
	/**
	 * @param difficulty
	 * @param mazeSize
	 * @param enemyCount
	 * @param observers
	 */
	public GameSetting(DifficultLevel difficulty, int mazeSize, int enemyCount, List<Observer> observers) {
		super();
		this.difficulty = difficulty;
		this.mazeSize = mazeSize;
		this.enemyCount = enemyCount;
		this.observers = observers;
	}
	public void addObserver(Observer observer) {
        observers.add(observer);
    }
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    @Override
    public void update() {
        // Cập nhật trạng thái khi có thay đổi
        System.out.println("Game settings updated: " + difficulty);
    }
}
