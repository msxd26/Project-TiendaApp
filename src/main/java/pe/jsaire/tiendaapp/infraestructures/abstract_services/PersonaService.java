package pe.jsaire.tiendaapp.infraestructures.abstract_services;

import org.springframework.data.domain.Page;
import pe.jsaire.tiendaapp.models.dto.request.PersonaRequest;
import pe.jsaire.tiendaapp.models.dto.response.PersonaResponse;
import pe.jsaire.tiendaapp.utils.enums.SortType;

public interface PersonaService extends GenericService<PersonaRequest, PersonaResponse, Long> {

    String FIELD_BY_SORT = "nombre";

    Page<PersonaResponse> getAll(Integer page, Integer size, SortType sortType);
}
