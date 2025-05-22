package Project;


public class HeartItem extends Item {
	public HeartItem(int x, int y) {
		super(x, y);
	}

	@Override
	public void applyEffect(Player player) {
		player.addScore(50);
		player.loseLife();
	}

}
