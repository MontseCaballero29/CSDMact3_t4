package pasteleria.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pasteleria.modelo.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNombreIgnoreCase(String nombre);
}
