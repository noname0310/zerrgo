package enginetest;

import core.graphics.resource.Texture;
import world.Time;
import world.hierarchical.Component;
import world.hierarchical.component.Sprite;
import world.hierarchical.component.characteristics.Startable;
import world.hierarchical.component.characteristics.Updatable;

import java.util.HashMap;
import java.util.Map;

public class SpriteAnimator extends Component implements Startable, Updatable {
    private Map<String, Texture[]> animations = new HashMap<>();
    private Texture[] currentAnimation;

    private Sprite sprite;
    private float animationDelay = 0.1f;
    private float currentDelay = 0.0f;
    private int currentFrame = 0;

    @Override
    public void start() {
        sprite = getGameObject().getComponent(Sprite.class);
    }

    @Override
    public void update() {
        if (currentAnimation == null) return;
        sprite.setTexture(currentAnimation[currentFrame]);
        currentDelay += Time.getDeltaTime();
        if (animationDelay < currentDelay) {
            currentDelay = 0.0f;
            currentFrame += 1;
            currentFrame %= currentAnimation.length;
        }
    }

    public void playAnimation(String clipName) {
        currentAnimation = animations.get(clipName);
        currentFrame = 0;
    }

    public void addAnimationClip(String clipName, Texture[] frames) {
        animations.put(clipName, frames);
    }

    public float getAnimationDelay() {
        return animationDelay;
    }

    public void setAnimationDelay(float animationDelay) {
        this.animationDelay = animationDelay;
    }
}
