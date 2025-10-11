package pe.jsaire.tiendaapp.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.tiendaapp.models.entities.DetalleIngreso;

public interface DetalleIngresoRepository extends JpaRepository<DetalleIngreso, Long> {
}
