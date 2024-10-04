package engine;

import java.awt.*;

public class GameConfig {
    public final int targetTicksPerSecond;
    public final int targetTickMs;

    public final Color backgroundColor;

    public final String assetFilePath;

    public final int width, height;
    public final float aspectRatio;

    public GameConfig(int width, int height, Color backgroundColor, String assetFilePath, int targetTicksPerSecond){
        this.width = width;
        this.height = height;
        aspectRatio = (float) width / height;

        this.backgroundColor = backgroundColor;

        this.assetFilePath = assetFilePath;

        this.targetTicksPerSecond = targetTicksPerSecond;
        targetTickMs = 1_000 / targetTicksPerSecond;
    }

    public GameConfig(int width, int height, Color backgroundColor, String assetFilePath){
        this(width, height, backgroundColor, assetFilePath, 30);
    }

    public GameConfig(int width, int height, Color backgroundColor){
        this(width, height, backgroundColor, "./src/assets/");
    }

    public GameConfig(int width, int height){
        this(width, height, Color.WHITE);
    }

}