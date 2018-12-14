package cn.com.qytx.cbb.attendance.action;

import cn.com.qytx.platform.base.query.Page;


public class PageUtil {
 
	  	/**
		 *  分页代码
		 * @return
		 */
	  public  static String getAjaxPageHtml(Page pageInfo) {
	        StringBuffer sb = new StringBuffer();
	        int totalPage=pageInfo.getTotalPages(); // 总页数
	    	long totalCount=pageInfo.getTotalElements(); // 总个数
	    	int pageNo=pageInfo.getNumber()+1; // 当前页码
	        sb.append("<script type='text/javascript' >");
	        sb.append("function actiontopage(vpage)");
	        sb.append("{");
	        sb.append("if(vpage>"+totalPage+")");
	        sb.append("{");
	        sb.append("vpage="+totalPage+";");
	        sb.append("}");
	        sb.append("document.getElementById('page').value=vpage;");
	        sb.append("getPage(vpage);");
	        sb.append("}");
	        sb.append("</script>");
	        sb.append("<div class=\"\">");
	        sb.append("<div class=\"dataTables_info\"  id=\"Table_info\">共 "+  totalCount+" 条数据 </div> ");
	        sb.append("<div class=\"dataTables_paginate paging_full_numbers\" id=\"Table_paginate\">");
	        long pageCount=totalPage;
	        if(pageCount>0) {
	             if(pageNo==1){
	                    //如果当前页是首页，则首页不能使用
	            	 	sb.append("<a class=\"first paginate_button paginate_button_disabled\" tabindex=\"0\" id=\"Table_first\">首页</a> ");
	               		sb.append("<a class=\"previous paginate_button paginate_button_disabled\" tabindex=\"0\" id=\"Table_previous\">上一页</a>");
	             } else {
	            	    sb.append("<a   onclick='actiontopage(1);return false;' class=\"first paginate_button paginate_button_disabled\" tabindex=\"0\" id=\"Table_first\">首页</a> ");
		                sb.append("<a onclick='actiontopage(" + (pageNo - 1) + ");return false;'  class=\"previous paginate_button paginate_button_disabled\" tabindex=\"0\" id=\"Table_previous\">上一页</a>");
	             }
	            //-显示中间部分页码，最多显示10页 
	           sb.append("<span>"); 
	           int pageSize=5;
	            if(pageCount>pageSize){
	                //如果大于10，则显示10条
	                int index=pageNo/pageSize;//获取当前页码
	                int step =pageSize;
	                for(int i=pageSize*index;i<pageSize*index+step;i++){
	                    if(i<totalPage){
	                    	if(pageNo==(i+1)){
	                    		  sb.append("<a  onclick='actiontopage("+(i+1)+");return false;' class=\"paginate_active\" tabindex=\"0\">"+(i+1)+"</a>"); 
	                    	}else{
	                    		 sb.append("<a  onclick='actiontopage("+(i+1)+");return false;' class=\"paginate_button\" tabindex=\"0\">"+(i+1)+"</a>"); 
	                    	}
	                    }
	                }
	            }else {
	                 for(int i=0;i<pageCount;i++){
	            	     if(pageNo==(i+1)){
	            	    	 	sb.append("<a  onclick='actiontopage("+(i+1)+");return false;' class=\"paginate_active\" tabindex=\"0\">"+(i+1)+"</a>"); 
	             	      }else{
	             	    	 	 sb.append("<a  onclick='actiontopage("+(i+1)+");return false;' class=\"paginate_button\" tabindex=\"0\">"+(i+1)+"</a>"); 
	                      }
	            	 }
	            }
	            sb.append("</span>");
	            
	            if (pageNo < totalPage) {
	            	sb.append("<a class=\"next paginate_button\" onclick='actiontopage(" + (pageNo + 1) + ");return false;' tabindex=\"0\" id=\"Table_next\">下一页</a>") ;
	                if(pageNo==totalPage)  {
	                      //如果当前页是最后页，则最后页不能使用
			              sb.append("<a class=\"last paginate_button\" tabindex=\"0\" id=\"Table_last\">尾页</a>") ;
	                } else {
	                	 sb.append("<a onclick='actiontopage(" + totalPage + ");return false;' class=\"last paginate_button\" tabindex=\"0\" id=\"Table_last\">尾页</a>") ;
	                }
	            } else {
	            	  sb.append("<a class=\"next paginate_button\" tabindex=\"0\" id=\"Table_next\">下一页</a>") ;
		              sb.append("<a class=\"last paginate_button\" tabindex=\"0\" id=\"Table_last\">尾页</a>") ;
	            }

	        }
	        
	        sb.append("</div>");
	        sb.append("<div class=\"clear\"></div>");
	        sb.append("</div>");
	        sb.append("<input type='hidden' name='page' id='page'/>");
	        String pageList = new String(sb);
	        return pageList;
	    }
	  
	  
}
