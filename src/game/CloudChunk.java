package game;

import java.util.Random;

public class CloudChunk {
    
    public CloudChunk(int left, int right, int top, int bottom) {
        Random rand = new Random();
        int numClouds = rand.nextInt(12) + 1;

        for (int i = 0; i < numClouds; i++) {
            int cloudX = rand.nextInt(right - left) + left;
            int cloudY = rand.nextInt(bottom - top) + top;

            new Cloud(cloudX, cloudY);
        }
    }
}
