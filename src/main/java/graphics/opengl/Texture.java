package graphics.opengl;

import org.lwjgl.opengl.GL11;

import java.lang.ref.Cleaner;
import java.nio.ByteBuffer;

public final class Texture {
    private static final Cleaner CLEANER = Cleaner.create();

    private final String name;
    private final int id;
    private final int width;
    private final int height;

    private record CleanerRunnable(int id) implements Runnable {
        @Override
        public void run() { GL11.glDeleteTextures(id); } //@todo make global dispose queue
    }

    public record TextureParameter(int name, int value) { }

    Texture(String name, int width, int height, ByteBuffer data, TextureParameter...parameters) {
        this.name = name;
        this.width = width;
        this.height = height;

        // create texture
        id = GL11.glGenTextures();

        // bind texture
        bind();
        setParameter(GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
        setParameter(GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        setParameter(GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        setParameter(GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        for (var item : parameters) setParameter(item.name(), item.value());

        // put image data.
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,GL11.GL_RGBA8, width,
                height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);

        // unbind
        unbind();

        var cleanerRunnable = new CleanerRunnable(id);
        CLEANER.register(this, cleanerRunnable);
    }

    void setParameter(int name, int value) {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, name, value);
    }

    void bind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
    }

    void unbind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public String getName() { return name; }
}