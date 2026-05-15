const db = require('../config/db');

exports.getHome = async (req, res) => {
    try {
        // Ejecutamos consultas paralelas en la base de datos
        const [civiRows] = await db.query('SELECT * FROM civilizacion WHERE id = 1');
        const [ejercitoRows] = await db.query('SELECT * FROM ejercito');

        res.render('home', {
            civi: civiRows[0],
            ejercito: ejercitoRows
        });
    } catch (error) {
        console.error("Error cargando el panel de control:", error);
        res.status(500).send("Error interno del servidor");
    }
};

exports.getBatallas = async (req, res) => {
    try {
        // Traemos las batallas ordenadas para ver las más recientes primero
        const [batallaRows] = await db.query('SELECT * FROM batallas ORDER BY numero_batalla DESC');
        
        res.render('batallas', {
            batallas: batallaRows
        });
    } catch (error) {
        console.error("Error cargando el histórico de batallas:", error);
        res.status(500).send("Error interno del servidor");
    }
};