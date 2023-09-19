package org.example.mapper;

import org.example.dto.BookCreateDto;
import org.example.dto.BookDto;
import org.example.model.Book;
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
public interface BookMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "year", source = "year")
    @Mapping(target = "author", source = "author")
    Book toEntity(BookCreateDto bookCreateDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "year", source = "year")
    @Mapping(target = "author", source = "author")
    Book toEntity(BookDto bookDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book partialUpdateToEntity(BookDto bookDto, @MappingTarget Book book);

    BookDto toDto(Book book);
}
