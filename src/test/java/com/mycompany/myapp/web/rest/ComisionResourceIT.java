package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PruebaApp;
import com.mycompany.myapp.domain.Comision;
import com.mycompany.myapp.repository.ComisionRepository;
import com.mycompany.myapp.repository.search.ComisionSearchRepository;
import com.mycompany.myapp.service.ComisionService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
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
 * Integration tests for the {@Link ComisionResource} REST controller.
 */
@SpringBootTest(classes = PruebaApp.class)
public class ComisionResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_RANGO = 1;
    private static final Integer UPDATED_RANGO = 2;

    private static final Double DEFAULT_PORCENTAJE = 1D;
    private static final Double UPDATED_PORCENTAJE = 2D;

    @Autowired
    private ComisionRepository comisionRepository;

    @Autowired
    private ComisionService comisionService;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.ComisionSearchRepositoryMockConfiguration
     */
    @Autowired
    private ComisionSearchRepository mockComisionSearchRepository;

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

    private MockMvc restComisionMockMvc;

    private Comision comision;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComisionResource comisionResource = new ComisionResource(comisionService);
        this.restComisionMockMvc = MockMvcBuilders.standaloneSetup(comisionResource)
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
    public static Comision createEntity(EntityManager em) {
        Comision comision = new Comision()
            .nombre(DEFAULT_NOMBRE)
            .rango(DEFAULT_RANGO)
            .porcentaje(DEFAULT_PORCENTAJE);
        return comision;
    }

    @BeforeEach
    public void initTest() {
        comision = createEntity(em);
    }

    @Test
    @Transactional
    public void createComision() throws Exception {
        int databaseSizeBeforeCreate = comisionRepository.findAll().size();

        // Create the Comision
        restComisionMockMvc.perform(post("/api/comisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comision)))
            .andExpect(status().isCreated());

        // Validate the Comision in the database
        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeCreate + 1);
        Comision testComision = comisionList.get(comisionList.size() - 1);
        assertThat(testComision.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testComision.getRango()).isEqualTo(DEFAULT_RANGO);
        assertThat(testComision.getPorcentaje()).isEqualTo(DEFAULT_PORCENTAJE);

        // Validate the Comision in Elasticsearch
        verify(mockComisionSearchRepository, times(1)).save(testComision);
    }

    @Test
    @Transactional
    public void createComisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comisionRepository.findAll().size();

        // Create the Comision with an existing ID
        comision.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComisionMockMvc.perform(post("/api/comisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comision)))
            .andExpect(status().isBadRequest());

        // Validate the Comision in the database
        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Comision in Elasticsearch
        verify(mockComisionSearchRepository, times(0)).save(comision);
    }


    @Test
    @Transactional
    public void getAllComisions() throws Exception {
        // Initialize the database
        comisionRepository.saveAndFlush(comision);

        // Get all the comisionList
        restComisionMockMvc.perform(get("/api/comisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comision.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].rango").value(hasItem(DEFAULT_RANGO)))
            .andExpect(jsonPath("$.[*].porcentaje").value(hasItem(DEFAULT_PORCENTAJE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getComision() throws Exception {
        // Initialize the database
        comisionRepository.saveAndFlush(comision);

        // Get the comision
        restComisionMockMvc.perform(get("/api/comisions/{id}", comision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comision.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.rango").value(DEFAULT_RANGO))
            .andExpect(jsonPath("$.porcentaje").value(DEFAULT_PORCENTAJE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingComision() throws Exception {
        // Get the comision
        restComisionMockMvc.perform(get("/api/comisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComision() throws Exception {
        // Initialize the database
        comisionService.save(comision);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockComisionSearchRepository);

        int databaseSizeBeforeUpdate = comisionRepository.findAll().size();

        // Update the comision
        Comision updatedComision = comisionRepository.findById(comision.getId()).get();
        // Disconnect from session so that the updates on updatedComision are not directly saved in db
        em.detach(updatedComision);
        updatedComision
            .nombre(UPDATED_NOMBRE)
            .rango(UPDATED_RANGO)
            .porcentaje(UPDATED_PORCENTAJE);

        restComisionMockMvc.perform(put("/api/comisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComision)))
            .andExpect(status().isOk());

        // Validate the Comision in the database
        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeUpdate);
        Comision testComision = comisionList.get(comisionList.size() - 1);
        assertThat(testComision.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testComision.getRango()).isEqualTo(UPDATED_RANGO);
        assertThat(testComision.getPorcentaje()).isEqualTo(UPDATED_PORCENTAJE);

        // Validate the Comision in Elasticsearch
        verify(mockComisionSearchRepository, times(1)).save(testComision);
    }

    @Test
    @Transactional
    public void updateNonExistingComision() throws Exception {
        int databaseSizeBeforeUpdate = comisionRepository.findAll().size();

        // Create the Comision

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComisionMockMvc.perform(put("/api/comisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comision)))
            .andExpect(status().isBadRequest());

        // Validate the Comision in the database
        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Comision in Elasticsearch
        verify(mockComisionSearchRepository, times(0)).save(comision);
    }

    @Test
    @Transactional
    public void deleteComision() throws Exception {
        // Initialize the database
        comisionService.save(comision);

        int databaseSizeBeforeDelete = comisionRepository.findAll().size();

        // Delete the comision
        restComisionMockMvc.perform(delete("/api/comisions/{id}", comision.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Comision> comisionList = comisionRepository.findAll();
        assertThat(comisionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Comision in Elasticsearch
        verify(mockComisionSearchRepository, times(1)).deleteById(comision.getId());
    }

    @Test
    @Transactional
    public void searchComision() throws Exception {
        // Initialize the database
        comisionService.save(comision);
        when(mockComisionSearchRepository.search(queryStringQuery("id:" + comision.getId())))
            .thenReturn(Collections.singletonList(comision));
        // Search the comision
        restComisionMockMvc.perform(get("/api/_search/comisions?query=id:" + comision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comision.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].rango").value(hasItem(DEFAULT_RANGO)))
            .andExpect(jsonPath("$.[*].porcentaje").value(hasItem(DEFAULT_PORCENTAJE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comision.class);
        Comision comision1 = new Comision();
        comision1.setId(1L);
        Comision comision2 = new Comision();
        comision2.setId(comision1.getId());
        assertThat(comision1).isEqualTo(comision2);
        comision2.setId(2L);
        assertThat(comision1).isNotEqualTo(comision2);
        comision1.setId(null);
        assertThat(comision1).isNotEqualTo(comision2);
    }
}
