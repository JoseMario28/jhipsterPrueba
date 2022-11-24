package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PruebaApp;
import com.mycompany.myapp.domain.Vehiculo;
import com.mycompany.myapp.repository.VehiculoRepository;
import com.mycompany.myapp.repository.search.VehiculoSearchRepository;
import com.mycompany.myapp.service.VehiculoService;
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
 * Integration tests for the {@Link VehiculoResource} REST controller.
 */
@SpringBootTest(classes = PruebaApp.class)
public class VehiculoResourceIT {

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final Float DEFAULT_KM = 1F;
    private static final Float UPDATED_KM = 2F;

    private static final LocalDate DEFAULT_ANNO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNO = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_PRECIO = 1F;
    private static final Float UPDATED_PRECIO = 2F;

    private static final String DEFAULT_PATENTE = "AAAAAAAAAA";
    private static final String UPDATED_PATENTE = "BBBBBBBBBB";

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private VehiculoService vehiculoService;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.VehiculoSearchRepositoryMockConfiguration
     */
    @Autowired
    private VehiculoSearchRepository mockVehiculoSearchRepository;

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

    private MockMvc restVehiculoMockMvc;

    private Vehiculo vehiculo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehiculoResource vehiculoResource = new VehiculoResource(vehiculoService);
        this.restVehiculoMockMvc = MockMvcBuilders.standaloneSetup(vehiculoResource)
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
    public static Vehiculo createEntity(EntityManager em) {
        Vehiculo vehiculo = new Vehiculo()
            .modelo(DEFAULT_MODELO)
            .marca(DEFAULT_MARCA)
            .km(DEFAULT_KM)
            .anno(DEFAULT_ANNO)
            .precio(DEFAULT_PRECIO)
            .patente(DEFAULT_PATENTE);
        return vehiculo;
    }

    @BeforeEach
    public void initTest() {
        vehiculo = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehiculo() throws Exception {
        int databaseSizeBeforeCreate = vehiculoRepository.findAll().size();

        // Create the Vehiculo
        restVehiculoMockMvc.perform(post("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculo)))
            .andExpect(status().isCreated());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeCreate + 1);
        Vehiculo testVehiculo = vehiculoList.get(vehiculoList.size() - 1);
        assertThat(testVehiculo.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testVehiculo.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testVehiculo.getKm()).isEqualTo(DEFAULT_KM);
        assertThat(testVehiculo.getAnno()).isEqualTo(DEFAULT_ANNO);
        assertThat(testVehiculo.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testVehiculo.getPatente()).isEqualTo(DEFAULT_PATENTE);

        // Validate the Vehiculo in Elasticsearch
        verify(mockVehiculoSearchRepository, times(1)).save(testVehiculo);
    }

    @Test
    @Transactional
    public void createVehiculoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiculoRepository.findAll().size();

        // Create the Vehiculo with an existing ID
        vehiculo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculoMockMvc.perform(post("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculo)))
            .andExpect(status().isBadRequest());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Vehiculo in Elasticsearch
        verify(mockVehiculoSearchRepository, times(0)).save(vehiculo);
    }


    @Test
    @Transactional
    public void getAllVehiculos() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        // Get all the vehiculoList
        restVehiculoMockMvc.perform(get("/api/vehiculos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())))
            .andExpect(jsonPath("$.[*].km").value(hasItem(DEFAULT_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].anno").value(hasItem(DEFAULT_ANNO.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].patente").value(hasItem(DEFAULT_PATENTE.toString())));
    }
    
    @Test
    @Transactional
    public void getVehiculo() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        // Get the vehiculo
        restVehiculoMockMvc.perform(get("/api/vehiculos/{id}", vehiculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehiculo.getId().intValue()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA.toString()))
            .andExpect(jsonPath("$.km").value(DEFAULT_KM.doubleValue()))
            .andExpect(jsonPath("$.anno").value(DEFAULT_ANNO.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.patente").value(DEFAULT_PATENTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehiculo() throws Exception {
        // Get the vehiculo
        restVehiculoMockMvc.perform(get("/api/vehiculos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehiculo() throws Exception {
        // Initialize the database
        vehiculoService.save(vehiculo);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockVehiculoSearchRepository);

        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();

        // Update the vehiculo
        Vehiculo updatedVehiculo = vehiculoRepository.findById(vehiculo.getId()).get();
        // Disconnect from session so that the updates on updatedVehiculo are not directly saved in db
        em.detach(updatedVehiculo);
        updatedVehiculo
            .modelo(UPDATED_MODELO)
            .marca(UPDATED_MARCA)
            .km(UPDATED_KM)
            .anno(UPDATED_ANNO)
            .precio(UPDATED_PRECIO)
            .patente(UPDATED_PATENTE);

        restVehiculoMockMvc.perform(put("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehiculo)))
            .andExpect(status().isOk());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
        Vehiculo testVehiculo = vehiculoList.get(vehiculoList.size() - 1);
        assertThat(testVehiculo.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testVehiculo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testVehiculo.getKm()).isEqualTo(UPDATED_KM);
        assertThat(testVehiculo.getAnno()).isEqualTo(UPDATED_ANNO);
        assertThat(testVehiculo.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testVehiculo.getPatente()).isEqualTo(UPDATED_PATENTE);

        // Validate the Vehiculo in Elasticsearch
        verify(mockVehiculoSearchRepository, times(1)).save(testVehiculo);
    }

    @Test
    @Transactional
    public void updateNonExistingVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();

        // Create the Vehiculo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculoMockMvc.perform(put("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculo)))
            .andExpect(status().isBadRequest());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Vehiculo in Elasticsearch
        verify(mockVehiculoSearchRepository, times(0)).save(vehiculo);
    }

    @Test
    @Transactional
    public void deleteVehiculo() throws Exception {
        // Initialize the database
        vehiculoService.save(vehiculo);

        int databaseSizeBeforeDelete = vehiculoRepository.findAll().size();

        // Delete the vehiculo
        restVehiculoMockMvc.perform(delete("/api/vehiculos/{id}", vehiculo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Vehiculo in Elasticsearch
        verify(mockVehiculoSearchRepository, times(1)).deleteById(vehiculo.getId());
    }

    @Test
    @Transactional
    public void searchVehiculo() throws Exception {
        // Initialize the database
        vehiculoService.save(vehiculo);
        when(mockVehiculoSearchRepository.search(queryStringQuery("id:" + vehiculo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(vehiculo), PageRequest.of(0, 1), 1));
        // Search the vehiculo
        restVehiculoMockMvc.perform(get("/api/_search/vehiculos?query=id:" + vehiculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].km").value(hasItem(DEFAULT_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].anno").value(hasItem(DEFAULT_ANNO.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].patente").value(hasItem(DEFAULT_PATENTE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehiculo.class);
        Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(1L);
        Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setId(vehiculo1.getId());
        assertThat(vehiculo1).isEqualTo(vehiculo2);
        vehiculo2.setId(2L);
        assertThat(vehiculo1).isNotEqualTo(vehiculo2);
        vehiculo1.setId(null);
        assertThat(vehiculo1).isNotEqualTo(vehiculo2);
    }
}
