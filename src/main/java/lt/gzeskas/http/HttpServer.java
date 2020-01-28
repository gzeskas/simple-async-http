package lt.gzeskas.http;

import lt.gzeskas.http.lifecycle.Stoppable;

/**
 * Created by gjurgo@gmail.com on 27/01/2020.
 */
public interface HttpServer extends Stoppable {
    void start();
}
