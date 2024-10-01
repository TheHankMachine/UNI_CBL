package game;

public class Config {
    public static final int TPS = 20;
    public static final int TARGET_TICK_MS = 1_000 / TPS;

    public static final String ASSET_FILE_PATH = "./src/assets/";

    public static final int WIDTH = 128;
    public static final int HEIGHT = 128;

    public static final float ASPECT_RATIO = (float) WIDTH / HEIGHT;
}
