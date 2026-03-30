package pe.jsaire.tiendaapp.infraestructures.abstract_services;

import org.springframework.data.domain.Page;
import pe.jsaire.tiendaapp.models.dto.request.CategoriaRequest;
import pe.jsaire.tiendaapp.models.dto.response.CategoriaResponse;
import pe.jsaire.tiendaapp.utils.enums.SortType;

import java.util.List;

public interface CategoriaService extends GenericService<CategoriaRequest, CategoriaResponse, Long> {

    String FIELD_BY_SORT = "nombre";

    Page<CategoriaResponse> getAll(Integer page, Integer size, SortType sortType);

    List<CategoriaResponse> buscarPorNombre(String nombre);

    boolean existsByNombre(String nombre);
}
