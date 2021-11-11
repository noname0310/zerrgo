package world.opengltest;

import core.graphics.AssetLoader;
import core.graphics.RenderScheduler;
import core.graphics.resource.Texture;
import core.window.Window;
import core.world.WorldContainer;

public final class GcTestWorld implements WorldContainer {
    private Texture texture; // strong reference

    @Override
    public void initialize(Window window, RenderScheduler renderScheduler, AssetLoader assetLoader) {
        var texture = assetLoader.getTexture("src/main/resources/20211104_102157-realesrgan.jpg");
    }

    @Override
    public void update() {
        System.gc(); // gc called in every frame
    }
}
