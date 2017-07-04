/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.example.database.DatabaseConfig;
import com.example.model.Customer;
import com.example.repo.CustomerRepository;
import com.example.util.Logger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

// TODO for page navigation and controller logic have a look at 
// https://spring.io/guides/gs/serving-web-content/
// The Main here has been jammed as a controller class
@Controller
@SpringBootApplication
public class Main {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  
  private static DataSource dataSource; // autowire does not work on static sources

  @Autowired
  private DataSource dataSource2;
  
  @Autowired
  CustomerRepository repository;
  
  public static void main(String[] args) throws Exception {
	Logger.log(Main.class.getName(), "calling Main Method");
    SpringApplication.run(Main.class, args);
    // TODO connecting manually to the database works fine. Need to Autowire this shiet
    //testingDatabaseConnection();
  
  }
  
  @RequestMapping("/findall") 
  public String findAll() {
	  String result = "<html>";
	  for(Customer customer: repository.findAll()) {
		  result += "<div>" + customer.toString() + "</div>";
		  
	  }
	  return result + "</html>";
  }
  

  public static void testingDatabaseConnection() throws SQLException, URISyntaxException {
	  Logger.log(Main.class.getName(), "calling testingDatabaseConnection");
	  DatabaseConfig config = new DatabaseConfig();
	  dataSource = config.dataSource();
	  
      Statement stmt = dataSource.getConnection().createStatement();
     /* stmt.executeUpdate("DROP TABLE IF EXISTS ticks");
      stmt.executeUpdate("CREATE TABLE ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");*/
      ResultSet rs = stmt.executeQuery("select * from actor");
      while (rs.next()) {
          System.out.println("Read from DB: " + rs.getString("actor_id") + " : " +  rs.getString("first_name") + ", " + rs.getString("last_name"));
      }
  }
  
  
  // TODO test method to connect to the database
  public static void testPostgresDB() throws URISyntaxException{
	  
	  
	 /* URI dbUri = new URI(System.getenv("DATABASE_URL"));
	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

	    Logger.log("username", username, "password", password, "dbUrl", dbUrl);*/
  }

  @RequestMapping("/")
  String index() {
    return "index";
  }

  @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource2.getConnection()) {
      //Statement stmt = connection.createStatement();
      //stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      //stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      //ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      //ArrayList<String> output = new ArrayList<String>();
      //while (rs.next()) {
      //  output.add("Read from DB: " + rs.getTimestamp("tick"));
      //}

    	/*for(Customer customer: repository.findAll()) {
    		model.put("records", customer.toString());
   		  
   	  	}*/
    	model.put("records", repository.findAll());
      
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }
  
  @RequestMapping("/test")
  String test() {
    try (Connection connection = dataSource2.getConnection()) {
      //Statement stmt = connection.createStatement();
      //stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      //stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      //ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      //ArrayList<String> output = new ArrayList<String>();
      //while (rs.next()) {
      //  output.add("Read from DB: " + rs.getTimestamp("tick"));
      //}

    	/*for(Customer customer: repository.findAll()) {
    		model.put("records", customer.toString());
   		  
   	  	}*/
   
      
      return "db";
    } catch (Exception e) {
     
      return "error";
    }
  }

  // Note that Spring Boot will reuse your DataSource anywhere one is required
  //@Bean
  public DataSource dataSource() throws SQLException, URISyntaxException {
	  
	  
 /*   if (dbUrl == null || dbUrl.isEmpty()) {
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }*/
	  
	  URI dbUri = new URI(System.getenv("DATABASE_URL"));

      String username = dbUri.getUserInfo().split(":")[0];
      String password = dbUri.getUserInfo().split(":")[1];
      String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
      
      Logger.log(this.getClass().getName(), "username", username, "password", password, "dbUrl", dbUrl);

      BasicDataSource basicDataSource = new BasicDataSource();
      basicDataSource.setUrl(dbUrl);
      basicDataSource.setUsername(username);
      basicDataSource.setPassword(password);

      return basicDataSource;
  }

}
