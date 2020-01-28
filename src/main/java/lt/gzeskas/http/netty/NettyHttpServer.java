package lt.gzeskas.http.netty;

import lt.gzeskas.http.netty.initializer.HttpInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lt.gzeskas.http.HttpServer;
import lt.gzeskas.http.config.HttpServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by gjurgo@gmail.com on 27/01/2020.
 */
public class NettyHttpServer implements HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyHttpServer.class);
    private final int serverPort;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public NettyHttpServer(HttpServerConfiguration configuration) {
        this.serverPort = configuration.getServerPort();
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
    }

    @Override
    public void start() {
        try {
            var serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = serverBootstrap.bind(serverPort).sync();
            logger.info("Stomp server is listening on port: {}", serverPort);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e){
            logger.error("Could not start server." , e);
            throw new RuntimeException(e);
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        logger.info("Shutting down netty http server.");
        bossGroup.shutdownGracefully().awaitUninterruptibly(1, TimeUnit.SECONDS);
        workerGroup.shutdownGracefully().awaitUninterruptibly(1, TimeUnit.SECONDS);
    }
}
