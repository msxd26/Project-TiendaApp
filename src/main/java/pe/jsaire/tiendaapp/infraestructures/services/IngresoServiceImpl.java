package pe.jsaire.tiendaapp.infraestructures.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.IngresoService;
import pe.jsaire.tiendaapp.mappers.IngresoMapper;
import pe.jsaire.tiendaapp.models.dto.request.DetalleIngresoRequest;
import pe.jsaire.tiendaapp.models.dto.request.IngresoRequest;
import pe.jsaire.tiendaapp.models.dto.response.IngresoResponse;
import pe.jsaire.tiendaapp.models.entities.Articulo;
import pe.jsaire.tiendaapp.models.entities.DetalleIngreso;
import pe.jsaire.tiendaapp.models.entities.Ingreso;
import pe.jsaire.tiendaapp.models.entities.Persona;
import pe.jsaire.tiendaapp.models.entities.Usuario;
import pe.jsaire.tiendaapp.models.repositories.ArticuloRepository;
import pe.jsaire.tiendaapp.models.repositories.IngresoRepository;
import pe.jsaire.tiendaapp.models.repositories.PersonaRepository;
import pe.jsaire.tiendaapp.models.repositories.UsuarioRepository;
import pe.jsaire.tiendaapp.utils.enums.EstadoTransaccion;
import pe.jsaire.tiendaapp.utils.enums.TipoComprobante;
import pe.jsaire.tiendaapp.utils.exceptions.ArticuloNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.IngresoNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.PersonaNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.StockInsuficienteException;
import pe.jsaire.tiendaapp.utils.exceptions.UsuarioNotFoundException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IngresoServiceImpl implements IngresoService {

    private final IngresoRepository ingresoRepository;
    private final IngresoMapper ingresoMapper;
    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ArticuloRepository articuloRepository;


    @Override
    @Transactional(readOnly = true)
    public IngresoResponse findById(Long id) {
        return ingresoMapper.toResponse(ingresoRepository.findById(id)
                .orElseThrow(() -> new IngresoNotFoundException("No existe un ingreso con este id: " + id)));
    }

    @Override
    @Transactional
    public IngresoResponse save(IngresoRequest ingresoRequest) {
        Ingreso ingreso = new Ingreso();

        Persona persona = personaRepository.findById(ingresoRequest.getIdproveedor())
                .orElseThrow(() -> new PersonaNotFoundException("No existe proveedor con id "
                        + ingresoRequest.getIdproveedor()));

        Usuario usuario = usuarioRepository.findById(ingresoRequest.getIdusuario())
                .orElseThrow(() -> new UsuarioNotFoundException("No existe usuario con id "
                        + ingresoRequest.getIdusuario()));
        ingreso.setPersona(persona);
        ingreso.setUsuario(usuario);
        ingreso.setTipoComprobante(TipoComprobante.valueOf(ingresoRequest.getTipoComprobante()));
        ingreso.setSerieComprobante(ingresoRequest.getSerieComprobante());
        ingreso.setNumeroComprobante(ingresoRequest.getNumComprobante());
        ingreso.setEstado(EstadoTransaccion.valueOf(ingresoRequest.getEstado()));

        Set<DetalleIngreso> detalles = new HashSet<>();

        for (DetalleIngresoRequest detalle : ingresoRequest.getDetalles()) {
            Articulo articulo = articuloRepository.findById(detalle.getIdarticulo())
                    .orElseThrow(() -> new ArticuloNotFoundException("No existe este articulo con id "
                            + detalle.getIdarticulo()));

            articulo.setStock(articulo.getStock() + detalle.getCantidad());
            articuloRepository.save(articulo);
            DetalleIngreso detalleIngreso = new DetalleIngreso();
            detalleIngreso.setArticulo(articulo);
            detalleIngreso.setCantidad(detalle.getCantidad());
            detalleIngreso.setPrecio(detalle.getPrecio());
            detalleIngreso.setIngreso(ingreso);
            detalles.add(detalleIngreso);
        }

        ingreso.setDetalleIngresos(detalles);
        ingreso.calcularTotales();
        ingresoRepository.save(ingreso);
        return ingresoMapper.toResponse(ingreso);
    }

    @Override
    @Transactional
    public IngresoResponse update(IngresoRequest ingresoRequest, Long id) {
        Ingreso ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new IngresoNotFoundException("No existe un ingreso con id " + id));
        ingreso.setTipoComprobante(TipoComprobante.valueOf(ingresoRequest.getTipoComprobante()));
        ingreso.setNumeroComprobante(ingresoRequest.getNumComprobante());
        ingreso.setEstado(EstadoTransaccion.valueOf(ingresoRequest.getEstado()));

        return ingresoMapper.toResponse(ingresoRepository.save(ingreso));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (!ingresoRepository.existsById(id)) {
            throw new IngresoNotFoundException("No existe un ingreso con id " + id);
        }
        ingresoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public IngresoResponse addDetalle(Long id, DetalleIngresoRequest detalleIngreso) {
        var ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new IngresoNotFoundException("No existe un ingreso con id "
                        + id));

        if (ingreso.getEstado() == EstadoTransaccion.Anulado) {
            throw new IllegalArgumentException("Estado anulado no puede ser anulado");
        }

        Articulo articulo = articuloRepository.findById(detalleIngreso.getIdarticulo()).orElseThrow(
                () -> new ArticuloNotFoundException("No existe ningun articulo con este id " + id));

        if (articulo.getStock() < detalleIngreso.getCantidad()) {
            throw new StockInsuficienteException("No hay stock de este articulo :" + articulo.getNombre());
        }

        DetalleIngreso detalleIngreso1 = new DetalleIngreso();
        detalleIngreso1.setArticulo(articulo);
        detalleIngreso1.setCantidad(detalleIngreso.getCantidad());
        detalleIngreso1.setPrecio(detalleIngreso.getPrecio());

        articulo.setStock(articulo.getStock() + detalleIngreso1.getCantidad());
        articuloRepository.save(articulo);

        ingreso.addDetalleIngreso(detalleIngreso1);

        return ingresoMapper.toResponse(ingresoRepository.save(ingreso));
    }

    @Override
    @Transactional
    public IngresoResponse removeDetalle(Long id, Long idDetalle) {

        var ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new IngresoNotFoundException("No existe un ingreso con id "
                        + id));

        if (ingreso.getEstado() == EstadoTransaccion.Anulado) {
            throw new IllegalArgumentException("Estado anulado no puede ser anulado");
        }

        DetalleIngreso detalleIngreso = ingreso.getDetalleIngresos().stream()
                .filter(detalle -> detalle.getIdDetalleIngreso().equals(idDetalle))
                .findFirst().orElseThrow(() -> new IngresoNotFoundException("No existe ningun detalle ingreso"));

        Articulo articulo = detalleIngreso.getArticulo();
        articulo.setStock(articulo.getStock() - detalleIngreso.getCantidad());
        articuloRepository.save(articulo);

        ingreso.removeDetalleIngreso(detalleIngreso);

        return ingresoMapper.toResponse(ingresoRepository.save(ingreso));
    }

    @Override
    public boolean existsIngresoBySerieComprobante(String serieComprobante) {
        return ingresoRepository.existsIngresoBySerieComprobante(serieComprobante);
    }
}

