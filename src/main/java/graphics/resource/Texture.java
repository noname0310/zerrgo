package graphics.resource;

import org.lwjgl.opengl.GL11;

import java.lang.ref.Cleaner;
import java.nio.ByteBuffer;

public final class Texture {
    private static final Cleaner CLEANER = Cleaner.create();

    private final String name;
    private final int id;
    private final int width;
    private final int height;

    Texture(String name, int width, int height, ByteBuffer data) {
        this.name = name;
        this.width = width;
        this.height = height;

        // create texture
        id = GL11.glGenTextures();

        // bind texture
        this.bind();
        this.setParameter(GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
        this.setParameter(GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        this.setParameter(GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        this.setParameter(GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        // put image data.
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,GL11.GL_RGBA8, width,
                height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);

        // unbind + free data.
        this.unbind();

        CLEANER.register(this, () -> GL11.glDeleteTextures(id));
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
