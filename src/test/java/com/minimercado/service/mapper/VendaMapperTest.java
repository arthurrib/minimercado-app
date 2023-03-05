package com.minimercado.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VendaMapperTest {

    private VendaMapper vendaMapper;

    @BeforeEach
    public void setUp() {
        vendaMapper = new VendaMapperImpl();
    }
}
