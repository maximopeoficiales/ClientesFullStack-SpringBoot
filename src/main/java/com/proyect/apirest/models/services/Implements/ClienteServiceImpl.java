package com.proyect.apirest.models.services.Implements;

import java.util.List;

import com.proyect.apirest.models.dao.IClienteDao;
import com.proyect.apirest.models.entity.Cliente;
import com.proyect.apirest.models.services.IClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//se agrega como service para luego inyectarlo
@Service
public class ClienteServiceImpl implements IClienteService {

     @Autowired
     private IClienteDao clienteDao;

     @Override
     @Transactional(readOnly = true)
     public List<Cliente> findAll() {
          return (List<Cliente>) clienteDao.findAll();
     }

}
