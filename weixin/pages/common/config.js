
const ENV = "dev";

const CONFIG = {
  dev:{
    server:"http://192.168.1.113:8080"
  },
  prod:{

  }
}

module.exports = CONFIG[ENV]