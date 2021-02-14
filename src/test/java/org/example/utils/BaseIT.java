package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.PostgresTestcontainersExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(PostgresTestcontainersExtension.class)
public class BaseIT {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;


    public ResultActions performPostRequest(String url, String json, Object ... var) throws Exception {
        return performPostRequestInternal(url, json, var);
    }

    public ResultActions performPostRequest(String url, String json) throws Exception {
        return performPostRequestInternal(url, json, (Object) null);
    }

    private ResultActions performPostRequestInternal(String url, String json, Object ... var) throws Exception {
        return mockMvc
                .perform(post(url, var)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print());
    }

    public ResultActions performPutRequest(String url, String json) throws Exception {
        return performPutRequestInternal(url, json, (Object) null);
    }

    private ResultActions performPutRequestInternal(String url, String json, Object ... var) throws Exception {
        return mockMvc
                .perform(put(url, var)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print());
    }

    public ResultActions performGetRequest(String url, Map<String,String> params) throws Exception {
        MockHttpServletRequestBuilder rb = MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON);

        params.forEach(rb::queryParam);

        return mockMvc
                .perform(rb)
                .andDo(print());
    }

    public ResultActions performGetRequest(String url) throws Exception {
        return performGetRequest(url, Collections.emptyMap());
    }
}
