package pe.jsaire.tiendaapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.CategoriaService;
import pe.jsaire.tiendaapp.models.dto.request.CategoriaRequest;
import pe.jsaire.tiendaapp.models.dto.response.CategoriaResponse;
import pe.jsaire.tiendaapp.security.AuthorityConstants;
import pe.jsaire.tiendaapp.utils.enums.SortType;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    @PreAuthorize(AuthorityConstants.READ_CATALOG)
    public ResponseEntity<Page<CategoriaResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "NONE") SortType sortType) {
        return ResponseEntity.ok(categoriaService.getAll(page, size, sortType));
    }

    @GetMapping("/buscar")
    @PreAuthorize(AuthorityConstants.READ_CATALOG)
    public ResponseEntity<List<CategoriaResponse>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(categoriaService.buscarPorNombre(nombre));
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthorityConstants.READ_CATALOG)
    public ResponseEntity<CategoriaResponse> getCategoria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findById(id));
    }

    @PostMapping
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<CategoriaResponse> createCategoria(@Valid @RequestBody CategoriaRequest categoriaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoriaRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<CategoriaResponse> updateCategoria(@Valid @RequestBody CategoriaRequest categoriaRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.update(categoriaRequest, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
