package ${package}.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ${package}.model.${entityName};
import ${package}.dto.${entityName}Dto;

@Mapper
public interface ${entityName}Mapper {
    ${entityName}Mapper INSTANCE = Mappers.getMapper(${entityName}Mapper.class);

    ${entityName}Dto toDto(${entityName} entity);
    ${entityName} toEntity(${entityName}Dto dto);
}
