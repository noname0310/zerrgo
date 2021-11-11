package enginetest;

import core.graphics.AssetLoader;
import core.graphics.record.Material;
import core.graphics.record.VertexContainer;
import core.graphics.resource.Mesh;
import core.graphics.resource.Model;
import core.graphics.resource.Texture;
import org.joml.Vector4f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ObjLoader {
    public static record ObjContainer(String name, VertexContainer vertexContainer, int[] indices) { }

    public static Model getModelFromObj(AssetLoader assetLoader, String path, Texture... textures) {
        var objContainer = getMeshFromObj(path);

        var meshes = new ArrayList<Mesh>();

        assert objContainer != null;
        for (var c : objContainer) {
            meshes.add(assetLoader.addMesh(path + c.name, c.vertexContainer, c.indices));
        }

        var meshesArray = new Mesh[meshes.size()];
        meshes.toArray(meshesArray);

        var materials = new Material[textures.length];
        var shader = assetLoader.getShader(
                "src\\main\\resources\\shader\\standardTexture2d_vertex.glsl",
                "src\\main\\resources\\shader\\standardTexture2d_fragment.glsl"
        );
        var whiteColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

        for (int i = 0; i < materials.length; ++i) {
            materials[i] = new Material(
                    textures[i].getName() + "_material",
                    textures[i],
                    whiteColor,
                    shader
            );
        }

        return assetLoader.addModel(path, meshesArray, materials);
    }

    public static List<ObjContainer> getMeshFromObj(String path) {
        var p = Path.of(path);
        String content;
        try {
            content = Files.readString(p);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        var contents = content.split("\\r?\\n");

        var objContainerList = new ArrayList<ObjContainer>();
        String name = null;
        var vertices = new ArrayList<Float>();
        var normals = new ArrayList<Float>();
        var uvs = new ArrayList<Float>();
        var indices = new ArrayList<Integer>();

        int startIndex = 0;
        for (int i = 0; i < contents.length; ++i) {
            if (contents[i].charAt(0) == 'o') {
                name = contents[i].split(" ")[1];
                startIndex = i + 1;
                break;
            }
        }

        for (int i = startIndex; i < contents.length; ++i) {
            var item = contents[i];

            switch (item.charAt(0)) {
                case 'o' -> {
                    objContainerList.add(makeContainer(name, vertices, normals, uvs, indices));
                    name = contents[i].split(" ")[1];
                    vertices.clear();
                    normals.clear();
                    uvs.clear();
                    indices.clear();
                }
                case 'v' -> {
                    if (item.charAt(1) == 't') {
                        var uvInfo = item.split(" ");
                        uvs.add(Float.parseFloat(uvInfo[1]));
                        uvs.add(Float.parseFloat(uvInfo[2]));
                    } else if (item.charAt(1) == 'n') {
                        var normalInfo = item.split(" ");
                        normals.add(Float.parseFloat(normalInfo[1]));
                        normals.add(Float.parseFloat(normalInfo[2]));
                        normals.add(Float.parseFloat(normalInfo[3]));
                    } else {
                        var vertexInfo = item.split(" ");
                        vertices.add(Float.parseFloat(vertexInfo[1]));
                        vertices.add(Float.parseFloat(vertexInfo[2]));
                        vertices.add(Float.parseFloat(vertexInfo[3]));
                    }
                }
                case 'f' -> {
                    var indexInfo = item.split(" ");
                    var index = Arrays.stream(indexInfo[1].split("/"))
                            .mapToInt(Integer::parseInt).toArray();
                    indices.add(index[0]);
                    indices.add(index[1]);
                    indices.add(index[2]);
                    var index2 = Arrays.stream(indexInfo[2].split("/"))
                            .mapToInt(Integer::parseInt).toArray();
                    indices.add(index2[0]);
                    indices.add(index2[1]);
                    indices.add(index2[2]);
                    var index3 = Arrays.stream(indexInfo[3].split("/"))
                            .mapToInt(Integer::parseInt).toArray();
                    indices.add(index3[0]);
                    indices.add(index3[1]);
                    indices.add(index3[2]);
                }
            }
        }
        objContainerList.add(makeContainer(name, vertices, normals, uvs, indices));
        return objContainerList;
    }

    private static ObjContainer makeContainer(
            String name,
            ArrayList<Float> vertices,
            ArrayList<Float> normals,
            ArrayList<Float> uvs,
            ArrayList<Integer> indices
    ) {
        var vertexArray = new float[vertices.size()];
        var normalArray = new float[vertices.size()];
        var uvArray = new float[uvs.size()];

        for (int j = 0; j < vertices.size(); ++j) vertexArray[j] = vertices.get(j);
        for (int j = 0; j < normals.size(); ++j) normalArray[j] = normals.get(j);
        for (int j = 0; j < uvs.size(); ++j) uvArray[j] = uvs.get(j);

        return new ObjContainer(
                name,
                new VertexContainer(vertexArray, normalArray, uvArray),
                indices.stream().mapToInt(j -> j).toArray()
        );
    }
}
