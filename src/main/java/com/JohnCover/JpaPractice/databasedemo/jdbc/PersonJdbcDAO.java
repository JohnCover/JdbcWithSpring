package com.JohnCover.JpaPractice.databasedemo.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.JohnCover.JpaPractice.databasedemo.entity.Person;

@Repository
public class PersonJdbcDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//defining custom row mapper inner class
	class PersonRowMapper implements RowMapper<Person> {

		@Override
		public Person mapRow(ResultSet rs, int rownNum) throws SQLException {
			Person person = new Person();
			person.setId(rs.getInt("id"));
			person.setName(rs.getString("name"));
			person.setLocation(rs.getString("location"));
			person.setBirthDate(rs.getDate("birth_date"));
			return person;
		}
		
	}
	
	public List<Person> findAll() {
		return jdbcTemplate.query("select * from person", 
				new BeanPropertyRowMapper<Person>(Person.class));	
	}

	//using custom row mapper
	public List<Person> findById(int id) {
		return jdbcTemplate.query("select * from person where id=?", new Object[] {id} ,new PersonRowMapper());
	}
	
	public int deleteById(int id) {
		return jdbcTemplate.update("delete from person where id=?", new Object[] {id});
	}
	
	public int deleteByName(String name) {
		return jdbcTemplate.update("delete from person where name=?", new Object[] {name});
	}
	
	public List<Person> findByName(String name) {
		//This parameter method throws org.h2.jdbc.JdbcSQLException
		//return jdbcTemplate.query("select * from person where name= "+name, new BeanPropertyRowMapper<Person>(Person.class));
		return jdbcTemplate.query("select * from person where name=?",new Object[]{name}, new BeanPropertyRowMapper<Person>(Person.class));
	}
	
	public List<Person> findByLocation(String location) {
		return jdbcTemplate.query("select * from person where location=?",new Object[] {location}, new BeanPropertyRowMapper<Person>(Person.class));
	}
	
	public int insert(Person person) {
		return jdbcTemplate.update(
				"INSERT INTO PERSON (id, name, location, birth_date) "
				+ "VALUES(?, ?, ?, ?)", 
				new Object[]{person.getId(), person.getName(), 
						person.getLocation(), 
						new Timestamp(person.getBirthDate().getTime())});
	}
	
	public int update(Person person) {
		return jdbcTemplate.update(
				"UPDATE PERSON "
				+ "set name = ?, location = ?, birth_date = ? "
				+ "where id = ? " , 
				new Object[]{person.getName(), 
						person.getLocation(), 
						new Timestamp(person.getBirthDate().getTime()),
						person.getId()});
	}
	
}
