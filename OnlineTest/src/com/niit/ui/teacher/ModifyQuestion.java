package com.niit.ui.teacher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.niit.dao.impl.CourseDao;
import com.niit.dao.impl.QuestionsDao;
import com.niit.entiry.Course;
import com.niit.entiry.Question;

public class ModifyQuestion extends JFrame{

	private JLabel lblCourse;
	private JLabel lblType;
	private JLabel lblTitle;
	private JLabel lblChoice;
	private JComboBox cboCourse;
	private ButtonGroup group;
	private JRadioButton[] rdoType;
	private String[] type;
	private JTextArea txtTitle;
	private JScrollPane scl;
	private JCheckBox[] chk;
	private JTextArea[] txtChoice;
	private JScrollPane[] sclChoice;
	private JButton btnSave;
	private JButton btnCancel;
	
	private CourseDao courseDao;
	private QuestionsDao questionDao;
	private Question qt;
	
	private EditQuestionsFrame qFrm;
	
	public ModifyQuestion(Question q,EditQuestionsFrame qFrm){
		this.qt = q;
		this.qFrm = qFrm;
		courseDao = new CourseDao();
		questionDao = new QuestionsDao();
		this.setBounds(300, 100, 500, 550);
		this.setTitle("�޸�����");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		//��ʼ�����
		initComponent();
		//�󶨼�����
		bindListener();
	}
	
	/**
	 * �󶨼�����
	 */
	private void bindListener() {
		ButtonListener btnListener = new ButtonListener();
		btnCancel.addActionListener(btnListener);
		btnSave.addActionListener(btnListener);
		
	}
	
	/**
	 * ��ť������
	 * @author Administrator
	 *
	 */
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			//����
			if(btn == btnSave){
				Question q = new Question();
				//��ȡ�γ̱��
				String name = cboCourse.getSelectedItem().toString();
				int courseId = courseDao.findCourseByCourseName(name).getCourseId();
				q.setCourseId(courseId);
				q.setQid(qt.getQid());
				//��ȡ��������
				int type = -1;
				for(int i = 0; i < rdoType.length; i++){
					if(rdoType[i].isSelected()){
						type = i;
						break;
					}
				}
				if(type == -1){
					JOptionPane.showMessageDialog(null, "��ѡ������");
				}
				else{
					q.setqType(type);
				}
				//��ȡ���
				String s = txtTitle.getText().trim();
				if(s.equals("")){
					JOptionPane.showMessageDialog(null, "����д�����Ϣ��");
					txtTitle.requestFocus();
					return;
				}
				q.setqTitle(s);
				//�ж��Ƿ���дѡ��
				for(int i = 0; i < txtChoice.length; i++){
					String str = txtChoice[i].getText().trim();
					if(str.equals("")){
						JOptionPane.showMessageDialog(null, "��������дѡ�����ݣ�");
						txtChoice[i].requestFocus();
						return;
					}
				}
				//�ж�ѡ���Ƿ��ظ�
				for(int i = 0; i < txtChoice.length - 1; i++){
					String str1 = txtChoice[i].getText().trim();
					for(int j = i+1; j < txtChoice.length; j++){
						String str2 = txtChoice[j].getText().trim();
						if(str1.equals(str2)){
							JOptionPane.showMessageDialog(null, "�벻Ҫ��д�ظ���ѡ�");
							txtChoice[j].requestFocus();
							txtChoice[j].selectAll();
							return;
						}
					}
				}
				//��ȡѡ��
				String choice = "";
				for(int i = 0,x = 65; i < txtChoice.length;i++){
					String ch = txtChoice[i].getText().trim();
					choice += chk[i].getText()+"."+txtChoice[i].getText()+"#";
				}
				q.setqChoices(choice);
				//��ȡ��
				ArrayList<Integer> ch = new ArrayList<Integer>();
				String answer = "";
				for(int i = 0; i< chk.length; i++){
					if(chk[i].isSelected()){
						ch.add(i);
					}
				}
				//û��ѡ���
				if(ch.size() == 0){
					JOptionPane.showMessageDialog(null, "��ѡ��𰸣�");
					return;
				}
				//����Ŀ�����Ͳ�����
				if(type == 0 && ch.size() >1){
					JOptionPane.showMessageDialog(null, "��ѡѡ��ֻ����һ����");
				}
				else{
					for(int i = 0; i< chk.length; i++){
						if(chk[i].isSelected()){
							answer += chk[i].getText().trim()+"#";
						}
					}
					answer = answer.substring(0,answer.length());
					q.setqAnswer(answer);
					
					int cho = JOptionPane.showConfirmDialog(null, "ȷ���޸ĸ����⣿","�޸�����",JOptionPane.YES_NO_OPTION);
					if(cho == JOptionPane.YES_OPTION){
						//��������
						questionDao.modifyQuestions(q);
						//�رյ�ǰ����
						ModifyQuestion.this.dispose();
						qFrm.loadData();
					}
				}
				
			}
			else{
				ModifyQuestion.this.dispose();
			}
			
		}
		
	}
	
	/**
	 * ��ʼ�����
	 */
	private void initComponent() {
		lblCourse = new JLabel("��Ŀ");
		lblType = new JLabel("����");
		lblTitle = new JLabel("���");
		lblChoice = new JLabel("ѡ��");
		txtTitle = new JTextArea();
		scl = new JScrollPane(txtTitle);
		chk = new JCheckBox[4];
		sclChoice = new JScrollPane[4];
		txtChoice = new JTextArea[4];
		btnCancel = new JButton("ȡ��");
		btnSave = new JButton("�޸�");
		
		//�Զ�����
		txtTitle.setLineWrap(true);
		//�������
		txtTitle.setText(qt.getqTitle());
		//��ȡ���пγ�
		ArrayList<Course> courseList = courseDao.findAllCourse();
		cboCourse = new JComboBox();
		//�γ�����
		String courseName = courseDao.findCourseById(qt.getCourseId()).getCourseName();
		//�����б�����ѡ��
		for(Course c : courseList){
			cboCourse.addItem(c.getCourseName());
			if(courseName.equals(c.getCourseName())){
				cboCourse.setSelectedItem(courseName);
			}
		}
		
		//�������͵�ѡ��ť
		group = new ButtonGroup();
		type = new String[]{"��ѡ","��ѡ"};
		rdoType = new JRadioButton[type.length];
		for(int i = 0,x = 180 ;i < type.length; i++,x +=80){
			rdoType[i] = new JRadioButton(type[i]);
			rdoType[i].setBounds(x, 50, 80, 20);
			group.add(rdoType[i]);
			this.add(rdoType[i]);	
		}
		int type = qt.getqType();
		rdoType[type].setSelected(true);
		//����ѡ��
		//��ȡѡ��
		String[] choices = qt.getqChoices().split("#");
		for(int i = 0; i < choices.length; i++){
			choices[i] = choices[i].substring(2); 
		}
		//��ȡ��
		String[] answer = qt.getqAnswer().split("#");
		for(int i = 0,y = 230,x = 65; i < chk.length; i++,y += 40,x++){
			chk[i] = new JCheckBox((char)x+"");
			txtChoice[i] = new JTextArea(choices[i]);
			chk[i].setBounds(180, y, 40, 20);
			//�Զ�����
			txtChoice[i].setLineWrap(true);
			sclChoice[i] = new JScrollPane(txtChoice[i]);
			sclChoice[i].setBounds(220, y, 150, 25);
			
			for(int j = 0; j < answer.length; j++){
				if(chk[i].getText().equals(answer[j])){
					chk[i].setSelected(true);
					break;
				}
			}
			this.add(chk[i]);
			this.add(sclChoice[i]);
		}
	
		
		//�������λ��
		lblCourse.setBounds(100, 10, 80, 20);
		cboCourse.setBounds(180, 10, 100, 20);
		lblType.setBounds(100, 50, 80, 20);
		lblTitle.setBounds(100, 90, 80, 20);
		scl.setBounds(180, 90, 200, 100);
		lblChoice.setBounds(100, 230, 80, 20);
		btnSave.setBounds(120, 450, 80, 20);
		btnCancel.setBounds(260, 450, 80, 20);
		//�������
		this.add(lblCourse);
		this.add(cboCourse);
		this.add(lblType);
		this.add(lblTitle);
		this.add(scl);
		this.add(lblChoice);
		this.add(btnSave);
		this.add(btnCancel);
	}
	
}