package Save_Load;

import Logic.Civilization;
import Logic.MilitaryUnit;
// Asegúrate de importar tus clases de unidades si están en otro package (ej: Logic.Units.* o donde estén)
// import Logic.Swordsman; 
// import Logic.Spearman;
// ...

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersistenciaOracle implements Persistente {

    // =========================================================================
    // CAMBIOS PARA PERSONA 2: Configurar aquí las credenciales de vuestro Oracle
    // =========================================================================
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; 
    private static final String USER = "SYSTEM"; 
    private static final String PASSWORD = "password"; 

    // Método privado para abrir la conexión
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void save(Civilization civilization) {
        // El try-with-resources asegura que la conexión se cierre de forma automática
        try (Connection conn = conectar()) {
            
            // -----------------------------------------------------------------
            // 1. GUARDAR DATOS BASE DE LA CIVILIZACIÓN
            // -----------------------------------------------------------------
            // Persona 2: Ajusta los nombres de las columnas si los has cambiado en tu script SQL
            String sqlCiv = "UPDATE civilizacion SET comida=?, madera=?, hierro=?, mana=?, "
                          + "nivel_granja=?, nivel_herreria=?, nivel_carpinteria=?, nivel_iglesia=?, nivel_torre_magos=?, "
                          + "tech_armadura=?, tech_ataque=? WHERE id_partida = 1";
            
            try (PreparedStatement ps = conn.prepareStatement(sqlCiv)) {
                // Recursos actuales
                ps.setInt(1, civilization.getFood());
                ps.setInt(2, civilization.getWood());
                ps.setInt(3, civilization.getIron());
                ps.setInt(4, civilization.getMana());
                
                // Niveles de edificios (Usa tus variables exactas)
                ps.setInt(5, civilization.getFarmLevel());
                ps.setInt(6, civilization.getSmithyLevel());
                ps.setInt(7, civilization.getCarpentryLevel());
                ps.setInt(8, civilization.getChurchLevel());
                ps.setInt(9, civilization.getMagicTowerLevel());
                
                // Tecnologías
                ps.setInt(10, civilization.getArmorTechnologyLevel());
                ps.setInt(11, civilization.getAttackTechnologyLevel());
                
                ps.executeUpdate();
            }

            // -----------------------------------------------------------------
            // 2. GUARDAR EL EJÉRCITO (ARMY)
            // -----------------------------------------------------------------
            // Primero limpiamos las unidades anteriores en la BD para evitar duplicados
            String sqlDeleteUnidades = "DELETE FROM unidades_partida WHERE id_partida = 1";
            try (PreparedStatement psDel = conn.prepareStatement(sqlDeleteUnidades)) {
                psDel.executeUpdate();
            }

            // Insertamos las unidades actuales utilizando procesamiento por lotes (Batch) para máxima eficiencia
            String sqlInsUnidad = "INSERT INTO unidades_partida (id_partida, tipo_unidad, experiencia) VALUES (1, ?, ?)";
            try (PreparedStatement psIns = conn.prepareStatement(sqlInsUnidad)) {
                
                // Recorremos tu array de ArrayLists (las 9 posiciones)
                for (int i = 0; i < civilization.getArmy().length; i++) {
                    ArrayList<MilitaryUnit> listaUnidades = civilization.getArmy()[i];
                    
                    for (MilitaryUnit unidad : listaUnidades) {
                        psIns.setInt(1, i); // Guardamos la posición (0=Swordsman, 1=Spearman, etc.)
                        psIns.setInt(2, unidad.getExperience()); // Guardamos su experiencia acumulada
                        psIns.addBatch(); // Lo añade al paquete
                    }
                }
                psIns.executeBatch(); // Envía todo el ejército de golpe a Oracle
            }
            
            System.out.println("[DB] Partida guardada con éxito en Oracle.");

        } catch (SQLException e) {
            System.err.println("[DB Error] Error al guardar la partida: " + e.getMessage());
        }
    }

    @Override
    public Civilization load() {
        Civilization civilization = new Civilization();
        
        try (Connection conn = conectar()) {
            
            // -----------------------------------------------------------------
            // 1. CARGAR DATOS BASE DE LA CIVILIZACIÓN
            // -----------------------------------------------------------------
            String sqlCiv = "SELECT * FROM civilizacion WHERE id_partida = 1";
            try (PreparedStatement ps = conn.prepareStatement(sqlCiv);
                 ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) {
                    // Persona 2: Ajusta los nombres de los rs.getInt("...") según tus columnas de Oracle
                    civilization.setFood(rs.getInt("comida"));
                    civilization.setWood(rs.getInt("madera"));
                    civilization.setIron(rs.getInt("hierro"));
                    civilization.setMana(rs.getInt("mana"));
                    
                    // NOTA PARA PERSONA 2: Si necesitas que cree métodos set específicos en mi clase 
                    // Civilization para los niveles (ej. setFarmLevel), avísame. De momento asumo que los tienes 
                    // o los añadiremos directamente si da error de compilación aquí:
                    // civilization.setFarmLevel(rs.getInt("nivel_granja"));
                    // civilization.setSmithyLevel(rs.getInt("nivel_herreria"));
                    // civilization.setCarpentryLevel(rs.getInt("nivel_carpinteria"));
                    // civilization.setChurchLevel(rs.getInt("nivel_iglesia"));
                    // civilization.setMagicTowerLevel(rs.getInt("nivel_torre_magos"));
                    // civilization.setArmorTechnologyLevel(rs.getInt("tech_armadura"));
                    // civilization.setAttackTechnologyLevel(rs.getInt("tech_ataque"));
                }
            }

            // -----------------------------------------------------------------
            // 2. RECONSTRUIR EL EJÉRCITO (ARMY)
            // -----------------------------------------------------------------
            String sqlUnidades = "SELECT tipo_unidad, experiencia FROM unidades_partida WHERE id_partida = 1";
            try (PreparedStatement ps = conn.prepareStatement(sqlUnidades);
                 ResultSet rs = ps.executeQuery()) {
                
                // Vaciamos el ejército inicial por seguridad
                for (ArrayList<MilitaryUnit> lista : civilization.getArmy()) {
                    lista.clear();
                }

                // Leemos las unidades de la base de datos una a una
                while (rs.next()) {
                    int tipo = rs.getInt("tipo_unidad");
                    int exp = rs.getInt("experiencia");
                    
                    MilitaryUnit unidad = null;
                    
                    // Reinstanciamos pasándole los niveles tecnológicos que acabamos de cargar
                    switch (tipo) {
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
                        unidad.setExperience(exp); // Le devolvemos los puntos de experiencia que salvó
                        civilization.getArmy()[tipo].add(unidad); // Almacenamos en su ArrayList del array
                    }
                }
            }
            
            System.out.println("[DB] Partida cargada con éxito desde Oracle.");
            return civilization;

        } catch (SQLException e) {
            System.err.println("[DB Error] Error al cargar la partida: " + e.getMessage());
            return null;
        }
    }
}