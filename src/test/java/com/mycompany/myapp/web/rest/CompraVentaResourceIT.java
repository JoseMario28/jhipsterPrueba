package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PruebaApp;
import com.mycompany.myapp.domain.CompraVenta;
import com.mycompany.myapp.repository.CompraVentaRepository;
import com.mycompany.myapp.repository.search.CompraVentaSearchRepository;
import com.mycompany.myapp.service.CompraVentaService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CompraVentaResource} REST controller.
 */
@SpringBootTest(classes = PruebaApp.class)
public class CompraVentaResourceIT {

    private static final LocalDate DEFAULT_FECHA_VENTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENTA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_GARANTIA = 1;
    private static final Integer UPDATED_GARANTIA = 2;

    private static final Double DEFAULT_PRECIO_TOTAL = 1D;
    private static final Double UPDATED_PRECIO_TOTAL = 2D;

    @Autowired
    private CompraVentaRepository compraVentaRepository;

    @Autowired
    private CompraVentaService compraVentaService;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.CompraVentaSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompraVentaSearchRepository mockCompraVentaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCompraVentaMockMvc;

    private CompraVenta compraVenta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompraVentaResource compraVentaResource = new CompraVentaResource(compraVentaService);
        this.restCompraVentaMockMvc = MockMvcBuilders.standaloneSetup(compraVentaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompraVenta createEntity(EntityManager em) {
        CompraVenta compraVenta = new CompraVenta()
            .fechaVenta(DEFAULT_FECHA_VENTA)
            .garantia(DEFAULT_GARANTIA)
            .precioTotal(DEFAULT_PRECIO_TOTAL);
        return compraVenta;
    }

    @BeforeEach
    public void initTest() {
        compraVenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompraVenta() throws Exception {
        int databaseSizeBeforeCreate = compraVentaRepository.findAll().size();

        // Create the CompraVenta
        restCompraVentaMockMvc.perform(post("/api/compra-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraVenta)))
            .andExpect(status().isCreated());

        // Validate the CompraVenta in the database
        List<CompraVenta> compraVentaList = compraVentaRepository.findAll();
        assertThat(compraVentaList).hasSize(databaseSizeBeforeCreate + 1);
        CompraVenta testCompraVenta = compraVentaList.get(compraVentaList.size() - 1);
        assertThat(testCompraVenta.getFechaVenta()).isEqualTo(DEFAULT_FECHA_VENTA);
        assertThat(testCompraVenta.getGarantia()).isEqualTo(DEFAULT_GARANTIA);
        assertThat(testCompraVenta.getPrecioTotal()).isEqualTo(DEFAULT_PRECIO_TOTAL);

        // Validate the CompraVenta in Elasticsearch
        verify(mockCompraVentaSearchRepository, times(1)).save(testCompraVenta);
    }

    @Test
    @Transactional
    public void createCompraVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compraVentaRepository.findAll().size();

        // Create the CompraVenta with an existing ID
        compraVenta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraVentaMockMvc.perform(post("/api/compra-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraVenta)))
            .andExpect(status().isBadRequest());

        // Validate the CompraVenta in the database
        List<CompraVenta> compraVentaList = compraVentaRepository.findAll();
        assertThat(compraVentaList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompraVenta in Elasticsearch
        verify(mockCompraVentaSearchRepository, times(0)).save(compraVenta);
    }


    @Test
    @Transactional
    public void getAllCompraVentas() throws Exception {
        // Initialize the database
        compraVentaRepository.saveAndFlush(compraVenta);

        // Get all the compraVentaList
        restCompraVentaMockMvc.perform(get("/api/compra-ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compraVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaVenta").value(hasItem(DEFAULT_FECHA_VENTA.toString())))
            .andExpect(jsonPath("$.[*].garantia").value(hasItem(DEFAULT_GARANTIA)))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCompraVenta() throws Exception {
        // Initialize the database
        compraVentaRepository.saveAndFlush(compraVenta);

        // Get the compraVenta
        restCompraVentaMockMvc.perform(get("/api/compra-ventas/{id}", compraVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compraVenta.getId().intValue()))
            .andExpect(jsonPath("$.fechaVenta").value(DEFAULT_FECHA_VENTA.toString()))
            .andExpect(jsonPath("$.garantia").value(DEFAULT_GARANTIA))
            .andExpect(jsonPath("$.precioTotal").value(DEFAULT_PRECIO_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompraVenta() throws Exception {
        // Get the compraVenta
        restCompraVentaMockMvc.perform(get("/api/compra-ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompraVenta() throws Exception {
        // Initialize the database
        compraVentaService.save(compraVenta);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCompraVentaSearchRepository);

        int databaseSizeBeforeUpdate = compraVentaRepository.findAll().size();

        // Update the compraVenta
        CompraVenta updatedCompraVenta = compraVentaRepository.findById(compraVenta.getId()).get();
        // Disconnect from session so that the updates on updatedCompraVenta are not directly saved in db
        em.detach(updatedCompraVenta);
        updatedCompraVenta
            .fechaVenta(UPDATED_FECHA_VENTA)
            .garantia(UPDATED_GARANTIA)
            .precioTotal(UPDATED_PRECIO_TOTAL);

        restCompraVentaMockMvc.perform(put("/api/compra-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompraVenta)))
            .andExpect(status().isOk());

        // Validate the CompraVenta in the database
        List<CompraVenta> compraVentaList = compraVentaRepository.findAll();
        assertThat(compraVentaList).hasSize(databaseSizeBeforeUpdate);
        CompraVenta testCompraVenta = compraVentaList.get(compraVentaList.size() - 1);
        assertThat(testCompraVenta.getFechaVenta()).isEqualTo(UPDATED_FECHA_VENTA);
        assertThat(testCompraVenta.getGarantia()).isEqualTo(UPDATED_GARANTIA);
        assertThat(testCompraVenta.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);

        // Validate the CompraVenta in Elasticsearch
        verify(mockCompraVentaSearchRepository, times(1)).save(testCompraVenta);
    }

    @Test
    @Transactional
    public void updateNonExistingCompraVenta() throws Exception {
        int databaseSizeBeforeUpdate = compraVentaRepository.findAll().size();

        // Create the CompraVenta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompraVentaMockMvc.perform(put("/api/compra-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraVenta)))
            .andExpect(status().isBadRequest());

        // Validate the CompraVenta in the database
        List<CompraVenta> compraVentaList = compraVentaRepository.findAll();
        assertThat(compraVentaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompraVenta in Elasticsearch
        verify(mockCompraVentaSearchRepository, times(0)).save(compraVenta);
    }

    @Test
    @Transactional
    public void deleteCompraVenta() throws Exception {
        // Initialize the database
        compraVentaService.save(compraVenta);

        int databaseSizeBeforeDelete = compraVentaRepository.findAll().size();

        // Delete the compraVenta
        restCompraVentaMockMvc.perform(delete("/api/compra-ventas/{id}", compraVenta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CompraVenta> compraVentaList = compraVentaRepository.findAll();
        assertThat(compraVentaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompraVenta in Elasticsearch
        verify(mockCompraVentaSearchRepository, times(1)).deleteById(compraVenta.getId());
    }

    @Test
    @Transactional
    public void searchCompraVenta() throws Exception {
        // Initialize the database
        compraVentaService.save(compraVenta);
        when(mockCompraVentaSearchRepository.search(queryStringQuery("id:" + compraVenta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(compraVenta), PageRequest.of(0, 1), 1));
        // Search the compraVenta
        restCompraVentaMockMvc.perform(get("/api/_search/compra-ventas?query=id:" + compraVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compraVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaVenta").value(hasItem(DEFAULT_FECHA_VENTA.toString())))
            .andExpect(jsonPath("$.[*].garantia").value(hasItem(DEFAULT_GARANTIA)))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraVenta.class);
        CompraVenta compraVenta1 = new CompraVenta();
        compraVenta1.setId(1L);
        CompraVenta compraVenta2 = new CompraVenta();
        compraVenta2.setId(compraVenta1.getId());
        assertThat(compraVenta1).isEqualTo(compraVenta2);
        compraVenta2.setId(2L);
        assertThat(compraVenta1).isNotEqualTo(compraVenta2);
        compraVenta1.setId(null);
        assertThat(compraVenta1).isNotEqualTo(compraVenta2);
    }
}
