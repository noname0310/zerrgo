package enginetest;

import core.ZerrgoEngine;
import core.window.event.KeyAction;
import core.window.event.KeyCode;
import org.joml.Vector3f;
import world.hierarchical.Component;
import world.hierarchical.component.characteristics.Startable;
import world.hierarchical.component.characteristics.Updatable;

public class PlayerController extends Component implements Startable, Updatable {
    private boolean upPressed;
    private boolean downPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private SpriteAnimator spriteAnimator;
    private int state;
    private static final int UP_MOVE = 510;
    private static final int UP_IDLE = 243;
    private static final int DOWN_MOVE = 374;
    private static final int DOWN_IDLE = 552;
    private static final int RIGHT_MOVE = 284;
    private static final int LEFT_MOVE = 135;

    @Override
    public void start() {
        getGameObject().getWorld().getWindow().getInputHandler().addOnKeyListener(this::OnKey);
        spriteAnimator = getGameObject().getComponent(SpriteAnimator.class);
    }

    public void OnKey(int key, int action, int modifier) {
        if (key == KeyCode.UP) {
            if (action == KeyAction.PRESS) {
                upPressed = true;
                spriteAnimator.playAnimation("up_move");
            }
            else if (action == KeyAction.RELEASE) {
                upPressed = false;
                spriteAnimator.playAnimation("up_idle");
            }
        }
        if (key == KeyCode.DOWN) {
            if (action == KeyAction.PRESS) {
                downPressed = true;
                spriteAnimator.playAnimation("down_move");
            }
            else if (action == KeyAction.RELEASE) {
                downPressed = false;
                spriteAnimator.playAnimation("down_idle");
            }
        }
        if (key == KeyCode.RIGHT) {
            if (action == KeyAction.PRESS) {
                rightPressed = true;
                spriteAnimator.playAnimation("right_move");
            }
            else if (action == KeyAction.RELEASE) {
                rightPressed = false;
                spriteAnimator.playAnimation("right_idle");
            }
        }
        if (key == KeyCode.LEFT) {
            if (action == KeyAction.PRESS) {
                leftPressed = true;
                spriteAnimator.playAnimation("left_move");
            }
            else if (action == KeyAction.RELEASE) {
                leftPressed = false;
                spriteAnimator.playAnimation("left_idle");
            }
        }
    }

    private Vector3f tempVector = new Vector3f();

    @Override
    public void update() {
        float x = 0;
        float y = 0;
        if (upPressed) y += 0.1f;
        if (downPressed) y -= 0.1f;
        if (rightPressed) x += 0.1f;
        if (leftPressed) x -= 0.1f;

        tempVector.set(getGameObject().getTransform().getPosition()).add(x, y, 0);
        getGameObject().getTransform().setPosition(tempVector);
    }
}
