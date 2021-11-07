package window.glfw;

import core.window.Window;
import core.window.WindowFactory;

public final class GlfwWindowFactory implements WindowFactory {
    @Override
    public void globalInitialize() { GlfwWindow.globalInitialize(); }

    @Override
    public void globalTerminate() { GlfwWindow.globalFinalize(); }

    /**
     * create glfw window
     * @param width window width
     * @param height window height
     * @param title window title
     */
    @Override
    public Window createWindow(int width, int height, String title) {
        return new GlfwWindow(width, height, title);
    }
}
