package com.hr_program.learningTest.AnniversaryApiTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.*;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test-application.properties")
public class GetAnniversaryInfoTest {

    @Value("${test.SERVICE_KEY}")
    String SERVICE_KEY;

    @Value("${test.BASE_URL}")
    String BASE_URL;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @DisplayName("기념일 정보를 가져온다.")
    @Test
    void getAnniversaryInfoTest() throws IOException {
        // given
        String encodedServiceKey = URLEncoder.encode(SERVICE_KEY, "UTF-8");
        String query = String.format("?ServiceKey=%s&pageNo=%s&_type=%s&numOfRows=%s&solYear=%s&solMonth=%s",
                encodedServiceKey, "1", "json", "10", "2023", "07");
        URL url = new URL(BASE_URL + query);

        // when
        String response = fetch(url);

        // then
        ResponseData responseData = OBJECT_MAPPER.readValue(response, ResponseData.class);
        var item = responseData.getResponse().getBody().getItems().getItem().get(0);

        assertThat(item.getLocdate()).isEqualTo("2023-07-07");
        assertThat(item.getDateName()).isEqualTo("소서");
        assertThat(item.getIsHoliday()).isEqualTo("N");
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
