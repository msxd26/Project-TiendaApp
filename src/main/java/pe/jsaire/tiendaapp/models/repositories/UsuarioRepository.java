package pe.jsaire.tiendaapp.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.tiendaapp.models.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
