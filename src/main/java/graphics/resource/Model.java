package graphics.resource;

public final class Model {
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
                    + meshes.length + "matrials.length" + materials.length + ")");
        }
    }

    void render() {
        for (int i = 0; i < meshes.length; ++i) {
            materials[i].render(meshes[i]);
        }
    }

    public String getName() { return name; }
}
