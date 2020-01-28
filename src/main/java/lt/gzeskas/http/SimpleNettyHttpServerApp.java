package lt.gzeskas.http;

import lt.gzeskas.http.config.HttpServerConfiguration;
import lt.gzeskas.http.netty.NettyHttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleNettyHttpServerApp {
	private static final Logger logger = LoggerFactory.getLogger(SimpleNettyHttpServerApp.class);

	public static void main(String[] args) {
		logger.info("Application initializing");
		ShutdownManager shutdownManager = new ShutdownManager();
		HttpServer httpServer = new NettyHttpServer(new HttpServerConfiguration(8080));
		shutdownManager.register(httpServer);
		httpServer.start();
	}

}
