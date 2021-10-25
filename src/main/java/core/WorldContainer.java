package core;

import window.Window;

public interface WorldContainer {
    void setWindow(Window window);
    void update();
    void render();
}
