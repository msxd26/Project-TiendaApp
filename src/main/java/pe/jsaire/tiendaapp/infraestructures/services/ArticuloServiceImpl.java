package pe.jsaire.tiendaapp.infraestructures.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.ArticuloService;
import pe.jsaire.tiendaapp.mappers.ArticuloMapper;
import pe.jsaire.tiendaapp.models.dto.request.ArticuloRequest;
import pe.jsaire.tiendaapp.models.dto.response.ArticuloResponse;
import pe.jsaire.tiendaapp.models.entities.Articulo;
import pe.jsaire.tiendaapp.models.entities.Categoria;
import pe.jsaire.tiendaapp.models.repositories.ArticuloRepository;
import pe.jsaire.tiendaapp.models.repositories.CategoriaRepository;
import pe.jsaire.tiendaapp.utils.enums.SortType;
import pe.jsaire.tiendaapp.utils.exceptions.ArticuloNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.CategoriaNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticuloServiceImpl implements ArticuloService {

    private final ArticuloRepository articuloRepository;
    private final ArticuloMapper articuloMapper;
    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ArticuloResponse> getAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = switch (sortType) {
            case LOWER -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
            default -> PageRequest.of(page, size);
        };
        return articuloRepository.findAll(pageRequest).map(articuloMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticuloResponse> buscarPorNombre(String nombre) {
        return articuloRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(articuloMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ArticuloResponse findById(Long id) {
        return articuloMapper.toResponse(articuloRepository.findById(id)
                .orElseThrow(() -> new ArticuloNotFoundException("No hay articulo con el id " + id)));
    }

    @Override
    @Transactional
    public ArticuloResponse save(ArticuloRequest articuloRequest) {
        Categoria categora = categoriaRepository.findById(articuloRequest.getIdcategoria())
                .orElseThrow(() -> new CategoriaNotFoundException("No hay categoria con el id " + articuloRequest.getIdcategoria()));

        Articulo articulo = articuloMapper.toEntity(articuloRequest);
        articulo.setCategoria(categora);

        return articuloMapper.toResponse(articuloRepository.save(articulo));
    }

    @Override
    @Transactional
    public ArticuloResponse update(ArticuloRequest articuloRequest, Long id) {

        Articulo articuloExistente = articuloRepository.findById(id).orElseThrow(
                () -> new ArticuloNotFoundException("No hay articulo con el id " + id));


        Categoria category = categoriaRepository.findById(articuloRequest.getIdcategoria())
                .orElseThrow(() -> new CategoriaNotFoundException("No hay categoria con el id " + id));

        articuloExistente.setNombre(articuloRequest.getNombre());
        articuloExistente.setDescripcion(articuloRequest.getDescripcion());
        articuloExistente.setStock(articuloRequest.getStock());
        articuloExistente.setPrecioVenta(articuloRequest.getPrecioVenta());
        articuloExistente.setCategoria(category);
        return articuloMapper.toResponse(articuloRepository.save(articuloExistente));
    }


    @Override
    @Transactional
    public void delete(Long id) {
        if (!articuloRepository.existsById(id)) {
            throw new ArticuloNotFoundException("No se puede eliminar, el artículo con ID " + id + " no existe.");
        }
        articuloRepository.deleteById(id);

    }

    @Override
    public boolean existsByNombre(String nombre) {
        return articuloRepository.existsByNombre(nombre);
    }
}
