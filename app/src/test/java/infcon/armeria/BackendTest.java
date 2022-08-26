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

        future.thenAccept(res -> {
            System.err.println("In call back Thread: " + Thread.currentThread().getName());
            System.err.println(res.contentUtf8());

            sendBackToTheOriginalClient(res); // 종료하고, 쓰레드는 놓아줌
        }).join();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendBackToTheOriginalClient(AggregatedHttpResponse res) {

    }

}