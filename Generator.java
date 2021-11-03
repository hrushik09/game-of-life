package life;

public class Generator {
    static int size;

    static void generate(Cell[][] currentGrid, Cell[][] nextGrid) {
        size = currentGrid.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int aliveNeighbours = getLiveNeighbours(currentGrid, i, j);
                if (currentGrid[i][j] == Cell.ALIVE) {
                    if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                        nextGrid[i][j] = Cell.DEAD;
                    } else {
                        nextGrid[i][j] = Cell.ALIVE;
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        nextGrid[i][j] = Cell.ALIVE;
                    } else {
                        nextGrid[i][j] = Cell.DEAD;
                    }
                }
            }
        }
    }

    static int getLiveNeighbours(Cell[][] currentGrid, int i2, int j2) {
        int i1 = i2 - 1;
        int i3 = i2 + 1;
        int j1 = j2 - 1;
        int j3 = j2 + 1;
        if (i1 == -1) {
            i1 = size - 1;
        }
        if (i3 == size) {
            i3 = 0;
        }
        if (j1 == -1) {
            j1 = size - 1;
        }
        if (j3 == size) {
            j3 = 0;
        }

        return currentGrid[i1][j1].getCount() + currentGrid[i1][j2].getCount() + currentGrid[i1][j3].getCount()
                + currentGrid[i2][j1].getCount() + currentGrid[i2][j3].getCount()
                + currentGrid[i3][j1].getCount() + currentGrid[i3][j2].getCount() + currentGrid[i3][j3].getCount();
    }
}
