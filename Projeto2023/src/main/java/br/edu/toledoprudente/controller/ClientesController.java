package br.edu.toledoprudente.controller;

import br.edu.toledoprudente.dao.ClientesDAO;
import br.edu.toledoprudente.pojo.AppAuthority;
import br.edu.toledoprudente.pojo.Clientes;
import br.edu.toledoprudente.pojo.Users;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/clientes")
public class ClientesController
{
    @Autowired
    ClientesDAO dao;

    @GetMapping("/novo")
    public String novo(ModelMap model)
    {
        Clientes cli = new Clientes();
        cli.setUsuarios(new Users());
        model.addAttribute("clientes",cli);

        return "/clientes/index";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model )
    {
        model.addAttribute("lista",dao.findAll());
        return "/clientes/listar";
    }

    @GetMapping("/prealterar")
    public String preAlterar(@RequestParam(name = "id") int id, ModelMap model)
    {
        model.addAttribute("clientes",dao.findById(id));
        return "/clientes/index";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam(name = "id")int id, ModelMap model)
    {
        try{
            dao.delete(id);
            model.addAttribute("mensagem", "Exclusão Efetuada");
            model.addAttribute("retorno", true);
        }
        catch (Exception e){
            model.addAttribute("mensagem", "Exclusão não Efetuada");
            model.addAttribute("retorno", false);
        }
        model.addAttribute("lista", dao.findAll());
        return "clientes/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("clientes") Clientes cli, ModelMap model) {
        try {

            Validator validator;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<Clientes>> constraintViolations =
                    validator.validate(cli);
            String errors = "";
            for (ConstraintViolation<Clientes> constraintViolation : constraintViolations) {
                errors = errors + constraintViolation.getMessage() + ". ";
            }
            if (errors != "") {
                //tem erros
                model.addAttribute("clientes", cli);
                model.addAttribute("mensagem", errors);
                model.addAttribute("retorno", false);
                return "/clientes/index";
            } else {

                Users usu = cli.getUsuarios();
                String senha = "{bcrypt}" + new BCryptPasswordEncoder().encode(usu.getPassword());

                usu.setPassword(senha);

                usu.setEnabled(true);
                usu.setAdmin(false);

                Set<AppAuthority> appAuthorities = new HashSet<AppAuthority>();
                AppAuthority app = new AppAuthority();
                app.setAuthority("USER");
                app.setUsername(usu.getUsername());
                appAuthorities.add(app);
                usu.setAppAuthorities( appAuthorities);

                if (cli.getId() == null)
                    dao.save(cli);
                else
                    dao.update(cli);
                model.addAttribute("mensagem", "Salvo com sucesso");
                model.addAttribute("retorno", true);
            }
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao salvar" + e.getMessage());
            model.addAttribute("retorno", false);
        }

        return "clientes/index";
    }
}
