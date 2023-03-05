package com.minimercado.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.minimercado.domain.Venda} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VendaDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime data;

    private String status;

    private Set<ProdutoDTO> produtos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VendaDTO)) {
            return false;
        }

        VendaDTO vendaDTO = (VendaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vendaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendaDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", status='" + getStatus() + "'" +
            ", produtos=" + getProdutos() +
            "}";
    }
}
