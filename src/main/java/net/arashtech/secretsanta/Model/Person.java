package net.arashtech.secretsanta.Model;

public class Person {
    private String name;
    private String secretSantaFor;
    private boolean receiving;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecretSantaFor() {
        return secretSantaFor;
    }

    public void setSecretSantaFor(String secretSantaFor) {
        this.secretSantaFor = secretSantaFor;
    }

    public boolean isReceiving() {
        return receiving;
    }

    public void setReceiving(boolean receiving) {
        this.receiving = receiving;
    }
}
