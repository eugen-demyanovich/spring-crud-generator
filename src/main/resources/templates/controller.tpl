package ${package}.controller;

import ${package}.service.${entityName}Service;
<#if config.withConverter?? && config.withConverter==true>
import ${package}.dto.${entityName}Dto;
import ${package}.mapper.${entityName}Mapper;
<#else>
import ${package}.model.${entityName};
</#if>
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/${entityVar}s")
public class ${entityName}Controller {
    private final <#if config.withFacade> ${entityName}Facade <#else> ${entityName}Service </#if> service;

    public ${entityName}Controller(<#if config.withFacade> ${entityName}Facade service <#else> ${entityName}Service service </#if>) {
        this.service = service;
    }

    @GetMapping
    public List<${entityName}<#if config.withConverter==true>Dto</#if>> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<${entityName}<#if config.withConverter==true>Dto</#if>> getById(@PathVariable Long id) {
        return service.findById(id)
                      .map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<${entityName}<#if config.withConverter==true>Dto</#if>> create(
        @RequestBody ${entityName}<#if config.withConverter==true>Dto</#if> payload) {
        var created = service.create(payload);
        return ResponseEntity.created(URI.create("/api/${entityVar}s/" + created.getId()))
                             .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<${entityName}<#if config.withConverter==true>Dto</#if>> update(
        @PathVariable Long id,
        @RequestBody ${entityName}<#if config.withConverter==true>Dto</#if> payload) {
        var updated = service.update(id, payload);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
