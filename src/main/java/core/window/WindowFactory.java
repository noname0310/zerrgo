package core.window;

public interface WindowFactory {
    void globalInitialize();

    void globalTerminate();

    Window createWindow(int width, int height, String name);
}
