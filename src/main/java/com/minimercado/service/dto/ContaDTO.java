package com.minimercado.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.minimercado.domain.Conta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private BigDecimal valor;

    private VendaDTO venda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public VendaDTO getVenda() {
        return venda;
    }

    public void setVenda(VendaDTO venda) {
        this.venda = venda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContaDTO)) {
            return false;
        }

        ContaDTO contaDTO = (ContaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valor=" + getValor() +
            ", venda=" + getVenda() +
            "}";
    }
}
