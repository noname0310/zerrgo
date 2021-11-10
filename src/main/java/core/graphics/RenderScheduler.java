package core.graphics;

import core.graphics.record.Camera;
import core.graphics.resource.Model;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;

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

    /**
     * draw primitive line in frame
     */
    void drawPrimitiveLine(
            float x1,
            float y1,
            float z1,
            float x2,
            float y2,
            float z2,
            float r,
            float g,
            float b);

    /**
     * draw primitive line in frame
     */
    void drawPrimitiveLine(Vector3fc a, Vector3fc b, Vector3fc color);

    /**
     * set camera for get view projection matrix mainly
     */
    void setCamera(Camera camera);

    /**
     * set render post-processor
     * @param postProcessor shader for post-processing
     */
    void setPostProcessor(PostProcessor postProcessor);
}
