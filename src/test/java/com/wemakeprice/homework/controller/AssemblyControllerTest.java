package com.wemakeprice.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeprice.homework.model.DivisionResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by blurrymargo on 2018. 9. 4..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssemblyControllerTest {
    final String INPUT_VALUES = "  !@# 8 a79A || | } { +_) 3(*---||}}{?><:\"  &^  %$!@#  $%^~`` \n y \t m \r Y  0x z Cc                ";
    final int BUNDLE_SIZE = 3;
    final String QUOTIENT = "A0a,3C7,c8m,9xY";
    final String REMINDER = "yz";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void successTest() throws Exception {

        MvcResult mvcResult = mvc.perform(
                post(String.format("/assemble"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("inputValue", INPUT_VALUES)
                        .param("bundleSize", String.valueOf(BUNDLE_SIZE))
        )
                .andDo(print())
                .andReturn();

        DivisionResult divisionResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DivisionResult.class);
        assertEquals(QUOTIENT, divisionResult.getQuotient());
        assertEquals(REMINDER, divisionResult.getReminder());
    }

    @Test
    public void exceptionAdviceTest() throws Exception {

        MvcResult mvcResult = mvc.perform(
                post(String.format("/assemble"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void badRequestTest() throws Exception {

        MvcResult mvcResult = mvc.perform(
                post(String.format("/assemble"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("inputValue", "")
                        .param("bundleSize", String.valueOf(0))
        )
                .andDo(print())
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

}