package com.minimercado.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContaMapperTest {

    private ContaMapper contaMapper;

    @BeforeEach
    public void setUp() {
        contaMapper = new ContaMapperImpl();
    }
}
