package br.edu.toledoprudente.controller;


import br.edu.toledoprudente.dao.FornecedoresDAO;
import br.edu.toledoprudente.pojo.Fornecedores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToFornecedoresConverter implements Converter<String, Fornecedores> {
    @Autowired
    private FornecedoresDAO dao;

    @Override
    public Fornecedores convert(String idTexto) {
// TODO Auto-generated method stub
        if(idTexto.isEmpty())
            return null;
        return dao.findById(Integer.parseInt(idTexto));
    }
}