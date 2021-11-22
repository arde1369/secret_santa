package net.arashtech.secretsanta.service;


import net.arashtech.secretsanta.Model.Database;
import net.arashtech.secretsanta.Model.Person;
import net.arashtech.secretsanta.exception.NoSuchPersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class SecretSantaServiceImpl implements SecretSantaService{

    @Autowired
    private Database db;

    @Override
    public String santaUp(String name) throws NoSuchPersonException, SQLException, IOException {
        String giftReceiver = "";
        List<Person> people = db.getAllPeople();
        for(Person person : people){
            if(person.getName().equalsIgnoreCase(name)){
                if(person.getSecretSantaFor().equalsIgnoreCase("0")){
                    Collections.shuffle(people);
                    Random random = new Random();
                    int index = 0;
                    boolean stopFlag = false;
                    while(!stopFlag){
                        index = random.nextInt(people.size());
                        if(people.get(index).isReceiving()){
                            people.remove(index);
                        }
                        else if(!people.get(index).getName().equalsIgnoreCase(name)){
                            stopFlag = true;
                        }
                    }

                    giftReceiver = people.get(index).getName();

                    db.update(name, giftReceiver);
                } else {
                    giftReceiver = person.getSecretSantaFor();
                }
            }
        }
        if(giftReceiver.equalsIgnoreCase("")){
            throw new NoSuchPersonException("Entered Name is invalid!");
        }
        return giftReceiver;
    }

    @Override
    public String getStatus() throws IOException, SQLException {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, Person> entry : db.getStatus().entrySet()){
            builder
                    .append(entry.getValue().getName())
                    .append(" | ")
                    .append(entry.getValue().getSecretSantaFor())
                    .append("\n");
        }
        return builder.toString();
    }
}
