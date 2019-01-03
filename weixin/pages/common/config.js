
const ENV = "dev";
const CONFIG = {
  dev:{
    server:"http://192.168.1.112:9988" 
  },
  prod:{
  }
}
module.exports = CONFIG[ENV]