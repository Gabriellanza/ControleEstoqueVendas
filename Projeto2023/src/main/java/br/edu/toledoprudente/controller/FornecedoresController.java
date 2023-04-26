package br.edu.toledoprudente.controller;

import br.edu.toledoprudente.dao.FornecedoresDAO;
import br.edu.toledoprudente.pojo.Fornecedores;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/fornecedores")
public class FornecedoresController
{
    @Autowired
    FornecedoresDAO dao;

    @GetMapping("/novo")
    public String novo(ModelMap model)
    {
        model.addAttribute("fornecedores",new Fornecedores());
        return "/fornecedores/index";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model )
    {
        model.addAttribute("lista",dao.findAll());
        return "/fornecedores/listar";
    }

    @GetMapping("/prealterar")
    public String preAlterar(@RequestParam(name = "id") int id, ModelMap model)
    {
        model.addAttribute("fornecedores",dao.findById(id));
        return "/fornecedores/index";
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
        return "fornecedores/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("fornecedores") Fornecedores forn, ModelMap model) {
        try {

            Validator validator;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<Fornecedores>> constraintViolations =
                    validator.validate(forn);
            String errors = "";
            for (ConstraintViolation<Fornecedores> constraintViolation : constraintViolations) {
                errors = errors + constraintViolation.getMessage() + ". ";
            }
            if (errors != "") {
                //tem erros
                model.addAttribute("fornecedores", forn);
                model.addAttribute("mensagem", errors);
                model.addAttribute("retorno", false);
                return "/fornecedores/index";
            } else {

                if (forn.getId() == null)
                    dao.update(forn);
                else
                    dao.update(forn);
                model.addAttribute("mensagem", "Salvo com sucesso");
                model.addAttribute("retorno", true);
            }
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao salvar" + e.getMessage());
            model.addAttribute("retorno", false);
        }

        return "fornecedores/index";
    }
}
