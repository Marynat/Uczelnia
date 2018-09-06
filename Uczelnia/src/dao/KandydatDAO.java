package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Kandydat;

@Named
@Stateless
@LocalBean
public class KandydatDAO extends CrudDAO<Long, Kandydat>{

}