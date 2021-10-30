package graphics.opengl;

import core.ZerrgoEngine;
import org.lwjgl.opengl.GL11;

import java.lang.ref.Cleaner;
import java.nio.ByteBuffer;
import java.util.Objects;

public final class Texture implements core.graphics.resource.Texture {
    private static final Cleaner CLEANER = Cleaner.create();

    private final String name;
    private final int id;
    private final int width;
    private final int height;

    private record CleanerRunnable(AssetDisposer assetDisposer, int id) implements Runnable {
        @Override
        public void run() { assetDisposer.addDisposeDelegate(() -> {
            ZerrgoEngine.Logger().info("disposing texture (id: " + id + ")");
            GL11.glDeleteTextures(id);
        }); }
    }

    public record TextureParameter(int name, int value) { }

    Texture(AssetDisposer assetDisposer,
            String name,
            int width,
            int height,
            ByteBuffer data,
            TextureParameter...parameters
    ) {
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

        var cleanerRunnable = new CleanerRunnable(assetDisposer, id);
        CLEANER.register(this, cleanerRunnable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Texture texture = (Texture) o;
        return Objects.equals(name, texture.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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

    @Override
    public String getName() { return name; }
}
