package pasteleria.configuracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pasteleria.modelo.Categoria;
import pasteleria.repositorio.CategoriaRepositorio;

@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarCategoriasIniciales(CategoriaRepositorio repositorio) {
        return args -> {
            if (repositorio.count() == 0) {
                repositorio.save(new Categoria(
                        "Pasteles",
                        "Pasteles completos para celebraciones y pedidos especiales"));
                repositorio.save(new Categoria(
                        "Cupcakes",
                        "Porciones individuales decoradas"));
                repositorio.save(new Categoria(
                        "Postres",
                        "Cheesecakes, pays, gelatinas y otros postres"));
            }
        };
    }
}
