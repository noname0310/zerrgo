import core.EngineBuilder;
import core.TestWorld;
import core.ZerrgoEngine;
import window.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        new EngineBuilder()
                .windowSize(1280, 720)
                .windowName("hello world")
                .enableVsync()
                .world(new TestWorld())
                .run();
    }
}