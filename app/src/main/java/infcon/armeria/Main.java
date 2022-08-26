package infcon.armeria;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;

import java.util.concurrent.CompletableFuture;

public final class Main {

    public static void main(String[] args) {
        final Backend foo = Backend.of("foo", 8080);
        foo.start();

        final WebClient client = WebClient.of("http://localhost:8080");
        final ServerBuilder serverBuilder = Server.builder();
        final Server server = serverBuilder.http(8080).service("/infcon", new MyService(client)).build();

        final CompletableFuture<Void> serverFuture = server.start();
        serverFuture.join();
    }
}
