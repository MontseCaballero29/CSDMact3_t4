package pasteleria.controlador;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pasteleria.modelo.Categoria;
import pasteleria.servicio.CategoriaServicio;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaRestControlador {

    private final CategoriaServicio categoriaServicio;

    public CategoriaRestControlador(CategoriaServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping
    public List<Categoria> listar() {
        return categoriaServicio.listar();
    }

    @PostMapping
    public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria categoria) {
        categoria.setId(null);
        Categoria guardada = categoriaServicio.guardar(categoria);
        return ResponseEntity
                .created(URI.create("/api/categorias/" + guardada.getId()))
                .body(guardada);
    }
}
