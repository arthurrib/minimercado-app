package com.minimercado.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.minimercado.domain.Estoque} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstoqueDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer qtd;

    @NotNull
    private BigDecimal valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstoqueDTO)) {
            return false;
        }

        EstoqueDTO estoqueDTO = (EstoqueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estoqueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstoqueDTO{" +
            "id=" + getId() +
            ", qtd=" + getQtd() +
            ", valor=" + getValor() +
            "}";
    }
}
