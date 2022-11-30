package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Comision;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Comision} entity.
 */
public interface ComisionSearchRepository extends ElasticsearchRepository<Comision, Long> {
}
