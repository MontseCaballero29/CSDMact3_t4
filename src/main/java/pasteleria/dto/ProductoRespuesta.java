package pasteleria.dto;

import java.math.BigDecimal;

public record ProductoRespuesta(
        Long id,
        String nombre,
        String sabor,
        String descripcion,
        BigDecimal precio,
        Boolean disponible,
        Long categoriaId,
        String categoriaNombre) {
}
