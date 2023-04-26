package br.edu.toledoprudente.controller;

import br.edu.toledoprudente.dao.ClientesDAO;
import br.edu.toledoprudente.pojo.Clientes;
import br.edu.toledoprudente.pojo.Funcionarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dependentes")
public class DependentesController {

    @Autowired
    ClientesDAO daoclientes;
    @GetMapping(path = "/getClientes/{nome}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getClientes(@PathVariable(value = "nome") String nome)
    {
        List<Clientes> lista = daoclientes.findByName(nome);
        return new ResponseEntity<Object>( lista, HttpStatus.OK);
    }

    @GetMapping(path = "/index")
    public String index(){
        return "/dependentes/index";
    }


    @PostMapping(path = "/salvarCliente", produces=MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> salvar(@RequestBody Clientes clientes) {
        try {
            if(clientes.getId()==null)
                daoclientes.save(clientes);
            else
                daoclientes.update(clientes);
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return new ResponseEntity<Object>( clientes, HttpStatus.OK);
    }
}
