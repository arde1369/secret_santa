package net.arashtech.secretsanta.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Database {

    public List<Person> getAllPeople() throws IOException, SQLException;

    public void update(String name, String gettingGiftFor) throws IOException, SQLException;

    public Map<String, Person> getStatus() throws IOException, SQLException;
}
