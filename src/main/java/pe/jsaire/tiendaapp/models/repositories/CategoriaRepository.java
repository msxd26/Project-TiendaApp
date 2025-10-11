package pe.jsaire.tiendaapp.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.tiendaapp.models.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
