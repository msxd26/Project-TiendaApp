package pe.jsaire.tiendaapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.mappers.RolMapper;
import pe.jsaire.tiendaapp.models.dto.response.RolResponse;
import pe.jsaire.tiendaapp.models.repositories.RolRepository;
import pe.jsaire.tiendaapp.security.AuthorityConstants;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rol")
@RequiredArgsConstructor
public class RolController {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    @GetMapping
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<List<RolResponse>> getAll() {
        return ResponseEntity.ok(
                rolRepository.findAll().stream()
                        .map(rolMapper::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
