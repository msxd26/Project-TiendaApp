package pe.jsaire.tiendaapp.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.tiendaapp.models.entities.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    boolean existsBySerieComprobante(String serieComprobante);

    boolean existsByNumeroComprobante(String numeroComprobante);
}
