package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Semestr;

@Named
@Stateless
@LocalBean
public class SemestrDAO extends CrudDAO<Long, Semestr>{

}