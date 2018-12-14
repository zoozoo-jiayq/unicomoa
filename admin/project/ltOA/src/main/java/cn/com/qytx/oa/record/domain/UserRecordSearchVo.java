package cn.com.qytx.oa.record.domain;

import com.google.gson.Gson;

/**
 * 功能:人事档案 搜索VO类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-04-03
 * 修改日期: 2013-04-03
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class UserRecordSearchVo extends UserRecord {

    /**
     * 生日
     */
    private String birthDayStart;
    private String birthDayEnd;

    /**
     * 入职时间
     */
    private String joinTimeStart;
    private String joinTimeEnd;

    /**
     * 参加工作时间
     */
    private String joinWorkTimeStart;
    private String joinWorkTimeEnd;

    /**
     * 入党时间
     */
    private String partyTimeStart;
    private String partyTimeEnd;

    /**
     * 毕业时间
     */
    private String graduationTimeStart;
    private String graduationTimeEnd;

    /**
     * 起薪日期
     */
    private String startPayTimeStart;
    private String startPayTimeEnd;

    
    private String keyWord;

    /**
     * 转成JSON
     *
     * @return json字符串
     */
    public String toJson() {
        return new Gson().toJson(this);
    }

    /**
     * 由json转为VO对象
     *
     * @param searchJson json串
     * @return VO对象
     */
    public static UserRecordSearchVo getInstanceBySearchJson(String searchJson) {
        return new Gson().fromJson(searchJson, UserRecordSearchVo.class);
    }


    public String getBirthDayStart() {
        return birthDayStart;
    }

    public void setBirthDayStart(String birthDayStart) {
        this.birthDayStart = birthDayStart;
    }


    public String getJoinTimeStart() {
        return joinTimeStart;
    }

    public void setJoinTimeStart(String joinTimeStart) {
        this.joinTimeStart = joinTimeStart;
    }

    public String getJoinTimeEnd() {
        return joinTimeEnd;
    }

    public void setJoinTimeEnd(String joinTimeEnd) {
        this.joinTimeEnd = joinTimeEnd;
    }

    public String getPartyTimeStart() {
        return partyTimeStart;
    }

    public void setPartyTimeStart(String partyTimeStart) {
        this.partyTimeStart = partyTimeStart;
    }

    public String getPartyTimeEnd() {
        return partyTimeEnd;
    }

    public void setPartyTimeEnd(String partyTimeEnd) {
        this.partyTimeEnd = partyTimeEnd;
    }

    public String getGraduationTimeStart() {
        return graduationTimeStart;
    }

    public void setGraduationTimeStart(String graduationTimeStart) {
        this.graduationTimeStart = graduationTimeStart;
    }

    public String getGraduationTimeEnd() {
        return graduationTimeEnd;
    }

    public void setGraduationTimeEnd(String graduationTimeEnd) {
        this.graduationTimeEnd = graduationTimeEnd;
    }

    public String getStartPayTimeStart() {
        return startPayTimeStart;
    }

    public void setStartPayTimeStart(String startPayTimeStart) {
        this.startPayTimeStart = startPayTimeStart;
    }

    public String getStartPayTimeEnd() {
        return startPayTimeEnd;
    }

    public void setStartPayTimeEnd(String startPayTimeEnd) {
        this.startPayTimeEnd = startPayTimeEnd;
    }

    public String getBirthDayEnd() {
        return birthDayEnd;
    }

    public void setBirthDayEnd(String birthDayEnd) {
        this.birthDayEnd = birthDayEnd;
    }

    public String getJoinWorkTimeEnd() {
        return joinWorkTimeEnd;
    }

    public void setJoinWorkTimeEnd(String joinWorkTimeEnd) {
        this.joinWorkTimeEnd = joinWorkTimeEnd;
    }

    public String getJoinWorkTimeStart() {

        return joinWorkTimeStart;
    }

    public void setJoinWorkTimeStart(String joinWorkTimeStart) {
        this.joinWorkTimeStart = joinWorkTimeStart;
    }

	/**
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
    
    
    
}
