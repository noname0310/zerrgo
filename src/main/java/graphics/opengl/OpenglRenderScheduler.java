package graphics.opengl;

import core.graphics.RenderScheduler;
import core.graphics.record.Camera;
import core.graphics.resource.Model;
import org.joml.Matrix4f;
import org.joml.Matrix4x3fc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class OpenglRenderScheduler implements RenderScheduler {
    private static record InstanceValue(Matrix4f worldTransformMatrix, boolean shouldDraw) { }

    private final Map<Model, InstanceValue> instances = new HashMap<>();

    @Override
    public void addInstance(int id, Model model, Matrix4x3fc matrix4x3) {

    }

    @Override
    public void removeInstance(int id) {

    }

    @Override
    public void displayInstance(int id, boolean display) {

    }

    @Override
    public void updateTransform(int id, Matrix4x3fc matrix4x3) {

    }

    @Override
    public void clearInstances() {

    }

    @Override
    public void drawPrimitive() {

    }

    @Override
    public void setCamera(Camera camera) {

    }

    Iterator<Map.Entry<Model, InstanceValue>> getDrawIterator() {
        return instances.entrySet().iterator();
    }
}
