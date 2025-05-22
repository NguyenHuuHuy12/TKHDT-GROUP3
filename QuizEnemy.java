package Project;

public class QuizEnemy {
	private String question;
	private String answer;
	/**
	 * @param question
	 * @param answer
	 */
	public QuizEnemy(String question, String answer) {
		super();
		this.question = question;
		this.answer = answer;
	}
	public boolean askQuestion() {
		return false;
		
	}
	public void onlncorrectAnswer(Player player) {
		
	}

}
