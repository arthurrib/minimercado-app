package com.minimercado.repository;

import com.minimercado.domain.Venda;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class VendaRepositoryWithBagRelationshipsImpl implements VendaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Venda> fetchBagRelationships(Optional<Venda> venda) {
        return venda.map(this::fetchProdutos);
    }

    @Override
    public Page<Venda> fetchBagRelationships(Page<Venda> vendas) {
        return new PageImpl<>(fetchBagRelationships(vendas.getContent()), vendas.getPageable(), vendas.getTotalElements());
    }

    @Override
    public List<Venda> fetchBagRelationships(List<Venda> vendas) {
        return Optional.of(vendas).map(this::fetchProdutos).orElse(Collections.emptyList());
    }

    Venda fetchProdutos(Venda result) {
        return entityManager
            .createQuery("select venda from Venda venda left join fetch venda.produtos where venda is :venda", Venda.class)
            .setParameter("venda", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Venda> fetchProdutos(List<Venda> vendas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, vendas.size()).forEach(index -> order.put(vendas.get(index).getId(), index));
        List<Venda> result = entityManager
            .createQuery("select distinct venda from Venda venda left join fetch venda.produtos where venda in :vendas", Venda.class)
            .setParameter("vendas", vendas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
