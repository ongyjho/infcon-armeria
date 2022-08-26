package infcon.armeria;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;

import java.util.concurrent.CompletableFuture;

public final class MyService implements HttpService {

    private WebClient fooClient;

    private WebClient barClient;

    public MyService(WebClient fooClient, WebClient barClient) {
        this.fooClient = fooClient;
        this.barClient = barClient;
    }

    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        final CompletableFuture<HttpResponse> future = new CompletableFuture<>();
        fooClient.get("/foo").aggregate().thenAccept(resp -> {
            barClient.get("/bar").aggregate().thenAccept(barResp -> {
                final HttpResponse response = HttpResponse.of(
                        resp.contentUtf8() + barResp.contentUtf8());
                future.complete(response);//<- 완성되면 future채워줌

            });
        });

        final HttpResponse res = HttpResponse.from(future); // <-
        return res;
    }
}
