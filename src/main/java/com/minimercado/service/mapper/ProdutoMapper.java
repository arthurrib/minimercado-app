package com.minimercado.service.mapper;

import com.minimercado.domain.Estoque;
import com.minimercado.domain.Produto;
import com.minimercado.service.dto.EstoqueDTO;
import com.minimercado.service.dto.ProdutoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produto} and its DTO {@link ProdutoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {
    @Mapping(target = "estoque", source = "estoque", qualifiedByName = "estoqueId")
    ProdutoDTO toDto(Produto s);

    @Named("estoqueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstoqueDTO toDtoEstoqueId(Estoque estoque);
}
