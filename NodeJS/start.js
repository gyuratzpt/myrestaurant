// Transpile all code following this line with babel and use 'env' (aka ES6) preset.
require('babel-register')({
    presets: ['env'],
});
//ez valami kötelező?


// Import the rest of our application.
module.exports = require('./app.js');
//egyetlen dolgot importálunk, a saját app-ot?? require