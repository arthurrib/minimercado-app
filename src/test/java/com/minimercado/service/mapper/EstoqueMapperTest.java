package com.minimercado.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstoqueMapperTest {

    private EstoqueMapper estoqueMapper;

    @BeforeEach
    public void setUp() {
        estoqueMapper = new EstoqueMapperImpl();
    }
}
