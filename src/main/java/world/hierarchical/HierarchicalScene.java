package world.hierarchical;

import core.graphics.AssetLoader;

public interface HierarchicalScene {
    GameObject.GameObjectBuilder create(AssetLoader assetLoader);
}
