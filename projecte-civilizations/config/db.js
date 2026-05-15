const mysql = require('mysql2');
require('dotenv').config(); // Por si acaso

const pool = mysql.createPool({
    host: process.env.DB_HOST || 'localhost',
    user: process.env.DB_USER || 'root',
    password: process.env.DB_PASSWORD || 'root', // <-- Aquí tiene que pillar el 'root'
    database: process.env.DB_NAME || 'civilizations_db',
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0
});

module.exports = pool.promise();