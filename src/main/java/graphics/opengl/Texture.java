package graphics.opengl;

import core.ZerrgoEngine;
import org.lwjgl.opengl.GL46;

import java.lang.ref.Cleaner;
import java.nio.ByteBuffer;

public final class Texture implements core.graphics.resource.Texture {
    private static final Cleaner CLEANER = Cleaner.create();

    private final String name;
    private final int id;
    private final int width;
    private final int height;

    private record CleanerRunnable(AssetDisposer assetDisposer, int id) implements Runnable {
        @Override
        public void run() {
            assetDisposer.addDisposeDelegate(() -> {
                ZerrgoEngine.Logger().info("disposing texture (id: " + id + ")");
                GL46.glDeleteTextures(id);
            });
        }
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


        ZerrgoEngine.Logger().info(
                "Creating texture of size " + width +
                        "x" + height + " with " + data.limit() + " bytes.");
        // create texture
        id = GL46.glGenTextures();

        // bind texture
        bind();
        setParameter(GL46.GL_TEXTURE_WRAP_S, GL46.GL_REPEAT);
        setParameter(GL46.GL_TEXTURE_WRAP_T, GL46.GL_REPEAT);
        //setParameter(GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_LINEAR_MIPMAP_LINEAR);
        //setParameter(GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_LINEAR);
        setParameter(GL46.GL_TEXTURE_MIN_FILTER, GL46.GL_NEAREST);
        setParameter(GL46.GL_TEXTURE_MAG_FILTER, GL46.GL_NEAREST);
        for (var item : parameters) setParameter(item.name(), item.value());

        // put image data.
        GL46.glTexImage2D(GL46.GL_TEXTURE_2D, 0,GL46.GL_RGBA8, width,
                height, 0, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, data);
        GL46.glGenerateMipmap(GL46.GL_TEXTURE_2D);

        // unbind
        unbind();

        var cleanerRunnable = new CleanerRunnable(assetDisposer, id);
        CLEANER.register(this, cleanerRunnable);
    }

    @Override
    public String getName() { return name; }

    void setParameter(int name, int value) {
        GL46.glTexParameteri(GL46.GL_TEXTURE_2D, name, value);
    }

    void bind(){
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, this.id);
    }

    void unbind(){
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, 0);
    }
}
