package com.minimercado.service;

import com.minimercado.domain.Conta;
import com.minimercado.repository.ContaRepository;
import com.minimercado.service.dto.ContaDTO;
import com.minimercado.service.mapper.ContaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Conta}.
 */
@Service
@Transactional
public class ContaService {

    private final Logger log = LoggerFactory.getLogger(ContaService.class);

    private final ContaRepository contaRepository;

    private final ContaMapper contaMapper;

    public ContaService(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    /**
     * Save a conta.
     *
     * @param contaDTO the entity to save.
     * @return the persisted entity.
     */
    public ContaDTO save(ContaDTO contaDTO) {
        log.debug("Request to save Conta : {}", contaDTO);
        Conta conta = contaMapper.toEntity(contaDTO);
        conta = contaRepository.save(conta);
        return contaMapper.toDto(conta);
    }

    /**
     * Update a conta.
     *
     * @param contaDTO the entity to save.
     * @return the persisted entity.
     */
    public ContaDTO update(ContaDTO contaDTO) {
        log.debug("Request to update Conta : {}", contaDTO);
        Conta conta = contaMapper.toEntity(contaDTO);
        conta = contaRepository.save(conta);
        return contaMapper.toDto(conta);
    }

    /**
     * Partially update a conta.
     *
     * @param contaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContaDTO> partialUpdate(ContaDTO contaDTO) {
        log.debug("Request to partially update Conta : {}", contaDTO);

        return contaRepository
            .findById(contaDTO.getId())
            .map(existingConta -> {
                contaMapper.partialUpdate(existingConta, contaDTO);

                return existingConta;
            })
            .map(contaRepository::save)
            .map(contaMapper::toDto);
    }

    /**
     * Get all the contas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contas");
        return contaRepository.findAll(pageable).map(contaMapper::toDto);
    }

    /**
     * Get one conta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContaDTO> findOne(Long id) {
        log.debug("Request to get Conta : {}", id);
        return contaRepository.findById(id).map(contaMapper::toDto);
    }

    /**
     * Delete the conta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Conta : {}", id);
        contaRepository.deleteById(id);
    }
}
