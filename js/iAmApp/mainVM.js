var models = require('../models');
// var bPromise = require('../config/bluebird');
var  mainVM = {
  model : undefined,
  init : function(cb) {
    iAmApp.iAmAjax.get('establishmentRegistry.json', (err, response) => {
      if(err) return cb(err);

      this.model = new models.Registry(response);
      // this.model = response;
      cb(null, this.model);
    });
    // this.model = new models.Launcher();
    // cb(null, this.model);
  },
  execute : function() {
    let args = arguments;
    let path = args[0];
    let action = args[1];
    let actionObj = pathParser(this, path);
    if(args.length < 3) return;
    // Mock Js object updation and callback Java callback
    // if(typeof args[2] === 'function') {
    //   actionObj[action](args[2]);
    // }
    // else {
    //   actionObj[action](args[2], args[3]);
    // }
  }
};

module.exports = mainVM;
