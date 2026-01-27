package com.example.cajerocliente.Controller;

import com.example.cajerocliente.BL.LoginService;
import com.example.cajerocliente.BL.UsuarioService;
import com.example.cajerocliente.ML.LoginResponse;
import com.example.cajerocliente.ML.Result;
import com.example.cajerocliente.ML.Rol;
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

    @Autowired
    private LoginService loginService;

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

        LoginResponse response = loginService.login(username, password);

        System.out.println("---- Login Controller -----");
        System.out.println("Response null? " + (response == null));

        if (response != null && response.isCorrect()) {

            session.setAttribute("token", response.getToken());

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(response.getIdUsuario());
            usuario.setUsername(response.getUsername());
            usuario.setNombre(response.getNombre());
            usuario.setApellidoPaterno(response.getApellidoPaterno());
            usuario.setApellidoMaterno(response.getApellidoMaterno());
            usuario.setCuenta(response.getCuenta());
            usuario.setSaldo(response.getCuenta());

            if (response.getIdRol() != null) {
                Rol rol = new Rol();
                rol.setIdRol(response.getIdRol());
                usuario.setRol(rol);
            }

            session.setAttribute("usuario", usuario);
            return "redirect:/usuario";
        } else {
            model.addAttribute("error", response != null ? response.getMensaje() : "Error de login");
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
