package graphics.opengl;

import core.ZerrgoEngine;
import core.graphics.PostProcessor;
import core.graphics.RenderScheduler;
import core.graphics.record.Camera;
import core.graphics.resource.Model;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;

import java.util.*;

final class RenderInstanceValue {
    private final Matrix4f worldTransformMatrix;
    private boolean shouldDraw;

    RenderInstanceValue(Matrix4fc worldTransformMatrix) {
        this.worldTransformMatrix = new Matrix4f(worldTransformMatrix);
        this.shouldDraw = true;
    }

    public Matrix4fc getWorldTransformMatrix() { return worldTransformMatrix; }

    public void setWorldTransformMatrix(Matrix4fc worldTransformMatrix) {
        this.worldTransformMatrix.set(worldTransformMatrix);
    }

    public boolean isShouldDraw() { return shouldDraw; }

    public void setShouldDraw(boolean shouldDraw) { this.shouldDraw = shouldDraw; }
}

final record RenderLineInstanceValue(
        float x1,
        float y1,
        float z1,
        float x2,
        float y2,
        float z2,
        float r,
        float g,
        float b)
{ }

public final class OpenglRenderScheduler implements RenderScheduler {
    private final Map<Model, Map<Integer, RenderInstanceValue>> instances = new HashMap<>();
    private final Map<Integer, Model> idModelMap = new HashMap<>();
    private final List<RenderLineInstanceValue> primitiveQueue = new ArrayList<>();

    private final OpenglRenderer openglRenderer;

    public OpenglRenderScheduler(OpenglRenderer openglRenderer) {
        this.openglRenderer = openglRenderer;
    }

    @Override
    public void addInstance(int id, Model model, Matrix4fc matrix4x4) {
        if (!(model instanceof graphics.opengl.Model openglModel)) {
            ZerrgoEngine.Logger().warning("Model("
                    + model.getName() + ") is not openGL compatible! OpenglRenderScheduler.addInstance() failed");
            return;
        }
        for (var material : openglModel.getMaterials()) {
            if (!(material.getShader() instanceof Shader)) {
                ZerrgoEngine.Logger().warning("Shader("+ material.getShader().getName() +") of Model("
                        + model.getName() + ") is not openGL compatible! OpenglRenderScheduler.addInstance() failed");
                return;
            }
            if (!(material.getTexture() instanceof Texture)) {
                ZerrgoEngine.Logger().warning("Texture("+ material.getTexture().getName() +") of Model("
                        + model.getName() + ") is not openGL compatible! OpenglRenderScheduler.addInstance() failed");
                return;
            }
        }
        for (var mesh : openglModel.getMeshes()) {
            if (!(mesh instanceof Mesh)) {
                ZerrgoEngine.Logger().warning("Mesh("+ mesh.getName() +") of Model("
                        + model.getName() + ") is not openGL compatible! OpenglRenderScheduler.addInstance() failed");
                return;
            }
        }
        instances.computeIfAbsent(model, k -> new HashMap<>())
                .put(id, new RenderInstanceValue(matrix4x4));
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
    public void drawPrimitiveLine(
            float x1,
            float y1,
            float z1,
            float x2,
            float y2,
            float z2,
            float r,
            float g,
            float b
    ) {
        primitiveQueue.add(new RenderLineInstanceValue(x1, y1, z1, x2, y2, z2, r, g, b));
    }

    @Override
    public void drawPrimitiveLine(Vector3fc a, Vector3fc b, Vector3fc color) {
        primitiveQueue.add(
                new RenderLineInstanceValue(a.x(), a.y(), a.z(), b.x(), b.y(), b.z(), color.x(), color.y(), color.z()));
    }

    @Override
    public void setCamera(Camera camera) { openglRenderer.setCamera(camera); }

    @Override
    public void setPostProcessor(PostProcessor postProcessor) {

    }

    private RenderInstanceValue getItemById(int id) {
        var model = idModelMap.get(id);
        if (model == null) return null;
        var innerMap = instances.get(model);
        if (innerMap == null) return null;
        return innerMap.get(id);
    }

    interface ForeachFunc {
        void func(Model drawModel, RenderInstanceValue renderInstanceValue);
    }

    void foreachRenderItem(ForeachFunc foreachFunc) {
        instances.forEach((model, innerMap) ->  innerMap.values().forEach(item -> foreachFunc.func(model, item)));
    }

    interface DequeItemFunc {
        void func(int index, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b);
    }

    void dequePrimitives(DequeItemFunc dequeItemFunc) {
        for (int i = 0; i < primitiveQueue.size(); ++i) {
            var item = primitiveQueue.get(i);
            dequeItemFunc.func(
                    i,
                    item.x1(), item.y1(), item.z1(),
                    item.x2(), item.y2(), item.z2(),
                    item.r(), item.g(), item.b());
        }
        primitiveQueue.clear();
    }

    int getPrimitivesCount() { return primitiveQueue.size(); }
}
