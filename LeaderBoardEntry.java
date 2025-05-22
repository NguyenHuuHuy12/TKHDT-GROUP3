package Project;

public class LeaderBoardEntry {
    private String playerName;
    private int score;
    private Date date;
    private DifficultyLevel difficulty;

    public LeaderBoardEntry(String playerName, int score, Date date, DifficultyLevel difficulty) {
        this.playerName = playerName;
        this.score = score;
        this.date = date;
        this.difficulty = difficulty;
    }

    public String toFileString() {
        return playerName + ";" + score + ";" + date.toString() + ";" + difficulty;
    }

    public static LeaderBoardEntry fromFileString(String line) {
        String[] parts = line.split(";");
        String name = parts[0];
        int score = Integer.parseInt(parts[1]);
        Date date = Date.fromString(parts[2]);
        DifficultyLevel diff = DifficultyLevel.valueOf(parts[3]);
        return new LeaderBoardEntry(name, score, date, diff);
    }

    @Override
    public String toString() {
        return "[" + difficulty + "] " + playerName + " - " + score + " points on " + date;
    }

    // Getters
    public int getScore() {
        return score;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }
}