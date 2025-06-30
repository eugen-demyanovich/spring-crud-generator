package ${package}.facade;

import ${package}.service.${entityName}Service;
import ${package}.mapper.${entityName}Mapper;
import ${package}.dto.${entityName}Dto;
import ${package}.model.${entityName};

import java.util.List;
import java.util.Optional;

public class ${entityName}Facade {
    private final ${entityName}Service service;
    private final ${entityName}Mapper mapper = ${entityName}Mapper.INSTANCE;

    public ${entityName}Facade(${entityName}Service service) {
        this.service = service;
    }

    public List<${entityName}Dto> findAll() {
        return service.findAll().stream()
            .map(mapper::toDto).toList();
    }

    public Optional<${entityName}Dto> findById(Long id) {
        return service.findById(id).map(mapper::toDto);
    }

    public ${entityName}Dto create(${entityName}Dto dto) {
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    public ${entityName}Dto update(Long id, ${entityName}Dto dto) {
        return mapper.toDto(service.update(id, mapper.toEntity(dto)));
    }

    public void deleteById(Long id) {
        service.deleteById(id);
    }
}
