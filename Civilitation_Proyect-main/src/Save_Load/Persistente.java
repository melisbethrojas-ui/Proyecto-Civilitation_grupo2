package Save_Load;

import Logic.Civilization;
public interface Persistente {
    public void save(Civilization civilization);
    
    public Civilization load();
}


