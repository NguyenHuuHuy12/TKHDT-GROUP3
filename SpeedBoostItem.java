package Project;


public class SpeedBoostItem extends Item {
	private static final int SPEED_DURATION = 15;

	public SpeedBoostItem(int x, int y) {
		super(x, y);
	}

	@Override
	public void applyEffect(Player player) {
		player.addScore(150);
		player.activeSpeedBoost(SPEED_DURATION);
	}

}
