package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Przedmiot;

@Named
@Stateless
@LocalBean
public class PrzedmiotDAO extends CrudDAO<Long, Przedmiot>{

}