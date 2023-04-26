package br.edu.toledoprudente.controller;

import br.edu.toledoprudente.dao.CategoriasDAO;
import br.edu.toledoprudente.pojo.Categorias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoriasConverter implements Converter<String, Categorias> {
    @Autowired
    private CategoriasDAO dao;

    @Override
    public Categorias convert(String idTexto) {
// TODO Auto-generated method stub
        if(idTexto.isEmpty())
            return null;
        return dao.findById(Integer.parseInt(idTexto));
    }
}