const express = require('express');
const { engine } = require('express-handlebars');
const path = require('path');
const webRoutes = require('./routes/webRoutes');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

// Configuración del Motor de Plantillas Handlebars (HBS)
app.engine('hbs', engine({
    extname: '.hbs',
    defaultLayout: 'main',
    layoutsDir: path.join(__dirname, 'views/layouts')
}));
app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));

// Middlewares
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'public'))); // Archivos estáticos (CSS)

// Rutas
app.use('/', webRoutes);

// Servidor escuchando
app.listen(PORT, () => {
    console.log(`Servidor de Civilizations corriendo en http://localhost:${PORT}`);
});