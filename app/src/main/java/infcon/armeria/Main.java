package infcon.armeria;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;

import java.util.concurrent.CompletableFuture;

public final class Main {

    public static void main(String[] args) {
        final ServerBuilder serverBuilder = Server.builder();

        final Server server = serverBuilder.http(8080).service("/infcon", (ctx, req) -> {
            return HttpResponse.of("Hello, Armeria!");
        }).build();

        final CompletableFuture<Void> serverFuture = server.start();
        serverFuture.join();
    }
}
