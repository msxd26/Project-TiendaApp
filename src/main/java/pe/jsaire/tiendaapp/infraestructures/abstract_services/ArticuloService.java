package pe.jsaire.tiendaapp.infraestructures.abstract_services;

import org.springframework.data.domain.Page;
import pe.jsaire.tiendaapp.models.dto.request.ArticuloRequest;
import pe.jsaire.tiendaapp.models.dto.response.ArticuloResponse;
import pe.jsaire.tiendaapp.utils.enums.SortType;

import java.util.List;

public interface ArticuloService extends GenericService<ArticuloRequest, ArticuloResponse, Long> {

    String FIELD_BY_SORT = "nombre";

    Page<ArticuloResponse> getAll(Integer page, Integer size, SortType sortType);

    List<ArticuloResponse> buscarPorNombre(String nombre);

    boolean existsByNombre(String nombre);
}
