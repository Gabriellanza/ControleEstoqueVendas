package br.edu.toledoprudente.controller;

import br.edu.toledoprudente.dao.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    UsersDAO dao;

    @GetMapping("/")
    public String home(ModelMap model)
    {
        model.addAttribute("nomeusuario",dao.getUsuarioLogado().getNome());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/login-error")
    public String loginError(ModelMap model) {
        model.addAttribute("mensagem","Dados inválidos");
        return "/login";
    }

}
