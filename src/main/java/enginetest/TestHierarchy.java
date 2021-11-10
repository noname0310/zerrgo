package enginetest;

import core.graphics.AssetLoader;
import org.joml.Vector3f;
import world.hierarchical.Component;
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
        GameObject player = new GameObject();
        root.appendChildren(new GameObject[] {
                GameObject.BuildWith(go -> { // player
                    go.appendComponents(
                            new Component[] {
                                    Transform.BuildWith(component -> {
                                        component.setPosition(new Vector3f(0,0,5f));
                                    }),
                                    new CameraComponent()
                            });
                }),
                new GameObject(), //item
                GameObject.BuildWith(go -> { //item2
                    go.appendComponents(
                            new Component[] {
                                    Transform.BuildWith(component -> {
                                        component.setPosition(new Vector3f(1,0,0));
                                    }),
                                    new Sprite(
                                            assetLoader.getPlaneModelFromTexture(
                                                    assetLoader.getTexture(
                                                            "src\\main\\resources\\20211104_102157-realesrgan.jpg"
                                                    )
                                            )
                                    )
                            }
                    );
                })
        });
    }

    @Override
    public GameObject getRootObject() {
        return root;
    }
}
