package pe.jsaire.tiendaapp.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.tiendaapp.models.entities.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findRolByNombre(String rol);
}
