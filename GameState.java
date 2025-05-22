package Project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable{
	// ho tro luu, tranh loi khi loadFile
	private static final long serialVersionUID = 1L;
	

	private Cell position;
	private List<Pair<Integer, Integer>> enemyPositions;
	private List<Pair<String, Integer>> playerItems;
	private int score;
	private int time;
	private int lives;
	private DifficultLevel difficulty;
	private GameSetting gameSetting;
	

	public GameState(Cell position, List<Pair<Integer, Integer>> enemyPositions,
			List<Pair<String, Integer>> playerItems, int score, int time, int lives, DifficultLevel difficulty) {
		super();
		this.position = position;
		this.enemyPositions = enemyPositions;
		this.playerItems = playerItems;
		this.score = score;
		this.time = time;
		this.lives = lives;
		this.difficulty = difficulty;

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Cell getPosition() {
		return position;
	}

	public List<Pair<Integer, Integer>> getEnemyPositions() {
		return enemyPositions;
	}

	public List<Pair<String, Integer>> getPlayerItems() {
		return playerItems;
	}

	public int getScore() {
		return score;
	}

	public int getTime() {
		return time;
	}

	public int getLives() {
		return lives;
	}

	public DifficultLevel getDifficulty() {
		return difficulty;
	}

	public GameSetting getGameSetting() {
		return gameSetting;
	}
	
	

	public void setPosition(Cell position) {
		this.position = position;
	}

	public void setEnemyPositions(List<Pair<Integer, Integer>> enemyPositions) {
		this.enemyPositions = enemyPositions;
	}

	public void setPlayerItems(List<Pair<String, Integer>> playerItems) {
		this.playerItems = playerItems;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void setDifficulty(DifficultLevel difficulty) {
		this.difficulty = difficulty;
	}

	public void setGameSetting(GameSetting gameSetting) {
		this.gameSetting = gameSetting;
	}

	// luu game
	public void saveToFile(String filePath) throws IOException{
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
			out.writeObject(this);
		}
		
		}
	
	// load game 
	public static GameState loadFromFile(String filePath) throws IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))){
			return (GameState) in.readObject();
		}
		
	}
	

}
