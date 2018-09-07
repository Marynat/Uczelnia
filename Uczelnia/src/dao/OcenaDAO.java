package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Ocena;

@Named
@Stateless
@LocalBean
public class OcenaDAO extends CrudDAO<Long, Ocena>{

}