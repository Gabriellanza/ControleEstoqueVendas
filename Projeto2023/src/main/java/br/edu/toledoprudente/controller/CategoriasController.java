package br.edu.toledoprudente.controller;

import br.edu.toledoprudente.dao.CategoriasDAO;
import br.edu.toledoprudente.pojo.Categorias;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/categorias")
public class CategoriasController
{
    @Autowired
    CategoriasDAO dao;

    @GetMapping("/novo")
    public String novo(ModelMap model)
    {
        model.addAttribute("categorias",new Categorias());
        return "/categorias/index";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model )
    {
        model.addAttribute("lista",dao.findAll());
        return "/categorias/listar";
    }

    @GetMapping("/prealterar")
    public String preAlterar(@RequestParam(name = "id") int id, ModelMap model)
    {
        model.addAttribute("categorias",dao.findById(id));
        return "/categorias/index";
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
        return "categorias/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("categorias") Categorias cat, ModelMap model) {
        try {

            Validator validator;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<Categorias>> constraintViolations =
                    validator.validate(cat);
            String errors = "";
            for (ConstraintViolation<Categorias> constraintViolation : constraintViolations) {
                errors = errors + constraintViolation.getMessage() + ". ";
            }
            if (errors != "") {
                //tem erros
                model.addAttribute("categorias", cat);
                model.addAttribute("mensagem", errors);
                model.addAttribute("retorno", false);
                return "/categorias/index";
            } else {

                if (cat.getId() == null)
                    dao.update(cat);
                else
                    dao.update(cat);
                model.addAttribute("mensagem", "Salvo com sucesso");
                model.addAttribute("retorno", true);
            }
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao salvar" + e.getMessage());
            model.addAttribute("retorno", false);
        }

        return "categorias/index";
    }
}
