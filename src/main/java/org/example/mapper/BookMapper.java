package org.example.mapper;

import org.example.dto.BookCreateDto;
import org.example.dto.BookDto;
import org.example.model.Author;
import org.example.model.Book;
import org.mapstruct.AfterMapping;
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
    @Mapping(target = "author.id", source = "authorId")
    Book toEntity(BookCreateDto bookCreateDto);

    @AfterMapping
    default void mapAuthorFromCreateDto(BookCreateDto bookCreateDto, @MappingTarget Book book) {
        if(bookCreateDto.getAuthorId() != null) {
            Author author = new Author();
            author.setId(bookCreateDto.getAuthorId());
            book.setAuthor(author);
        }
    }


    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "year", source = "year")
    @Mapping(target = "author.id", source = "authorId")
    Book toEntity(BookDto bookDto);

    @AfterMapping
    default void mapAuthorFromDto(BookDto bookDto, @MappingTarget Book book) {
        if(bookDto.getAuthorId() != null) {
            Author author = new Author();
            author.setId(bookDto.getAuthorId());
            book.setAuthor(author);
        }
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book partialUpdateToEntity(BookCreateDto bookDto, @MappingTarget Book book);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "year", source = "year")
    @Mapping(target = "authorId", source = "author.id")
    BookDto toDto(Book book);
}
