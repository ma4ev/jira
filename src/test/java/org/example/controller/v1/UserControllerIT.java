package org.example.controller.v1;

import lombok.val;
import org.example.transport.dto.UserFilterRequest;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.utils.BaseIT;
import org.example.utils.DataProviderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class UserControllerIT extends BaseIT {

    private static final String USERS_REQUEST_URL = "/api/v1/users";

    @Autowired
    UserRepository userRepository;

    UserFilterRequest filterRequest;

    @BeforeEach
    void setUp() {
        filterRequest = new UserFilterRequest();
    }

    @Test
    @Sql(scripts = "users_getAll.sql")
    void getAll_test() throws Exception {

        //when
        ResultActions result = performGetRequest(USERS_REQUEST_URL);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)));
    }

    @Test
    void create_test() throws Exception {
        //given
        User user = createNewUser();

        val json = objectMapper.writeValueAsString(user);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL, json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.middleName", is(user.getMiddleName())))
                .andExpect(jsonPath("$.phone", is(user.getPhone())))
                .andExpect(jsonPath("$.tagName", is("@f.lastname")))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").doesNotExist());

        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(1);
    }

    @DisplayName("getFiltered when search by telegram and pattern is not null and not empty")
    @ParameterizedTest
    @ValueSource(strings = {"lyska", "lys", "LYSKA", "LYS"})
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byTelegram(String value) throws Exception {
        //given
        filterRequest.setTelegram(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", hasSize(1)));
    }

    @DisplayName("getFiltered when search by telegram and pattern is null or empty")
    @ParameterizedTest
    @NullAndEmptySource
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byTelegramAndValueIsNullOrEmpty(String value) throws Exception {
        //given
        filterRequest.setTelegram(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", hasSize(4)));
    }

    @DisplayName("getFiltered when search by telegram and value is not matched")
    @Sql(scripts = "users_getFiltered.sql")
    @Test
    void getFiltered_byTelegramAndValueIsNotMatched() throws Exception {
        //given
        filterRequest.setTelegram("not matched value");

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id").doesNotExist());

    }

    @DisplayName("getFiltered when search by id and pattern is not null and size is 1")
    @Test
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byIdOneItem() throws Exception {
        //given
        filterRequest.setIds(List.of(1L));

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$..id", hasSize(1)));
    }

    @DisplayName("getFiltered when search by id and pattern is not null and size is 2")
    @Test
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byIdTwoItems() throws Exception {
        //given
        filterRequest.setIds(Arrays.asList(1L, 2L));

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$..id", hasSize(2)));
    }

    @DisplayName("getFiltered when search by id and pattern is null or empty list")
    @ParameterizedTest
    @MethodSource("getNullAndEmptyListArgumentProvider")
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byIdAndValueIsNullOrEmpty(List<Long> ids) throws Exception {
        //given
        filterRequest.setIds(ids);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", hasSize(4)));
    }

    private static Stream<Arguments> getNullAndEmptyListArgumentProvider(){
        return DataProviderUtils.getNullAndEmptyListArgumentProvider();
    }

    @DisplayName("getFiltered when search by id and pattern is not matched")
    @Test
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byIdAndValueNotMatched() throws Exception {
        //given
        filterRequest.setIds(List.of(1000L));

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id").doesNotExist());
    }

    @DisplayName("getFiltered when search by projectId and value is not null and not empty")
    @Test
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byProjectId() throws Exception {
        //given
        filterRequest.setProjectId(1L);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$..id", hasSize(2)));
    }

    @DisplayName("getFiltered when search by projectId and value is null")
    @Test
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byProjectIdAndValueIsNull() throws Exception {
        //given
        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", hasSize(4)));
    }

    @DisplayName("getFiltered when search by projectId and pattern is not matched")
    @Test
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byProjectIdAndValueIsNotMatched() throws Exception {
        //given
        filterRequest.setProjectId(1000L);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", hasSize(0)));
    }

    @DisplayName("getFiltered when search by first name")
    @ParameterizedTest
    @ValueSource(strings = {"Люся", "Люс", "ЛЮСЯ", "ЛЮС"})
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byFirstName(String value) throws Exception {
        //given
        filterRequest.setFirstName(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName", is("Люся")))
                .andExpect(jsonPath("$..id", hasSize(1)));
    }

    @DisplayName("getFiltered when search by first name and value is null or empty")
    @ParameterizedTest
    @NullAndEmptySource
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byFirstNameAndValueIsNullOrEmpty(String value) throws Exception {
        //given
        filterRequest.setFirstName(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", hasSize(4)));
    }

    @DisplayName("getFiltered when search by first name and value is not matched")
    @Sql(scripts = "users_getFiltered.sql")
    @Test
    void getFiltered_byFirstNameAndValueIsNotMatched() throws Exception {
        //given
        filterRequest.setFirstName("not matched value");

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id").doesNotExist());
    }

    @DisplayName("getFiltered when search by last name")
    @ParameterizedTest
    @ValueSource(strings = {"Варежкина", "Варежкин", "ВАРЕЖКИНА", "ВАРЕЖКИН"})
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byLastName(String value) throws Exception {
        //given
        filterRequest.setLastName(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].lastName", is("Варежкина")))
                .andExpect(jsonPath("$..id", hasSize(1)));
    }

    @DisplayName("getFiltered when search by last name and value is null or empty")
    @ParameterizedTest
    @NullAndEmptySource
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byLastNameAndValueIsNullOrEmpty(String value) throws Exception {
        //given
        filterRequest.setLastName(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", hasSize(4)));
    }

    @DisplayName("getFiltered when search by last name and value is not matched")
    @Sql(scripts = "users_getFiltered.sql")
    @Test
    void getFiltered_byLastNameAndValueIsNotMatched() throws Exception {
        //given
        filterRequest.setLastName("not matched value");

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id").doesNotExist());
    }

    @DisplayName("getFiltered when search by middle name")
    @ParameterizedTest
    @ValueSource(strings = {"Ильинична", "Ильиничн", "ИЛЬИНИЧНА", "ИЛЬИНИЧН"})
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byMiddleName(String value) throws Exception {
        //given
        filterRequest.setMiddleName(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].middleName", is("Ильинична")))
                .andExpect(jsonPath("$..id", hasSize(1)));
    }

    @DisplayName("getFiltered when search by middle name and value is null or empty")
    @ParameterizedTest
    @NullAndEmptySource
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byMiddleNameAndValueIsNullOrEmpty(String value) throws Exception {
        //given
        filterRequest.setMiddleName(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", hasSize(4)));
    }

    @DisplayName("getFiltered when search by middle name and value is not matched")
    @Sql(scripts = "users_getFiltered.sql")
    @Test
    void getFiltered_byMiddleNameAndValueIsNotMatched() throws Exception {
        //given
        filterRequest.setMiddleName("not matched value");

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id").doesNotExist());
    }

    @DisplayName("getFiltered when search by email")
    @ParameterizedTest
    @ValueSource(strings = {"tr@uyt.rt", "tr@uyt.r", "TR@UYT.RT", "TR@UYT.R"})
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byEmail(String value) throws Exception {
        //given
        filterRequest.setEmail(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].email", is("tr@uyt.rt")))
                .andExpect(jsonPath("$..id", hasSize(1)));
    }

    @DisplayName("getFiltered when search by middle name and value is null or empty")
    @ParameterizedTest
    @NullAndEmptySource
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byEmailAndValueIsNullOrEmpty(String value) throws Exception {
        //given
        filterRequest.setEmail(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", hasSize(4)));
    }

    @DisplayName("getFiltered when search by email and value is not matched")
    @Sql(scripts = "users_getFiltered.sql")
    @Test
    void getFiltered_byEmailNameAndValueIsNotMatched() throws Exception {
        //given
        filterRequest.setEmail("not matched value");

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id").doesNotExist());
    }

    @DisplayName("getFiltered when search by phone")
    @ParameterizedTest
    @ValueSource(strings = {"+79211112233", "+7921111223"})
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byPhone(String value) throws Exception {
        //given
        filterRequest.setPhone(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].phone", is("+79211112233")))
                .andExpect(jsonPath("$..id", hasSize(1)));
    }

    @DisplayName("getFiltered when search by phone and value is null or empty")
    @ParameterizedTest
    @NullAndEmptySource
    @Sql(scripts = "users_getFiltered.sql")
    void getFiltered_byPhoneAndValueIsNullOrEmpty(String value) throws Exception {
        //given
        filterRequest.setPhone(value);

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", hasSize(4)));
    }

    @DisplayName("getFiltered when search by phone and value is not matched")
    @Sql(scripts = "users_getFiltered.sql")
    @Test
    void getFiltered_byPhoneAndValueIsNotMatched() throws Exception {
        //given
        filterRequest.setPhone("not matched value");

        String json = objectMapper.writeValueAsString(filterRequest);

        //when
        ResultActions result = performPostRequest(USERS_REQUEST_URL + "/getFiltered", json);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$..id").doesNotExist());
    }

    private User createNewUser(){
        User user = new User();

        user.setEmail("test@ds.ru");

        user.setFirstName("firstname");

        user.setLastName("lastname");

        user.setMiddleName("middlename");

        user.setPhone("+79212222223");

        return user;
    }
}