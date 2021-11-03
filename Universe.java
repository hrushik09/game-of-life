package life;

import java.util.Random;

public class Universe {
    int size;
    Cell[][] currentGrid;
    Cell[][] nextGrid;
    int generationNumber;
    int numberOfAliveCells;
    int maxGenerations;

    public Universe(int size) {
        this.size = size;
        this.currentGrid = new Cell[size][size];
        this.nextGrid = new Cell[size][size];
        generationNumber = 0;
        numberOfAliveCells = 0;
        maxGenerations = 0;
        create();
    }

    void create() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                currentGrid[i][j] = random.nextBoolean() ? Cell.ALIVE : Cell.DEAD;
                nextGrid[i][j] = Cell.DEAD;
            }
        }
    }

    void generate() {
        numberOfAliveCells = 0;
        Generator.generate(currentGrid, nextGrid);
        swap();
        count();
    }

    void count() {
        generationNumber++;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                numberOfAliveCells += currentGrid[i][j].count;
            }
        }
    }

    void swap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                currentGrid[i][j] = nextGrid[i][j];
            }
        }
    }
}
