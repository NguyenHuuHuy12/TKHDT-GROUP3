package Project;


import java.util.ArrayList;
import java.util.List;

public class Cell {
    private boolean isWall;
    private int x, y;
    private List<Item> items;
    private Trap trap;

    public Cell(int x, int y, boolean isWall) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.items = new ArrayList<>();
        this.trap = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWall() {
        return isWall;
    }

    public boolean hasTrap() {
        return trap != null;
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
    }

    public Trap getTrap() {
        return trap;
    }

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void removeItem(Item item) {
        if (item != null) {
            items.remove(item);
        }
    }

    public void clearItems() {
        items.clear();
    }

    public List<Item> getItems() {
        return items;
    }

    // Kiểm tra người chơi có đang đứng trên cell này không
    public boolean isReached(Player player) {
        return player != null && player.getX() == this.x && player.getY() == this.y;
    }
}
