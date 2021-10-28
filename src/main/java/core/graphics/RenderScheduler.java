package core.graphics;

import org.joml.Matrix4f;

/**
 * manage render schedule, what to draw
 */
public interface RenderScheduler {
    /**
     * add instance to renderer to draw
     * @param id instance id for distinction
     * @param renderTarget draw model
     * @param matrix4x4 transform matrix
     */
    void addInstance(int id, RenderTarget renderTarget, Matrix4f matrix4x4);

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
     * @param matrix4x4 transform matrix
     */
    void updateTransform(int id, Matrix4f matrix4x4);

    /**
     * clear all instance in scheduler
     */
    void clearInstances();

    void drawPrimitive();
}
