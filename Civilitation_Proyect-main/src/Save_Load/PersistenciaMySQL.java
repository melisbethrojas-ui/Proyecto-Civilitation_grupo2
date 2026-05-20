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

    private static final String URL = "jdbc:mysql://192.168.17.234:3306/civilizations_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // =========================================================================
    // MÉTODO GUARDAR (SAVE)
    // =========================================================================
    public void save(Civilization civilization) {
        try (Connection conn = conectar()) {

            // 1. Guardar recursos, tecnologías y edificios (como contadores)
            String sqlCiv = "UPDATE civilizacion SET comida=?, madera=?, hierro=?, mana=?, "
                    + "tech_defensa=?, tech_ataque=?, granjas=?, carpinterias=?, herrerias=?, "
                    + "torres_magicas=?, iglesias=? WHERE id = 1";

            try (PreparedStatement ps = conn.prepareStatement(sqlCiv)) {

                ps.setInt(1, civilization.food);
                ps.setInt(2, civilization.wood);
                ps.setInt(3, civilization.iron);
                ps.setInt(4, civilization.mana);

                ps.setInt(5, civilization.getTechnologyDefense());
                ps.setInt(6, civilization.getTechnologyAttack());

                ps.setInt(7, civilization.farm);
                ps.setInt(8, civilization.carpentry);
                ps.setInt(9, civilization.smithy);
                ps.setInt(10, civilization.magicTower);
                ps.setInt(11, civilization.church);

                ps.executeUpdate();
            }

            // 2. Guardar ejército
            String[] nombresUnidades = {
                    "Swordsman", "Spearman", "Crossbow", "Cannon",
                    "Arrow Tower", "Catapult", "Rocket Launcher", "Magician", "Priest"
            };

            String sqlEjercito = "UPDATE ejercito SET cantidad = ? WHERE tipo_unidad = ?";

            try (PreparedStatement psEj = conn.prepareStatement(sqlEjercito)) {
                for (int i = 0; i < civilization.army.length; i++) {
                    int cantidadActual = civilization.army[i].size();

                    psEj.setInt(1, cantidadActual);
                    psEj.setString(2, nombresUnidades[i]);
                    psEj.addBatch();
                }
                psEj.executeBatch();
            }

            System.out.println("[MySQL] Estado guardado correctamente.");

        } catch (SQLException e) {
            System.err.println("[MySQL Error] Error al guardar: " + e.getMessage());
        }
    }

    // =========================================================================
    // MÉTODO CARGAR (LOAD)
    // =========================================================================
    public Civilization load() {

        Civilization civilization = new Civilization();

        try (Connection conn = conectar()) {

            // 1. Cargar recursos, tecnologías y edificios
            String sqlCiv = "SELECT * FROM civilizacion WHERE id = 1";

            try (PreparedStatement ps = conn.prepareStatement(sqlCiv);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    civilization.food = rs.getInt("comida");
                    civilization.wood = rs.getInt("madera");
                    civilization.iron = rs.getInt("hierro");
                    civilization.mana = rs.getInt("mana");

                    civilization.setArmorTechnologyLevel(rs.getInt("tech_defensa"));
                    civilization.setAttackTechnologyLevel(rs.getInt("tech_ataque"));

                    civilization.farm = rs.getInt("granjas");
                    civilization.carpentry = rs.getInt("carpinterias");
                    civilization.smithy = rs.getInt("herrerias");
                    civilization.magicTower = rs.getInt("torres_magicas");
                    civilization.church = rs.getInt("iglesias");
                }
            }

            // 2. Cargar ejército
            String sqlEjercito = "SELECT tipo_unidad, cantidad FROM ejercito";

            try (PreparedStatement ps = conn.prepareStatement(sqlEjercito);
                 ResultSet rs = ps.executeQuery()) {

                for (ArrayList<MilitaryUnit> lista : civilization.army) {
                    lista.clear();
                }

                while (rs.next()) {

                    String tipo = rs.getString("tipo_unidad");
                    int cantidad = rs.getInt("cantidad");

                    int index = switch (tipo) {
                        case "Swordsman" -> 0;
                        case "Spearman" -> 1;
                        case "Crossbow" -> 2;
                        case "Cannon" -> 3;
                        case "Arrow Tower" -> 4;
                        case "Catapult" -> 5;
                        case "Rocket Launcher" -> 6;
                        case "Magician" -> 7;
                        case "Priest" -> 8;
                        default -> -1;
                    };

                    if (index != -1) {
                        for (int i = 0; i < cantidad; i++) {

                            MilitaryUnit unidad = switch (index) {
                                case 0 -> new Swordsman(civilization.getTechnologyDefense(), civilization.getTechnologyAttack());
                                case 1 -> new Spearman(civilization.getTechnologyDefense(), civilization.getTechnologyAttack());
                                case 2 -> new Crossbow(civilization.getTechnologyDefense(), civilization.getTechnologyAttack());
                                case 3 -> new Cannon(civilization.getTechnologyDefense(), civilization.getTechnologyAttack());
                                case 4 -> new ArrowTower(civilization.getTechnologyDefense(), civilization.getTechnologyAttack());
                                case 5 -> new Catapult(civilization.getTechnologyDefense(), civilization.getTechnologyAttack());
                                case 6 -> new RocketLauncherTower(civilization.getTechnologyDefense(), civilization.getTechnologyAttack());
                                case 7 -> new Magician(civilization.getTechnologyDefense(), civilization.getTechnologyAttack());
                                case 8 -> new Priest(civilization.getTechnologyDefense(), civilization.getTechnologyAttack());
                                default -> null;
                            };

                            if (unidad != null) {
                                civilization.army[index].add(unidad);
                            }
                        }
                    }
                }
            }

            System.out.println("[MySQL] Partida cargada correctamente.");
            return civilization;

        } catch (SQLException e) {
            System.err.println("[MySQL Error] Error al cargar: " + e.getMessage());
            return null;
        }
    }
}
