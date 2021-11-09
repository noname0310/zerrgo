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
    private HierarchicalWorld world;

    public void update() {
        for (Component component : components) {
            if (component instanceof Updatable) {
                if(!((Updatable)component).isStarted){
                    ((Updatable)component).start();
                }
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
        world = parent.world;
        if(this.parent != null)
            this.parent.children.remove(this);
        this.parent = parent;
        if(parent.getChildren().contains(this)){
            this.parent.children.add(this);
        }
    }

    public void appendChild(GameObject object){
        children.add(object);
        object.world = world;
        object.parent = this;
    }

    public void removeChild(GameObject object){
        children.remove(object);
        if(object.getParent() == this) {
            object.parent = null;
        }
    }

    public boolean appendComponent(Component component){
        component.setGameObject(this);
        if(component instanceof Positional) {
            if(isPositional) {
                return false;
            }
            component.setGameObject(this);
            transform = component;
            components.add(component);
            isPositional = true;
            return true;
        }
        components.add(component);
        return true;
    }

    public void removeComponent(Component component){
        component.setGameObject(null);
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

    public HierarchicalWorld getWorld(){
        return world;
    }

    public void setWorld(HierarchicalWorld w){
        world = w;
        for(GameObject object : children){
            object.setWorld(w);
        }
    }

}
