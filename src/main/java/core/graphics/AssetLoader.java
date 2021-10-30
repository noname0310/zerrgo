package core.graphics;

import core.graphics.resource.Mesh;
import core.graphics.resource.Texture;
import core.graphics.resource.VertexContainer;

import java.util.Optional;

public interface AssetLoader {
    Texture getTexture(String path);

    Mesh addMesh(String name, VertexContainer vertexContainer, int[] indices);

    Optional<Mesh> getMesh(String name);

    Mesh getPlaneMesh();
}
