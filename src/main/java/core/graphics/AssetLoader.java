package core.graphics;

import core.graphics.record.Material;
import core.graphics.resource.Mesh;
import core.graphics.resource.Model;
import core.graphics.resource.Shader;
import core.graphics.resource.Texture;
import core.graphics.record.VertexContainer;

import java.util.Optional;

public interface AssetLoader {
    Texture getTexture(String path);

    Mesh addMesh(String name, VertexContainer vertexContainer, int[] indices);

    Optional<Mesh> getMesh(String name);

    Mesh getPlaneMesh();

    Shader getShader(String vertexShaderPath, String fragmentShaderPath);

    Model addModel(String name, Mesh[] meshes, Material[] materials);

    Model addModel(String name, Mesh mesh, Material material);

    Optional<Model> getModel(String name);

    Model getPlaneModelFromTexture(Texture texture);
}
