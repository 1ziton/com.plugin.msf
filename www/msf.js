var exec = require('cordova/exec');



exports.initMsf = function(arg0, success, error) {
    exec(success, error, "msf", "init", []);
};

exports.sign = function(arg0, success, error) {
    exec(success, error, "msf", "sign", [arg0]);
};
