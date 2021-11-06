package world.opengltest;

import core.graphics.AssetLoader;
import core.graphics.RenderScheduler;
import core.graphics.record.OrthographicCamera;
import core.window.Window;
import core.world.WorldContainer;
import org.joml.*;

import java.lang.Math;

public class OpenglTestWorld implements WorldContainer {
    private RenderScheduler renderScheduler;

    private final Vector3f takahiroPosition = new Vector3f();
    private final Quaternionf takahiroRotation = new Quaternionf();
    private final Matrix4f takahiroTransformMatrix = new Matrix4f();

    private final Vector3f pikaPosition = new Vector3f();
    private final Quaternionf pikaRotation = new Quaternionf();
    private final Matrix4f pikaTransformMatrix = new Matrix4f();
    private float a = 0;

    @Override
    public void initialize(Window window, RenderScheduler renderScheduler, AssetLoader assetLoader) {
        this.renderScheduler = renderScheduler;

        renderScheduler.setCamera(
                new OrthographicCamera(
                        window.getFrameBufferWidth(),
                        window.getFrameBufferHeight(),
                        3,
                        0.0f,
                        100.0f,
                        new Vector3f(0, 0, 10),
                        new Quaternionf(),
                        new Vector4f(0.5f, 0.5f, 0.5f, 1.0f)
                )
        );

        var model = assetLoader.getPlaneModelFromTexture(
                assetLoader.getTexture("src\\main\\resources\\20211104_102157-realesrgan.jpg"));
        var model2 = assetLoader.getPlaneModelFromTexture(
                assetLoader.getTexture("src\\main\\resources\\하이라이트없음_배경흰색.png"));

        renderScheduler.addInstance(1, model, takahiroTransformMatrix);
        renderScheduler.addInstance(2, model2, pikaTransformMatrix);
    }

    @Override
    public void update() {
        a += 0.05f;

        takahiroRotation.rotateZ((float) Math.toRadians(1f));
        takahiroPosition.set(Math.cos(a), Math.sin(a), 0f);
        renderScheduler.updateTransform(1,
                takahiroTransformMatrix.identity()
                        .translate(takahiroPosition)
                        .rotate(takahiroRotation)
        );

        pikaPosition.set(Math.sin(a), Math.cos(a), 0f);
        renderScheduler.updateTransform(2,
                pikaTransformMatrix.identity()
                        .translate(pikaPosition)
                        .rotate(pikaRotation)
        );
    }
}
