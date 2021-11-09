package enginetest;

import world.hierarchical.GameObject;
import world.hierarchical.component.CameraComponent;
import world.hierarchical.component.Transform;

public final class TestHierarchy {
    private TestHierarchy() { }

    public static GameObject build(){
        GameObject root = new GameObject();
        GameObject player = new GameObject();
        root.appendChild(player);
        player.appendComponent(new Transform());
        player.appendComponent(new CameraComponent());
        GameObject item = new GameObject();
        player.appendChild(item);
        GameObject item2 = new GameObject();
        root.appendChild(item2);
        return root;
    }
}
