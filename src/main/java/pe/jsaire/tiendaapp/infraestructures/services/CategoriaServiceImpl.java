package pe.jsaire.tiendaapp.infraestructures.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.CategoriaService;
import pe.jsaire.tiendaapp.mappers.CategoriaMapper;
import pe.jsaire.tiendaapp.models.dto.request.CategoriaRequest;
import pe.jsaire.tiendaapp.models.dto.response.CategoriaResponse;
import pe.jsaire.tiendaapp.models.entities.Categoria;
import pe.jsaire.tiendaapp.models.repositories.CategoriaRepository;
import pe.jsaire.tiendaapp.utils.enums.SortType;
import pe.jsaire.tiendaapp.utils.exceptions.CategoriaNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {


    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaResponse> getAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = switch (sortType) {
            case LOWER -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
            default -> PageRequest.of(page, size);
        };
        return categoriaRepository.findAll(pageRequest).map(categoriaMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponse> buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(categoriaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponse findById(Long id) {
        return categoriaMapper.toResponse(categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("No Existe una categoria con id : " + id)));
    }

    @Override
    @Transactional
    public CategoriaResponse save(CategoriaRequest categoriaRequest) {
        return categoriaMapper.toResponse(categoriaRepository.save(categoriaMapper.toEntity(categoriaRequest)));
    }

    @Override
    @Transactional
    public CategoriaResponse update(CategoriaRequest categoriaRequest, Long id) {
        Categoria categoriaExiste = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("No Existe una categoria con id : " + id));

        categoriaExiste.setNombre(categoriaRequest.getNombre());
        categoriaExiste.setDescripcion(categoriaRequest.getDescripcion());
        categoriaRepository.save(categoriaExiste);
        return categoriaMapper.toResponse(categoriaRepository.save(categoriaExiste));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNotFoundException("No Existe una categoria con id : " + id);
        }
        categoriaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return categoriaRepository.existsByNombre(nombre);
    }
}
