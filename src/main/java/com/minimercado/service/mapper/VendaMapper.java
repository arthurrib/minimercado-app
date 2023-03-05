package com.minimercado.service.mapper;

import com.minimercado.domain.Produto;
import com.minimercado.domain.Venda;
import com.minimercado.service.dto.ProdutoDTO;
import com.minimercado.service.dto.VendaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venda} and its DTO {@link VendaDTO}.
 */
@Mapper(componentModel = "spring")
public interface VendaMapper extends EntityMapper<VendaDTO, Venda> {
    @Mapping(target = "produtos", source = "produtos", qualifiedByName = "produtoIdSet")
    VendaDTO toDto(Venda s);

    @Mapping(target = "removeProduto", ignore = true)
    Venda toEntity(VendaDTO vendaDTO);

    @Named("produtoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProdutoDTO toDtoProdutoId(Produto produto);

    @Named("produtoIdSet")
    default Set<ProdutoDTO> toDtoProdutoIdSet(Set<Produto> produto) {
        return produto.stream().map(this::toDtoProdutoId).collect(Collectors.toSet());
    }
}
