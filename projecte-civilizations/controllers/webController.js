const db = require('../config/db');

// 1. PORTADA: Muestra info del juego y las 2 últimas batallas
exports.getPrincipal = async (req, res) => {
    try {
        const [ultimasBatallas] = await db.query('SELECT * FROM batallas ORDER BY id DESC LIMIT 2');
        res.render('principal', { ultimas: ultimasBatallas });
    } catch (error) {
        console.error("Error en Principal:", error);
        res.status(500).send("Error al cargar la página principal");
    }
};

// 2. HISTORIAL DE BATALLAS: Sincronizado exactamente con tu tabla 'batallas'
exports.getBatallas = async (req, res) => {
    try {
        const [todas] = await db.query('SELECT * FROM batallas ORDER BY id DESC');
        const [contador] = await db.query('SELECT COUNT(*) AS total FROM batallas');
        
        // Pasamos un objeto limpio convirtiendo los resultados de MySQL a JSON directo
        res.render('batallas', { 
            batallas: JSON.parse(JSON.stringify(todas)), 
            totalBatallas: contador[0].total 
        });
    } catch (error) {
        console.error("Error en Batallas:", error);
        res.status(500).send("Error al cargar el historial de batallas");
    }
};

// 3. INFORME DETALLADO: Muestra una sola batalla pasándole ?informe=id
exports.getInformeBatalla = async (req, res) => {
    try {
        const idBatalla = req.query.informe;
        if (!idBatalla) return res.status(400).send("Falta el ID del informe.");

        const [resultado] = await db.query('SELECT * FROM batallas WHERE id = ?', [idBatalla]);
        if (resultado.length === 0) return res.status(404).send("Informe no encontrado.");

        res.render('informe', { batalla: resultado[0] });
    } catch (error) {
        console.error("Error en Informe:", error);
        res.status(500).send("Error al cargar el informe de batalla");
    }
};

// 4. CIVILITZACIÓ: Apuntando directamente a 'civilizacion' y 'ejercito'
exports.getCivilitzacio = async (req, res) => {
    try {
        const [rowsCivilizacion] = await db.query('SELECT * FROM civilizacion LIMIT 1');
        const [rowsEjercito] = await db.query('SELECT * FROM ejercito');
        
        res.render('civilitzacio', { 
            civi: rowsCivilizacion[0] ? JSON.parse(JSON.stringify(rowsCivilizacion[0])) : null, 
            ejercito: JSON.parse(JSON.stringify(rowsEjercito))
        });
    } catch (error) {
        console.error("Error en Civilització:", error);
        res.status(500).send("Error al cargar las estadísticas de la civilización");
    }
};
// 5. PROGRAMADORS: Página del equipo
exports.getProgramadors = (req, res) => {
    // CORRECCIÓN: Añadimos la 's' para que coincida exactamente con programadors.hbs
    res.render('programadors'); 
};