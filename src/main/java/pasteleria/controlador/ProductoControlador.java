package pasteleria.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import pasteleria.dto.ProductoPeticion;
import pasteleria.servicio.CategoriaServicio;
import pasteleria.servicio.ProductoServicio;

@Controller
@RequestMapping("/productos")
public class ProductoControlador {

    private final ProductoServicio productoServicio;
    private final CategoriaServicio categoriaServicio;

    public ProductoControlador(ProductoServicio productoServicio,
                               CategoriaServicio categoriaServicio) {
        this.productoServicio = productoServicio;
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Long categoriaId, Model model) {
        model.addAttribute("productos", productoServicio.listar(categoriaId));
        model.addAttribute("categorias", categoriaServicio.listar());
        model.addAttribute("categoriaSeleccionada", categoriaId);
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        ProductoPeticion formulario = new ProductoPeticion();
        formulario.setDisponible(true);
        model.addAttribute("producto", formulario);
        cargarCategorias(model);
        return "productos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoServicio.convertirAFormulario(id));
        cargarCategorias(model);
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("producto") ProductoPeticion producto,
                          BindingResult resultado,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (resultado.hasErrors()) {
            cargarCategorias(model);
            return "productos/formulario";
        }

        boolean esNuevo = producto.getId() == null;
        productoServicio.guardar(producto);
        redirectAttributes.addFlashAttribute(
                "mensaje",
                esNuevo ? "Producto creado correctamente" : "Producto actualizado correctamente");
        return "redirect:/productos";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoServicio.buscarPorId(id));
        return "productos/detalle";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productoServicio.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado correctamente");
        return "redirect:/productos";
    }

    private void cargarCategorias(Model model) {
        model.addAttribute("categorias", categoriaServicio.listar());
    }
}
