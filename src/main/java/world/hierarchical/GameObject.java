package world.hierarchical;
import core.ZerrgoEngine;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import world.hierarchical.component.Transform;
import world.hierarchical.component.annotation.DisallowMultipleComponent;
import world.hierarchical.component.characteristics.Renderable;
import world.hierarchical.component.characteristics.Updatable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private boolean isEnabled = false;
    private final List<GameObject> children = new ArrayList<>();
    private GameObject parent;
    private final List<Component> components = new ArrayList<>();
    private Transform transform;
    private HierarchicalWorld world;

    public void start(){
        for (Component component : components) {
            if (component instanceof Updatable) {
                ((Updatable) component).start();
            }
        }
        for (GameObject object : children) {
            object.start();
        }
    }

    public void update() {
        for (Component component : components) {
            if (component instanceof Updatable) {
                ((Updatable) component).update();
            }
            if (component instanceof Renderable) {
                ((Renderable) component).render();
            }
            if(transform != null && parent.transform != null) {
                Vector3f parentPosition = new Vector3f(parent.transform.getPosition());
                Quaternionf parentRotation = new Quaternionf(parent.transform.getRotation());
                transform.setPosition(parentPosition.add(transform.getLocalPosition()));
                transform.setRotation(parentRotation.add(transform.getLocalRotation()));
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

    public void appendChildren(GameObject[] objects){
        for (var go : objects) appendChild(go);
    }

    public void removeChild(GameObject object){
        children.remove(object);
        if(object.getParent() == this) {
            object.parent = null;
        }
    }

    public Component appendComponent(Class<? extends Component> componentType){
        if (!checkMultipleComponent(componentType)) return null;

        Component component = null;
        try {
            component = componentType.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException e) {
            ZerrgoEngine.Logger().warning("component("
                    + componentType.getName() + ") must have default constructor!");
        }

        assert component != null;
        component.setGameObject(this);
        if(component instanceof Transform transform) {
            this.transform = transform;
        } else {
            components.add(component);
        }
        return component;
    }

    public interface ComponentInitializeFunc {
        void execute(Component component);
    }

    public Component appendComponentWith(
            Class<? extends Component> componentType,
            ComponentInitializeFunc componentInitializeFunc
    ) {
        var component = appendComponent(componentType);
        if (component == null) return null;
        componentInitializeFunc.execute(component);
        return component;
    }

    private boolean checkMultipleComponent(Class<? extends Component> componentType) {
        for (var annotation : componentType.getAnnotations()) {
            if (annotation.annotationType().equals(DisallowMultipleComponent.class)) {
                var find = false;
                for (var item : components) {
                    if (item.getClass().equals(componentType)) {
                        find = true;
                        ZerrgoEngine.Logger().warning("component("
                                + componentType.getName() + ") is not allowed to multiple append");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void removeComponent(Component component){
        if (component instanceof Transform) {
            transform = null;
            return;
        }
        components.remove(component);
    }

    public Transform getTransform(){
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

    public interface InitializeFunc {
        void execute(GameObject go);
    }

    public static GameObject BuildWith(InitializeFunc initializeFunc) {
        var go = new GameObject();
        initializeFunc.execute(go);
        return go;
    }
}
