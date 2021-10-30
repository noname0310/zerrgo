package world.opengltest;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;
import core.graphics.AssetLoader;
import core.graphics.RenderScheduler;
import core.graphics.resource.Texture;
import core.window.Window;
import core.world.WorldContainer;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.Map;

public class OpenglTestWorld implements WorldContainer {
    static public void startLoggingGc() {
        for (GarbageCollectorMXBean gcMbean : ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ManagementFactory.getPlatformMBeanServer().
                        addNotificationListener(gcMbean.getObjectName(), listener, null,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static private NotificationListener listener = (notification, handback) -> {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            CompositeData cd = (CompositeData) notification.getUserData();
            GarbageCollectionNotificationInfo gcNotificationInfo = GarbageCollectionNotificationInfo.from(cd);
            GcInfo gcInfo = gcNotificationInfo.getGcInfo();
            System.out.println("GarbageCollection: "+
                    gcNotificationInfo.getGcAction() + " " +
                    gcNotificationInfo.getGcName() +
                    " duration: " + gcInfo.getDuration() + "ms" +
                    " used: " + sumUsedMb(gcInfo.getMemoryUsageBeforeGc()) + "MB" +
                    " -> " + sumUsedMb(gcInfo.getMemoryUsageAfterGc()) + "MB");
        }
    };

    static private long sumUsedMb(Map<String, MemoryUsage> memUsages) {
        long sum = 0;
        for (MemoryUsage memoryUsage : memUsages.values()) {
            sum += memoryUsage.getUsed();
        }
        return sum / (1024 * 1024);
    }

    private AssetLoader assetLoader;
    private Texture texture;

    @Override
    public void initialize(Window window, RenderScheduler renderScheduler, AssetLoader assetLoader) {
        this.assetLoader = assetLoader;
        //startLoggingGc();
        texture = assetLoader.getTexture("C:\\noname\\thumb.png");
        texture = assetLoader.getTexture("C:\\noname\\thumb.png");
        texture = null;
    }

    @Override
    public void update() {
        new Object();
        System.gc();
    }
}
