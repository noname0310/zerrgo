package graphics.opengl;

import core.ZerrgoEngine;
import core.graphics.RenderScheduler;
import core.graphics.record.Camera;
import core.graphics.resource.Model;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

import java.util.HashMap;
import java.util.Map;

final class RenderInstanceValue {
    private Matrix4f worldTransformMatrix;
    private boolean shouldDraw;

    RenderInstanceValue (Matrix4fc worldTransformMatrix, boolean shouldDraw) {
        this.worldTransformMatrix = new Matrix4f(worldTransformMatrix);
        this.shouldDraw = shouldDraw;
    }

    public Matrix4fc getWorldTransformMatrix() { return worldTransformMatrix; }

    public void setWorldTransformMatrix(Matrix4fc worldTransformMatrix) {
        this.worldTransformMatrix = new Matrix4f(worldTransformMatrix);
    }

    public boolean isShouldDraw() { return shouldDraw; }

    public void setShouldDraw(boolean shouldDraw) { this.shouldDraw = shouldDraw; }
}

public final class OpenglRenderScheduler implements RenderScheduler {
    private final Map<Model, Map<Integer, RenderInstanceValue>> instances = new HashMap<>();
    private final Map<Integer, Model> idModelMap = new HashMap<>();

    private final OpenglRenderer openglRenderer;

    public OpenglRenderScheduler(OpenglRenderer openglRenderer) {
        this.openglRenderer = openglRenderer;
    }

    @Override
    public void addInstance(int id, Model model, Matrix4fc matrix4x4) {
        if (!(model instanceof graphics.opengl.Model)) {
            ZerrgoEngine.Logger().warning("Model("
                    + model.getName() + ") is not openGL compatible! OpenglRenderScheduler.addInstance() failed");
            return;
        }
        instances.computeIfAbsent(model, k -> new HashMap<>())
                .put(id, new RenderInstanceValue(matrix4x4, true));
        idModelMap.put(id, model);
    }

    @Override
    public void removeInstance(int id) {
        var model = idModelMap.remove(id);
        if (model == null) return;
        var innerMap = instances.get(model);
        if (innerMap != null) innerMap.remove(id);
    }

    @Override
    public void displayInstance(int id, boolean display) {
        var item = getItemById(id);
        if (item == null) return;
        item.setShouldDraw(display);
    }

    @Override
    public void updateTransform(int id, Matrix4fc matrix4x4) {
        var item = getItemById(id);
        if (item == null) return;
        item.setWorldTransformMatrix(matrix4x4);
    }

    @Override
    public void clearInstances() {
        instances.clear();
        idModelMap.clear();
    }

    @Override
    public void drawPrimitive() {

    }

    @Override
    public void setCamera(Camera camera) { openglRenderer.setCamera(camera); }

    private RenderInstanceValue getItemById(int id) {
        var model = idModelMap.get(id);
        if (model == null) return null;
        var innerMap = instances.get(model);
        if (innerMap == null) return null;
        return innerMap.get(id);
    }

    interface foreachFunc {
        void foreach(Model drawModel, RenderInstanceValue renderInstanceValue);
    }

    void foreachRenderItem(foreachFunc foreachFunc) {
        instances.forEach((model, innerMap) ->  innerMap.values().forEach(item -> foreachFunc.foreach(model, item)));
    }
}
