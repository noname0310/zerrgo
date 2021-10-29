package world.hierarchical;
import world.hierarchical.component.Characteristics.Updateable;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private boolean isEnabled = false;
    private List<GameObject> children = new ArrayList<GameObject>();
    private GameObject parent;
    private List<Component> components = new ArrayList<Component>();

    public void Update(){
        for(Component component:components){
            if(component instanceof Updateable){
                ((Updateable) component).update();
            }
        }
        for(GameObject object:children){
            object.Update();
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
        if(children.contains(object)){
            children.remove(object);
        }
    }

    public void appendComponent(Component component){
        components.add(component);
    }

    public void removeComponent(Component component){
        if(components.contains(component)){
            components.remove(component);
        }
    }

}
