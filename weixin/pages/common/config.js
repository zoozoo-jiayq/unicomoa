
const ENV = "dev";
const CONFIG = {
  dev:{
    server:"http://192.168.1.112:9988",
    appid:"wx65f1a966482372e9",
    appsecret:"cd82045d6af6c27ee91444a0fe6275d6"
  },
  prod:{
  }
}
module.exports = CONFIG[ENV]