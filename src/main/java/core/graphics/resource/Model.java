package core.graphics.resource;

import core.graphics.record.Material;

public interface Model {
    String getName();

    Material getMaterialAt(int i);

    void setMaterialAt(int i, Material material);

    Material[] getMaterials();

    void setMaterials(Material[] materials);
}
