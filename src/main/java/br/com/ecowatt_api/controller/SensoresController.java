package br.com.ecowatt_api.controller;

import br.com.ecowatt_api.dto.DetalheSensorResponseDTO;
import br.com.ecowatt_api.dto.SensoresRequestDTO;
import br.com.ecowatt_api.dto.SensoresResponseDTO;
import br.com.ecowatt_api.model.Sensores;
import br.com.ecowatt_api.repository.SensoresRepository;
import br.com.ecowatt_api.security.SecurityConfigurations;
import br.com.ecowatt_api.service.SensoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/sensores")
@Tag(name = "sensores")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class SensoresController {
    @Autowired
    private SensoresService sensoresService;

    @Autowired
    private SensoresRepository sensoresRepository;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping()
    @Operation(summary = "Cadastro de sensor", description = "Metodo para cadastrar um sensor")
    @ApiResponse(responseCode = "201", description = "sensor criado com sucesso")
    @ApiResponse(responseCode = "400", description = "erro de validção")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity<SensoresResponseDTO> created(@RequestBody @Valid SensoresRequestDTO dtoRequest) {
        SensoresResponseDTO saved = sensoresService.created(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @GetMapping("all/{usuarioId}")
    @Operation(summary = "Lista de sensores por id usuario", description = "Metodo para buscar a lista de sensores do usuario pelo id")
    @ApiResponse(responseCode = "200", description = "lista retornada com sucesso")
    @ApiResponse(responseCode = "204", description = "lista retorna vazia, pois não tem nenhum sensor cadastrado para esse usuario")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity<Page<SensoresResponseDTO>> getAllSensoresByUsuario(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nomeSensor") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<SensoresResponseDTO> sensoresPage = sensoresService.getAllSensoresByUsuario(usuarioId, page, size, sortBy, direction);

        if(sensoresPage.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(sensoresPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca sensor por id", description = "Metodo para buscar sensor por id")
    @ApiResponse(responseCode = "200", description = "sensor retornado com sucesso")
    @ApiResponse(responseCode = "204", description = "retorna vazio pois não existe nenhum sensor cadastrado com esse id")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity<DetalheSensorResponseDTO> read(@PathVariable Long id) {
        Optional<Sensores> sensores = sensoresRepository.findById(id);
        if (sensores.isPresent()) {
            Sensores sensoresGet = sensores.get();
            DetalheSensorResponseDTO responseDTO =  modelMapper.map(sensoresGet, DetalheSensorResponseDTO.class);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza sensor por id", description = "Metodo para atualizar sensor por id")
    @ApiResponse(responseCode = "200", description = "sensor atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "sensor não encontrado")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity<SensoresResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SensoresRequestDTO dtoRequest) {
        SensoresResponseDTO responseDTO = sensoresService.update(dtoRequest, id);
        if (responseDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta sensor por id", description = "Metodo para deletar sensor por id")
    @ApiResponse(responseCode = "200", description = "sensor deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "sensor não encontrado")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Sensores> sensores = sensoresRepository.findById(id);
        if (sensores.isPresent()) {
            sensoresRepository.delete(sensores.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

