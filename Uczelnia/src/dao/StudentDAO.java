package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Student;

@Named
@Stateless
@LocalBean
public class StudentDAO extends CrudDAO<Long, Student>{

}