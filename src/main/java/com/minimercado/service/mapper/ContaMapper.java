package com.minimercado.service.mapper;

import com.minimercado.domain.Conta;
import com.minimercado.domain.Venda;
import com.minimercado.service.dto.ContaDTO;
import com.minimercado.service.dto.VendaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conta} and its DTO {@link ContaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContaMapper extends EntityMapper<ContaDTO, Conta> {
    @Mapping(target = "venda", source = "venda", qualifiedByName = "vendaId")
    ContaDTO toDto(Conta s);

    @Named("vendaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VendaDTO toDtoVendaId(Venda venda);
}
