package lt.gzeskas.http.config;

public class HttpServerConfiguration implements Configuration {
    private final int serverPort;

    public HttpServerConfiguration(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return serverPort;
    }
}
