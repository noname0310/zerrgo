package window;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryStack;
import window.event.KeyEventListener;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

public final class Window {
    private List<KeyEventListener> listeners = new ArrayList<>();
    private long handle;
    private int frameBufferWidth;
    private int frameBufferHeight;

    /**
     * create glfw window
     * @param width window width
     * @param height  window height
     * @param title window title
     */
    public Window(int width, int height, String title) {
        /* create window */
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL) throw new RuntimeException("Failed to create the GLFW window");

        /* register event */
        glfwSetKeyCallback(handle, this::onKey);
        glfwSetFramebufferSizeCallback(handle, this::onFramebufferSize);
        glfwSetCursorPosCallback(handle, this::onMouseMove);
        glfwSetMouseButtonCallback(handle, this::onMouseButton);
        glfwSetScrollCallback(handle, this::onScroll);

        /* get framebuffer size */
        try (MemoryStack stack = stackPush()) {
            IntBuffer framebufferSize = stack.mallocInt(2);
            nglfwGetFramebufferSize(handle, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
            frameBufferWidth = framebufferSize.get(0);
            frameBufferHeight = framebufferSize.get(1);
        }

        /* Make the OpenGL context current */
        glfwMakeContextCurrent(handle);

        /* Enable v-sync */
        glfwSwapInterval(1);
    }

    public void run() {
        /* ensure capabilities */
        GL.createCapabilities();

        /* Make the window visible */
        glfwShowWindow(handle);

        /* OpenGL configures. */
        glViewport(0, 0, frameBufferWidth, frameBufferHeight);
        glEnable(GL11.GL_CULL_FACE);
        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        /* Run the rendering loop until the user has attempted to close
         * the window or has pressed the ESCAPE key. */
        while (!glfwWindowShouldClose(handle) ) {
            glfwPollEvents(); // Poll for window events.
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
            glfwSwapBuffers(handle); // swap the color buffers
        }

        /* Free the window callbacks and destroy the window */
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);
    }

    public void close() { glfwSetWindowShouldClose(handle, true); }

    public void rename(String name) { glfwSetWindowTitle(handle, name);}

    public void resize(int width, int height) { glfwSetWindowSize(handle, width, height); }

    public int getFrameBufferWidth() { return frameBufferWidth; }

    public int getFrameBufferHeight() { return frameBufferHeight; }

    private void onKey(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            glfwSetWindowShouldClose(window, true);
    }

    private void onFramebufferSize(long l, int i, int i1) {

    }

    private void onMouseMove(long l, double v, double v1) {
    }

    private void onMouseButton(long l, int i, int i1, int i2) {
    }

    private void onScroll(long l, double v, double v1) {
    }


    public void addListener(KeyEventListener eventListener) {
        if (!listeners.contains(eventListener)) listeners.add(eventListener);
    }

    public void removeListener(KeyEventListener eventListener) {
        if (listeners.contains(eventListener)) listeners.remove(eventListener);
    }

    private void invokeEvent(int key, int action, int mods) {
        for (final var listener : listeners) {
            listener.onKey(key, action, mods);
        }
    }
}
