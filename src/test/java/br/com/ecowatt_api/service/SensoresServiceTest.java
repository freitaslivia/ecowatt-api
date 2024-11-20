package br.com.ecowatt_api.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.ecowatt_api.dto.SensoresRequestDTO;
import br.com.ecowatt_api.dto.SensoresResponseDTO;
import br.com.ecowatt_api.exception.ErrorException;
import br.com.ecowatt_api.model.Sensores;
import br.com.ecowatt_api.model.Usuario;
import br.com.ecowatt_api.repository.SensoresRepository;
import br.com.ecowatt_api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SensoresServiceTest {

    @InjectMocks
    private SensoresService sensoresService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SensoresRepository sensoresRepository;

    private SensoresRequestDTO sensoresRequestDto;
    private Sensores sensores;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNomeCompleto("User Test");
        usuario.setLogin("test");
        usuario.setDataCriacao(LocalDateTime.now());

        sensoresRequestDto = new SensoresRequestDTO();
        sensoresRequestDto.setUsuarioId(1L);
        sensoresRequestDto.setTipoSensor("Temperatura");
        sensoresRequestDto.setStatus("Ativo");
        sensoresRequestDto.setNomeSensor("Sensor 1");
        sensoresRequestDto.setProdutoConectado("Produto X");

        sensores = new Sensores();
        sensores.setId(1L);
        sensores.setTipoSensor("Temperatura");
        sensores.setStatus("Ativo");
        sensores.setNomeSensor("Sensor 1");
        sensores.setProdutoConectado("Produto X");
        sensores.setUsuario(usuario);
        sensores.setDataCriacao(LocalDateTime.now());
    }

    @Test
    public void testCreated() {
        when(usuarioRepository.findById(sensoresRequestDto.getUsuarioId())).thenReturn(Optional.of(usuario));
        when(modelMapper.map(sensoresRequestDto, Sensores.class)).thenReturn(sensores);
        when(modelMapper.map(sensores, SensoresResponseDTO.class)).thenReturn(new SensoresResponseDTO());

        SensoresResponseDTO response = sensoresService.created(sensoresRequestDto);

        verify(sensoresRepository, times(1)).save(sensores);

        assertNotNull(response);
    }

    @Test
    public void testUpdate_Success() {
        SensoresRequestDTO updateDto = new SensoresRequestDTO();
        updateDto.setUsuarioId(1L);
        updateDto.setTipoSensor("Atualizado");
        updateDto.setStatus("Ativo");
        updateDto.setNomeSensor("Sensor Atualizado");
        updateDto.setProdutoConectado("Produto Y");

        when(usuarioRepository.findById(updateDto.getUsuarioId())).thenReturn(Optional.of(usuario));
        when(sensoresRepository.findById(1L)).thenReturn(Optional.of(sensores));
        when(modelMapper.map(updateDto, Sensores.class)).thenReturn(sensores);
        when(modelMapper.map(sensores, SensoresResponseDTO.class)).thenReturn(new SensoresResponseDTO());

        SensoresResponseDTO updatedSensor = sensoresService.update(updateDto, 1L);

        verify(sensoresRepository, times(1)).updateSensorById(
                sensores.getId(),
                sensores.getTipoSensor(),
                sensores.getStatus(),
                sensores.getNomeSensor(),
                sensores.getProdutoConectado(),
                sensores.getDataModificacao()
        );

        assertNotNull(updatedSensor);
    }

    @Test
    public void testUpdate_UsuarioNaoEncontrado() {
        SensoresRequestDTO updateDto = new SensoresRequestDTO();
        updateDto.setUsuarioId(999L);

        when(usuarioRepository.findById(updateDto.getUsuarioId())).thenReturn(Optional.empty());

        assertThrows(ErrorException.class, () -> {
            sensoresService.update(updateDto, 1L);
        });
    }

    @Test
    public void testGetAllSensoresByUsuario() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nomeSensor"));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Sensores sensor1 = new Sensores();
        sensor1.setId(1L);
        sensor1.setTipoSensor("Temperatura");

        Sensores sensor2 = new Sensores();
        sensor2.setId(2L);
        sensor2.setTipoSensor("Pressão");

        Page<Sensores> sensoresPage = new PageImpl<>(List.of(sensor1, sensor2));

        when(sensoresRepository.findByUsuarioId(1L, pageRequest)).thenReturn(sensoresPage);

        Page<SensoresResponseDTO> result = sensoresService.getAllSensoresByUsuario(1L, 1, 10, "nomeSensor", "asc");

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testGetAllSensoresByUsuario_UsuarioNaoEncontrado() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "nomeSensor"));
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        Page<SensoresResponseDTO> result = sensoresService.getAllSensoresByUsuario(999L, 1, 10, "nomeSensor", "asc");

        assertTrue(result.isEmpty());
    }
}