package world.hierarchical;

import core.graphics.AssetLoader;

public interface HierarchicalScene {
    void build(AssetLoader assetLoader);
    GameObject getRootObject();
}
