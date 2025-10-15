package pe.jsaire.tiendaapp.infraestructures.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.UsuarioService;
import pe.jsaire.tiendaapp.mappers.UsuarioMapper;
import pe.jsaire.tiendaapp.models.dto.request.UsuarioRequest;
import pe.jsaire.tiendaapp.models.dto.response.UsuarioResponse;
import pe.jsaire.tiendaapp.models.entities.Rol;
import pe.jsaire.tiendaapp.models.entities.Usuario;
import pe.jsaire.tiendaapp.models.repositories.RolRepository;
import pe.jsaire.tiendaapp.models.repositories.UsuarioRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Set<UsuarioResponse> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponse).collect(Collectors.toSet());
    }

    @Override
    public UsuarioResponse findById(Long id) {
        return null;
    }

    @Override
    public UsuarioResponse save(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        Optional<Rol> role = rolRepository.findRolByNombre("ROLE_USER");
        Set<Rol> roles = new HashSet<>();
        role.ifPresent(roles::add);

        if (usuarioRequest.isAdmin()) {
            Optional<Rol> admin = rolRepository.findRolByNombre("ROLE_ADMIN");
            admin.ifPresent(roles::add);
        }
        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setTipoDocumento(usuarioRequest.getTipoDocumento());
        usuario.setNumeroDocumento(usuarioRequest.getNumDocumento());
        usuario.setDireccion(usuarioRequest.getDireccion());
        usuario.setTelefono(usuarioRequest.getTelefono());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        usuario.setRols(roles);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioResponse update(UsuarioRequest usuarioRequest, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
