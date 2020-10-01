import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Http {

    public static void main(String[] args) throws IOException, InterruptedException {
        //Budowa Klienta
        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).followRedirects(HttpClient.Redirect.NORMAL).connectTimeout(Duration.ofSeconds(20)).build();
        //Budowa Żądania
        HttpRequest get = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/r")).build();
        //Budowa odpowiedzi
        HttpResponse<String> response = httpClient.send(get,HttpResponse.BodyHandlers.ofString());

        HttpHeaders headers = response.headers();
        //headers.map().forEach((k,v)->System.out.println(k+" "+v));
        System.out.println(response.statusCode());
        if(response.statusCode()==200){
            System.out.println(response.body());
        }
        HttpRequest post = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/r/?id=ABC")).POST(HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> resp = httpClient.send(post,HttpResponse.BodyHandlers.ofString());
        System.out.println(resp.body());

        RestTemplate rs = new RestTemplate();
        HttpEntity<String> entity = rs.getForEntity("http://localhost:8080/r/ex",String.class);
        System.out.println(entity.getBody());
        System.out.println(entity.getHeaders());
    }
}
