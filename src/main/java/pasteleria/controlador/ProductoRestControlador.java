package pasteleria.controlador;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pasteleria.dto.ProductoPeticion;
import pasteleria.dto.ProductoRespuesta;
import pasteleria.modelo.Producto;
import pasteleria.servicio.ProductoServicio;

@RestController
@RequestMapping("/api/productos")
@Validated
public class ProductoRestControlador {

    private final ProductoServicio productoServicio;

    public ProductoRestControlador(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    @GetMapping
    public List<ProductoRespuesta> listar(@RequestParam(required = false) Long categoriaId) {
        return productoServicio.listar(categoriaId)
                .stream()
                .map(productoServicio::convertirARespuesta)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductoRespuesta buscar(@PathVariable Long id) {
        return productoServicio.convertirARespuesta(productoServicio.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoRespuesta> crear(
            @Valid @RequestBody ProductoPeticion peticion) {
        peticion.setId(null);
        Producto guardado = productoServicio.guardar(peticion);
        ProductoRespuesta respuesta = productoServicio.convertirARespuesta(guardado);
        return ResponseEntity
                .created(URI.create("/api/productos/" + guardado.getId()))
                .body(respuesta);
    }

    @PutMapping("/{id}")
    public ProductoRespuesta actualizar(@PathVariable Long id,
                                        @Valid @RequestBody ProductoPeticion peticion) {
        Producto actualizado = productoServicio.actualizar(id, peticion);
        return productoServicio.convertirARespuesta(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
