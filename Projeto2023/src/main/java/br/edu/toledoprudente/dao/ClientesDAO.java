package br.edu.toledoprudente.dao;

import br.edu.toledoprudente.pojo.Users;
import org.springframework.stereotype.Repository;

import br.edu.toledoprudente.pojo.Clientes;

import java.util.List;

@Repository
public class ClientesDAO extends AbstractDao<Clientes, Integer> {
    public List<Clientes> findByName(String name) {
        List<Clientes> lista = this.createQuery("select c from Clientes c where c.nome like ?1", name) ;
        return lista;
    }
}
