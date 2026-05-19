package Save_Load;

import Logic.ArrowTower;
import Logic.Cannon;
import Logic.Catapult;
import Logic.Civilization;
import Logic.Crossbow;
import Logic.Magician;
import Logic.MilitaryUnit;
import Logic.Priest;
import Logic.RocketLauncherTower;
import Logic.Spearman;
import Logic.Swordsman;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersistenciaMySQL {

    // Persona 2 configurará aquí su puerto de conexión local y contraseña de MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/civilizations_db"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // =========================================================================
    // MÉTODO GUARDAR (SAVE)
    // =========================================================================
    public void save(Civilization civilization) {
        try (Connection conn = conectar()) {
            
            // 1. Actualizar recursos y edificios mapeando tus getters con sus columnas
            String sqlCiv = "UPDATE civilizacion SET comida=?, madera=?, hierro=?, mana=?, "
                          + "tech_defensa=?, tech_ataque=?, granjas=?, carpinterias=?, herrerias=?, "
                          + "torres_magicas=?, iglesias=? WHERE id = 1";
            
            try (PreparedStatement ps = conn.prepareStatement(sqlCiv)) {
                ps.setInt(1, civilization.getFood());
                ps.setInt(2, civilization.getWood());
                ps.setInt(3, civilization.getIron());
                ps.setInt(4, civilization.getMana());
                
                ps.setInt(5, civilization.getArmorTechnologyLevel());
                ps.setInt(6, civilization.getAttackTechnologyLevel());
                
                ps.setInt(7, civilization.getFarmLevel());
                ps.setInt(8, civilization.getCarpentryLevel());
                ps.setInt(9, civilization.getSmithyLevel());
                ps.setInt(10, civilization.getMagicTowerLevel());
                ps.setInt(11, civilization.getChurchLevel());
                
                ps.executeUpdate();
            }

            // 2. Actualizar las cantidades de tropas según las etiquetas VARCHAR de su script
            String[] nombresUnidades = {
                "Swordsman", "Spearman", "Crossbow", "Cannon", 
                "Arrow Tower", "Catapult", "Rocket Launcher", "Magician", "Priest"
            };

            String sqlEjercito = "UPDATE ejercito SET cantidad = ? WHERE tipo_unidad = ?";
            
            try (PreparedStatement psEj = conn.prepareStatement(sqlEjercito)) {
                for (int i = 0; i < civilization.getArmy().length; i++) {
                    int cantidadActual = civilization.getArmy()[i].size(); 
                    
                    psEj.setInt(1, cantidadActual);
                    psEj.setString(2, nombresUnidades[i]);
                    psEj.addBatch();
                }
                psEj.executeBatch();
            }
            
            System.out.println("[MySQL] Estado guardado correctamente en la base de datos.");

        } catch (SQLException e) {
            System.err.println("[MySQL Error] Error al ejecutar el guardado: " + e.getMessage());
        }
    }

    // =========================================================================
    // MÉTODO CARGAR (LOAD)
    // =========================================================================
    public Civilization load() {
        Civilization civilization = new Civilization();
        
        try (Connection conn = conectar()) {
            
            // 1. Extraer los datos relacionales e introducirlos con tus setters
            String sqlCiv = "SELECT * FROM civilizacion WHERE id = 1";
            try (PreparedStatement ps = conn.prepareStatement(sqlCiv);
                 ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) {
                    civilization.setFood(rs.getInt("comida"));
                    civilization.setWood(rs.getInt("madera"));
                    civilization.setIron(rs.getInt("hierro"));
                    civilization.setMana(rs.getInt("mana"));

                    civilization.setArmorTechnologyLevel(rs.getInt("tech_defensa"));
                    civilization.setAttackTechnologyLevel(rs.getInt("tech_ataque"));
                    
                    civilization.setFarmLevel(rs.getInt("granjas"));
                    civilization.setCarpentryLevel(rs.getInt("carpinterias"));
                    civilization.setSmithyLevel(rs.getInt("herrerias"));
                    civilization.setMagicTowerLevel(rs.getInt("torres_magicas"));
                    civilization.setChurchLevel(rs.getInt("iglesias"));
                }
            }

            // 2. Traducir las strings del ejército de tu compañero para rellenar tus ArrayLists
            String sqlEjercito = "SELECT tipo_unidad, cantidad FROM ejercito";
            try (PreparedStatement ps = conn.prepareStatement(sqlEjercito);
                 ResultSet rs = ps.executeQuery()) {
                
                // Limpiamos las listas iniciales para reconstruirlas limpias desde la base de datos
                for (ArrayList<MilitaryUnit> lista : civilization.getArmy()) {
                    lista.clear();
                }

                while (rs.next()) {
                    String nombreTipo = rs.getString("tipo_unidad");
                    int cantidad = rs.getInt("cantidad");
                    
                    int indiceArray = -1;
                    switch (nombreTipo) {
                        case "Swordsman":       indiceArray = 0; break;
                        case "Spearman":        indiceArray = 1; break;
                        case "Crossbow":        indiceArray = 2; break;
                        case "Cannon":          indiceArray = 3; break;
                        case "Arrow Tower":     indiceArray = 4; break;
                        case "Catapult":        indiceArray = 5; break;
                        case "Rocket Launcher": indiceArray = 6; break;
                        case "Magician":        indiceArray = 7; break;
                        case "Priest":          indiceArray = 8; break;
                    }
                    
                    if (indiceArray != -1) {
                        for (int i = 0; i < cantidad; i++) {
                            MilitaryUnit unidad = null;
                            
                            // Instanciamos inyectando el nivel de tecnologías que acabamos de recuperar arriba
                            switch (indiceArray) {
                                case 0: unidad = new Swordsman(civilization.getArmorTechnologyLevel(), civilization.getAttackTechnologyLevel()); break;
                                case 1: unidad = new Spearman(civilization.getArmorTechnologyLevel(), civilization.getAttackTechnologyLevel()); break;
                                case 2: unidad = new Crossbow(civilization.getArmorTechnologyLevel(), civilization.getAttackTechnologyLevel()); break;
                                case 3: unidad = new Cannon(civilization.getArmorTechnologyLevel(), civilization.getAttackTechnologyLevel()); break;
                                case 4: unidad = new ArrowTower(civilization.getArmorTechnologyLevel(), civilization.getAttackTechnologyLevel()); break;
                                case 5: unidad = new Catapult(civilization.getArmorTechnologyLevel(), civilization.getAttackTechnologyLevel()); break;
                                case 6: unidad = new RocketLauncherTower(civilization.getArmorTechnologyLevel(), civilization.getAttackTechnologyLevel()); break;
                                case 7: unidad = new Magician(civilization.getArmorTechnologyLevel(), civilization.getAttackTechnologyLevel()); break;
                                case 8: unidad = new Priest(civilization.getArmorTechnologyLevel(), civilization.getAttackTechnologyLevel()); break;
                            }
                            
                            if (unidad != null) {
                                civilization.getArmy()[indiceArray].add(unidad);
                            }
                        }
                    }
                }
            }
            
            System.out.println("[MySQL] Partida restaurada y cargada con éxito.");
            return civilization;

        } catch (SQLException e) {
            System.err.println("[MySQL Error] Error al cargar la partida: " + e.getMessage());
            return null;
        }
    }
}