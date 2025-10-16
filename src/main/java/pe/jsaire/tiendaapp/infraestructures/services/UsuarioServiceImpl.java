package pe.jsaire.tiendaapp.infraestructures.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.UsuarioService;
import pe.jsaire.tiendaapp.mappers.UsuarioMapper;
import pe.jsaire.tiendaapp.models.dto.request.RolRequest;
import pe.jsaire.tiendaapp.models.dto.request.UsuarioRequest;
import pe.jsaire.tiendaapp.models.dto.response.UsuarioResponse;
import pe.jsaire.tiendaapp.models.entities.Rol;
import pe.jsaire.tiendaapp.models.entities.Usuario;
import pe.jsaire.tiendaapp.models.repositories.RolRepository;
import pe.jsaire.tiendaapp.models.repositories.UsuarioRepository;
import pe.jsaire.tiendaapp.utils.enums.RolNombre;

import java.util.HashSet;
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
    @Transactional(readOnly = true)
    public Set<UsuarioResponse> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponse).collect(Collectors.toSet());
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public void addRol(Long id, RolRequest rolRequest) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Rol rol = rolRepository.findRolByNombre(RolNombre.valueOf(rolRequest.getNombre()))
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolRequest.getNombre()));

        usuario.addRol(rol);

        usuarioRepository.save(usuario);
    }

    @Override
    public void removeRol(Long id, String rolNombre) {
        var usuario = usuarioRepository.findById(id).
                orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        RolNombre nombreRol;
        try {
            nombreRol = RolNombre.valueOf(rolNombre);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol inválido: " + rolNombre);
        }

        Rol rol = usuario.getRols().stream()
                .filter(r -> r.getNombre().equals(rolNombre))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El usuario no tiene el rol: " + rolNombre));

        usuario.removeRol(rol);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse findById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public UsuarioResponse save(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        Set<Rol> roles = new HashSet<>();
        Rol rolInvitado = rolRepository.findRolByNombre(RolNombre.ROLE_INVITADO)
                .orElseThrow(() -> new RuntimeException("Rol INVITADO no encontrado"));
        roles.add(rolInvitado);

        if (usuarioRequest.isAdmin()) {
            Rol rolAdmin = rolRepository.findRolByNombre(RolNombre.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Rol INVITADO no encontrado"));
            roles.add(rolAdmin);
        }
        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setTipoDocumento(usuarioRequest.getTipoDocumento());
        usuario.setNumeroDocumento(usuarioRequest.getNumDocumento());
        usuario.setDireccion(usuarioRequest.getDireccion());
        usuario.setTelefono(usuarioRequest.getTelefono());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        usuario.setRols(roles);
        System.out.println(usuarioRequest.isAdmin());
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }


    @Override
    @Transactional
    public void delete(Long id) {

        if (!usuarioRepository.existsById(id)) {
            throw new UsernameNotFoundException("No Existe el usuario con el id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
