package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Administrator;

@Named
@Stateless
@LocalBean
public class AdministratorDAO extends CrudDAO<Long, Administrator>{

}