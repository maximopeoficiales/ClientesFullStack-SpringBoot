package com.proyect.apirest.models.dao;

import com.proyect.apirest.models.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;

// porque JpaRepository porque este extiende de PagingAndSortingRepository y este asu vez de CrudRepository por eso nos servira para paginacion
public interface IClienteDao extends JpaRepository<Cliente, Long> {

}
