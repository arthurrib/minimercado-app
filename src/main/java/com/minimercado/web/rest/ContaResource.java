package com.minimercado.web.rest;

import com.minimercado.repository.ContaRepository;
import com.minimercado.service.ContaService;
import com.minimercado.service.dto.ContaDTO;
import com.minimercado.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.minimercado.domain.Conta}.
 */
@RestController
@RequestMapping("/api")
public class ContaResource {

    private final Logger log = LoggerFactory.getLogger(ContaResource.class);

    private static final String ENTITY_NAME = "conta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContaService contaService;

    private final ContaRepository contaRepository;

    public ContaResource(ContaService contaService, ContaRepository contaRepository) {
        this.contaService = contaService;
        this.contaRepository = contaRepository;
    }

    /**
     * {@code POST  /contas} : Create a new conta.
     *
     * @param contaDTO the contaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contaDTO, or with status {@code 400 (Bad Request)} if the conta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contas")
    public ResponseEntity<ContaDTO> createConta(@Valid @RequestBody ContaDTO contaDTO) throws URISyntaxException {
        log.debug("REST request to save Conta : {}", contaDTO);
        if (contaDTO.getId() != null) {
            throw new BadRequestAlertException("A new conta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContaDTO result = contaService.save(contaDTO);
        return ResponseEntity
            .created(new URI("/api/contas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contas/:id} : Updates an existing conta.
     *
     * @param id the id of the contaDTO to save.
     * @param contaDTO the contaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contaDTO,
     * or with status {@code 400 (Bad Request)} if the contaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contas/{id}")
    public ResponseEntity<ContaDTO> updateConta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContaDTO contaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Conta : {}, {}", id, contaDTO);
        if (contaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContaDTO result = contaService.update(contaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contas/:id} : Partial updates given fields of an existing conta, field will ignore if it is null
     *
     * @param id the id of the contaDTO to save.
     * @param contaDTO the contaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contaDTO,
     * or with status {@code 400 (Bad Request)} if the contaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContaDTO> partialUpdateConta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContaDTO contaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Conta partially : {}, {}", id, contaDTO);
        if (contaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContaDTO> result = contaService.partialUpdate(contaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contas} : get all the contas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contas in body.
     */
    @GetMapping("/contas")
    public ResponseEntity<List<ContaDTO>> getAllContas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Contas");
        Page<ContaDTO> page = contaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contas/:id} : get the "id" conta.
     *
     * @param id the id of the contaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contas/{id}")
    public ResponseEntity<ContaDTO> getConta(@PathVariable Long id) {
        log.debug("REST request to get Conta : {}", id);
        Optional<ContaDTO> contaDTO = contaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contaDTO);
    }

    /**
     * {@code DELETE  /contas/:id} : delete the "id" conta.
     *
     * @param id the id of the contaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contas/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        log.debug("REST request to delete Conta : {}", id);
        contaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
