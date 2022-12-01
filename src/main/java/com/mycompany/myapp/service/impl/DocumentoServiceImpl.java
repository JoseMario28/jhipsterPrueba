package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DocumentoService;
import com.mycompany.myapp.domain.Documento;
import com.mycompany.myapp.repository.DocumentoRepository;
import com.mycompany.myapp.repository.search.DocumentoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Documento}.
 */
@Service
@Transactional
public class DocumentoServiceImpl implements DocumentoService {

    private final Logger log = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    private final Path root = Paths.get("uploads");

    private final DocumentoRepository documentoRepository;

    private final DocumentoSearchRepository documentoSearchRepository;

    public DocumentoServiceImpl(DocumentoRepository documentoRepository, DocumentoSearchRepository documentoSearchRepository) {
        this.documentoRepository = documentoRepository;
        this.documentoSearchRepository = documentoSearchRepository;
    }

    /**
     * Save a documento.
     *
     * @param documento the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Documento save(Documento documento) {
        log.debug("Request to save Documento : {}", documento);
        Documento result = documentoRepository.save(documento);
        documentoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the documentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Documento> findAll(Pageable pageable) {
        log.debug("Request to get all Documentos");
        return documentoRepository.findAll(pageable);
    }


    /**
     * Get one documento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Documento> findOne(Long id) {
        log.debug("Request to get Documento : {}", id);
        return documentoRepository.findById(id);
    }

    /**
     * Delete the documento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Documento : {}", id);
        documentoRepository.deleteById(id);
        documentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the documento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Documento> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Documentos for query {}", query);
        return documentoSearchRepository.search(queryStringQuery(query), pageable);    }




        @Override
        public void init() {
          try {
            Files.createDirectories(root);
          } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
          }
        }

        @Override
        public void save2(MultipartFile file) {
          try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
          } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
              throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
          }
        }

        @Override
        public Resource load(String filename) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void deleteAll() {
            // TODO Auto-generated method stub

        }

        @Override
        public Stream<Path> loadAll() {
            // TODO Auto-generated method stub
            return null;
        }


}
