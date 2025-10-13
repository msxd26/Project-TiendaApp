package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.jsaire.tiendaapp.models.dto.request.CategoriaRequest;
import pe.jsaire.tiendaapp.models.dto.response.CategoriaResponse;
import pe.jsaire.tiendaapp.models.entities.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    @Mapping(target = "idcategoria", source = "idCategoria")
    CategoriaResponse toResponse(Categoria categoria);

    Categoria toEntity(CategoriaRequest categoriaRequest);
}
