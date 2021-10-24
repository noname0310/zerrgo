import window.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        Application.init();
        var window = new Window(1280, 720, "hello world");
        window.addListener((key, action, mods) -> {
            if (key == GLFW_KEY_A)
                new Window(300, 300, "A");
        });

        Application.free();
    }
}
