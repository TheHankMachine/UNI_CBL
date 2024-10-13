package game;

import java.util.Arrays;

public class Chunk {
    private final int[] coordinates;

    public Chunk(int x, int y) {
        coordinates = new int[] {x, y};
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chunk chunk = (Chunk) o;
        return Arrays.equals(coordinates, chunk.coordinates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }
}