package core.world;

import core.graphics.RenderScheduler;
import window.glfw.GlfwWindow;

public interface WorldContainer {
    /**
     * initialize world before game loop
     * @param window global window variable
     * @param renderScheduler global render scheduler variable
     */
    void initialize(GlfwWindow window, RenderScheduler renderScheduler);

    /**
     * call by game loop when update World
     */
    void update();
}
