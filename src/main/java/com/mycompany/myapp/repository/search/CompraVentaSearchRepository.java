package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.CompraVenta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompraVenta} entity.
 */
public interface CompraVentaSearchRepository extends ElasticsearchRepository<CompraVenta, Long> {
}
