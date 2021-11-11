package world.hierarchical;

import core.ZerrgoEngine;
import world.hierarchical.component.Transform;
import world.hierarchical.component.annotation.DisallowMultipleComponent;
import world.hierarchical.component.characteristics.Renderable;
import world.hierarchical.component.characteristics.Startable;
import world.hierarchical.component.characteristics.Updatable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private boolean isEnabled = false;
    private String name;
    private GameObject parent;
    final List<GameObject> children = new ArrayList<>();
    private final List<Component> components = new ArrayList<>();
    private final List<Updatable> updatableComponents = new ArrayList<>();
    private final List<Renderable> renderableComponents = new ArrayList<>();
    private Transform transform;
    private HierarchicalWorld world;

    private GameObject(String name) {
        this.name = name;
    }

    void start() {
        for (Component component : components) {
            if (component instanceof Startable startable) startable.start();
        }
        for (GameObject object : children) {
            object.start();
        }
    }

    void update() {
        for (Updatable component : updatableComponents) {
            component.update();
        }
        for (Renderable component : renderableComponents) {
            component.render();
        }
        for (GameObject object : children) {
            if (object == null) continue;
            object.update();
        }
    }

    public void updateChildTransforms(){
        for(GameObject child : children){
            if(child != null) {
                child.updateTransform();
            }
        }
    }

    void updateTransform(){
        if(transform != null){
            transform.resetMatrix();
        }
        for(GameObject child : children){
            if(child != null) {
                child.updateTransform();
            }
        }
    }

    public GameObject getParent() {
        return parent;
    }

    public Component[] getComponents() {
        var componentsArray = new Component[components.size()];
        components.toArray(componentsArray);
        return componentsArray;
    }

    public GameObject[] getChildren() {
        var childrenArray = new GameObject[children.size()];
        children.toArray(childrenArray);
        return childrenArray;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setParent(GameObject parent) {
        world = parent.world;
        if (this.parent != null) {
            for (int i = 0; i < this.parent.children.size(); ++i) {
                if (this.parent.children.get(i) == this) {
                    this.parent.children.set(i, null);
                }
            }
        }
        this.parent = parent;
        var addedAtNull = false;
        for (int i = 0; i < this.parent.children.size(); ++i) {
            if (this.parent.children.get(i) == null) {
                this.parent.children.set(i, this);
                addedAtNull = true;
                break;
            }
        }
        if (!addedAtNull) this.parent.children.add(this);
        if (transform != null && parent.transform != null) {
            transform.changeParent(parent.transform);
        }
    }

    public void appendChild(GameObject object) {
        var addedAtNull = false;
        for (int i = 0; i < children.size(); ++i) {
            if (children.get(i) == null) {
                children.set(i, object);
                addedAtNull = true;
                break;
            }
        }
        if (!addedAtNull) children.add(object);
        if (object.parent != null) {
            object.parent.removeChild(object);
        }
        object.world = world;
        object.parent = this;
        if (transform != null && object.transform != null) {
            object.transform.changeParent(transform);
        }
    }

    public void removeChild(GameObject object) {
        for (int i = 0; i < children.size(); ++i) {
            if (children.get(i) == object) {
                children.set(i, null);
            }
        }
        if (object.getParent() == this) {
            object.parent = null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<? extends Component> componentType) {
        for (var compo : components) {
            if (compo.getClass().equals(componentType)) {
                return (T) compo;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> List<T> getComponents(Class<? extends Component> componentType) {
        List<T> result = new ArrayList<>();
        for (var compo : components) {
            if (compo.getClass().equals(componentType)) {
                result.add((T) compo);
            }
        }
        return result;
    }

    public <T extends Component> T appendComponent(Class<T> componentType) {
        if (!checkMultipleComponent(componentType)) return null;

        T component = null;
        try {
            component = componentType.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException |
                InstantiationException |
                IllegalAccessException e) {
            ZerrgoEngine.Logger().warning("component("
                    + componentType.getName() + ") must have default constructor!");
        } catch (InvocationTargetException e) {
            ZerrgoEngine.Logger().warning("component("
                    + componentType.getName() + ") construction failed!");
            ZerrgoEngine.Logger().severe(e.getTargetException().getMessage());
        }
        if (component == null) return null;

        component.setGameObject(this);
        if (component instanceof Transform transform) {
            this.transform = transform;
        } else {
            components.add(component);
            if (component instanceof Updatable updatable) {
                updatableComponents.add(updatable);
            }
            if (component instanceof Renderable renderable) {
                renderableComponents.add(renderable);
            }
        }
        return component;
    }

    private boolean checkMultipleComponent(Class<? extends Component> componentType) {
        for (var annotation : componentType.getAnnotations()) {
            if (annotation.annotationType().equals(DisallowMultipleComponent.class)) {
                for (var item : components) {
                    if (item.getClass().equals(componentType)) {
                        ZerrgoEngine.Logger().warning("component("
                                + componentType.getName() + ") is not allowed to multiple append");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void removeComponent(Component component) {
        if (component instanceof Transform) {
            transform = null;
            return;
        }
        if (component instanceof Updatable) {
            updatableComponents.remove((Updatable) component);
        }
        if (component instanceof Renderable) {
            renderableComponents.remove((Renderable) component);
        }
        components.remove(component);
    }

    public Transform getTransform() {
        return transform;
    }

    public HierarchicalWorld getWorld() {
        return world;
    }

    public void setWorld(HierarchicalWorld w) {
        world = w;
        for (GameObject object : children) {
            object.setWorld(w);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static final class GameObjectBuilder {
        private final GameObject gameObject;
        private final List<GameObjectBuilder> children;
        private final List<InternalComponentInitializeFunc> componentInitializeFuncList;

        private GameObjectBuilder(GameObject gameObject) {
            this.gameObject = gameObject;
            this.children = new ArrayList<>();
            this.componentInitializeFuncList = new ArrayList<>();
        }

        public <T extends Component> GameObjectBuilder component(Class<T> componentType) {
            gameObject.appendComponent(componentType);
            return this;
        }

        public interface ComponentInitializeFunc<T extends Component> {
            void execute(T component);
        }

        private interface InternalComponentInitializeFunc {
            void execute();
        }

        public <T extends Component> GameObjectBuilder component(
                Class<T> componentType,
                ComponentInitializeFunc<T> componentInitializeFunc
        ) {
            var component = gameObject.appendComponent(componentType);
            if (component != null) {
                this.componentInitializeFuncList.add(() -> componentInitializeFunc.execute(component));
            }
            return this;
        }

        public GameObjectBuilder child(GameObjectBuilder gameObject) {
            children.add(gameObject);
            return this;
        }

        public GameObject build() {
            for (var item : children) gameObject.appendChild(item.build());
            return gameObject;
        }

        public GameObject initialize() {
            for (var item : componentInitializeFuncList) item.execute();
            for (var item : children) item.initialize();
            return gameObject;
        }
    }

    public static GameObjectBuilder CreateWith(String name) {
        return new GameObjectBuilder(new GameObject(name));
    }
}
