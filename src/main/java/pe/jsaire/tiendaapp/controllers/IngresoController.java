package pe.jsaire.tiendaapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.IngresoService;
import pe.jsaire.tiendaapp.models.dto.request.DetalleIngresoRequest;
import pe.jsaire.tiendaapp.models.dto.request.IngresoRequest;
import pe.jsaire.tiendaapp.models.dto.response.IngresoResponse;

@RestController
@RequestMapping("/ingreso")
@RequiredArgsConstructor
public class IngresoController {

    private final IngresoService ingresoService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ALMACENERO','VENDEDOR')")
    public ResponseEntity<IngresoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ingresoService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IngresoResponse> create(@Valid @RequestBody IngresoRequest ingresoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingresoService.save(ingresoRequest));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ingresoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{idIngreso}/detalles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IngresoResponse> addDetalleVenta(@Valid @RequestBody DetalleIngresoRequest ingresoRequest
            , @PathVariable Long idIngreso) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ingresoService.addDetalle(idIngreso, ingresoRequest));
    }

    @PatchMapping("/{idIngreso}/detalles/{idDetalle}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IngresoResponse> removeDetalleVenta(
            @PathVariable Long idIngreso,
            @PathVariable Long idDetalle) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ingresoService.removeDetalle(idIngreso, idDetalle));
    }
}
