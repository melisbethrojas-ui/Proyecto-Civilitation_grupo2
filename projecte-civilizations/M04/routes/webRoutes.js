const express = require('express');
const router = express.Router();
const webController = require('../controllers/webController');

router.get('/', webController.getPrincipal);
router.get('/batallas', webController.getBatallas);
router.get('/informe', webController.getInformeBatalla);
router.get('/civilitzacio', webController.getCivilitzacio);
router.get('/programadors', webController.getProgramadors);

module.exports = router;