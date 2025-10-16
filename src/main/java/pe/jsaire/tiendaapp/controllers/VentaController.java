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
import pe.jsaire.tiendaapp.infraestructures.abstract_services.VentaService;
import pe.jsaire.tiendaapp.models.dto.request.DetalleVentaRequest;
import pe.jsaire.tiendaapp.models.dto.request.VentaRequest;
import pe.jsaire.tiendaapp.models.dto.response.VentaResponse;

@RestController
@RequestMapping("/venta")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ALMACENERO','VENDEDOR')")
    public ResponseEntity<VentaResponse> readById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VentaResponse> create(@Valid @RequestBody VentaRequest ventaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.save(ventaRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ventaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{idVenta}/detalles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VentaResponse> addDetalleVenta(
            @PathVariable Long idVenta,
            @RequestBody DetalleVentaRequest detalleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.addDetalle(idVenta, detalleRequest));
    }

    @PatchMapping("/{idVenta}/detalles/{idDetalle}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VentaResponse> removeDetalleVenta(
            @PathVariable Long idVenta,
            @PathVariable Long idDetalle) {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.removeDetalle(idVenta, idDetalle));
    }
}
