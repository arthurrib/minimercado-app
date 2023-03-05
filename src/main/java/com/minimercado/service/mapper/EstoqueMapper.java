package com.minimercado.service.mapper;

import com.minimercado.domain.Estoque;
import com.minimercado.service.dto.EstoqueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estoque} and its DTO {@link EstoqueDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstoqueMapper extends EntityMapper<EstoqueDTO, Estoque> {}
