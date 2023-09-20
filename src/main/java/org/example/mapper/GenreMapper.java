package org.example.mapper;

import org.example.dto.GenreCreateDto;
import org.example.dto.GenreDto;
import org.example.model.Genre;
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
public interface GenreMapper {
    @Mapping(target = "type", source = "type")
    Genre toEntity(GenreCreateDto genreCreateDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    Genre toEntity(GenreDto genreDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Genre partialUpdateToEntity(GenreCreateDto genreDto, @MappingTarget Genre genre);

    GenreDto toDto(Genre genre);
}
