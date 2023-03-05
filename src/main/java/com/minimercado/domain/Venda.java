package com.minimercado.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Venda.
 */
@Entity
@Table(name = "venda")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private ZonedDateTime data;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "venda")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "venda" }, allowSetters = true)
    private Set<Conta> contas = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_venda__produto",
        joinColumns = @JoinColumn(name = "venda_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estoque", "vendas" }, allowSetters = true)
    private Set<Produto> produtos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venda id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return this.data;
    }

    public Venda data(ZonedDateTime data) {
        this.setData(data);
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getStatus() {
        return this.status;
    }

    public Venda status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Conta> getContas() {
        return this.contas;
    }

    public void setContas(Set<Conta> contas) {
        if (this.contas != null) {
            this.contas.forEach(i -> i.setVenda(null));
        }
        if (contas != null) {
            contas.forEach(i -> i.setVenda(this));
        }
        this.contas = contas;
    }

    public Venda contas(Set<Conta> contas) {
        this.setContas(contas);
        return this;
    }

    public Venda addConta(Conta conta) {
        this.contas.add(conta);
        conta.setVenda(this);
        return this;
    }

    public Venda removeConta(Conta conta) {
        this.contas.remove(conta);
        conta.setVenda(null);
        return this;
    }

    public Set<Produto> getProdutos() {
        return this.produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Venda produtos(Set<Produto> produtos) {
        this.setProdutos(produtos);
        return this;
    }

    public Venda addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.getVendas().add(this);
        return this;
    }

    public Venda removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.getVendas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venda)) {
            return false;
        }
        return id != null && id.equals(((Venda) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venda{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
