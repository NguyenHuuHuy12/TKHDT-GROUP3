package Project;


public class FreezeEnemyItem extends Item {
	public FreezeEnemyItem(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	private static final int FREEZE_DURATION = 20;

	@Override
	public void applyEffect(Player player) {
		player.addScore(200);
		player.activeFreezeEnemy(FREEZE_DURATION);
	}

}
