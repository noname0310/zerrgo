package graphics.opengl;

import core.graphics.resource.VertexContainer;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class AssetLoader implements core.graphics.AssetLoader {
    private static class WeakValueReference<K, V> extends WeakReference<V>
    {
        private final K key;

        public WeakValueReference(K key, V value, ReferenceQueue<V> referenceQueue)
        {
            super(value, referenceQueue);
            this.key = key;
        }
    }

    private static record ShaderKey(String vertexShaderPath, String fragmentShaderPath) { }

    private final Map<String, WeakValueReference<String, Texture>> textures = new HashMap<>();
    private final ReferenceQueue<Texture> texturesRefQueue = new ReferenceQueue<>();

    private final Map<String, WeakValueReference<String, Mesh>> meshes = new HashMap<>();
    private final ReferenceQueue<Mesh> meshesRefQueue = new ReferenceQueue<>();

    private final Map<ShaderKey, WeakValueReference<ShaderKey, Shader>> shaders = new HashMap<>();
    private final ReferenceQueue<Shader> shadersRefQueue = new ReferenceQueue<>();

    private final AssetDisposer assetDisposer;

    AssetLoader(AssetDisposer assetDisposer) {
        this.assetDisposer = assetDisposer;
    }

    @Override
    public core.graphics.resource.Texture getTexture(String path) {
        cleanMap(texturesRefQueue, textures);
        var texture = textures.get(path);
        if (texture != null && texture.get() != null) return texture.get();

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

        var newTexture = new Texture(assetDisposer, path, width.get(0), height.get(0), image);
        textures.put(
                path,
                new WeakValueReference<>(path, newTexture, texturesRefQueue)
        );
        return newTexture;
    }

    @Override
    public core.graphics.resource.Mesh addMesh(String name, VertexContainer vertexContainer, int[] indices) {
        cleanMap(meshesRefQueue, meshes);
        var mesh = meshes.get(name);
        if (mesh != null && mesh.get() != null) return mesh.get();
        var newMesh = new Mesh(assetDisposer, name, vertexContainer, indices);
        meshes.put(name, new WeakValueReference<>(name, newMesh, meshesRefQueue));
        return newMesh;
    }

    @Override
    public Optional<core.graphics.resource.Mesh> getMesh(String name) {
        cleanMap(meshesRefQueue, meshes);
        var mesh = meshes.get(name);
        if (mesh != null) return Optional.ofNullable(mesh.get());
        else return Optional.empty();
    }

    @Override
    public core.graphics.resource.Mesh getPlaneMesh() {
        return getMesh("engine_default_plane")
                .orElseGet(() -> addMesh(
                        "engine_default_plane",
                        new VertexContainer(
                                new float[]{ //positions
                                        -0.5f, 0.5f, 0.0f,
                                        0.5f, 0.5f, 0.0f,
                                        -0.5f, -0.5f, 0.0f,
                                        0.5f, -0.5f, 0.0f
                                },
                                new float[]{ //normals
                                        0.0f, 0.0f, -1.0f,
                                        0.0f, 0.0f, -1.0f,
                                        0.0f, 0.0f, -1.0f,
                                        0.0f, 0.0f, -1.0f,
                                },
                                new float[]{ //uvs
                                        0.0f, 0.0f,
                                        1.0f, 0.0f,
                                        0.0f, 1.0f,
                                        1.0f, 1.0f
                                }
                        ),
                        new int[]{ //indices
                                1, 2, 3,
                                2, 4, 3
                        }));
    }

    @Override
    public core.graphics.resource.Shader getShader(String vertexShaderPath, String fragmentShaderPath) {
        cleanMap(shadersRefQueue, shaders);
        var key = new ShaderKey(vertexShaderPath, fragmentShaderPath);
        var shader = shaders.get(key);
        if (shader != null && shader.get() != null) return shader.get();
        var newShader = new Shader(assetDisposer, vertexShaderPath, fragmentShaderPath);
        shaders.put(key, new WeakValueReference<>(key, newShader, shadersRefQueue));
        return newShader;
    }

    /**
     * item in referenceQueue must instance of WeakValueReference<K, V>
     */
    @SuppressWarnings("unchecked")
    private <K, V> void cleanMap(ReferenceQueue<V> referenceQueue, Map<K, WeakValueReference<K, V>> map) {
        WeakValueReference<K, V> weakValueReference;
        while ((weakValueReference = (WeakValueReference<K, V>) referenceQueue.poll()) != null) {
            if (weakValueReference == map.get(weakValueReference.key)) {
                map.remove(weakValueReference.key);
            }
        }
    }
}
