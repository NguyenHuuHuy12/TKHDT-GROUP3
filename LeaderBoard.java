package Project;

import java.io.*;
import java.util.*;

public class LeaderBoard {
    private List<LeaderBoardEntry> entries;

    public LeaderBoard() {
        entries = new ArrayList<>();
    }

    /**
	 * @return the entries
	 */
	public List<LeaderBoardEntry> getEntries() {
		return entries;
	}

	/**
	 * @param entries the entries to set
	 */
	public void setEntries(List<LeaderBoardEntry> entries) {
		this.entries = entries;
	}

	public void addEntry(String playerName, int rawScore, int timeElapsedSeconds, DifficultyLevel difficulty) {
        int bonus = calculateBonusScore(timeElapsedSeconds, difficulty);
        int finalScore = rawScore + bonus;
        Date date = new Date();
        LeaderBoardEntry entry = new LeaderBoardEntry(playerName, finalScore, date, difficulty);
        entries.add(entry);
        System.out.println("Added to Leaderboard: " + entry);
    }

    private int calculateBonusScore(int timeElapsed, DifficultyLevel difficulty) {
        int baseTime = switch (difficulty) {
            case EASY -> 300;
            case MEDIUM -> 200;
            case HARD -> 150;
        };
        int bonus = Math.max(0, (baseTime - timeElapsed)) * 2;
        return bonus;
    }

    public List<LeaderBoardEntry> getTopScores(int limit) {
        return entries.stream()
                .sorted(Comparator.comparingInt(LeaderBoardEntry::getScore).reversed())
                .limit(limit)
                .toList();
    }

    public void saveToFile(String path) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (LeaderBoardEntry e : entries) {
                writer.println(e.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Failed to save leaderboard: " + e.getMessage());
        }
    }

    public void loadFromFile(String path) {
        entries.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LeaderBoardEntry entry = LeaderBoardEntry.fromFileString(line);
                entries.add(entry);
            }
        } catch (IOException e) {
            System.out.println("Failed to load leaderboard: " + e.getMessage());
        }
    }

	
}
