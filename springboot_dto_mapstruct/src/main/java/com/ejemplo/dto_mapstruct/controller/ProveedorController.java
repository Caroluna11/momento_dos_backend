package com.ejemplo.dto_mapstruct.controller;

import com.ejemplo.dto_mapstruct.dto.ProveedorDTO;
import com.ejemplo.dto_mapstruct.entity.Proveedor;
import com.ejemplo.dto_mapstruct.mapper.ProveedorMapper;
import com.ejemplo.dto_mapstruct.repository.ProveedorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorRepository repository;
    private final ProveedorMapper mapper;

    // Inyección de dependencias por constructor
    public ProveedorController(ProveedorRepository repository, ProveedorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // GET → Consultar todos los proveedores
    @GetMapping
    public List<ProveedorDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // POST → Registrar un proveedor nuevo
    @PostMapping
    public ProveedorDTO crear(@RequestBody ProveedorDTO dto) {
        // 1. Convertir DTO recibido a Entidad
        Proveedor proveedor = mapper.toEntity(dto);

        // 2. Guardar la Entidad en la base de datos
        Proveedor guardado = repository.save(proveedor);

        // 3. Convertir la Entidad guardada de vuelta a DTO para responder
        return mapper.toDTO(guardado);
    }
}