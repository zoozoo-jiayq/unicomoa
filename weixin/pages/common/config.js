
const ENV = "dev";

const CONFIG = {
  dev:{
    server:"http://192.168.1.111:8080"
  },
  prod:{

  }
}

module.exports = CONFIG[ENV]