package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Vehiculo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Vehiculo} entity.
 */
public interface VehiculoSearchRepository extends ElasticsearchRepository<Vehiculo, Long> {
}
