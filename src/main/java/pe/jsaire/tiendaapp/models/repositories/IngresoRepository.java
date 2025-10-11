package pe.jsaire.tiendaapp.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.tiendaapp.models.entities.Ingreso;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
}
