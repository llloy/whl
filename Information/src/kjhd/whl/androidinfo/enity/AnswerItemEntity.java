package kjhd.whl.androidinfo.enity;
/**
 * ��ѡ��ʵ��
 * @author whl (81813780@qq.com)
 * @data 2012-07-10
 * @version 1.1
 */
public class AnswerItemEntity {
	//��ѡ����
	private int answeritem;

	//������
	private String answercontent;
	
	
	public String getAnswercontent() {
		return answercontent;
	}

	public void setAnswercontent(String answercontent) {
		this.answercontent = answercontent;
	}

	//��Ŀid
	private int test_id;


	//�÷�
	private int score;
	//�۷�˵��
	private String instruction;
	
	
	public int getTest_id() {
		return test_id;
	}

	public void setTest_id(int test_id) {
		this.test_id = test_id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	
	public int getAnsweritem() {
		return answeritem;
	}

	public void setAnsweritem(int answeritem) {
		this.answeritem = answeritem;
	}

}
