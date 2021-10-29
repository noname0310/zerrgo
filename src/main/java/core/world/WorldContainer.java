package core.world;

import core.graphics.RenderScheduler;
import core.window.Window;

public interface WorldContainer {
    /**
     * initialize world before game loop
     * @param window global window variable
     * @param renderScheduler global render scheduler variable
     */
    void initialize(Window window, RenderScheduler renderScheduler);

    /**
     * call by game loop when update World
     */
    void update();
}
