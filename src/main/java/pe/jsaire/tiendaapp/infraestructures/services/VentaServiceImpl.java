package pe.jsaire.tiendaapp.infraestructures.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.VentaService;
import pe.jsaire.tiendaapp.mappers.VentaMapper;
import pe.jsaire.tiendaapp.models.dto.request.DetalleVentaRequest;
import pe.jsaire.tiendaapp.models.dto.request.VentaRequest;
import pe.jsaire.tiendaapp.models.dto.response.VentaResponse;
import pe.jsaire.tiendaapp.models.entities.Articulo;
import pe.jsaire.tiendaapp.models.entities.DetalleVenta;
import pe.jsaire.tiendaapp.models.entities.Persona;
import pe.jsaire.tiendaapp.models.entities.Usuario;
import pe.jsaire.tiendaapp.models.entities.Venta;
import pe.jsaire.tiendaapp.models.repositories.ArticuloRepository;
import pe.jsaire.tiendaapp.models.repositories.PersonaRepository;
import pe.jsaire.tiendaapp.models.repositories.UsuarioRepository;
import pe.jsaire.tiendaapp.models.repositories.VentaRepository;
import pe.jsaire.tiendaapp.utils.enums.EstadoTransaccion;
import pe.jsaire.tiendaapp.utils.enums.TipoComprobante;
import pe.jsaire.tiendaapp.utils.exceptions.ArticuloNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.PersonaNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.StockInsuficienteException;
import pe.jsaire.tiendaapp.utils.exceptions.UsuarioNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.VentaNotFoundException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;
    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ArticuloRepository articuloRepository;


    @Override
    @Transactional(readOnly = true)
    public VentaResponse findById(Long id) {
        return ventaMapper.toResponse(ventaRepository.findById(id)
                .orElseThrow(() -> new VentaNotFoundException("No existe ninguna venta con este id " + id)));
    }

    @Override
    @Transactional
    public VentaResponse save(VentaRequest ventaRequest) {
        Venta venta = new Venta();
        Persona persona = personaRepository.findById(ventaRequest.getIdcliente())
                .orElseThrow(() -> new PersonaNotFoundException("No existe ningun cliente con este id "
                        + ventaRequest.getIdcliente()));

        Usuario usuario = usuarioRepository.findById(ventaRequest.getIdusuario())
                .orElseThrow(() -> new UsuarioNotFoundException("No exixste ningun usuario con este id "
                        + ventaRequest.getIdusuario()));


        venta.setPersona(persona);
        venta.setUsuario(usuario);
        venta.setTipoComprobante(TipoComprobante.valueOf(ventaRequest.getTipoComprobante()));
        venta.setEstado(EstadoTransaccion.valueOf(ventaRequest.getEstado()));
        venta.setSerieComprobante(ventaRequest.getSerieComprobante());
        venta.setNumeroComprobante(ventaRequest.getNumComprobante());

        Set<DetalleVenta> detalleVentas = new HashSet<>();

        for (DetalleVentaRequest detalle : ventaRequest.getDetalles()) {
            Articulo articulo = articuloRepository.findById(detalle.getIdarticulo()).
                    orElseThrow(() -> new ArticuloNotFoundException("No existe ningun articulo con este id "
                            + detalle.getIdarticulo()));

            if (articulo.getStock() < detalle.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para el artículo: "
                        + articulo.getNombre() + ". Stock disponible: " + articulo.getStock());
            }

            articulo.setStock(articulo.getStock() - detalle.getCantidad());
            articuloRepository.save(articulo);

            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setArticulo(articulo);
            detalleVenta.setCantidad(detalle.getCantidad());
            detalleVenta.setPrecio(articulo.getPrecioVenta());
            detalleVenta.setVenta(venta);
            detalleVentas.add(detalleVenta);
        }


        venta.setDetalleVentas(detalleVentas);
        venta.calcularTotales();
        return ventaMapper.toResponse(ventaRepository.save(venta));
    }

    @Override
    public VentaResponse update(VentaRequest ventaRequest, Long aLong) {
        return null;
    }


    @Override
    public void delete(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new VentaNotFoundException("No existe ninguna venta con este id " + id);
        }
        ventaRepository.deleteById(id);
    }

    @Override
    public VentaResponse addDetalle(Long id, DetalleVentaRequest detalleVentaRequest) {
        var venta = ventaRepository.findById(id).
                orElseThrow(() -> new VentaNotFoundException("No existe ninguna venta  con este id " + id));

        if (venta.getEstado() == EstadoTransaccion.Anulado) {
            throw new IllegalArgumentException("Estado anulado no puede ser anulado");
        }

        Articulo articulo = articuloRepository.findById(detalleVentaRequest.getIdarticulo()).orElseThrow(
                () -> new ArticuloNotFoundException("No existe ningun articulo con este id " + id));

        if (articulo.getStock() < detalleVentaRequest.getCantidad()) {
            throw new StockInsuficienteException("No hay stock de este articulo :" + articulo.getNombre());
        }

        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setArticulo(articulo);
        detalleVenta.setCantidad(detalleVentaRequest.getCantidad());
        detalleVenta.setPrecio(articulo.getPrecioVenta());

        articulo.setStock(articulo.getStock() - detalleVenta.getCantidad());
        articuloRepository.save(articulo);

        venta.addDetalleVenta(detalleVenta);
        return ventaMapper.toResponse(ventaRepository.save(venta));
    }

    @Override
    public VentaResponse removeDetalle(Long id, Long idDetalle) {

        Venta venta = ventaRepository.findById(id).
                orElseThrow(() -> new VentaNotFoundException("No existe ninguna venta  con este id " + id));

        if (venta.getEstado() == EstadoTransaccion.Anulado) {
            throw new IllegalArgumentException("Estado anulado no puede ser anulado");
        }

        DetalleVenta detalleVenta = venta.getDetalleVentas().stream()
                .filter(detalle -> detalle.getIdDetalleVenta().equals(idDetalle))
                .findFirst()
                .orElseThrow(() -> new VentaNotFoundException("Detalle no encontrado"));

        Articulo articulo = detalleVenta.getArticulo();
        articulo.setStock(articulo.getStock() + detalleVenta.getCantidad());
        articuloRepository.save(articulo);

        venta.removeDetalleVenta(detalleVenta);

        return ventaMapper.toResponse(ventaRepository.save(venta));
    }

    @Override
    public VentaResponse updateCantidad(Long idVenta, Long idDetalleVenta, Integer cantidad) {
        return null;
    }
}
