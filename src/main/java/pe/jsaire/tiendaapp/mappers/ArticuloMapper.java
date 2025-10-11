package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.jsaire.tiendaapp.models.dto.request.ArticuloRequest;
import pe.jsaire.tiendaapp.models.dto.response.ArticuloResponse;
import pe.jsaire.tiendaapp.models.entities.Articulo;

@Mapper(componentModel = "spring")
public interface ArticuloMapper {

    ArticuloMapper INSTANCE = Mappers.getMapper(ArticuloMapper.class);

    ArticuloResponse toResponse(Articulo articulo);

    Articulo toEntity(ArticuloRequest articuloRequest);
}
