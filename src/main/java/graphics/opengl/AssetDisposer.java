package graphics.opengl;

import core.graphics.DisposeDelegate;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AssetDisposer {
    private final Queue<DisposeDelegate> disposeDelegates = new ConcurrentLinkedQueue<>();

    void addDisposeDelegate(DisposeDelegate disposeDelegate) { disposeDelegates.add(disposeDelegate); }

    /**
     * dispose dead resources
     * it should have call by main game thread
     */
    public void disposeDeadResources() {
        while (!disposeDelegates.isEmpty()) disposeDelegates.poll().dispose();
    }
}
