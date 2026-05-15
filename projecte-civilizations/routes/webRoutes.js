const express = require('express');
const router = express.Router();
const webController = require('../controllers/webController');

router.get('/', webController.getHome);
router.get('/batallas', webController.getBatallas);

module.exports = router;