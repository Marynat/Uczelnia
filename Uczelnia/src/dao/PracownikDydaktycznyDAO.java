package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Pracownik_dydaktyczny;

@Named
@Stateless
@LocalBean
public class PracownikDydaktycznyDAO extends CrudDAO<Long, Pracownik_dydaktyczny>{

}