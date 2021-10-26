package core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import window.Window;

public final class ZerrgoEngine {
    private final Window window;
    private final WorldContainer world;

    ZerrgoEngine(EngineBuilder engineBuilder) {
        /* Set up an error callback. The default implementation
         * will print the error message in System.err. */
        var errorCallback = GLFWErrorCallback.createPrint(System.err).set();

        /* init glfw */
        if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        /* Create the window */
        window = new Window(
                engineBuilder.getWindowWidth(),
                engineBuilder.getWindowHeight(),
                engineBuilder.getWindowName()
        );
        window.handleEvent();
        window.makeContext();
        window.vsync(engineBuilder.vsync());
        window.show();

        this.world = engineBuilder.getWorldContainer();
        world.initialize(window);

        glInit();
        loop();

        window.unHandleEvent();

        /*Destroy the window */
        window.destroy();

        /* Terminate GLFW and free the error callback */
        GLFW.glfwTerminate();
        errorCallback.free();
    }

    private void glInit() {
        /* OpenGL configures. */
        GL11.glViewport(0, 0, window.getFrameBufferWidth(), window.getFrameBufferHeight());
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void loop() {
        while(!window.shouldClose()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // clear the framebuffer

            world.update();
            world.render();

            window.swapBuffers();
            window.pollEvents();
        }
    }
}
