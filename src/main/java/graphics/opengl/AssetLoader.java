package graphics.opengl;

import core.graphics.resource.VertexContainer;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public final class AssetLoader implements core.graphics.AssetLoader {
    private final Map<String, Texture> textures = new WeakHashMap<>();
    private final Map<String, Mesh> meshes = new WeakHashMap<>();
    private final AssetDisposer assetDisposer;

    AssetLoader(AssetDisposer assetDisposer) {
        this.assetDisposer = assetDisposer;
    }

    public core.graphics.resource.Texture getTexture(String path) {
        var texture = textures.get(path);
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
        return textures.put(path, new Texture(assetDisposer, path, width.get(0), height.get(0), image));
    }

    public core.graphics.resource.Mesh addMesh(String name, VertexContainer vertexContainer, int[] indices) {
        var mesh = meshes.get(name);
        if (mesh != null) return mesh;
        return meshes.put(name, new Mesh(assetDisposer, name, vertexContainer, indices));
    }

    public Optional<core.graphics.resource.Mesh> getMesh(String name) {
        return Optional.ofNullable(meshes.get(name));
    }
}
