package com.hr_program.learningTest.AnniversaryApiTest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData {
    public Response getResponse() {
        return response;
    }


    @JsonProperty("response")
    private Response response;

    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {

        public Body getBody() {
            return body;
        }
        @JsonProperty("header")
        private Header header;

        @JsonProperty("body")
        private Body body;

        @Getter
        @ToString
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Header {

            @JsonProperty("resultCode")
            private String resultCode;

            @JsonProperty("resultMsg")
            private String resultMsg;
        }

        @Getter
        @ToString
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Body {

            public Items getItems() {
                return items;
            }

            @JsonProperty("items")
            private Items items;

            @Getter
            @ToString
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Items {

                public List<Item> getItem() {
                    return item;
                }

                @JsonProperty("item")
                private List<Item> item;

                @Getter
                @ToString
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Item {

                    public String getDateName() {
                        return dateName;
                    }

                    public String getIsHoliday() {
                        return isHoliday;
                    }

                    public LocalDate getLocdate() {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                        LocalDate date = LocalDate.parse(String.valueOf(locdate), formatter);
                        return date;
                    }

                    @JsonProperty("dateKind")
                    private String dateKind;

                    @JsonProperty("dateName")
                    private String dateName;

                    @JsonProperty("isHoliday")
                    private String isHoliday;

                    @JsonProperty("kst")
                    private String kst;

                    @JsonProperty("locdate")
                    private int locdate;

                    @JsonProperty("seq")
                    private int seq;

                    @JsonProperty("sunLongitude")
                    private int sunLongitude;
                }
            }
        }
    }
}
