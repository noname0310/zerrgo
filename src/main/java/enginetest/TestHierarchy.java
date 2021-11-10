package enginetest;

import core.graphics.AssetLoader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import world.hierarchical.GameObject;
import world.hierarchical.HierarchicalScene;
import world.hierarchical.component.CameraComponent;
import world.hierarchical.component.Sprite;
import world.hierarchical.component.Transform;

public final class TestHierarchy implements HierarchicalScene {
    private GameObject root;

    @Override
    public void build(AssetLoader assetLoader){
        root = new GameObject();
        root.appendChildren(new GameObject[] {
                GameObject.BuildWith(go -> { // player
                    go.appendComponentWith(Transform.class, (component -> {
                        ((Transform)component).setPosition(new Vector3f(0,0,5f));
                    }));
                    go.appendComponent(CameraComponent.class);
                }),
                new GameObject(), //item
                GameObject.BuildWith(go -> { //item2
                    go.appendComponentWith(Transform.class, component -> {
                        ((Transform)component).setPosition(new Vector3f(1,0,0));
                    });
                    go.appendComponentWith(Sprite.class, component -> {
                        ((Sprite) component).setTexture(
                                assetLoader.getPlaneModelFromTexture(
                                        assetLoader.getTexture(
                                                "src\\main\\resources\\20211104_102157-realesrgan.jpg"
                                        )
                                ));
                    });
                })
        });
    }

    @Override
    public GameObject getRootObject() { return root; }
}
