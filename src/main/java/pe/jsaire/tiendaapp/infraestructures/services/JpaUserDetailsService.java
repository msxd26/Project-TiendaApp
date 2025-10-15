package pe.jsaire.tiendaapp.infraestructures.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.tiendaapp.models.entities.Usuario;
import pe.jsaire.tiendaapp.models.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("No existe el usuario con el email %s ", email));
        }

        Usuario usuario = usuarioOptional.orElseThrow();

        List<GrantedAuthority> grantedAuthorities = usuario.getRols().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
        return new User(usuario.getEmail(), usuario.getPassword(), usuario.isEstado(),
                true, true, true, grantedAuthorities);
    }
}
