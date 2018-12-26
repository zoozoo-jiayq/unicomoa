
const ENV = "dev";

const CONFIG = {
  dev:{
    server:"http://127.0.0.1:8080/xmjcyOA" 
  },
  prod:{

  }
}

module.exports = CONFIG[ENV]