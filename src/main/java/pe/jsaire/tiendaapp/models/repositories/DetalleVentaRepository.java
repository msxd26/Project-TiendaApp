package pe.jsaire.tiendaapp.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.tiendaapp.models.entities.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}
