/*�丮�縦 �����Ѵ�.*/
package food;
public class Cook {
	// ���� �ڷ������� has a ���踦 ������� �� ��� ����?
	// ���� �ڷ����� �����ǰų�, ��ȭ�� ������� ���� Ŭ������ has a ���迡 �ִ� Ŭ���� ��
	// �������� ��ȭ ���ױ� ������ ������������ ��������.	
	private Pan pan;	// ������� ������ �߻�.. ��Ȯ�� �ڷ������� has a ���踦 ǥ������ ����..
	
	// �ܺηκ��� �ʿ��� ��ü�� ���Թޱ� ���� setter �޼���
	public void setPan(Pan pan) {
		this.pan = pan;
	}
	
	public void makeFood() {
		pan.boil();
	}
	
}
