package pasteleria.servicio;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pasteleria.excepcion.RecursoNoEncontradoExcepcion;
import pasteleria.modelo.Categoria;
import pasteleria.repositorio.CategoriaRepositorio;
import pasteleria.repositorio.ProductoRepositorio;

@Service
public class CategoriaServicio {

    private final CategoriaRepositorio categoriaRepositorio;
    private final ProductoRepositorio productoRepositorio;

    public CategoriaServicio(CategoriaRepositorio categoriaRepositorio,
                             ProductoRepositorio productoRepositorio) {
        this.categoriaRepositorio = categoriaRepositorio;
        this.productoRepositorio = productoRepositorio;
    }

    @Transactional(readOnly = true)
    public List<Categoria> listar() {
        return categoriaRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
        return categoriaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion(
                        "No existe la categoría con ID " + id));
    }

    @Transactional
    public Categoria guardar(Categoria categoria) {
        categoria.setNombre(categoria.getNombre().trim());
        if (categoria.getDescripcion() != null) {
            categoria.setDescripcion(categoria.getDescripcion().trim());
        }

        categoriaRepositorio.findByNombreIgnoreCase(categoria.getNombre())
                .filter(existente -> !existente.getId().equals(categoria.getId()))
                .ifPresent(existente -> {
                    throw new DataIntegrityViolationException(
                            "Ya existe una categoría con ese nombre");
                });

        return categoriaRepositorio.save(categoria);
    }

    @Transactional
    public void eliminar(Long id) {
        buscarPorId(id);
        if (productoRepositorio.existsByCategoriaId(id)) {
            throw new DataIntegrityViolationException(
                    "No puedes eliminar una categoría que tiene productos asociados");
        }
        categoriaRepositorio.deleteById(id);
    }
}
