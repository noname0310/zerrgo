package core.window.event;

import org.lwjgl.glfw.GLFW;

public class KeyModifier {
    private KeyModifier() { }

    public static final int SHIFT = 1;
    public static final int CONTROL = 2;
    public static final int ALT = 4;
    public static final int SUPER = 8;
    public static final int CAPS_LOCK = 16;
    public static final int NUM_LOCK = 32;
}
