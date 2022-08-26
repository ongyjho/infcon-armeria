package infcon.armeria;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;

import java.util.concurrent.CompletableFuture;

public final class MyService implements HttpService {

    private WebClient fooClient;

    public MyService(WebClient fooClient) {
        this.fooClient = fooClient;
    }

    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        final CompletableFuture<HttpResponse> future = new CompletableFuture<>();
        fooClient.get("/foo").aggregate().thenAccept(resp -> {
            System.err.println(resp.contentUtf8());
            // event loop
            final HttpResponse response = resp.toHttpResponse(); // 콜백 안에 있어서 못보냄 ㅠㅠ
        });

        final HttpResponse res = HttpResponse.from(future); // <-
        return res;
    }
}
