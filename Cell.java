package life;

public enum Cell {
    ALIVE("O", 1),
    DEAD(" ", 0);

    final String symbol;
    final int count;

    Cell(String symbol, int count) {
        this.symbol = symbol;
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }
}
