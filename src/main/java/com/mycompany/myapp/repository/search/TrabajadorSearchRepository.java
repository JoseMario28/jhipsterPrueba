package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Trabajador;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Trabajador} entity.
 */
public interface TrabajadorSearchRepository extends ElasticsearchRepository<Trabajador, Long> {
}
