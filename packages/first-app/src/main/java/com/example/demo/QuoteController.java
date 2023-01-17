package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// Controller
@RestController
public class QuoteController {

  @Autowired RestTemplate restTemplate;
  private ObjectMapper objectMapper = new ObjectMapper();

  @GetMapping("/quote")
  public QuoteObj quoteGenerator() throws JsonMappingException, JsonProcessingException {

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<String>(headers);
    var jsonRes =
        restTemplate
            .exchange("https://api.quotable.io/random", HttpMethod.GET, entity, String.class)
            .getBody();
    // System.out.println("response: " + jsonRes);
    QuoteObj quote = objectMapper.readValue(jsonRes, QuoteObj.class);
    return quote;
  }
}

// Model

@JsonIgnoreProperties(ignoreUnknown = true)
class QuoteObj {
  @JsonProperty("_id")
  private String id;

  private String content;
  private String author;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
}
