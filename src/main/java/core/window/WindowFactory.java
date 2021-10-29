package core.window;

public interface WindowFactory {
    void globalInitialize();

    void globalFinalize();

    Window createWindow(int width, int height, String name);
}
