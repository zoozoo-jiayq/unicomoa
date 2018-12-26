
const ENV = "dev";

const CONFIG = {
  dev:{
    server:"http://47.92.159.206:10067/ltOA" 
  },
  prod:{

  }
}

module.exports = CONFIG[ENV]