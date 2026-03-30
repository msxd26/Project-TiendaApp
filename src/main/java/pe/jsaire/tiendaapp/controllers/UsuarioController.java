package pe.jsaire.tiendaapp.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.UsuarioService;
import pe.jsaire.tiendaapp.models.dto.request.RolRequest;
import pe.jsaire.tiendaapp.models.dto.request.UsuarioRequest;
import pe.jsaire.tiendaapp.models.dto.response.UsuarioResponse;
import pe.jsaire.tiendaapp.security.AuthorityConstants;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> me(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @GetMapping("/")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
    }

    @PostMapping
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<?> create(@Valid @RequestBody UsuarioRequest usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioRequest usuario) {
        usuario.setAdmin(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/roles")
    @PreAuthorize(AuthorityConstants.ROLE_ASSIGN)
    public ResponseEntity<Void> addRol(@PathVariable Long id, @RequestBody RolRequest rolRequest) {
        usuarioService.addRol(id, rolRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/roles/{rolNombre}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<Void> removeRol(@PathVariable Long id, @PathVariable String rolNombre) {
        usuarioService.removeRol(id, rolNombre);
        return ResponseEntity.noContent().build();
    }
}
