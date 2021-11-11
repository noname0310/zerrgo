package enginetest;

import core.window.Window;
import core.window.event.KeyAction;
import core.window.event.KeyCode;
import core.window.event.MouseCode;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import world.hierarchical.Component;
import world.hierarchical.component.CameraComponent;
import world.hierarchical.component.characteristics.Startable;
import world.hierarchical.component.characteristics.Updatable;

public class CameraController extends Component implements Updatable, Startable {

    private final Vector3f cameraPosition = new Vector3f(0, 0, 10);
    private final Quaternionf cameraRotation = new Quaternionf();
    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean sPressed = false;
    private boolean dPressed = false;
    private boolean lShlPressed = false;
    private boolean spacePressed = false;
    private boolean mouseLeftPressed = false;
    private float lastMouseX = Float.POSITIVE_INFINITY;
    private float lastMouseY = Float.POSITIVE_INFINITY;
    private float xRotation = 0.0f;
    private float yRotation = 0.0f;
    private CameraComponent cameraComponent;
    @Override
    public void update() {
        if (wPressed) {
            cameraPosition.add(cameraComponent.getViewMatrix().positiveZ(new Vector3f()).mul(-0.1f));
            getGameObject().getTransform().setLocalPosition(cameraPosition);
        }
        if (sPressed) {
            cameraPosition.add(cameraComponent.getViewMatrix().positiveZ(new Vector3f()).mul(0.1f));
            getGameObject().getTransform().setLocalPosition(cameraPosition);
        }
        if (aPressed) {
            cameraPosition.add(cameraComponent.getViewMatrix().positiveX(new Vector3f()).mul(-0.1f));
            getGameObject().getTransform().setLocalPosition(cameraPosition);
        }
        if (dPressed) {
            cameraPosition.add(cameraComponent.getViewMatrix().positiveX(new Vector3f()).mul(0.1f));
            getGameObject().getTransform().setLocalPosition(cameraPosition);
        }
        if (lShlPressed) {
            cameraPosition.y -= 0.05;
            getGameObject().getTransform().setLocalPosition(cameraPosition);
        }
        if (spacePressed) {
            cameraPosition.y += 0.05;
            getGameObject().getTransform().setLocalPosition(cameraPosition);
        }

    }

    @Override
    public void start() {
        cameraComponent = getGameObject().getComponent(CameraComponent.class);
        Window window = getGameObject().getWorld().getWindow();
        window.getInputHandler().addOnKeyListener((keyCode, action, modifier) -> {
            if (keyCode == KeyCode.W) {
                if (action == KeyAction.PRESS) wPressed = true;
                if (action == KeyAction.RELEASE) wPressed = false;
            } else if (keyCode == KeyCode.S) {
                if (action == KeyAction.PRESS) sPressed = true;
                if (action == KeyAction.RELEASE) sPressed = false;
            } else if (keyCode == KeyCode.A) {
                if (action == KeyAction.PRESS) aPressed = true;
                if (action == KeyAction.RELEASE) aPressed = false;
            } else if (keyCode == KeyCode.D) {
                if (action == KeyAction.PRESS) dPressed = true;
                if (action == KeyAction.RELEASE) dPressed = false;
            } else if (keyCode == KeyCode.LEFT_SHIFT) {
                if (action == KeyAction.PRESS) lShlPressed = true;
                if (action == KeyAction.RELEASE) lShlPressed = false;
            } else if (keyCode == KeyCode.SPACE) {
                if (action == KeyAction.PRESS) spacePressed = true;
                if (action == KeyAction.RELEASE) spacePressed = false;
            }
        });

        window.getInputHandler().addOnMouseButtonListener((button, action, modifier) -> {
            if (button == MouseCode.BUTTON_LEFT) {
                if (action == KeyAction.PRESS) mouseLeftPressed = true;
                if (action == KeyAction.RELEASE) mouseLeftPressed = false;
            }
        });

        window.getInputHandler().addOnMouseMoveListener((x, y) -> {
            if (lastMouseX != Float.POSITIVE_INFINITY) {
                if (mouseLeftPressed) {
                    xRotation += (lastMouseX - (float) x) / 500.0f;
                    yRotation += (lastMouseY - (float) y) / 500.0f;
                    getGameObject().getTransform().setRotation(cameraRotation.identity().rotateY(xRotation).rotateX(yRotation));
                }
            }
            lastMouseX = (float) x;
            lastMouseY = (float) y;
        });
    }
}
