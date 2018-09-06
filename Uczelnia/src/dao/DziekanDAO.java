package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Dziekan;

@Named
@Stateless
@LocalBean
public class DziekanDAO extends CrudDAO<Long, Dziekan>{

}