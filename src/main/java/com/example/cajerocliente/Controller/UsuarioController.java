package com.example.cajerocliente.Controller;

import com.example.cajerocliente.BL.CajeroService;
import com.example.cajerocliente.BL.UsuarioService;
import com.example.cajerocliente.ML.Cajero;
import com.example.cajerocliente.ML.Result;
import com.example.cajerocliente.ML.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CajeroService cajeroService;

    @GetMapping
    public String index(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        Result saldoResult = usuarioService.consultarSaldo(usuario.getIdUsuario());
        if (saldoResult.correct && saldoResult.data != null) {
            usuario.setSaldo((Integer) saldoResult.data);
            session.setAttribute("usuario", usuario);
        }

        model.addAttribute("usuario", usuario);
        return "PaginaInicio";
    }

    @GetMapping("/retiro")
    public String retiro(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Cajero> cajeros = cajeroService.getAll();

        model.addAttribute("usuario", usuario);
        model.addAttribute("cajeros", cajeros);
        return "Retiro";
    }

    @PostMapping("/retiro")
    public String retiroPost(@RequestParam int idCajero,
            @RequestParam int monto,
            HttpSession session,
            Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        Result result = usuarioService.retiro(usuario.getIdUsuario(), idCajero, monto);

        if (result.correct) {
            Result saldoResult = usuarioService.consultarSaldo(usuario.getIdUsuario());
            if (saldoResult.correct && saldoResult.data != null) {
                usuario.setSaldo((Integer) saldoResult.data);
                session.setAttribute("usuario", usuario);
            }

            model.addAttribute("mensaje", "Retiro realizado exitosamente. Nuevo saldo: $" + usuario.getSaldo());
            model.addAttribute("tipo", "success");
        } else {
            model.addAttribute("mensaje", result.errorMessage);
            model.addAttribute("tipo", "error");
        }

        List<Cajero> cajeros = cajeroService.getAll();

        model.addAttribute("usuario", usuario);
        model.addAttribute("cajeros", cajeros);
        return "Retiro";
    }
}
