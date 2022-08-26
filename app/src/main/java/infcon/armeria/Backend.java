package infcon.armeria;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;

import java.time.Duration;

public final class Backend {

    public static Backend of(String name, int port) {
        return new Backend(name, port);
    }

    private final Server server;

    private Backend(String name, int port) {
        server = Server.builder().http(port).service("/foo", (ctx, req) -> {
            return HttpResponse.delayed(HttpResponse.of("Hello, from" + name + "!"), Duration.ofSeconds(3));
        }).build();
    }

    public void start() {
        server.start().join();
    }
}
