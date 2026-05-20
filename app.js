const express = require('express');
const { engine } = require('express-handlebars');
const path = require('path');
require('dotenv').config({ path: path.join(__dirname, '.env') });

const app = express();
const PORT = process.env.PORT || 3000;

// Configuración del motor de plantillas Handlebars (HBS)
app.engine('hbs', engine({
    extname: '.hbs',
    defaultLayout: 'main',
    layoutsDir: path.join(__dirname, 'projecte-civilizations', 'views', 'layouts')
}));
app.set('view engine', 'hbs');

// CORRECCIÓN CRUCIAL: Decirle a Express dónde están las vistas reales
app.set('views', path.join(__dirname, 'projecte-civilizations', 'views'));

// Servir archivos estáticos (CSS, JS de la web)
app.use(express.static(path.join(__dirname, 'projecte-civilizations', 'public')));

// Middleware para entender datos de formularios y JSON
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

// CORRECCIÓN CRUCIAL: Ruta corregida apuntando a la subcarpeta
const webRoutes = require('./projecte-civilizations/routes/webRoutes');
app.use('/', webRoutes);

// Arrancar el servidor
app.listen(PORT, () => {
    console.log(`\x1b[32m%s\x1b[0m`, `[OK] Servidor de Civilizations corriendo en http://localhost:${PORT}`);
});