package world.opengltest;

import core.ZerrgoEngine;
import core.graphics.AssetLoader;
import core.graphics.RenderScheduler;
import core.graphics.record.Camera;
import core.graphics.record.PerspectiveCamera;
import core.window.Window;
import core.window.event.KeyAction;
import core.window.event.KeyCode;
import core.window.event.MouseCode;
import core.world.WorldContainer;
import enginetest.ObjLoader;
import org.joml.*;

import java.lang.Math;

public final class OpenglTestWorld implements WorldContainer {
    private RenderScheduler renderScheduler;

    private final Vector3f takahiroPosition = new Vector3f();
    private final Quaternionf takahiroRotation = new Quaternionf();
    private final Matrix4f takahiroTransformMatrix = new Matrix4f();

    private Matrix4f pikaTransformMatrix = new Matrix4f().translate(2, 0, 0);

    private final Matrix4f pikaParentTransformMatrix = new Matrix4f();

    private final Matrix4f pikaParent2TransformMatrix = new Matrix4f().translate(2, 0, 0);

    private Matrix4f pikaCurrentParentTransformMatrix = pikaParentTransformMatrix;

    private final Matrix4f pikaWorldTransformMatrix = new Matrix4f();
    private float a = 0;
    private int frameCount = 0;

    private Camera camera;
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

    private static final int gridCount = 10;
    private final Vector3fc[] gridX1 = new Vector3fc[gridCount + 1];
    private final Vector3fc[] gridX2 = new Vector3fc[gridCount + 1];
    private final Vector3fc[] gridY1 = new Vector3fc[gridCount + 1];
    private final Vector3fc[] gridY2 = new Vector3fc[gridCount + 1];
    private final Vector3fc gridColor = new Vector3f(1.0f, 1.0f, 1.0f);

    @Override
    public void initialize(Window window, RenderScheduler renderScheduler, AssetLoader assetLoader) {
        this.renderScheduler = renderScheduler;

        camera = new PerspectiveCamera(
                (float) Math.toRadians(60),
                window.getFrameBufferWidth() / (float) window.getFrameBufferHeight(),
                0.2f,
                5000.0f,
                cameraPosition,
                cameraRotation,
                new Vector4f(0.5f, 0.5f, 0.5f, 1.0f)
        );
        renderScheduler.setCamera(camera);

        var model = assetLoader.getPlaneModelFromTexture(
                assetLoader.getTexture("src\\main\\resources\\20211104_102157-realesrgan.jpg"));
        var model2 = assetLoader.getPlaneModelFromTexture(
                assetLoader.getTexture("src\\main\\resources\\하이라이트없음_배경흰색.png"));

        var texture3 = assetLoader.getTexture("src\\main\\resources\\profile-realesrgan-realesrgan.png");
        var textureRatio = (float) texture3.getHeight() / texture3.getWidth();
        var model3 = assetLoader.getPlaneModelFromTexture(texture3);

        renderScheduler.addInstance(1, model, takahiroTransformMatrix);
        renderScheduler.addInstance(2, model2, pikaTransformMatrix);
        renderScheduler.addInstance(3, model3, new Matrix4f().scale(1.0f, textureRatio, 1.0f));


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
                    camera.setRotation(cameraRotation.identity().rotateY(xRotation).rotateX(yRotation));
                }
            }
            lastMouseX = (float) x;
            lastMouseY = (float) y;
        });

        for (int i = 0; i < gridCount + 1; ++i) {
            gridX1[i] = new Vector3f(-(gridCount / 2.0f * 0.5f), (i - (gridCount / 2.0f)) * 0.5f, 1.0f);
            gridX2[i] = new Vector3f(gridCount / 2.0f * 0.5f, (i - (gridCount / 2.0f)) * 0.5f, 1.0f);
            gridY1[i] = new Vector3f((i - (gridCount / 2.0f)) * 0.5f, -(gridCount / 2.0f * 0.5f), 1.0f);
            gridY2[i] = new Vector3f((i - (gridCount / 2.0f)) * 0.5f, gridCount / 2.0f * 0.5f, 1.0f);
        }
    }

    @Override
    public void update() {
        a += 0.05f;
        frameCount += 1;

        takahiroRotation.rotateZ((float) Math.toRadians(1f));
        takahiroPosition.set(Math.cos(a), Math.sin(a), -1.5f);
        renderScheduler.updateTransform(1,
                takahiroTransformMatrix.identity()
                        .translate(takahiroPosition)
                        .rotate(takahiroRotation)
        );


        if (frameCount % 120 == 0) {
            Matrix4f prevParent;
            if (pikaCurrentParentTransformMatrix == pikaParentTransformMatrix) {
                pikaCurrentParentTransformMatrix = pikaParent2TransformMatrix;
                prevParent = pikaParentTransformMatrix;
            } else {
                pikaCurrentParentTransformMatrix = pikaParentTransformMatrix;
                prevParent = pikaParent2TransformMatrix;
            }

            var prevWorldTransform = new Matrix4f();

            Quaternionf quaternionf = new Quaternionf();
            Vector3f scale = new Vector3f();
            Vector3f position = new Vector3f();

            prevWorldTransform.getNormalizedRotation(quaternionf);
            prevWorldTransform.getScale(scale);
            prevWorldTransform.getRow(3, position);

            prevParent.mul(pikaTransformMatrix, prevWorldTransform);

            var invertMatrix = new Matrix4f();
            pikaCurrentParentTransformMatrix.invert(invertMatrix);

            pikaTransformMatrix = invertMatrix.mul(prevWorldTransform);
        }

        pikaCurrentParentTransformMatrix.rotateZ((float) Math.toRadians(1f));

        pikaCurrentParentTransformMatrix.mul(pikaTransformMatrix, pikaWorldTransformMatrix);

        renderScheduler.updateTransform(2, pikaWorldTransformMatrix);

        if (wPressed) {
            cameraPosition.add(camera.getViewMatrix().positiveZ(new Vector3f()).mul(-0.1f));
            camera.setPosition(cameraPosition);
        }
        if (sPressed) {
            cameraPosition.add(camera.getViewMatrix().positiveZ(new Vector3f()).mul(0.1f));
            camera.setPosition(cameraPosition);
        }
        if (aPressed) {
            cameraPosition.add(camera.getViewMatrix().positiveX(new Vector3f()).mul(-0.1f));
            camera.setPosition(cameraPosition);
        }
        if (dPressed) {
            cameraPosition.add(camera.getViewMatrix().positiveX(new Vector3f()).mul(0.1f));
            camera.setPosition(cameraPosition);
        }
        if (lShlPressed) {
            cameraPosition.y -= 0.05;
            camera.setPosition(cameraPosition);
        }
        if (spacePressed) {
            cameraPosition.y += 0.05;
            camera.setPosition(cameraPosition);
        }

        for (int i = 0; i < gridCount + 1; ++i) {
            renderScheduler.drawPrimitiveLine(gridX1[i], gridX2[i], gridColor);
            renderScheduler.drawPrimitiveLine(gridY1[i], gridY2[i], gridColor);
        }
    }
}
