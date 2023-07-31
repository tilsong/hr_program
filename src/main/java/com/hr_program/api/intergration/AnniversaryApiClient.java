package com.hr_program.api.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr_program.api.intergration.response.ResponseData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Component
public class AnniversaryApiClient {

    @Value("${ANNIVERSARY_SERVICE_KEY}")
    String SERVICE_KEY;

    @Value("${ANNIVERSARY_BASE_URL}")
    String BASE_URL;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public List<ResponseData.Response.Body.Items.Item> getAnniversaryInfo(String pageNo, String numOfRows, String year, String month) {
        String encodedServiceKey = URLEncoder.encode(SERVICE_KEY, "UTF-8");
        String query = String.format("?ServiceKey=%s&pageNo=%s&_type=%s&numOfRows=%s&solYear=%s&solMonth=%s",
                encodedServiceKey, pageNo, "json", numOfRows, year, month);
        URL url = new URL(BASE_URL + query);

        String response = fetch(url);

        ResponseData responseData = objectMapper.readValue(response, ResponseData.class);

        return responseData.getResponse().getBody().getItems().getItem();
    }

    private String fetch(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            conn.disconnect();
        }
    }
}
