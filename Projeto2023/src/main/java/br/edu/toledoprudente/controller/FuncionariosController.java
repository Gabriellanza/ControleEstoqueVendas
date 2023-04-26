package br.edu.toledoprudente.controller;

        import br.edu.toledoprudente.dao.FuncionariosDAO;
        import br.edu.toledoprudente.pojo.Funcionarios;
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
@RequestMapping("/funcionarios")
public class FuncionariosController
{
    @Autowired
    FuncionariosDAO dao;

    @GetMapping("/novo")
    public String novo(ModelMap model)
    {
        model.addAttribute("funcionarios",new Funcionarios());
        return "/funcionarios/index";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model )
    {
        model.addAttribute("lista",dao.findAll());
        return "/funcionarios/listar";
    }

    @GetMapping("/prealterar")
    public String preAlterar(@RequestParam(name = "id") int id, ModelMap model)
    {
        model.addAttribute("funcionarios",dao.findById(id));
        return "/funcionarios/index";
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
        return "funcionarios/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("funcionarios") Funcionarios func, ModelMap model) {
        try {

            Validator validator;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<Funcionarios>> constraintViolations =
                    validator.validate(func);
            String errors = "";
            for (ConstraintViolation<Funcionarios> constraintViolation : constraintViolations) {
                errors = errors + constraintViolation.getMessage() + ". ";
            }
            if (errors != "") {
                //tem erros
                model.addAttribute("funcionarios", func);
                model.addAttribute("mensagem", errors);
                model.addAttribute("retorno", false);
                return "/funcionarios/index";
            } else {

                if (func.getId() == null)
                    dao.update(func);
                else
                    dao.update(func);
                model.addAttribute("mensagem", "Salvo com sucesso");
                model.addAttribute("retorno", true);
            }
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao salvar" + e.getMessage());
            model.addAttribute("retorno", false);
        }

        return "funcionarios/index";
    }
}
