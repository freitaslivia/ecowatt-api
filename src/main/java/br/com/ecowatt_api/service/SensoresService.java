package br.com.ecowatt_api.service;

import br.com.ecowatt_api.dto.SensoresRequestDTO;
import br.com.ecowatt_api.dto.SensoresResponseDTO;
import br.com.ecowatt_api.exception.ErrorException;
import br.com.ecowatt_api.model.Sensores;
import br.com.ecowatt_api.model.Usuario;
import br.com.ecowatt_api.repository.SensoresRepository;
import br.com.ecowatt_api.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class SensoresService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    SensoresRepository sensoresRepository;

    public SensoresResponseDTO created(SensoresRequestDTO sensoresRequestDto) {
        Sensores saved = modelMapper.map(sensoresRequestDto, Sensores.class);

        saved.setId(null);

        Usuario usuario = usuarioRepository.findById(sensoresRequestDto.getUsuarioId())
                .orElseThrow(() -> new ErrorException("Usuário não encontrado"));

        saved.setUsuario(usuario);
        saved.setDataCriacao(LocalDateTime.now());
        sensoresRepository.save(saved);


        return modelMapper.map(saved, SensoresResponseDTO.class);

    }
    public SensoresResponseDTO update(SensoresRequestDTO sensoresRequestDto, Long idSensor) {
        Usuario usuario = usuarioRepository.findById(sensoresRequestDto.getUsuarioId())
                .orElseThrow(() -> new ErrorException("Usuário não encontrado"));

        Sensores sensores = sensoresRepository.findById(idSensor)
                .orElseThrow(() -> new ErrorException("Sensor não encontrado"));

        Long idUsuario = sensores.getUsuario().getId();
        Long idUsuarioDTO = sensoresRequestDto.getUsuarioId();

        if (!idUsuarioDTO.equals(idUsuario)) {
            throw new ErrorException("Você não tem permissão para alterar este sensor.");
        }

        Sensores updatedSensor = modelMapper.map(sensoresRequestDto, Sensores.class);
        updatedSensor.setDataCriacao(sensores.getDataCriacao());
        updatedSensor.setDataModificacao(LocalDateTime.now());

        sensoresRepository.save(updatedSensor);

        return modelMapper.map(updatedSensor, SensoresResponseDTO.class);

    }




    public Page<SensoresResponseDTO> getAllSensoresByUsuario(Long usuarioId, int page, int size, String sortBy, String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(sortDirection, sortBy));

        Optional<Usuario> user = usuarioRepository.findById(usuarioId);
        if (user.isPresent()) {

            Page<Sensores> sensoresPage = sensoresRepository.findByUsuarioId(usuarioId, pageRequest);

            return sensoresPage.map(sensores -> {
                return modelMapper.map(sensores, SensoresResponseDTO.class);
            });


        } else {

            return Page.empty();
        }
    }


}

