const express = require('express');
const app = express();
const path = require('path');

app.set('view engine', 'ejs');
app.use(express.static(path.join(__dirname, 'public')));

// Simulación de datos de la DB Oracle
const datosPartida = {
    nombreCiv: "Imperio de Alumno",
    recursos: { madera: 4500, hierro: 2100, comida: 8000, mana: 120 },
    tecnologias: { ataque: 5, defensa: 3 },
    ejercito: [
        { tipo: "Swordsman", cant: 45 },
        { tipo: "Spearman", cant: 30 },
        { tipo: "Magician", cant: 5 }
    ],
    reportes: [
        { id: 1, fecha: "2024-05-20 14:30", resultado: "Victoria", bajas: 4, botin: "500 Madera" },
        { id: 2, fecha: "2024-05-21 09:15", resultado: "Derrota", bajas: 12, botin: "0" }
    ]
};

app.get('/', (req, res) => {
    res.render('index', { datos: datosPartida });
});

app.get('/reportes', (req, res) => {
    res.render('reportes', { reportes: datosPartida.reportes });
});

const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Web de Civilizations lista en http://localhost:${PORT}`);
});