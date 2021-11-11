package enginetest;

import core.graphics.AssetLoader;
import org.joml.Vector3f;
import world.hierarchical.GameObject;
import world.hierarchical.HierarchicalScene;
import world.hierarchical.component.CameraComponent;
import world.hierarchical.component.Sprite;
import world.hierarchical.component.Transform;

public final class TestHierarchy implements HierarchicalScene {
    @Override
    public GameObject.GameObjectBuilder create(AssetLoader assetLoader) {
        return GameObject.CreateWith("root")

                .child(GameObject.CreateWith("player")
                        .component(Transform.class,
                                transform -> transform.setPosition(new Vector3f(0, 0, 5f)))
                        .component(CameraComponent.class))

                .child(GameObject.CreateWith("item")
                        .component(Transform.class,
                                transform -> transform.setPosition(new Vector3f(-1, 0, 0))))

                .child(GameObject.CreateWith("item2")
                        .component(Transform.class,
                                transform -> transform.setPosition(new Vector3f(1, 0, 0)))
                        .component(Sprite.class,
                                sprite -> sprite.setTexture(
                                        assetLoader.getTexture(
                                                "src\\main\\resources\\20211104_102157-realesrgan.jpg"))
                        )
                        .component(TestGameObjectRotator.class)
                        .child(GameObject.CreateWith("childItem1")
                                .component(Transform.class,
                                        transform -> transform.setLocalPosition(new Vector3f(1, 0, 0)))
                                .component(Sprite.class,
                                        sprite -> sprite.setTexture(
                                                assetLoader.getTexture(
                                                        "src\\main\\resources\\20211104_102157-realesrgan.jpg"))
                                )
                                .component(TestGameObjectRotator.class)
                                .child(GameObject.CreateWith("childItem2")
                                        .component(Transform.class,
                                                transform -> transform.setLocalPosition(new Vector3f(1, 0, 0)))
                                        .component(Sprite.class,
                                                sprite -> sprite.setTexture(
                                                        assetLoader.getTexture(
                                                                "src\\main\\resources\\20211104_102157-realesrgan.jpg"))
                                        )
                                        .component(TestGameObjectRotator.class)
                                .component(TestGameObjectRotator.class)
                                //.component(TestParentReplacer.class)
                                )
                        )
                );
    }
}
