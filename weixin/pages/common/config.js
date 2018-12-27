
const ENV = "dev";
const CONFIG = {
  dev:{
    server:"http://47.92.159.206:9988" 
  },
  prod:{
  }
}
module.exports = CONFIG[ENV]