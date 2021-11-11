package enginetest;

import core.graphics.AssetLoader;
import core.graphics.resource.Texture;
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
                        .component(CameraComponent.class)
                        .component(CameraController.class))


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
                )
                .child(GameObject.CreateWith("yungi")
                        .component(Transform.class)
                        .component(PlayerController.class)
                        .component(Sprite.class, sprite -> sprite.setTexture(
                                assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi0.png")
                        ))
                        .component(SpriteAnimator.class, animator -> {
                            animator.addAnimationClip(
                                    "down_move",
                                    new Texture[] {
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi0.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi1.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi2.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi3.png"),
                                    });
                            animator.addAnimationClip(
                                    "down_idle",
                                    new Texture[] {
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi0.png")
                                    });
                            animator.addAnimationClip(
                                    "right_move",
                                    new Texture[] {
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi4.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi5.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi6.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi7.png"),
                                    });
                            animator.addAnimationClip(
                                    "right_idle",
                                    new Texture[] {
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi4.png"),
                                    });
                            animator.addAnimationClip(
                                    "up_move",
                                    new Texture[] {
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi8.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi9.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi10.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi11.png"),
                                    });
                            animator.addAnimationClip(
                                    "up_idle",
                                    new Texture[] {
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi8.png"),
                                    });
                            animator.addAnimationClip(
                                    "left_move",
                                    new Texture[] {
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi12.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi13.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi14.png"),
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi15.png"),
                                    });
                            animator.addAnimationClip(
                                    "left_idle",
                                    new Texture[] {
                                            assetLoader.getTexture("src\\main\\resources\\yungi\\Yungi12.png"),
                                    });
                        })
                );
    }
}
