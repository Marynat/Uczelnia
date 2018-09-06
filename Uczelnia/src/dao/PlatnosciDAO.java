package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Platnosci;

@Named
@Stateless
@LocalBean
public class PlatnosciDAO extends CrudDAO<Long, Platnosci>{

}