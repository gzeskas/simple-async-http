package lt.gzeskas.http;

import lt.gzeskas.http.lifecycle.Stoppable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjurgo@gmail.com on 27/01/2020.
 */
public class ShutdownManager {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownManager.class);
    private List<Stoppable> instances = new ArrayList<>();

    public ShutdownManager() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::onShutdown));
    }

    public void register(Stoppable stoppable) {
        this.instances.add(stoppable);
    }

    private void onShutdown() {
        logger.info("Received shutdown event from system.");
        instances.forEach(Stoppable::stop);
    }
}
