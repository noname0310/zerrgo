package window;

import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

public final class Window {
    /**
     * event handle
     */
    private List<WindowEventListener> listeners = new ArrayList<>();

    /**
     * window handle
     */
    private long handle;

    /**
     * frame buffer size
     */
    private int frameBufferWidth;
    private int frameBufferHeight;

    /**
     * create glfw window
     * @param width window width
     * @param height  window height
     * @param title window title
     */
    public Window(int width, int height, String title) {
        /* init glfw */
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

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
    }

    public void rename(String name) { glfwSetWindowTitle(handle, name);}

    public void resize(int width, int height) { glfwSetWindowSize(handle, width, height); }

    public int getFrameBufferWidth() { return frameBufferWidth; }

    public int getFrameBufferHeight() { return frameBufferHeight; }

    /**
     *
     * @param window window handle
     * @param key key code
     * @param scancode unique for every key, regardless of whether it has a key token
     * @param action GLFW_PRESS, GLFW_REPEAT or GLFW_RELEASE. it can be GLFW_KEY_UNKNOWN if GLFW lacks a key token
     * @param mods modifier key flags e.g Shift, Control, Alt ... it defined like GLFW_MOD_SHIFT
     */
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
}
