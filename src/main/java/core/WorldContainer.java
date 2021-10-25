package core;

import window.Window;

public interface WorldContainer {
    /**
     * initialize world before game loop
     * @param window global window variable
     */
    void initialize(Window window);

    /**
     * call by game loop when update World
     */
    void update();

    /**
     * call by game loop when render World
     */
    void render();
}
