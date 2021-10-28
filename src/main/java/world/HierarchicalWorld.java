package world;

import core.graphics.RenderScheduler;
import core.world.WorldContainer;
import window.glfw.GlfwWindow;

public class HierarchicalWorld implements WorldContainer {
    private GlfwWindow window;

    @Override
    public void initialize(GlfwWindow window, RenderScheduler renderScheduler) {
        this.window = window;
    }

    @Override
    public void update() {

    }
}
