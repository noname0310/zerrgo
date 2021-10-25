package core;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import window.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public final class ZerrgoEngine {
    private final Window window;
    private final WorldContainer world;

    ZerrgoEngine(
            int windowWidth,
            int windowHeight,
            String windowName,
            boolean vsync,
            WorldContainer world
    ) {
        /* Set up an error callback. The default implementation
         * will print the error message in System.err. */
        var errorCallback = GLFWErrorCallback.createPrint(System.err).set();

        /* init glfw */
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        /* Create the window */
        window = new Window(windowWidth, windowHeight, windowName);
        window.handleEvent();
        window.makeContext();
        window.vsync(vsync);
        window.show();

        world.setWindow(window);
        this.world = world;

        glInit();
        loop();

        window.unHandleEvent();

        /*Destroy the window */
        window.destroy();

        /* Terminate GLFW and free the error callback */
        glfwTerminate();
        errorCallback.free();
    }

    private void glInit() {
        /* OpenGL configures. */
        glViewport(0, 0, window.getFrameBufferWidth(), window.getFrameBufferHeight());
        glEnable(GL11.GL_CULL_FACE);
        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void loop() {
        while(!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

            world.update();
            world.render();

            window.swapBuffers();
            window.pollEvents();
        }
    }
}
