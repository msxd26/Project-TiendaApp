package pe.jsaire.tiendaapp.models.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.tiendaapp.models.entities.Articulo;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    boolean existsByNombre(String nombre);
}
