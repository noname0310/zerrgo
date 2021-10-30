package graphics.opengl;

import java.util.Objects;

public final class Model implements core.graphics.resource.Model {
    private final String name;
    private final Mesh[] meshes;
    private final Material[] materials;

    /**
     * create model with meshes and materials
     * @param name model name
     * @param meshes model meshes
     * @param materials One-to-one correspondence with meshes, materials length must be equal to meshes length
     */
    public Model(String name, Mesh[] meshes, Material[] materials) {
        this.name = name;
        this.meshes = meshes;
        this.materials = materials;

        if (meshes.length != materials.length) {
            throw new RuntimeException("materials count must equal to meshes count! (meshes.length: "
                    + meshes.length + "materials.length" + materials.length + ")");
        }
    }

    void render() {
        for (int i = 0; i < meshes.length; ++i) {
            materials[i].render(meshes[i]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String getName() { return name; }
}
