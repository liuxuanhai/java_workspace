
public class Exam10 {

	/**
	 * 40��С���Χ��һȦ���ӵ�һ���˿�ʼ������ÿ�α���3��С�����Ҫ����һ����Ŀ����һ�α�����ʱ���Ǵ�1��ʼ��
	 * ���ݹ���Ŀ��С��齫�˳�ԲȦ���ʻ�ʣ�����һ���˵�ʱ��һ�����˶��ٴ���
	 * @param args
	 */
	public static void main(String[] args) {
		//�ܵ�����
		int num = 40;
		//�����Ĵ���
		int count = 0;
		//��ǰ������
		int now = 1;
		//���û�е����һ���������ѭ������
		while(num != 1){
			//��¼�������ܴ���
			count++;
			//�жϵ�ǰ�ı���
			//�����ǰ�ı�����3���û����ݽ�Ŀ
			if(now == 3){
				System.out.println("С�������˽�Ŀ");
				//�������Ŀ��û���˳���Ϸ����ζ������������һ����
				num--;
				//��һ�α�����ͷ��ʼ
				now = 1;
			}
			//�����ǰ��������3����������
			else{
				now++;
			}
		}
		System.out.println("һ��������"+count+"��");
	}

}