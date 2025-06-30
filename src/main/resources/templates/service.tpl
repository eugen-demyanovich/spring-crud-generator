package ${package}.service;

import ${package}.model.${entityName};
import ${package}.repository.${entityName}Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ${entityName}Service {
    private final ${entityName}Repository repository;

    public ${entityName}Service(${entityName}Repository repository) {
        this.repository = repository;
    }

    public List<${entityName}> findAll() {
        return (List<${entityName}>) repository.findAll();
    }

    public Optional<${entityName}> findById(Long id) {
        return repository.findById(id);
    }

    public ${entityName} create(${entityName} entity) {
        return repository.save(entity);
    }

    public ${entityName} update(Long id, ${entityName} entity) {
        entity.setId(id);
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
