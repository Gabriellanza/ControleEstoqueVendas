package br.edu.toledoprudente.controller;

import br.edu.toledoprudente.dao.CategoriasDAO;
import br.edu.toledoprudente.dao.FornecedoresDAO;
import br.edu.toledoprudente.dao.ProdutosDAO;
import br.edu.toledoprudente.pojo.Categorias;
import br.edu.toledoprudente.pojo.Fornecedores;
import br.edu.toledoprudente.pojo.Produtos;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Controller
@RequestMapping("/produtos")
public class ProdutosController
{
    @Autowired
    private ProdutosDAO dao;

    @Autowired
    private CategoriasDAO daocategorias;

    @Autowired
    private FornecedoresDAO daofornecedores;

    @GetMapping("/novo")
    public String novo(ModelMap model)
    {
        model.addAttribute("produtos",new Produtos());
        return "/produtos/index";
    }

    @ModelAttribute(name = "listacategorias")
    public List<Categorias> listacategorias()
    {
        return daocategorias.findAll();
    }

    @ModelAttribute(name = "listafornecedores")
    public List<Fornecedores> listafornecedores()
    {
        return daofornecedores.findAll();
    }

    @GetMapping("/listar")
    public String listar(ModelMap model )
    {
        model.addAttribute("lista",dao.findAll());
        return "/produtos/listar";
    }

    @GetMapping("/prealterar")
    public String preAlterar(@RequestParam(name = "id") int id, ModelMap model)
    {
        model.addAttribute("produtos",dao.findById(id));
        return "/produtos/index";
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
        return "produtos/listar";
    }


    // Metodo para fazer o processo de salvar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("produtos") Produtos prod, ModelMap model, @RequestParam("file") MultipartFile file)
 {
        try {

            Validator validator;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<Produtos>> constraintViolations = validator.validate(prod);
            String errors = "";
            for (ConstraintViolation<Produtos> constraintViolation : constraintViolations) {
                errors = errors + constraintViolation.getMessage() + ". ";

                // validar a imagem
                if (file.isEmpty()) {
                    errors = errors + "Selecione uma imagem!";
                }

            }
            if (errors != "") {
                // tem erros
                model.addAttribute("produtos", prod);
                model.addAttribute("mensagem", errors);
                model.addAttribute("retorno", false);
                return "/produtos/index";
            } else {

                Random random = new Random();
                String nomeArquivo = random.nextInt() + file.getOriginalFilename();

                prod.setImagem(nomeArquivo);

                if (prod.getId() == null)
                    dao.save(prod);
                else
                    dao.update(prod);
                model.addAttribute("mensagem", "Salvo com Sucesso!");
                model.addAttribute("retorno", true);

                try { byte[] bytes = file.getBytes();
                    Path path = Paths.get(System.getProperty("user.dir") +"\\src\\main\\resources\\static\\image\\" + nomeArquivo);
                    Files.write(path, bytes);
                } catch (IOException e) {
                    e.printStackTrace(); }


            }
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao salvar!");
            model.addAttribute("retorno", false);
        }
        return "/produtos/index";
    }


    @ResponseBody
    @RequestMapping(value = "/getimagem/{nome}", method = RequestMethod.GET)
    public HttpEntity<byte[]> download(@PathVariable(value = "nome") String nome) throws IOException {
        byte[] arquivo =Files.readAllBytes( Paths.get(System.getProperty("user.dir") +"\\src\\main\\resources\\static\\image\\" + nome));
        HttpHeaders httpHeaders = new HttpHeaders();
        switch (nome.substring(nome.lastIndexOf(".") + 1).toUpperCase()) {
            case "JPG":
                httpHeaders.setContentType(MediaType.IMAGE_JPEG);break;
            case "GIF":
                httpHeaders.setContentType(MediaType.IMAGE_GIF); break;
            case "PNG":
                httpHeaders.setContentType(MediaType.IMAGE_PNG); break;
            default:
                break;
        } httpHeaders.setContentLength(arquivo.length);
        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);
        return entity;}

}
