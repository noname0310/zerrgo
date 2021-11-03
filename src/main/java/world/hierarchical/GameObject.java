package world.hierarchical;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import world.hierarchical.component.characteristics.Positional;
import world.hierarchical.component.characteristics.Renderable;
import world.hierarchical.component.characteristics.Updatable;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private boolean isEnabled = false;
    private final List<GameObject> children = new ArrayList<>();
    private GameObject parent;
    private final List<Component> components = new ArrayList<>();
    private boolean isPositional = false;
    private Component transform;

    public void update() {
        for (Component component : components) {
            if (component instanceof Updatable) {
                ((Updatable) component).update();
            }
            if(component instanceof Positional) {
                if(parent.isPositional()){
                    Vector3f parentPosition = new Vector3f(((Positional)(parent.getTransform())).getPosition());
                    Quaternionf parentRotation = new Quaternionf(((Positional)(parent.getTransform())).getRotation());
                    ((Positional) component).setPosition(parentPosition.add(((Positional) component).getLocalPosition()));
                    ((Positional) component).setRotation(parentRotation.add(((Positional) component).getLocalRotation()));
                }
                if (component instanceof Renderable) {
                    ((Renderable) component).render();
                }
                //joml 벡터
            }
        }
        for (GameObject object : children) {
            object.update();
        }
    }

    public GameObject getParent() {
        return parent;
    }

    public List<Component> getComponents() {
        return components;
    }

    public List<GameObject> getChildren() {
        return children;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public void appendChild(GameObject object){
        children.add(object);
    }

    public void removeChild(GameObject object){
        children.remove(object);
    }

    public boolean appendComponent(Component component){
        if(component instanceof Positional) {
            if(isPositional) {
                return false;
            }
            transform = component;
            components.add(component);
            return true;
        }
        components.add(component);
        return true;
    }

    public void removeComponent(Component component){
        components.remove(component);
        if(component instanceof Positional) {
            isPositional = false;
        }
    }

    public boolean isPositional(){
        return isPositional;
    }

    public Component getTransform(){
        return transform;
    }

}
