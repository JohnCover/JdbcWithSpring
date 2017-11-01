package com.JohnCover.JpaPractice.databasedemo;


import java.sql.Date;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.JohnCover.JpaPractice.databasedemo.entity.Person;
import com.JohnCover.JpaPractice.databasedemo.jdbc.PersonJdbcDAO;

import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class DatabaseDemoApplication implements CommandLineRunner{

	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PersonJdbcDAO dao;
	
	public static void main(String[] args) {
		SpringApplication.run(DatabaseDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("all users -> {}",dao.findAll());
		logger.info("User id 10001 -> {}",dao.findById(10001));
		logger.info("User name James -> {}", dao.findByName("James"));
		logger.info("User at location Amsterdam -> {}", dao.findByLocation("Amsterdam"));
		logger.info("Deleting 10002 -> # of Rows Deleted: {}", dao.deleteById(10002));
		logger.info("Delete user with name Ranga -> # of Rows Deleted: {}", dao.deleteByName("Ranga"));
		logger.info("Inserting 10004 -> Rows affected {}", 
				dao.insert(new Person(10004,"Tara", "Berlin", new Date(0))));
		logger.info("Updating 10004 -> Rows affected {}", 
				dao.update(new Person(10004,"John", "California", new Date(0))));
	}
}
