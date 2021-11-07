package graphics.opengl;

import core.graphics.record.Material;

import java.util.Arrays;
import java.util.Objects;

public final class Model implements core.graphics.resource.Model {
    private final String name;
    private final core.graphics.resource.Mesh[] meshes;
    private final Material[] materials;

    /**
     * create model with meshes and materials
     * @param name model name
     * @param meshes model meshes
     * @param materials One-to-one correspondence with meshes, materials length must be equal to meshes length
     */
    Model(String name, core.graphics.resource.Mesh[] meshes, Material[] materials) {
        this.name = name;
        this.meshes = meshes;
        this.materials = materials;

        if (meshes.length != materials.length) {
            throw new RuntimeException("materials count must equal to meshes count! (meshes.length: "
                    + meshes.length + "materials.length" + materials.length + ")");
        }
    }

    Model(String name, Mesh mesh, Material material) {
        this.name = name;
        this.meshes = new Mesh[] { mesh };
        this.materials = new Material[] { material };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return name.equals(model.name)
                && Arrays.equals(meshes, model.meshes)
                && Arrays.equals(materials, model.materials);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(meshes);
        result = 31 * result + Arrays.hashCode(materials);
        return result;
    }

    @Override
    public String getName() { return name; }

    core.graphics.resource.Mesh[] getMeshes() { return meshes; }

    Material[] getMaterials() { return materials; }
}
