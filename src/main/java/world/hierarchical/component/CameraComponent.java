package world.hierarchical.component;

import core.ZerrgoEngine;
import core.graphics.RenderScheduler;
import core.graphics.record.OrthographicCamera;
import core.graphics.record.PerspectiveCamera;
import org.joml.*;
import world.hierarchical.Component;
import world.hierarchical.component.characteristics.Positional;
import world.hierarchical.component.characteristics.Updatable;
import core.graphics.record.Camera;

import java.lang.Math;
import java.lang.reflect.Type;
import java.util.logging.Level;

public class CameraComponent extends Component implements Updatable {
    private boolean enabled = false;
    private PerspectiveCamera perspectiveCamera;
    private OrthographicCamera orthographicCamera;
    private boolean isPerspective = true;
    private RenderScheduler scheduler;


    @Override
    public void update() {
        if(enabled){
            Positional t = (Positional) this.getGameObject().getTransform();
            orthographicCamera.setPosition(t.getPosition());
            orthographicCamera.setRotation(t.getRotation());
            perspectiveCamera.setPosition(t.getPosition());
            perspectiveCamera.setRotation(t.getRotation());
        }
    }

    @Override
    public void start(){
        // DI를 만들자
        scheduler = this.getGameObject().getWorld().getRenderScheduler();
        if(this.getGameObject().isPositional() && getGameObject().getTransform() instanceof Transform){
            perspectiveCamera = new PerspectiveCamera(
                    (float) Math.toRadians(60),
                    getGameObject().getWorld().getWindow().getFrameBufferWidth() / (float) getGameObject().getWorld().getWindow().getFrameBufferHeight(),
                    0.2f,
                    100.0f,
                    ((Transform)this.getGameObject().getTransform()).getPosition(),
                    ((Transform)this.getGameObject().getTransform()).getRotation(),
                    new Vector4f(0.5f, 0.5f, 0.5f, 1.0f));
            orthographicCamera = new OrthographicCamera(
                    10,
                    getGameObject().getWorld().getWindow().getFrameBufferWidth() / (float) getGameObject().getWorld().getWindow().getFrameBufferHeight(),
                    0.2f,
                    100.0f,
                    ((Transform)this.getGameObject().getTransform()).getPosition(),
                    ((Transform)this.getGameObject().getTransform()).getRotation(),
                    new Vector4f(0.5f, 0.5f, 0.5f, 1.0f));
            setPerspective(isPerspective);
            enabled = true;
            //render?
        }
        else {
            ZerrgoEngine.Logger().log(Level.SEVERE, "GameObject of " + this + " is not positional.");
            enabled = false;
        }
    }

    public boolean isPerspective() {
        return isPerspective;
    }

    public void setPerspective(boolean b){
        isPerspective = b;
        if(isPerspective){
            scheduler.setCamera(perspectiveCamera);
        }
        else{
            scheduler.setCamera(orthographicCamera);
        }
    }

    public float getNear(){
        return perspectiveCamera.getNearClippingPlane();
    }
    public void setNear(float near) {
        perspectiveCamera.setClippingPlanes(near, perspectiveCamera.getFarClippingPlane());
        orthographicCamera.setClippingPlanes(near, perspectiveCamera.getFarClippingPlane());
    }
    public float getFar(){
        return perspectiveCamera.getFarClippingPlane();
    }
    public void setFar(float far){
        perspectiveCamera.setClippingPlanes(perspectiveCamera.getNearClippingPlane(), far);
        orthographicCamera.setClippingPlanes(perspectiveCamera.getNearClippingPlane(), far);
    }

    public void setBackGroundColor(Vector4fc color){
        orthographicCamera.setBackgroundColor(color);
        perspectiveCamera.setBackgroundColor(color);
    }

    public float getViewSize() { return orthographicCamera.getViewSize(); }
    public void setViewSize(float size) { orthographicCamera.setViewSize(size); }

    public float getFov() { return perspectiveCamera.getFieldOfView(); }
    public void setFov(float fov) { perspectiveCamera.setFieldOfView(fov); }


    public float getAspectRatio() { return orthographicCamera.getAspectRatio(); }

    public void setAspectRatio(float aspectRatio) {
        orthographicCamera.setAspectRatio(aspectRatio);
        perspectiveCamera.setAspectRatio(aspectRatio);
    }
}
