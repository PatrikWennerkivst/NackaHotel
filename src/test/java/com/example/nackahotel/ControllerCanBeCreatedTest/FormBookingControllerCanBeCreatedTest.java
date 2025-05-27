package com.example.nackahotel.ControllerCanBeCreatedTest;

import com.example.nackahotel.Controller.FormBookingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FormBookingControllerCanBeCreatedTest {

    @Autowired
    private FormBookingController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
