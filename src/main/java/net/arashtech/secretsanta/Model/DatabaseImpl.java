package net.arashtech.secretsanta.Model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseImpl implements  Database{

    private Map<String, Person> mapOfPeople = new HashMap<>();
    private final String DB_URL = "sql5.freemysqlhosting.net:3306";
    private final String DB_USER ="sql5453116";
    private final String DB_PASS="ETNS5Be7pU";

    public void read() throws SQLException {
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from sql5453116");
        ) {
            while(rs.next()){
                Person person = new Person();
                person.setName(rs.getString("name").toLowerCase());
                person.setReceiving(rs.getInt("receiving") != 0);
                person.setSecretSantaFor(rs.getString("giving").toLowerCase());

                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", name: " + rs.getString("name"));
                System.out.print(", receiving: " + rs.getInt("receiving"));
                System.out.println(", giving: " + rs.getString("giving"));

                mapOfPeople.put(person.getName(), person);
            }
        }
    }

    @Override
    public List<Person> getAllPeople() throws SQLException {
        read();
        List<Person> listOfPeople = new ArrayList<>();
        for(Map.Entry<String, Person> entry : mapOfPeople.entrySet()){
            listOfPeople.add(entry.getValue());
        }

        return listOfPeople;
    }

    @Override
    public void update(String name, String gettingGiftFor) throws SQLException {

        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS) ) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("update sys.secret_santa set giving='"+gettingGiftFor.toLowerCase()+"' where name ='"+ name.toLowerCase() +"'");
            stmt.executeUpdate("update sys.secret_santa set receiving='"+1+"' where name ='"+ gettingGiftFor.toLowerCase() +"'");
        }
//        close();
    }

    @Override
    public Map<String, Person> getStatus() throws SQLException {
        read();
        return mapOfPeople;
    }

//    @Override
//    public void close() throws IOException {
//        BufferedWriter writer = new BufferedWriter(new FileWriter(ResourceUtils.getFile("classpath:gift_list.txt")));
//        for(Map.Entry<String, Person> entry : mapOfPeople.entrySet()){
//            writer.write(entry.getKey()+"|"+entry.getValue()+ System.lineSeparator());
//        }
//        writer.close();
//    }
}
