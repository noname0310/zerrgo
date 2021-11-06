package core.graphics;

import core.graphics.record.Camera;
import core.graphics.resource.Model;
import org.joml.Matrix4fc;

/**
 * manage render schedule, what to draw
 */
public interface RenderScheduler {
    /**
     * add instance to renderer to draw
     * @param id instance id for distinction
     * @param model draw model
     * @param matrix4x4 world transform matrix
     */
    void addInstance(int id, Model model, Matrix4fc matrix4x4);

    /**
     * remove instance from renderer
     * @param id instance id
     */
    void removeInstance(int id);

    /**
     * set display instance
     * @param id instance id
     * @param display should display
     */
    void displayInstance(int id, boolean display);

    /**
     * update instance transform
     * @param id instance id
     * @param matrix4x4 world transform matrix
     */
    void updateTransform(int id, Matrix4fc matrix4x4);

    /**
     * clear all instance in scheduler
     */
    void clearInstances();

    void drawPrimitive();

    /**
     * set camera for get view projection matrix mainly
     */
    void setCamera(Camera camera);
}
