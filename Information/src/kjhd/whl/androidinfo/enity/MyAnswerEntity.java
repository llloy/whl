package kjhd.whl.androidinfo.enity;

/**
 * 题目信息实体
 * @author whl (81813780@qq.com)
 * @data 2012-07-10
 * @version 1.1
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kjhd.whl.R.drawable;

import android.R.integer;

public class MyAnswerEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//标题
	private String title;
	//要求
	private String request;
	//答案类型类型
	private int select_type;
	//评分规则
	private String pfgz;
	//分值
	private double tm_value;
	//问卷id
	private int questionId;
	//问卷指标id
	private int indexId;
	//id序号
	private int idXh;
	//执行编码
	private String excuteId;
	//id前缀
    private String idPr;
    //得分
  	private double value;
  	//扣分原因
  	private String reason;
    //截图地址
  	private String imgUrl;
  	//备注
  	private String bz;
  	//答案
  	private String tm_key;
  	//是否跳题
  	private int jump;
  	//支持跳题的答案
  	private String jump_key;
  	//跳过的题目编号
  	private String jump_testid;
  	//执行标准
  	private String zxbz;
  	//截图说明
  	private String jtsm;
  	//选中的答案
  	private String selectKey;
  	//题目类型
  	private int title_type;
  	//判断是否必做题目
  	private int test_null;
  	/**
	 * 答案列表
	 */
	private List<AnswerItemEntity> answersitem = new ArrayList<AnswerItemEntity>();
	
	/**
	 * 单选题
	 */
	public static final int SINGLE = 1;
	
	/**
	 * 多选题
	 */
	public static final int MULTLCHECK = 2;
	/**
	 * 多选题
	 */
	public static final int DESCRIPTION = 3;
	
	/**
	 * 描述题目
	 */
	public static final int TRUEORFALSE = 3;
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getIdPr() {
		return idPr;
	}

	public void setIdPr(String idPr) {
		this.idPr = idPr;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}

	public int getIdXh() {
		return idXh;
	}

	public void setIdXh(int idXh) {
		this.idXh = idXh;
	}

	public String getExcuteId() {
		return excuteId;
	}

	public void setExcuteId(String excuteId) {
		this.excuteId = excuteId;
	}


	public static int getSingle() {
		return SINGLE;
	}

	public static int getMultlcheck() {
		return MULTLCHECK;
	}

	public static int getTrueorfalse() {
		return TRUEORFALSE;
	}
	

	public List<AnswerItemEntity> getAnswersitem() {
		return answersitem;
	}

	public void setAnswersitem(List<AnswerItemEntity> answersitem) {
		this.answersitem = answersitem;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public int getSelect_type() {
		return select_type;
	}

	public void setSelect_type(int select_type) {
		this.select_type = select_type;
	}

	public String getPfgz() {
		return pfgz;
	}

	public void setPfgz(String pfgz) {
		this.pfgz = pfgz;
	}

	public double getTm_value() {
		return tm_value;
	}

	public void setTm_value(Double tm_value) {
		this.tm_value = tm_value;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getTm_key() {
		return tm_key;
	}

	public void setTm_key(String tm_key) {
		this.tm_key = tm_key;
	}

	public int getJump() {
		return jump;
	}

	public void setJump(int jump) {
		this.jump = jump;
	}

	public String getJump_key() {
		return jump_key;
	}

	public void setJump_key(String jump_key) {
		this.jump_key = jump_key;
	}

	public String getJump_testid() {
		return jump_testid;
	}

	public void setJump_testid(String jump_testid) {
		this.jump_testid = jump_testid;
	}

	public String getZxbz() {
		return zxbz;
	}

	public void setZxbz(String zxbz) {
		this.zxbz = zxbz;
	}

	public static int getDescription() {
		return DESCRIPTION;
	}

	public void setTm_value(double tm_value) {
		this.tm_value = tm_value;
	}

	

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getJtsm() {
		return jtsm;
	}

	public void setJtsm(String jtsm) {
		this.jtsm = jtsm;
	}

	public String getSelectKey() {
		return selectKey;
	}

	public void setSelectKey(String selectKey) {
		this.selectKey = selectKey;
	}

	public int getTitle_type() {
		return title_type;
	}

	public void setTitle_type(int title_type) {
		this.title_type = title_type;
	}

	public int getTest_null() {
		return test_null;
	}

	public void setTest_null(int test_null) {
		this.test_null = test_null;
	}
	
	
	
}
