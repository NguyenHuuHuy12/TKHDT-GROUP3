package Project;

import java.util.HashMap;
import java.util.Map;

public class Enemy extends Movable implements EnemyMoveStrategy {
	private EnemyMoveStrategy strategy;
	private int speed;
	private int freezeTurns;

	/**
	 * @param strategy
	 * @param speed
	 * @param freezeTurns
	 */
	public Enemy(EnemyMoveStrategy strategy, int speed, int freezeTurns) {
		super();
		this.strategy = strategy;
		this.speed = 1;
		this.freezeTurns = 0;
	}

	@Override
	public void move(Enemy enemy) {
		// TODO Auto-generated method stub
		if(freezeTurns>0) {
			freezeTurns--;
			return;
		}
		if(strategy!=null) {
			strategy.move(enemy);
		}
	}

	@Override
	public void move(String direction) {
		// TODO Auto-generated method stub
		if(freezeTurns>0) {
			freezeTurns--;
			return;
		}
		Map<String, Runnable > moveAction = new HashMap<>();
		moveAction.put("up", ()-> setY(getY()-speed));
		moveAction.put("down",()-> setY(getY()+speed));
		moveAction.put("left",()-> setX(getX()-speed));
		moveAction.put("right", ()-> setX(getX()+speed));
		
		Runnable action = moveAction.get(direction.toLowerCase());
		if(action !=null) {
			action.run();
		}else {
			System.out.println("huong khong hop le"+direction);
		}
		
		
	}

	/**
	 * @return the strategy
	 */
	public EnemyMoveStrategy getStrategy() {
		return strategy;
	}

	/**
	 * @param strategy the strategy to set
	 */
	public void setStrategy(EnemyMoveStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the freezeTurns
	 */
	public int getFreezeTurns() {
		return freezeTurns;
	}

	/**
	 * @param freezeTurns the freezeTurns to set
	 */
	public void setFreezeTurns(int freezeTurns) {
		this.freezeTurns = freezeTurns;
	}

	@Override
	public Movable createEntity() {
		// TODO Auto-generated method stub
		return new Enemy (this.strategy,this.freezeTurns, this.speed);
	}
	public void freeze(int tums) {
		if(tums>0) {
			this.freezeTurns = Math.max(tums, 0);
		}
	}

}
