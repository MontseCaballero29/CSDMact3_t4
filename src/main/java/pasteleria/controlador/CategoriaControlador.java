package pasteleria.controlador;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import pasteleria.modelo.Categoria;
import pasteleria.servicio.CategoriaServicio;

@Controller
@RequestMapping("/categorias")
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;

    public CategoriaControlador(CategoriaServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping
    public String listar(Model model) {
        if (!model.containsAttribute("categoria")) {
            model.addAttribute("categoria", new Categoria());
        }
        model.addAttribute("categorias", categoriaServicio.listar());
        return "categorias/lista";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("categoria") Categoria categoria,
                          BindingResult resultado,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (resultado.hasErrors()) {
            model.addAttribute("categorias", categoriaServicio.listar());
            return "categorias/lista";
        }

        try {
            categoriaServicio.guardar(categoria);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría creada correctamente");
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/categorias";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaServicio.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría eliminada correctamente");
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/categorias";
    }
}
