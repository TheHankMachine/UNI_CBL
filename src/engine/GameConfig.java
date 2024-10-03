package engine;

public class GameConfig {

    public final int targetTicksPerSecond;
    public final int targetTickMs;

    public final String assetFilePath;

    public final int width, height;

    public final float aspectRatio;

    public GameConfig(int width, int height, String assetFilePath, int targetTicksPerSecond){
        this.width = width;
        this.height = height;
        aspectRatio = (float) width / height;

        this.assetFilePath = assetFilePath;

        this.targetTicksPerSecond = targetTicksPerSecond;
        targetTickMs = 1_000 / targetTicksPerSecond;
    }

    public GameConfig(int width, int height, String assetFilePath){
        this(width, height, assetFilePath, 30);
    }

    public GameConfig(int width, int height){
        this(width, height, "./src/assets");
    }

}