package com.example.cajerocliente.Controller;

import com.example.cajerocliente.BL.CajeroService;
import com.example.cajerocliente.BL.DenominacionService;
import com.example.cajerocliente.ML.Cajero;
import com.example.cajerocliente.ML.Denominacion;
import com.example.cajerocliente.ML.Result;
import com.example.cajerocliente.ML.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario/rellenar")
public class CajeroController {

    @Autowired
    private CajeroService cajeroService;

    @Autowired
    private DenominacionService denominacionService;

    @GetMapping("")
    public String rellenar(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (usuario.getRol() == null || usuario.getRol().getIdRol() != 1) {
            model.addAttribute("mensaje", "No tienes permisos para acceder a esta sección");
            model.addAttribute("tipo", "error");
            return "redirect:/usuario";
        }

        List<Cajero> cajeros = cajeroService.getAll();
        List<Denominacion> denominaciones = denominacionService.getAll();

        model.addAttribute("usuario", usuario);
        model.addAttribute("cajeros", cajeros);
        model.addAttribute("denominaciones", denominaciones);

        return "usuario/Rellenar";
    }

    @PostMapping("")
    public String rellenarPost(@RequestParam int idCajero,
            @RequestParam int idDenominacion,
            @RequestParam int cantidad,
            HttpSession session,
            Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (usuario.getRol() == null || usuario.getRol().getIdRol() != 1) {
            return "redirect:/usuario";
        }

        Result result = cajeroService.rellenar(usuario.getIdUsuario(), idCajero, idDenominacion, cantidad);

        if (result.correct) {
            model.addAttribute("mensaje", "Cajero rellenado exitosamente");
            model.addAttribute("tipo", "success");
        } else {
            model.addAttribute("mensaje", result.errorMessage);
            model.addAttribute("tipo", "error");
        }

        List<Cajero> cajeros = cajeroService.getAll();
        List<Denominacion> denominaciones = denominacionService.getAll();

        model.addAttribute("usuario", usuario);
        model.addAttribute("cajeros", cajeros);
        model.addAttribute("denominaciones", denominaciones);

        return "usuario/Rellenar";
    }
}
