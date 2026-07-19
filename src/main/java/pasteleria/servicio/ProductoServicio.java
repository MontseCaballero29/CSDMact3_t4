package pasteleria.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pasteleria.dto.ProductoPeticion;
import pasteleria.dto.ProductoRespuesta;
import pasteleria.excepcion.RecursoNoEncontradoExcepcion;
import pasteleria.modelo.Categoria;
import pasteleria.modelo.Producto;
import pasteleria.repositorio.ProductoRepositorio;

@Service
public class ProductoServicio {

    private final ProductoRepositorio productoRepositorio;
    private final CategoriaServicio categoriaServicio;

    public ProductoServicio(ProductoRepositorio productoRepositorio,
                            CategoriaServicio categoriaServicio) {
        this.productoRepositorio = productoRepositorio;
        this.categoriaServicio = categoriaServicio;
    }

    @Transactional(readOnly = true)
    public List<Producto> listar(Long categoriaId) {
        if (categoriaId == null) {
            return productoRepositorio.findAllByOrderByIdDesc();
        }
        categoriaServicio.buscarPorId(categoriaId);
        return productoRepositorio.findByCategoriaIdOrderByIdDesc(categoriaId);
    }

    @Transactional(readOnly = true)
    public Producto buscarPorId(Long id) {
        return productoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion(
                        "No existe el producto con ID " + id));
    }

    @Transactional
    public Producto guardar(ProductoPeticion peticion) {
        Producto producto = peticion.getId() == null
                ? new Producto()
                : buscarPorId(peticion.getId());
        copiarDatos(peticion, producto);
        return productoRepositorio.save(producto);
    }

    @Transactional
    public Producto actualizar(Long id, ProductoPeticion peticion) {
        Producto producto = buscarPorId(id);
        copiarDatos(peticion, producto);
        return productoRepositorio.save(producto);
    }

    @Transactional
    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        productoRepositorio.delete(producto);
    }

    @Transactional(readOnly = true)
    public ProductoPeticion convertirAFormulario(Long id) {
        Producto producto = buscarPorId(id);
        ProductoPeticion formulario = new ProductoPeticion();
        formulario.setId(producto.getId());
        formulario.setNombre(producto.getNombre());
        formulario.setSabor(producto.getSabor());
        formulario.setDescripcion(producto.getDescripcion());
        formulario.setPrecio(producto.getPrecio());
        formulario.setDisponible(producto.getDisponible());
        formulario.setCategoriaId(producto.getCategoria().getId());
        return formulario;
    }

    public ProductoRespuesta convertirARespuesta(Producto producto) {
        return new ProductoRespuesta(
                producto.getId(),
                producto.getNombre(),
                producto.getSabor(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getDisponible(),
                producto.getCategoria().getId(),
                producto.getCategoria().getNombre());
    }

    private void copiarDatos(ProductoPeticion peticion, Producto producto) {
        Categoria categoria = categoriaServicio.buscarPorId(peticion.getCategoriaId());

        producto.setNombre(peticion.getNombre().trim());
        producto.setSabor(peticion.getSabor().trim());
        producto.setDescripcion(
                peticion.getDescripcion() == null ? null : peticion.getDescripcion().trim());
        producto.setPrecio(peticion.getPrecio());
        producto.setDisponible(Boolean.TRUE.equals(peticion.getDisponible()));
        producto.setCategoria(categoria);
    }
}
