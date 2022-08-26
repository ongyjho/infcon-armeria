package infcon.armeria;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;

import java.util.concurrent.CompletableFuture;

public final class Main {

    public static void main(String[] args) {
        final Backend foo = Backend.of("foo", 8080);
        foo.start();


        final Server server = serverBuilder.http(8080).service("/infcon", new MyService()).build();

        final CompletableFuture<Void> serverFuture = server.start();
        serverFuture.join();
    }
}
