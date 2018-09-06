package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Wnioski;

@Named
@Stateless
@LocalBean
public class WnioskiDAO extends CrudDAO<Long, Wnioski>{

}