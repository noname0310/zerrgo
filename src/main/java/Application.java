import org.lwjgl.glfw.GLFWErrorCallback;
import window.Window;

import static org.lwjgl.glfw.GLFW.*;

public final class Application {
    private Application() { }

    /**
     * Begins running a standard application message loop on the current thread, and makes the specified window visible.
     * @param window that represents the window to make visible.
     */
    public static void run(Window window) {
        /* Setup an error callback. The default implementation
         * will print the error message in System.err. */
        GLFWErrorCallback.createPrint(System.err).set();
        /* init glfw */
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
    }

    public static void free() {
        /* Terminate GLFW and free the error callback */
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
