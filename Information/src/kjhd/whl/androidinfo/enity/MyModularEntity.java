package kjhd.whl.androidinfo.enity;

/**
 * 模块信息实体
 * @author whl (81813780@qq.com)
 * @data 2012-07-10
 * @version 1.1
 */


public class MyModularEntity {
	
	//答案
	private String module_key;
	//题目编号
	private String title_id;
	//说明
	private String description;
	//为空判断
	private int title_null;
	//题目说明
	private String title_description;
	//模块类型
	private int type;
	//问卷id
	private  int question_id;
	//题目类型
	private int title_type;
    //题目名称
	private String title_name;
	
	
	public String getModule_key() {
		return module_key;
	}
	public void setModule_key(String module_key) {
		this.module_key = module_key;
	}
	public String getTitle_id() {
		return title_id;
	}
	public void setTitle_id(String title_id) {
		this.title_id = title_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTitle_null() {
		return title_null;
	}
	public void setTitle_null(int title_null) {
		this.title_null = title_null;
	}
	public String getTitle_description() {
		return title_description;
	}
	public void setTitle_description(String title_description) {
		this.title_description = title_description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public int getTitle_type() {
		return title_type;
	}
	public void setTitle_type(int title_type) {
		this.title_type = title_type;
	}
	public String getTitle_name() {
		return title_name;
	}
	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}
	
	
}
