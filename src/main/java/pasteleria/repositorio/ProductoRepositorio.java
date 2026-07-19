package pasteleria.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pasteleria.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

    List<Producto> findAllByOrderByIdDesc();

    List<Producto> findByCategoriaIdOrderByIdDesc(Long categoriaId);

    boolean existsByCategoriaId(Long categoriaId);
}
