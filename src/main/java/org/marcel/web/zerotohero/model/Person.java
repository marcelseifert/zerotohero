/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero.model;

/**
 *
 * @author Marcel
 */
public class Person {
    
    private String vorname;
    private String nachname;
    
    private Anrede anrede;
    
    private Adresse adresse;
    public Person()  {}
 
    public Person(String vorname, String nachname, Anrede anrede, Adresse adresse) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.anrede = anrede;
        this.adresse = adresse;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Anrede getAnrede() {
        return anrede;
    }

    public void setAnrede(Anrede anrede) {
        this.anrede = anrede;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    } 
    
}
