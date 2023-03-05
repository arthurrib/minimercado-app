package com.minimercado.web.rest;

import com.minimercado.repository.VendaRepository;
import com.minimercado.service.VendaService;
import com.minimercado.service.dto.VendaDTO;
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
 * REST controller for managing {@link com.minimercado.domain.Venda}.
 */
@RestController
@RequestMapping("/api")
public class VendaResource {

    private final Logger log = LoggerFactory.getLogger(VendaResource.class);

    private static final String ENTITY_NAME = "venda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendaService vendaService;

    private final VendaRepository vendaRepository;

    public VendaResource(VendaService vendaService, VendaRepository vendaRepository) {
        this.vendaService = vendaService;
        this.vendaRepository = vendaRepository;
    }

    /**
     * {@code POST  /vendas} : Create a new venda.
     *
     * @param vendaDTO the vendaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vendaDTO, or with status {@code 400 (Bad Request)} if the venda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vendas")
    public ResponseEntity<VendaDTO> createVenda(@Valid @RequestBody VendaDTO vendaDTO) throws URISyntaxException {
        log.debug("REST request to save Venda : {}", vendaDTO);
        if (vendaDTO.getId() != null) {
            throw new BadRequestAlertException("A new venda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VendaDTO result = vendaService.save(vendaDTO);
        return ResponseEntity
            .created(new URI("/api/vendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vendas/:id} : Updates an existing venda.
     *
     * @param id the id of the vendaDTO to save.
     * @param vendaDTO the vendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendaDTO,
     * or with status {@code 400 (Bad Request)} if the vendaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vendas/{id}")
    public ResponseEntity<VendaDTO> updateVenda(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VendaDTO vendaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Venda : {}, {}", id, vendaDTO);
        if (vendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vendaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VendaDTO result = vendaService.update(vendaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vendaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vendas/:id} : Partial updates given fields of an existing venda, field will ignore if it is null
     *
     * @param id the id of the vendaDTO to save.
     * @param vendaDTO the vendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendaDTO,
     * or with status {@code 400 (Bad Request)} if the vendaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vendaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vendas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VendaDTO> partialUpdateVenda(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VendaDTO vendaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Venda partially : {}, {}", id, vendaDTO);
        if (vendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vendaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vendaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VendaDTO> result = vendaService.partialUpdate(vendaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vendaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vendas} : get all the vendas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vendas in body.
     */
    @GetMapping("/vendas")
    public ResponseEntity<List<VendaDTO>> getAllVendas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Vendas");
        Page<VendaDTO> page;
        if (eagerload) {
            page = vendaService.findAllWithEagerRelationships(pageable);
        } else {
            page = vendaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vendas/:id} : get the "id" venda.
     *
     * @param id the id of the vendaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vendaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vendas/{id}")
    public ResponseEntity<VendaDTO> getVenda(@PathVariable Long id) {
        log.debug("REST request to get Venda : {}", id);
        Optional<VendaDTO> vendaDTO = vendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendaDTO);
    }

    /**
     * {@code DELETE  /vendas/:id} : delete the "id" venda.
     *
     * @param id the id of the vendaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vendas/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        log.debug("REST request to delete Venda : {}", id);
        vendaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
