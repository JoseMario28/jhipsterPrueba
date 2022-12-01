package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Documento;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service Interface for managing {@link Documento}.
 */
public interface DocumentoService {


     void init();

     void save2(MultipartFile file);

     Resource load(String filename);

     void deleteAll();

     Stream<Path> loadAll();
        /**
     * Save a documento.
     *
     * @param documento the entity to save.
     * @return the persisted entity.
     */
    Documento save(Documento documento);

    /**
     * Get all the documentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Documento> findAll(Pageable pageable);


    /**
     * Get the "id" documento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Documento> findOne(Long id);

    /**
     * Delete the "id" documento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the documento corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Documento> search(String query, Pageable pageable);
}
