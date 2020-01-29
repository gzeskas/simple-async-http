package lt.gzeskas.http.netty.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        logger.trace("Client connected, context: {}", ctx.toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof DefaultFullHttpRequest ){
            logger.info("Received full http request");
        }

        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            DefaultHttpResponse response = new DefaultHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK);
            ctx.writeAndFlush(response).addListener(future -> ctx.close());
        } else if (msg instanceof DefaultLastHttpContent) {
            DefaultLastHttpContent content = (DefaultLastHttpContent) msg;
            logger.info("Received content: {}", content.content());
        }
        else if (msg instanceof LastHttpContent) {
        } else {
            logger.warn("Incoming request is unknown");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Error dealing with connection", cause);
        ctx.close();
    }

}
