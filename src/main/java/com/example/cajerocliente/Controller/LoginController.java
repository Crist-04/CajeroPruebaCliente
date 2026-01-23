package com.example.cajerocliente.Controller;

import com.example.cajerocliente.BL.UsuarioService;
import com.example.cajerocliente.ML.Result;
import com.example.cajerocliente.ML.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/usuario";
        }
        return "LoginCajero";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        System.out.println("=== LOGINCONTROLLER ===");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        Result result = usuarioService.login(username, password);

        System.out.println("Result correct: " + result.correct);
        System.out.println("Result message: " + result.errorMessage);

        if (result.correct) {
            Usuario usuario = (Usuario) result.object;
            session.setAttribute("usuario", usuario);
            return "redirect:/usuario";
        } else {
            model.addAttribute("error", result.errorMessage);
            return "LoginCajero";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("mensaje", "Sesión cerrada exitosamente");
        return "redirect:/login";
    }
}
