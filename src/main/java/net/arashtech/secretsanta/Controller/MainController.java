package net.arashtech.secretsanta.Controller;

import net.arashtech.secretsanta.Model.Person;
import net.arashtech.secretsanta.exception.NoSuchPersonException;
import net.arashtech.secretsanta.service.SecretSantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@RestController
public class MainController {

    @Autowired
    private SecretSantaService service;

    @GetMapping("/")
    public String getName(@RequestParam(name="name", required=true) String name, Model model) {
        model.addAttribute("name", name);

        StringBuilder stringBuilder = new StringBuilder();

        try {
            String giftingTo = service.santaUp(name);
            stringBuilder
                    .append("Hello ")
                    .append(name.toUpperCase())
                    .append("!")
                    .append("\n")
                    .append("You will be the secret santa for ... ")
                    .append("\n")
                    .append(giftingTo.toUpperCase());
        } catch (NoSuchPersonException | IOException | SQLException e) {
            stringBuilder
                    .append("ERROR!")
                    .append("\n")
                    .append(e.getLocalizedMessage());
        }

        return stringBuilder.toString();
    }

    @GetMapping("/status")
    public String getMapOfPeople() {
        String message = "";
        try{
            message = service.getStatus();
        } catch(Exception e){
            message = "ERROR! " + e.getLocalizedMessage();
        }
        return message;
    }
}
