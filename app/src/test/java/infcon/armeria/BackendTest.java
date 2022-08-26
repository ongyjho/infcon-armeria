package infcon.armeria;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.HttpResponse;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

class BackendTest {

    @Test
    void backend() {
        final Backend foo = Backend.of("/foo", 8080);
        foo.start();

        final WebClient client = WebClient.of("http://localhost:8080");
        System.err.println("Thread: " + Thread.currentThread().getName());
        final HttpResponse httpResponse = client.get("/foo");
        final CompletableFuture<AggregatedHttpResponse> future = httpResponse.aggregate();
        // 비동기 서버이므로 기다리지 않음. HttpResponse 껍데기만 갖고 있음. 한번에 안온다고 보는 게 편함

        final AggregatedHttpResponse response = future.join();// blocking, 비동기서버에서는 사용하지 말것
        System.err.println(response.contentUtf8());
    }

}