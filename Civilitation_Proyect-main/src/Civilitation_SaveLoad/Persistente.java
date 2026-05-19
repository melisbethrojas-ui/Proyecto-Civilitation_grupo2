package Civilitation_SaveLoad;


import Civilitation_Proyect.Civilization;

public interface Persistente {
    public void save(Civilization civilization);
    
    public Civilization load();
}