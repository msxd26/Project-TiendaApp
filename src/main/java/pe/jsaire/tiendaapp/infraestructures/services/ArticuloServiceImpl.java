package pe.jsaire.tiendaapp.infraestructures.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.ArticuloService;
import pe.jsaire.tiendaapp.mappers.ArticuloMapper;
import pe.jsaire.tiendaapp.models.dto.request.ArticuloRequest;
import pe.jsaire.tiendaapp.models.dto.response.ArticuloResponse;
import pe.jsaire.tiendaapp.models.repositories.ArticuloRepository;

@Service
@RequiredArgsConstructor
public class ArticuloServiceImpl implements ArticuloService {

    private final ArticuloRepository articuloRepository;
    private final ArticuloMapper articuloMapper;

    @Override
    public ArticuloResponse findById(Long id) {
        return articuloMapper.toResponse(articuloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Existe ")));
    }

    @Override
    public ArticuloResponse save(ArticuloRequest articuloRequest) {
        return null;
    }

    @Override
    public ArticuloResponse update(ArticuloRequest articuloRequest, Long aLong) {
        return null;
    }


    @Override
    public void delete(Long aLong) {

    }
}
