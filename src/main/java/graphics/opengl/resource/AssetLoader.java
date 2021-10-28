package graphics.opengl.resource;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.WeakHashMap;

public final class AssetLoader {
    private static final Map<String, Texture> TEXTURES = new WeakHashMap<>();

    private AssetLoader() { }

    public static Texture getTexture(String path) {
        var texture = TEXTURES.get(path);
        if (texture != null) return texture;

        // create buffers to store data.
        ByteBuffer image;
        IntBuffer width = MemoryUtil.memAllocInt(1);
        IntBuffer height = MemoryUtil.memAllocInt(1);
        IntBuffer comp = MemoryUtil.memAllocInt(1);

        // load data from path.
        // width, height info is stored in length 1 buffer.
        image = STBImage.stbi_load(path, width, height, comp, 4);

        // failed to create image -> error
        if (image == null) {
            throw new RuntimeException("Failed to load a texture file!"
                    + System.lineSeparator() + STBImage.stbi_failure_reason());
        }
        return TEXTURES.put(path, new Texture(path, width.get(0), height.get(0), image));
    }
}
