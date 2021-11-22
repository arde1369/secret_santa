package net.arashtech.secretsanta.service;

import net.arashtech.secretsanta.Model.Person;
import net.arashtech.secretsanta.exception.NoSuchPersonException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public interface SecretSantaService {
    public String santaUp(String name) throws NoSuchPersonException, IOException, SQLException;

    public String getStatus() throws IOException, SQLException;
}
