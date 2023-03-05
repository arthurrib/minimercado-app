package com.minimercado.service;

import com.minimercado.domain.Estoque;
import com.minimercado.repository.EstoqueRepository;
import com.minimercado.service.dto.EstoqueDTO;
import com.minimercado.service.mapper.EstoqueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Estoque}.
 */
@Service
@Transactional
public class EstoqueService {

    private final Logger log = LoggerFactory.getLogger(EstoqueService.class);

    private final EstoqueRepository estoqueRepository;

    private final EstoqueMapper estoqueMapper;

    public EstoqueService(EstoqueRepository estoqueRepository, EstoqueMapper estoqueMapper) {
        this.estoqueRepository = estoqueRepository;
        this.estoqueMapper = estoqueMapper;
    }

    /**
     * Save a estoque.
     *
     * @param estoqueDTO the entity to save.
     * @return the persisted entity.
     */
    public EstoqueDTO save(EstoqueDTO estoqueDTO) {
        log.debug("Request to save Estoque : {}", estoqueDTO);
        Estoque estoque = estoqueMapper.toEntity(estoqueDTO);
        estoque = estoqueRepository.save(estoque);
        return estoqueMapper.toDto(estoque);
    }

    /**
     * Update a estoque.
     *
     * @param estoqueDTO the entity to save.
     * @return the persisted entity.
     */
    public EstoqueDTO update(EstoqueDTO estoqueDTO) {
        log.debug("Request to update Estoque : {}", estoqueDTO);
        Estoque estoque = estoqueMapper.toEntity(estoqueDTO);
        estoque = estoqueRepository.save(estoque);
        return estoqueMapper.toDto(estoque);
    }

    /**
     * Partially update a estoque.
     *
     * @param estoqueDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EstoqueDTO> partialUpdate(EstoqueDTO estoqueDTO) {
        log.debug("Request to partially update Estoque : {}", estoqueDTO);

        return estoqueRepository
            .findById(estoqueDTO.getId())
            .map(existingEstoque -> {
                estoqueMapper.partialUpdate(existingEstoque, estoqueDTO);

                return existingEstoque;
            })
            .map(estoqueRepository::save)
            .map(estoqueMapper::toDto);
    }

    /**
     * Get all the estoques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstoqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Estoques");
        return estoqueRepository.findAll(pageable).map(estoqueMapper::toDto);
    }

    /**
     * Get one estoque by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EstoqueDTO> findOne(Long id) {
        log.debug("Request to get Estoque : {}", id);
        return estoqueRepository.findById(id).map(estoqueMapper::toDto);
    }

    /**
     * Delete the estoque by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Estoque : {}", id);
        estoqueRepository.deleteById(id);
    }
}
