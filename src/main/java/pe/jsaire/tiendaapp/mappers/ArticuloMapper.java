package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.jsaire.tiendaapp.models.dto.request.ArticuloRequest;
import pe.jsaire.tiendaapp.models.dto.response.ArticuloResponse;
import pe.jsaire.tiendaapp.models.entities.Articulo;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface ArticuloMapper {

    ArticuloMapper INSTANCE = Mappers.getMapper(ArticuloMapper.class);

    @Mapping(target = "idarticulo", source = "articulo.idArticulo")
    @Mapping(target = "idcategoria", source = "articulo.categoria.idCategoria")
    ArticuloResponse toResponse(Articulo articulo);

    @Mapping(target = "categoria.idCategoria", source = "articuloRequest.idcategoria")
    Articulo toEntity(ArticuloRequest articuloRequest);
}
