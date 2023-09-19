package org.example.mapper;

import org.example.dto.AuthorCreateDto;
import org.example.dto.AuthorDto;
import org.example.model.Author;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "yearOfBirth", source = "yearOfBirth")
    Author toEntity(AuthorCreateDto authorCreateDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "yearOfBirth", source = "yearOfBirth")
    Author toEntity(AuthorDto authorDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Author partialUpdateToEntity(AuthorDto authorDto, @MappingTarget Author author);

    AuthorDto toDto(Author author);
}
