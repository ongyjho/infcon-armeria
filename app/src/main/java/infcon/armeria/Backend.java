package infcon.armeria;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;

public final class Backend {

    public static Backend of(String name, int port) {
        return new Backend(name, port);
    }

    private final Server server;

    private Backend(String name, int port) {
        server = Server.builder().http(port).service("/foo", (ctx, req) -> {
            return HttpResponse.of("Response from : " + name);
        }).build();
    }

    public void start() {
        server.start().join();
    }
}
