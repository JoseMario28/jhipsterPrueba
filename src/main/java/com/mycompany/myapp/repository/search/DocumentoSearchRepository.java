package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Documento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Documento} entity.
 */
public interface DocumentoSearchRepository extends ElasticsearchRepository<Documento, Long> {
}
