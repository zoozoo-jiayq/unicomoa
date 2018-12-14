
    var type = $("#menu").val();//menu
    var searchType = $("#searchType").val();
    var columns = [];
    var  _title;
    //---待办任务--begin
    //收文登记
    if(searchType == 'processing'){
        if(type == 1){
            columns = [
                { "mDataProp": "title","sClass": "longTxt","sWidth": "34%",
                   "fnRender" :function(obj){
                	 var instanceid = obj.aData["instanceId"];
                	 var title = obj.aData["title"];
                	 _title = title;
                     return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                    }
                },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "33%"},
                { "mDataProp": "sendDept","sWidth": "33%","sClass": "longTxt"},
                // { "mDataProp": "gatherDept","sWidth": "160"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
                { "mDataProp": "secretLevel","sWidth": "60"},
                { "mDataProp": "huanji","sWidth": "60"},
                { "mDataProp": "sourceZh","sWidth": "50",
                  "fnRender" :function(obj){
                        var sourceZh = obj.aData["sourceZh"];
                        if(sourceZh == "系统外"){
                            return "<font>"+sourceZh+"</font>"
                        }else{
                            return sourceZh;
                        }
                  }
                },
                { "mDataProp": "startTime","sWidth": "120"},
                { "mDataProp": "option","sWidth": "80","sClass":"data_l right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"]; 
                    var source = obj.aData["source"]; 
                    var instanceId = obj.aData["instanceId"];
                    var title = _title;
                    var msg = "确认要删除["+title+"]的公文吗？删除后不可恢复。";
                    if(source == 0){
                        return "<a href='#'  onclick='domListOption.doEdit("+taskId+")' >登记</a><a  onclick='domListOption.doDelete("+taskId+",\""+msg+"\")'  href='#'>删除</a>";
                    }else{
                        return "<a href='#'  onclick='domListOption.doInnerSystemUpdate("+taskId+",\""+instanceId+"\")' >登记</a>";
                    }    
                  }
                }
            ] ;
        }else if(type == 2){//领导批阅
             columns = [
                { "mDataProp": "title","sClass": "longTxt" ,"sWidth": "34%",
                    "fnRender" :function(obj){
                   	 var instanceid = obj.aData["instanceId"];
                   	 var title = obj.aData["title"];
                   	 _title = title;
                        return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                       }
                   },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "33%"},
                { "mDataProp": "sendDept","sWidth": "33%","sClass": "longTxt"},
                // { "mDataProp": "gatherDept","sWidth": "160"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
                { "mDataProp": "secretLevel","sWidth": "60"},
                { "mDataProp": "huanji","sWidth": "60"},
                { "mDataProp": "creater","sWidth": "70"},
                { "mDataProp": "startTime","sWidth": "120"},
                { "mDataProp": "option","sWidth": "70","sClass":"right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"]; 
                    var source = obj.aData["source"];
                    //系统外
                    if(source == 0 ){
                       return "<a href='#' onclick='domListOption.doEdit("+taskId+")'>批阅</a>";
                    }else{//系统内
                       return "<a href='#' onclick='domListOption.doEdit("+taskId+")'>批阅</a>";
                    }       
                  }
                }
            ] ;
        }else if(type == 3){//收文分发
            columns = [
                { "mDataProp": "title","sClass": "longTxt","sWidth": "34%",
                    "fnRender" :function(obj){
                      	 var instanceid = obj.aData["instanceId"];
                      	 var title = obj.aData["title"];
                      	 _title = title;
                           return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                          }
                      },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "33%"},
                { "mDataProp": "sendDept","sWidth": "33%","sClass": "longTxt"},
                // { "mDataProp": "gatherDept","sWidth": "160"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
                { "mDataProp": "secretLevel","sWidth": "60"},
                { "mDataProp": "huanji","sWidth": "60"},
                { "mDataProp": "creater","sWidth": "70"},
                { "mDataProp": "startTime","sWidth": "120"},
                { "mDataProp": "option","sWidth": "70","sClass":"right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"];        
                       return "<a href='#' onclick='domListOption.doEdit("+taskId+")'>分发</a>";
                  }
                }
            ] ;
        }
        else if(type == 4){//收文阅读
            columns = [
                { "mDataProp": "title","sClass": "longTxt","sWidth": "34%",
                    "fnRender" :function(obj){
                      	 var instanceid = obj.aData["instanceId"];
                      	 var title = obj.aData["title"];
                      	 _title = title;
                           return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                          }
                      },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "33%"},
                { "mDataProp": "sendDept","sWidth": "33%","sClass": "longTxt"},
                // { "mDataProp": "gatherDept","sWidth": "160"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
                { "mDataProp": "secretLevel","sWidth": "60"},
                { "mDataProp": "huanji","sWidth": "60"},
                { "mDataProp": "creater","sWidth": "70"},
                { "mDataProp": "startTime","sWidth": "120"},
                { "mDataProp": "option","sWidth": "70","sClass":"right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"];        
                       return "<a href='#' onclick='domListOption.doEdit("+taskId+")'>阅读</a>";
                  }
                }
            ] ;
        }else if(type == 5){
            columns = [
                { "mDataProp": "title","sClass": "longTxt","sWidth": "50%",
                    "fnRender" :function(obj){
                      	 var instanceid = obj.aData["instanceId"];
                      	 var title = obj.aData["title"];
                      	 _title = title;
                           return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                          }
                      },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "50%"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
                { "mDataProp": "secretLevel","sWidth": "60"},
                { "mDataProp": "huanji","sWidth": "60"},
                { "mDataProp": "startTime","sWidth": "120"},
                { "mDataProp": "option","sWidth": "80","sClass":"oper right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"];  
                     var title = _title;
                     var msg = "确认要删除["+title+"]的公文吗？删除后不可恢复。";      
                       return "<a href='#' onclick='domListOption.doEdit("+taskId+")'>编辑</a><a onclick='domListOption.doDelete("+taskId+",\""+msg+"\")'  href='#'>删除</a>";
                  }
                }
            ] ;
        }else if(type == 6){
            columns = [
                { "mDataProp": "title","sClass": "longTxt","sWidth": "50%",
                    "fnRender" :function(obj){
                      	 var instanceid = obj.aData["instanceId"];
                      	 var title = obj.aData["title"];
                      	 _title = title;
                           return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                          }
                      },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "50%"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
                { "mDataProp": "secretLevel","sWidth": "60"},
                { "mDataProp": "huanji","sWidth": "60"},
                { "mDataProp": "creater","sWidth": "70"},
                { "mDataProp": "startTime","sWidth": "120"},
                { "mDataProp": "option","sWidth": "70","sClass":"right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"];        
                       return "<a href='#' onclick='domListOption.doEdit("+taskId+")'>核稿</a>";
                  }
                }
            ] ;
        }else if(type == 7){
            columns = [
                { "mDataProp": "title","sClass": "longTxt","sWidth": "50%" ,
                    "fnRender" :function(obj){
                      	 var instanceid = obj.aData["instanceId"];
                      	 var title = obj.aData["title"];
                      	 _title = title;
                           return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                          }
                      },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "50%"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
                { "mDataProp": "secretLevel","sWidth": "60"},
                { "mDataProp": "huanji","sWidth": "60"},
                { "mDataProp": "creater","sWidth": "70"},
                { "mDataProp": "startTime","sWidth": "120"},
                { "mDataProp": "option","sWidth": "70","sClass":"right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"];        
                       return "<a href='#' onclick='domListOption.doEdit("+taskId+")'>套红盖章</a>";
                  }
                }
            ] ;
        }else if(type == 8){
            columns = [
                { "mDataProp": "title","sClass": "longTxt","sWidth": "50%",
                    "fnRender" :function(obj){
                      	 var instanceid = obj.aData["instanceId"];
                      	 var title = obj.aData["title"];
                      	 _title = title;
                           return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                          }
                      },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "50%"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "150"},
                { "mDataProp": "secretLevel","sWidth": "60"},
                { "mDataProp": "huanji","sWidth": "60"},
                { "mDataProp": "creater","sWidth": "70"},
                { "mDataProp": "startTime","sWidth": "120"},
                { "mDataProp": "option","sWidth": "70","sClass":"right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"];        
                       return "<a href='#' onclick='domListOption.doEdit("+taskId+")'>分发</a>";
                  }
                }
            ] ;
        }else if(type == 9){
            columns = [
                { "mDataProp": "batchZip","sWidth": "30",
                    "fnRender":function(obj){
                      var taskId = obj.aData["option"];
                      var source = obj.aData["isZip"]; 
                      if(source == 'yes'){
                        return '<input type="checkbox" name="taskId" value="'+taskId+'" />';
                      }else{
                        return '<input type="checkbox" name="noSelected" disabled value="'+taskId+'" />';
                      }
                    }
                },
                { "mDataProp": "title","sClass": "longTxt","sWidth": "",
                    "fnRender" :function(obj){
                      	 var instanceid = obj.aData["instanceId"];
                      	 var title = obj.aData["title"];
                      	 _title = title;
                           return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                          }
                },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": ""},
                { "mDataProp": "sendDept","sWidth": "150","sClass": "longTxt"},
                // { "mDataProp": "gatherDept","sWidth": "160"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": ""},
                //{ "mDataProp": "secretLevel","sWidth": "60"},
                //{ "mDataProp": "huanji","sWidth": "60"},
                { "mDataProp": "ydrs","sWidth": "","sClass":"data_r"},
                { "mDataProp": "sdrs","sWidth": "","sClass":"data_r"},
                { "mDataProp": "creater","sWidth": ""},
                { "mDataProp": "startTime","sWidth": ""},
                { "mDataProp": "option","sWidth": "","sClass":"data_l right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"]; 
                    var source = obj.aData["isZip"]; 
                    var instanceId = obj.aData["instanceId"];
                    if(source == 'yes'){
                        return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceId+"\")' >查看</a><a href='#'  onclick='domListOption.doEdit("+taskId+")' >归档</a><a href='#' onclick='domListOption.doReadState(\""+instanceId+"\")'>传阅状态</a>";
                    }else{
                        return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceId+"\")'>查看</a><a href='#'  onclick='domListOption.doReadState(\""+instanceId+"\")' >传阅状态</a>";
                    }    
                  }
                }
            ] ;
        }else if(type == 10){
             columns = [
                { "mDataProp": "batchZip","sWidth": "30",
                  "fnRender":function(obj){
                     var taskId = obj.aData["option"];
                     return '<input type="checkbox" name="taskId" value="'+taskId+'" />';
                  }
                },
                { "mDataProp": "title","sClass": "longTxt","sWidth": "50%",
                    "fnRender" :function(obj){
                      	 var instanceid = obj.aData["instanceId"];
                      	 var title = obj.aData["title"];
                      	 _title = title;
                           return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                          }
                      },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "50%"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
                { "mDataProp": "secretLevel","sWidth": "60"},
                { "mDataProp": "huanji","sWidth": "60"},
                { "mDataProp": "creater","sWidth": "70"},
                { "mDataProp": "startTime","sWidth": "120"},
                { "mDataProp": "option","sWidth": "80","sClass":"oper right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"];   
                    var instanceId = obj.aData["instanceId"];     
                       return "<a href='#' onclick='domListOption.doEdit("+taskId+")'>归档</a><a href='#' onclick='domListOption.doViewHistory(\""+instanceId+"\")'>查看</a>";
                  }
                }
            ] ;
        }
    }
    //----待办任务---END

    //----已办任务---begin
    //已登记
    if(searchType == 'completed'){
        if(type == 1){
            columns = [
                { "mDataProp": "title","sClass": "longTxt","sWidth": "34%",
                    "fnRender" :function(obj){
                      	 var instanceid = obj.aData["instanceId"];
                      	 var title = obj.aData["title"];
                      	 _title = title;
                           return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                          }
                      },
                { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "33%"},
                { "mDataProp": "sendDept","sWidth": "33%","sClass": "longTxt"},
                // { "mDataProp": "gatherDept","sWidth": "160"},
                { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
                { "mDataProp": "secretLevel","sWidth": "40"},
                { "mDataProp": "huanji","sWidth": "40"},
                { "mDataProp": "state","sWidth": "50"},
                { "mDataProp": "sourceZh","sWidth": "50"},
                { "mDataProp": "creater","sWidth": "70"},
                 { "mDataProp": "startTime","sWidth": "120"},
                { "mDataProp": "option","sWidth": "70","sClass": "right_bdr0",
                  "fnRender" : function(obj){
                    var taskId = obj.aData["option"]; 
                    var instanceid = obj.aData["instanceId"];
                        return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceid+"\")'>查看</a>";
      
                  }
                }
            ] ;
        }else if(type == 2){
         columns = [
            { "mDataProp": "title","sClass": "longTxt" ,"sWidth": "40%",
                "fnRender" :function(obj){
                  	 var instanceid = obj.aData["instanceId"];
                  	 var title = obj.aData["title"];
                  	 _title = title;
                       return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                      }
                  },
            { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "30%"},
            { "mDataProp": "sendDept","sWidth": "30%","sClass": "longTxt"},
            // { "mDataProp": "gatherDept","sWidth": "160"},
            { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
            { "mDataProp": "secretLevel","sWidth": "50"},
            { "mDataProp": "huanji","sWidth": "50"},
            { "mDataProp": "state","sWidth": "60"},
            { "mDataProp": "creater","sWidth": "70"},
            { "mDataProp": "lastTimestamp","sWidth": "120"},
            { "mDataProp": "option","sWidth": "70","sClass": "right_bdr0",

              "fnRender" : function(obj){
                var taskId = obj.aData["option"]; 
                var instanceid = obj.aData["instanceId"];
                    return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceid+"\")'>查看</a>";
  
              }
            }
        ] ;
    }else if(type == 3){
        columns = [
            { "mDataProp": "title","sClass": "longTxt","sWidth": "" ,
                "fnRender" :function(obj){
                  	 var instanceid = obj.aData["instanceId"];
                  	 var title = obj.aData["title"];
                  	 _title = title;
                       return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                      }
                  },
            { "mDataProp": "wenhao","sClass": "longTxt","sWidth": ""},
            { "mDataProp": "sendDept","sWidth": "","sClass": "longTxt"},
            // { "mDataProp": "gatherDept","sWidth": "160"},
            { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": ""},
            { "mDataProp": "secretLevel","sWidth": ""},
            { "mDataProp": "huanji","sWidth": ""},
            { "mDataProp": "state","sWidth": ""},
            { "mDataProp": "creater","sWidth": ""},
            { "mDataProp": "lastTimestamp","sWidth": ""},
            { "mDataProp": "option","sWidth": "","sClass":"oper right_bdr0",

              "fnRender" : function(obj){
                var taskId = obj.aData["option"]; 
                var instanceid = obj.aData["instanceId"];
                    return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceid+"\")'>查看</a><a href='#' onclick='domListOption.doReadState(\""+instanceid+"\")'>传阅状态</a>";
  
              }
            }
        ] ;
    }else if(type == 4){
       columns = [
            { "mDataProp": "title","sClass": "longTxt","sWidth": "40%",
                "fnRender" :function(obj){
                  	 var instanceid = obj.aData["instanceId"];
                  	 var title = obj.aData["title"];
                  	 _title = title;
                       return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                      }
                  },
            { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "30%"},
            { "mDataProp": "sendDept","sWidth": "30%","sClass": "longTxt"},
            // { "mDataProp": "gatherDept","sWidth": "160"},
            { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
            { "mDataProp": "secretLevel","sWidth": "60"},
            { "mDataProp": "huanji","sWidth": "60"},
            { "mDataProp": "state","sWidth": "60"},
            { "mDataProp": "creater","sWidth": "70"},
            { "mDataProp": "lastTimestamp","sWidth": "120"},
            { "mDataProp": "option","sWidth": "70","sClass": "right_bdr0",

              "fnRender" : function(obj){
                var taskId = obj.aData["option"]; 
                var instanceid = obj.aData["instanceId"];
                    return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceid+"\")'>查看</a>";
  
              }
            }
        ] ;
    }else if(type == 5){
         columns = [
            { "mDataProp": "title","sClass": "longTxt","sWidth": "50%",
                "fnRender" :function(obj){
                  	 var instanceid = obj.aData["instanceId"];
                  	 var title = obj.aData["title"];
                  	 _title = title;
                       return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                      }
                  },
            { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "50%"},
            { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
            { "mDataProp": "secretLevel","sWidth": "60"},
            { "mDataProp": "huanji","sWidth": "60"},
            { "mDataProp": "state","sWidth": "60"},
            { "mDataProp": "lastTimestamp","sWidth": "120"},
            { "mDataProp": "option","sWidth": "70","sClass":"right_bdr0",
              "fnRender" : function(obj){
                var taskId = obj.aData["option"]; 
                var instanceId = obj.aData["instanceId"];
                    return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceId+"\")'>查看</a>";
  
              }
            }
        ] ;
    }else if(type == 6){
        columns = [
            { "mDataProp": "title","sClass": "longTxt","sWidth": "50%",
                "fnRender" :function(obj){
                  	 var instanceid = obj.aData["instanceId"];
                  	 var title = obj.aData["title"];
                  	 _title = title;
                       return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                      }
                  },
            { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "50%"},
            { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
            { "mDataProp": "secretLevel","sWidth": "60"},
            { "mDataProp": "huanji","sWidth": "60"},
            { "mDataProp": "state","sWidth": "60"},
            { "mDataProp": "creater","sWidth": "70"},
            { "mDataProp": "lastTimestamp","sWidth": "120"},
            { "mDataProp": "option","sWidth": "70","sClass":"right_bdr0",
              "fnRender" : function(obj){
                var taskId = obj.aData["option"]; 
                var instanceId = obj.aData["instanceId"];
                    return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceId+"\")'>查看</a>";
  
              }
            }
        ] ;
    }else if(type == 7){
        columns = [
             { "mDataProp": "title","sClass": "longTxt","sWidth": "50%",
                    "fnRender" :function(obj){
                   	 var instanceid = obj.aData["instanceId"];
                   	 var title = obj.aData["title"];
                   	 _title = title;
                        return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                       }
                   },
            { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "50%"},
            { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
            { "mDataProp": "secretLevel","sWidth": "60"},
            { "mDataProp": "huanji","sWidth": "60"},
            { "mDataProp": "state","sWidth": "60"},
            { "mDataProp": "creater","sWidth": "70"},
            { "mDataProp": "lastTimestamp","sWidth": "120"},
            { "mDataProp": "option","sWidth": "70","sClass":"right_bdr0",
              "fnRender" : function(obj){
                var taskId = obj.aData["option"]; 
                var instanceId = obj.aData["instanceId"];
                    return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceId+"\")'>查看</a>";
  
              }
            }
        ] ;
    }else if(type == 8){
        columns = [
             { "mDataProp": "title","sClass": "longTxt","sWidth": "50%",
                    "fnRender" :function(obj){
                   	 var instanceid = obj.aData["instanceId"];
                   	 var title = obj.aData["title"];
                   	 _title = title;
                        return "<a href='#' onclick='domListOption.doViewHistoryTitle(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                       }
                   },
            { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "50%"},
            { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "115"},
            { "mDataProp": "secretLevel","sWidth": "60"},
            { "mDataProp": "huanji","sWidth": "60"},
            { "mDataProp": "state","sWidth": "60"},
            { "mDataProp": "creater","sWidth": "70"},
            { "mDataProp": "lastTimestamp","sWidth": "120"},
            { "mDataProp": "option","sWidth": "70","sClass":"right_bdr0",
              "fnRender" : function(obj){
                var taskId = obj.aData["option"]; 
                var instanceId = obj.aData["instanceId"];
                    return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceId+"\")'>查看</a>";
  
              }
            }
        ] ;
    }else if(type == 11){
        columns = [
            { "mDataProp": "batchDownload","sWidth": "30",
              "fnRender" : function(obj){
                var instanceId = obj.aData["instanceId"];
                return '<input type="checkbox" name="taskId" value="'+instanceId+'" />';
              } 
            },
            { "mDataProp": "title","sClass": "longTxt","sWidth": "50%",
                    "fnRender" :function(obj){
                   	 var instanceid = obj.aData["instanceId"];
                   	 var title = obj.aData["title"];
                   	 _title = title;
                        return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceid+"\",\""+title+"\")'>"+title+"</a>";	
                       }
            },
            { "mDataProp": "wenhao","sClass": "longTxt","sWidth": "50%"},
            { "mDataProp": "gongwenType","sClass": "longTxt","sWidth": "150"},
            { "mDataProp": "secretLevel","sWidth": "60"},
            { "mDataProp": "huanji","sWidth": "60"},
            { "mDataProp": "creater","sWidth": "70"},
            { "mDataProp": "endTime","sWidth": "120"},
            { "mDataProp": "option","sWidth": "80","sClass":"oper right_bdr0",
              "fnRender" : function(obj){
                var taskId = obj.aData["option"]; 
                var instanceId = obj.aData["instanceId"];
                    return "<a href='#' onclick='domListOption.doViewHistory(\""+instanceId+"\")'>查看</a><a href='#' onclick='domListOption.downloadZip(\""+instanceId+"\")'>下载</a>";
  
              }
            }
        ] ;
    }
}
    //----已办任务---end
    var search = function(){
        $('#viewlist').dataTable({
            "bProcessing": true,
            'bServerSide': true,
            "bDestroy":true,
            "sServerMethod": "POST",
            "bAutoWidth": true,
            'fnServerParams': function ( aoData ) {
                aoData.push({"name":"menu","value":$("#menu").val()},
                            {"name":"searchType","value":$("#searchType").val()},
                            {"name":"gongwenTypeId","value":$("#gongwenTypeId").val()},
                            {"name":"title","value":$("#title").val().trim()}
                    );
            },
            "sAjaxSource": basePath+"/dom/public!searchMyDom.action",
            "sPaginationType": "full_numbers",
            "bPaginate": true, //翻页功能
            "bLengthChange": false, //改变每页显示数据数量
            "bFilter": false, //过滤功能
            "bSort": false, //排序功能
            "bInfo": true,//页脚信息
            "bAutoWidth": false,//自动宽度
            "iDisplayLength":15, //每页显示多少行
            "aoColumns": columns ,
            "oLanguage": {
                "sUrl":  basePath+"/plugins/datatable/cn.txt" //中文包
            } ,
            "fnDrawCallback": function (oSettings) {
           	 //提示
				$('#viewlist tbody  tr td').each( function() {
					 var t = jQuery(this).text();
					var showText = $(this).text();
		    	    showText = showText.replace(/<[^>].*?>/g,"");  
		            if (t.indexOf('[')<0&&showText.length>3)    {
		                   $(this).attr("title",showText); // 每个TD显示title
		             }   
				});
            $("input[name=\"taskId\"]").click(function(){
              	 var count=0; //全部选中
              	 var selectCount=0; //是否有没选中的
              	  $("input[name=\"taskId\"]").each(function(){
              		   if($(this).attr("checked")=="checked"){
              			   selectCount++;
              		   }  
              		   count++;
              	  });
              	  if(count>0&&selectCount==count){ //全选中
              		  $("#selectAll").attr("checked",true);
              	  }else{
              		  $("#selectAll").attr("checked",false);
              	  }
            });
           // _getChecked();
            }
        });
        
    }

    search();

    //绑定回车事件
    $("body").keydown(function(){
        if(event.keyCode == 13){
            search();
        }
        
    });

    $("#searchButton").click(function(){
        search();
    });

   //公文列表操作对象
    var domListOption = {

        getTabName:function(){
            var searchType = $("#searchType").val();
            var menu = $("#menu").val();
            menu = menu*1;
            var title = "";
            if(searchType == "processing"){
                switch(menu){
                    case 1:title = "收文登记";break;
                    case 2:title = "领导批阅";break;
                    case 3:title = "收文分发";break;
                    case 4:title = "收文阅读";break;
                    case 9:title = "收文归档";break;
                    case 5:title = "发文拟稿";break;
                    case 6:title = "发文核稿";break;
                    case 7:title = "套红盖章";break;
                    case 8:title = "发文分发";break;
                    case 10:title="发文归档";break;
                }
            }else if(searchType == "completed"){
                switch(menu){
                    case 1:title = "已登记";break;
                    case 2:title = "已批阅";break;
                    case 3:title = "已分发";break;
                    case 4:title = "已阅读";break;
                    case 9:title = "已归档";break;
                    case 5:title = "已拟稿";break;
                    case 6:title = "已核稿";break;
                    case 7:title = "已盖章";break;
                    case 8:title = "已发文";break;
                    case 11:title="已归档";break;

                }
            }
            return title;
        },

        //系统内登记
        doInnerSystemUpdate:function(taskId,instanceId){
          var url = basePath+"/dom/gather!toInnerSystemUpate.action?instanceId="+instanceId+"&taskId="+taskId;
          $.get(basePath+"/dom/public!findGongwenTypeId.action?instanceId="+instanceId+"&r="+Math.random(),function(data){
            //如果存在公文类型
            if(data == 'success'){
                domListOption.doEdit(taskId);
            }else{
                window.top.addTab("系统内登记",url,"系统内登记");
            	//windowOpen(url);
            }
          });
        },
        
        //编辑
        doEdit:function(taskId){
            var title = domListOption.getTabName();
            $.get(basePath+"/dom/public!taskIsNull.action?taskId="+taskId+"&r="+Math.random(),function(data){
                if(data == 'yes'){
                    art.dialog.alert("该任务已办结，请刷新该页面!",function(){
                    	window.location.reload();
                    });
                    return;
                }else if(data == "no"){
                	var url = basePath+'dom/public!toMainByForward.action?taskId='+taskId+"&menu="+type;
                	//windowOpen(basePath+'dom/public!toMainByForward.action?taskId='+taskId+"&menu="+type);
                	window.top.addTab(Math.random()+Math.random()+Math.random(),url,title);
                }
            });
        },
        //删除流程实例
        doDelete:function(taskId,msg){
            $.get(basePath+"/dom/public!taskIsNull.action?taskId="+taskId,function(data){
                if(data == "yes"){
                    art.dialog.alert("该任务已办结，请刷新该页面!");
                    return;
                }else if(data == "no"){
                    art.dialog.confirm(msg,function(){
                        $.get(basePath+"dom/public!delete.action?taskId="+taskId,function(data){
                            search();
                        });
                    });
                }
            });


        },
        //查看
        doViewHistory:function(instanceId){
            var url = basePath+"dom/public!toView.action?instanceId="+encodeURI(instanceId)+"&history=history";
        	//windowOpen(basePath+"dom/public!toView.action?instanceId="+encodeURI(instanceId)+"&history=history");
            window.top.addTab(parseInt(Math.random()*100),url,"查看公文");
        },
        doViewHistoryTitle:function(instanceId,title){
        	var url = basePath+"dom/public!toView.action?instanceId="+encodeURI(instanceId)+"&history=history";
        	//windowOpen(basePath+"dom/public!toView.action?instanceId="+encodeURI(instanceId)+"&history=history");
        	window.top.addTab(parseInt(Math.random()*100),url,title);
        },
        //传阅状态
        doReadState:function(instanceId) {
        	window.parent.addTab(Math.random(),basePath+"dom/gather!toGetReadState.action?instanceId="+encodeURI(instanceId), "传阅状态");
//            // body...
//            if(window.parent.document.getElementById("div_tab")){
//            }else{
//                window.location.href=basePath+"dom/gather!toGetReadState.action?instanceId="+encodeURI(instanceId);
//            }
        },
        //归档下载
        downloadZip:function(instanceId){
            window.open(basePath+"/dom/public!downloadZipFile.action?instanceId="+instanceId);
        }
    }

    function windowOpen(url){
    	var width = window.screen.availWidth;
    	var height = window.screen.availHeight;
    	window.open(url,"","width="+width+",height="+height+",menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes");
    }


    //获取选中的任务ID
    var getSelectTaskId = function(){
        var cs = document.getElementsByName("taskId");
        var str = "";
        for(var i=0;i<cs.length;i++){
            var temp = cs[i];
            if(temp.checked){
                str+=temp.value+",";
            }
        }
        return str;
    }
    
    //绑定事件
    jQuery(document).ready(function($){
        //全选/全取消
    	$("#selectAll").live("click",function(){
            if($(this).attr("checked")){
                var cs = document.getElementsByName("taskId");
                for(var i=0; i<cs.length; i++){
                    if(!$(cs[i]).attr("disabled")){
                        cs[i].checked = "checked";
                    }
                }
            }else{
                var cs = document.getElementsByName("taskId");
                for(var i=0; i<cs.length; i++){
                    cs[i].checked = "";
                }
            }
        });
    });
/*
 * 批量归档
 */
function batchZip(){
    var taskIds = getSelectTaskId();
    if(!taskIds){
      art.dialog.alert("请选择待归档的任务!");
      return ;
    }else{
      art.dialog.confirm("确定要归档吗?",function(){
        $.post(basePath+"dom/public!batchZip.action?taskIds="+taskIds,function(data){
          if(data == 'success'){
        	  qytx.app.dialog.tips("归档成功",function(){
        		  $("#searchButton").click();
        	  })
          }
        })
      })
    }
};

$("#downloadBatch").click(function(){
    var instanceIds = getSelectTaskId();
    if(!instanceIds){
      art.dialog.alert("请选择待下载的公文！");
      return ;
    }else{
      window.open(basePath+"dom/public!batchDowanload.action?instanceIds="+instanceIds);
    }
});
       

    
    