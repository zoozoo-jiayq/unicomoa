package cn.com.qytx.oa.record.action;

import java.io.File;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.monitor.client.log.MonitorLogger;
import cn.com.qytx.monitor.client.logImpl.Log4jImpl;
import cn.com.qytx.oa.record.service.IUserRecord;
import cn.com.qytx.oa.util.ImportBaseAction;
import cn.com.qytx.oa.util.TimeUtil;

import com.google.gson.Gson;
/**
 * 功能:档案管理导入
 * 版本: 1.0
 * 开发人员: 吴胜光
 * 创建日期: 2017-01-14
 * 修改日期: 2017-01-14
 * 修改列表:
 */
public class UserRecordImportAction extends ImportBaseAction{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1459251772424453946L;

	/**
     * log4j日志对象
     */
	private final static MonitorLogger logger =new Log4jImpl(UserRecordImportAction.class);

    /**
     * 档案管理接口
     */
	@Autowired
	private IUserRecord userRecordService;
	 
	@Autowired
	private IDict dictService;
    
    /**
     * 表头
     */
    private static String[] names = new String[53];
    
    /**
     * 初始化表头
     */
    public static void initNames(){
    	names[0] = "姓名";// 添加错误信息表的表头。这几行是固定的。
        names[1] = "手机号码";
        names[2] = "曾用名";
        names[3] = "英文名";
        names[4] = "身份证号";
        names[5] = "年休假（天）";
        names[6] = "籍贯（省份）";
        names[7] = "籍贯（市县）";
        names[8] = "民族";
        names[9] = "婚姻状况";
        names[10] = "健康状况";
        names[11] = "政治面貌";
        names[12] = "入党时间";
        names[13] = "户口类型";
        names[14] = "户口所在地";
        names[15] = "工种";
        names[16] = "行政级别";
        names[17] = "员工类型";
        names[18] = "职称";
        names[19] = "入职时间";
        names[20] = "职称级别";
        names[21] = "岗位";
        names[22] = "单位工龄(年)";
        names[23] = "起薪时间";
        names[24] = "在职状态";
        names[25] = "总工龄";
        names[26] = "参加工作时间";
        names[27] = "MSN";
        names[28] = "QQ";
        names[29] = "家庭地址";
        names[30] = "其他联系方式";
        names[31] = "开户行1";
        names[32] = "帐号1";
        names[33] = "开户行2";
        names[34] = "账户2";
        names[35] = "学历";
        names[36] = "学位";
        names[37] = "毕业时间";
        names[38] = "毕业学校";
        names[39] = "专业";
        names[40] = "计算机水平";
        names[41] = "外语语种1";
        names[42] = "外语水平1";
        names[43] = "外语语种2";
        names[44] = "外语水平2";
        names[45] = "外语语种3";
        names[46] = "外语水平3";
        names[47] = "特长";
        names[48] = "职务情况";
        names[49] = "担保记录";
        names[50] = "社会缴纳情况";
        names[51] = "体检记录";
        names[52] = "备注";
    }
    
    
    /**
     * 数据字典集合
     */
    private Map<String, Map<String, Integer>> baseDataMap = new HashMap<String, Map<String,Integer>>();
    /**
     * 把选项中的基础数据放在MAP中
     */
    private void putBaseDataToMap() {
        String[] tags = {"user_type", "marriage_status", "politics_face", "registered_type",
                "job_title", "job_title_level", "station", "work_status", "edu_qualifications", "edu_level"};
        for (String tag : tags) {
            List<Dict> list = this.dictService.findList(tag, 1);
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (Dict dict : list) {
            	map.put(dict.getName(), dict.getValue());
			}
            baseDataMap.put(tag, map);
        }
    }
    
    /**
     * 功能：验证各个单元是否符合要求
     * @param thisCellContent 单元格内容
     * @param r 行号
     * @param c 列号
     * @return
     */
    private String verify(String thisCellContent, int r, int c){
       	String regex1 = "^[0-9]+(.[0-9]{1,6})?$";// 正则（验证输入六位小数）
    	String regex2 = "^[0-9]+(.[0-9]{1,8})?$";// 正则（验证输入八位小数）
    	String regex3 = "^1[3578]\\d{9}$";// 正则（验证输入正确手机号）
    	String regex4 = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";//正则判断是否全为18或15位数字，最后一位可以是大小写字母X
        String result = null;
        String[] arr1 = {"","北京市","天津市","河北省","山西省","内蒙古自治区","辽宁省","吉林省","黑龙江省",
        		"上海市","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省",
        		"广西壮族自治区","海南省","重庆市","四川省","贵州省","云南省","西藏自治区","陕西省","甘肃省","青海省",
        		"宁夏回族自治区","新疆维吾尔自治区","香港特别行政区","澳门特别行政区","台湾省","其它"};
        String[] arr2 = {"","汉族","蒙古族","回族","藏族","维吾尔族","苗族","彝族","壮族",
        		"布依族","朝鲜族","满族","侗族","瑶族","白族","土家族","哈尼族","哈萨克族","傣族","黎族",
        		"傈僳族","佤族","畲族","高山族","拉祜族","水族","东乡族","纳西族","景颇族","柯尔克孜族",
        		"土族","达斡尔族","仫佬族","羌族","布朗族","撒拉族","毛南族","仡佬族","锡伯族","阿昌族","普米族",
        		"塔吉克族","怒族","乌孜别克族","俄罗斯族","鄂温克族","德昂族","保安族","裕固族","京族","塔塔尔族",
        		"独龙族","鄂伦春族","赫哲族","门巴族","珞巴族","基诺族"};
        if (thisCellContent != null){
            thisCellContent = thisCellContent.trim();
        }
        if (thisCellContent == null || "".equals(thisCellContent)){
            if(c == 0||c == 1){
                result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"不能为空！";
                return result;
            }
        }else{
        	//手机号
        	if(c == 1){
        		if(!thisCellContent.matches(regex3)){
        			result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"必须为有效手机号！";
        			return result;
        		}
        	}
        	//身份证
        	if(c == 4){
        		if(!thisCellContent.matches(regex4)){
        			result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"必须为18或15位数字，最后一位可以是大小写字母X！";
        			return result;
        		}
        	}
        	//年休假(天)
        	if(c == 5){
        		if(Integer.parseInt(thisCellContent)>365){
        			result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"天数不能大于365！";
        			return result;
        		}
        	}
        	//籍贯（省份）
        	if(c == 6){
        		if(!Arrays.asList(arr1).contains(thisCellContent)){
        			result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"不存在！";
        			return result;
        		}
        	}
        	//民族
        	if(c == 8){
        		if(!Arrays.asList(arr2).contains(thisCellContent)){
        			result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"不存在！";
        			return result;
        		}
        	}
        	//婚姻状况
            if(c == 9 && StringUtils.isNotBlank(thisCellContent)){
            	Map<String, Integer> map = baseDataMap.get("marriage_status");
            	if(map.get(thisCellContent) == null){
            		result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"输入有误！";
        			return result;
            	}
            }
            //政治面貌
            if(c == 11 && StringUtils.isNotBlank(thisCellContent)){
            	Map<String, Integer> map = baseDataMap.get("politics_face");
            	if(map.get(thisCellContent) == null){
            		result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"输入有误！";
        			return result;
            	}
            }
            //户口类型
            if(c == 13 && StringUtils.isNotBlank(thisCellContent)){
            	Map<String, Integer> map = baseDataMap.get("registered_type");
            	if(map.get(thisCellContent) == null){
            		result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"输入有误！";
        			return result;
            	}
            }
            //员工类型
            if(c == 17 && StringUtils.isNotBlank(thisCellContent)){
            	Map<String, Integer> map = baseDataMap.get("user_type");
            	if(map.get(thisCellContent) == null){
            		result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"输入有误！";
        			return result;
            	}
            }
            //职称
            if(c == 18 && StringUtils.isNotBlank(thisCellContent)){
            	Map<String, Integer> map = baseDataMap.get("job_title");
            	if(map.get(thisCellContent) == null){
            		result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"输入有误！";
        			return result;
            	}
            }
            //职称级别
            if(c == 20 && StringUtils.isNotBlank(thisCellContent)){
            	Map<String, Integer> map = baseDataMap.get("job_title_level");
            	if(map.get(thisCellContent) == null){
            		result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"输入有误！";
        			return result;
            	}
            }
            //岗位
            if(c == 21 && StringUtils.isNotBlank(thisCellContent)){
            	Map<String, Integer> map = baseDataMap.get("station");
            	if(map.get(thisCellContent) == null){
            		result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"输入有误！";
        			return result;
            	}
            }
            //在职状态
            if(c == 24 && StringUtils.isNotBlank(thisCellContent)){
            	Map<String, Integer> map = baseDataMap.get("work_status");
            	if(map.get(thisCellContent) == null){
            		result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"输入有误！";
        			return result;
            	}
            }
            //学历
            if(c == 35 && StringUtils.isNotBlank(thisCellContent)){
            	Map<String, Integer> map = baseDataMap.get("edu_qualifications");
            	if(map.get(thisCellContent) == null){
            		result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"输入有误！";
        			return result;
            	}
            }
            //学位
            if(c == 36 && StringUtils.isNotBlank(thisCellContent)){
            	Map<String, Integer> map = baseDataMap.get("edu_level");
            	if(map.get(thisCellContent) == null){
            		result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"输入有误！";
        			return result;
            	}
            }
            //时间格式验证（  入党时间/入职时间/起薪时间/毕业时间 ）
            if(c == 12 || c == 19 || c == 23 || c == 27){
        		Timestamp t = TimeUtil.stringToTimestamp(thisCellContent+" 01:00:00");
        		if(t==null){
        			result = "第" + (r + 1) + "行第" + (c + 1) + "列，"+names[c]+"必须为有效时间（格式为2017-01-01）！";
	                return result;
        		}
        	}
        }
        
        
        
        return result;
    }
    
    /**
     * 导入数据时调用的方法
     * 次方法一般不用修改
     * @return
     */
    /**
     * @return
     */
    public String importList(){
    	try{
    		//初始化错误表头
    		initNames();
    		//把选项中的基础数据放在MAP中
    		putBaseDataToMap();
            if (uploadFile()){// 上传文件
                String result = initSheet();
                if(result==null){
                	if(OldCols<names.length){
                		result = "要导入的文件格式不正确！";
                	}
                }
                
                if (result == null){
                    boolean lastRowPass = false;
                    for (int r = 1; r < OldRows; r++){
                        Cell[] cells = new Cell[OldCols];
                        boolean thisRowPass = true;
                        for (int c = 0; c < OldCols; c++){
                            Cell cell = sheet.getCell(c, r);
                            cells[c] = cell;// 把这一列中的每一单元格存入代表这一行单元格的数组中。方便下面的recordThisLineCalls(cells)方法调用。
                            boolean dynamicCellPass = maskExcelFile(cell, c, r);// 用来筛分每个单元格，过滤出不符合标准的excel信息，并另存入excel错误信息统计表中。并返回一个boolean值，true表示该行全部符合。
                            if (!dynamicCellPass){// 在这一行当中。只要有一列不符合标准。这一行就不符合标准，这一行就不通过。
                                thisRowPass = false;
                            }
                        }
                        //如果这行验证没问题，调用存储过程保存
                        if (thisRowPass){
                        	// 调用存储过程 保存数据
                        	String temp = userRecordService.importSave(cells,getLoginUser(),baseDataMap);
                        	if (StringUtils.isNotBlank(temp)) {
                    			long spid = getSpId(temp);
                    			if (spid != -1) {// 成功
                    				successNum++;
                    			} else {// 失败
                                    //解析存储过程返回结果
                                    Gson gson = new Gson();
                                    List<Map<String, Object>> jsonList = gson.fromJson(temp,List.class);
                                    //key 错误行数 	value 错误原因
                                    Map<String, String> errorMap = new HashMap<String, String>();
                                    for (Map<String, Object> map : jsonList) {
                                    	//map.get("errorCol").toString()  解析出来的列数为4.0   5.0格式，不是int型数字
                                    	errorMap.put(map.get("errorCol").toString(), map.get("errorMessage").toString());
									}
                                    
                                    //循环封装错误数据
                                    boolean isError = false;
                                    for (int i = 0; i < cells.length; i++) {
                                    	Cell cell = sheet.getCell(i, r);
                                    	String thisCellContent = cell.getContents();
                                    	WritableCellFormat wcfDt = new WritableCellFormat(NumberFormats.TEXT);// 设置单元格为文本格式
                                    	Label label0x = null;
                                    	try{
                                    		isError = StringUtils.isNotBlank(errorMap.get((i+1)+".0"));
                                    		// 根据这个返回结果，判断是否给这一列添加底色
                                            if (isError){
                                            	wcfDt.setBackground(Colour.RED);
                                                label0x = new Label(i, errorRowsNum, thisCellContent, wcfDt);
                                                WritableCellFeatures cellFeatures = new WritableCellFeatures();
                                                cellFeatures.setComment(errorMap.get((i+1)+".0"));//错误信息
                                                label0x.setCellFeatures(cellFeatures);
                                            }else{
                                            	label0x = new Label(i, errorRowsNum, thisCellContent, wcfDt);
                                            }
                                            errorws.addCell(label0x);// 把这个单元格添加到错误信息表中。若错误行数不变时，这个单元格中的数据会被覆盖掉。
                                        }catch (Exception e){
                                        	logger.error("maskExcelFile error:"+ e);
                                        }
									}
                                    
                                    errorRowsNum++;
                                    errorImportNum++;
                    			}
                    		}else{
                    			errorRowsNum++;
                                errorImportNum++;
                    		}
                        	
                        	
                        }else{
                            errorRowsNum++;
                            errorImportNum++;
                        }
                        lastRowPass = thisRowPass;
                    }
                    if (errorWWB != null){
                        if (errorImportNum > 0){
                            if (lastRowPass){// 当最后一行是正确的数据时，移除最后一行。
                                errorws = errorWWB.getSheet(0);
                                errorws.removeRow(errorRowsNum);
                            }
                            errorWWB.write();
                        }
                    }
                    
                    
                    /* ==== 后续操作，显示出导入的结果。==== */
                    result = getImportResult();
                }
                
                
                this.getResponse().setHeader("ContentType", "text/json");
                this.getResponse().setCharacterEncoding("utf-8");
                this.getResponse().setContentType("text/html;charset=utf-8");
                Map<String, Object> jsonMap = new HashMap<String, Object>();
                jsonMap.put("result", result);
                ajax(jsonMap);
            }
        }
        catch (Exception e){
        	logger.error("importRecord error:"+ e);
        }
        finally{
            colseAllStream();
        }
        return null;
    }
    
    /**
	 * 得到存储过程导入成功后的ID或返回-1
	 * @param str
	 * @return
	 */
	private long getSpId(String str){
		try{
			return Long.valueOf(str);
		}catch(Exception e){
			return -1;
		}
	}

    /**
     * 功能：初始化输出的错误文件的头部文件
     */
    private boolean initErrorFileHead() {
        boolean flage = initErrorFile("人事档案导入异常数据");
        if (flage && errorWWB == null){
            try{
                errorWWB = Workbook.createWorkbook(new File(errorFileNamePath));
                errorws = errorWWB.createSheet("sheet1", 0);
                for (int i = 0; i < names.length; i++) {
                	Label label = new Label(i, 0, names[i]);
                	errorws.addCell(label);
				}
            }catch (Exception e){
            	logger.error("initErrorFileHead error:"+ e);
                return false;
            }
        }
        return flage;

    }
    
    
    /**
     * 功能：判断单元格是否符合要求 并记录到错误的sheet中
     * @param cell 单元格
     * @param c 列
     * @param r 行
     * @return
     */
    protected boolean maskExcelFile(Cell cell, int c, int r){
        if (initErrorFileHead()){
            String thisCellContent = cell.getContents();
            String verifyResult = verify(thisCellContent, r, c);// 通过这个单元格的内容，和这个单元格所在的列号，判断出这个单元格中的信息是否符合标准。符合返回true。
            WritableCellFormat wcfDt = new WritableCellFormat(NumberFormats.TEXT);// 设置单元格为文本格式
            Label label0x = null;
            try{
                if (verifyResult == null){// 根据这个单元格中内容的符合性来，判断是否给这一列添加底色。
                    label0x = new Label(c, errorRowsNum, thisCellContent, wcfDt);
                }else{
                    wcfDt.setBackground(Colour.RED);
                    label0x = new Label(c, errorRowsNum, thisCellContent, wcfDt);
                    /* 给错误的单元格添加批注信息 - 开始 */
                    WritableCellFeatures cellFeatures = new WritableCellFeatures();
                    cellFeatures.setComment(verifyResult);
                    label0x.setCellFeatures(cellFeatures);
                    /* 给错误的单元格添加批注信息 - 结束 */
                }
                errorws.addCell(label0x);// 把这个单元格添加到错误信息表中。若错误行数不变时，这个单元格中的数据会被覆盖掉。
            }catch (Exception e){
            	logger.error("maskExcelFile error:"+ e);
            }
            return verifyResult == null;// 返回一个boolean值。true表示这个单元格中的数据符合标准。
        }else{
            return false;
        }
    }

}
