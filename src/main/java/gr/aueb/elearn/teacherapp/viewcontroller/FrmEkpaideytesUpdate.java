package gr.aueb.elearn.teacherapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.elearn.teacherapp.dao.ITeacherDAO;
import gr.aueb.elearn.teacherapp.dao.TeacherDAOImpl;
import gr.aueb.elearn.teacherapp.dto.TeacherDTO;
import gr.aueb.elearn.teacherapp.model.Teacher;
import gr.aueb.elearn.teacherapp.service.ITeacherService;
import gr.aueb.elearn.teacherapp.service.TeacherServiceImpl;
import gr.aueb.elearn.teacherapp.service.exceptions.TeacherNotFoundException;

import javax.swing.JOptionPane;
import java.sql.*;
//import java.sql.PreparedStatement;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;

public class FrmEkpaideytesUpdate extends JFrame {

	private static final long serialVersionUID = 1L;

	private ITeacherDAO teacherDAO = new TeacherDAOImpl();
	private ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);
	
	private List<Teacher> teachers;
	private int listPosition, listSize;
	
	private JPanel contentPane;
	private JTextField udfrm_sname;
	private JTextField udfrm_fname;
	private JTextField udfrm_id;
	
	private TeacherDTO oldTeacherDTO = new TeacherDTO();
	private TeacherDTO newTeacherDTO = new TeacherDTO();
	
	
	public FrmEkpaideytesUpdate() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {	
				try{
					teachers =  teacherService.getTeachersBySurname(TeachersApp.ekpaidSearchFrame.getSearchEpwnymo());
					
					
					if (teachers != null)
				    {
						listPosition = 0;
						listSize = teachers.size();
						
						udfrm_id.setText(String.format("%s", teachers.get(0).getId()));
				        udfrm_sname.setText(teachers.get(0).getSname());
				        udfrm_fname.setText(teachers.get(0).getFname());
				        
				    } else {
				    	JOptionPane.showMessageDialog(null, "Δεν υπάρχουν εγγραφές", "Empty Search", JOptionPane.PLAIN_MESSAGE);
				    	TeachersApp.ekpaidSearchFrame.setEnabled(true);
						TeachersApp.ekpaidUpdateFrame.setVisible(false);
				    }
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			      
			/*@Override
			public void windowDeactivated(WindowEvent e) {
				udfrm_id.setText("");
				udfrm_sname.setText("");
				udfrm_fname.setText("");
			}*/
		});
		
		setBackground(SystemColor.activeCaption);
		setTitle("\u0395\u03BA\u03C0\u03B1\u03B9\u03B4\u03B5\u03C5\u03C4\u03AD\u03C2");
		
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 296, 250);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_ID = new JLabel("ID");
		lbl_ID.setForeground(new Color(153, 0, 0));
		lbl_ID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_ID.setBounds(9, 13, 56, 16);
		contentPane.add(lbl_ID);
		
		JLabel lbl_sname = new JLabel("\u0395\u03C0\u03AF\u03B8\u03B5\u03C4\u03BF");
		lbl_sname.setForeground(new Color(153, 0, 0));
		lbl_sname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_sname.setBounds(9, 48, 56, 16);
		contentPane.add(lbl_sname);
		
		JLabel lbl_fname = new JLabel("\u038C\u03BD\u03BF\u03BC\u03B1");
		lbl_fname.setForeground(new Color(153, 0, 0));
		lbl_fname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_fname.setBounds(9, 83, 56, 16);
		contentPane.add(lbl_fname);
		
		udfrm_id = new JTextField();
		udfrm_id.setEditable(false);
		udfrm_id.setBounds(78, 13, 56, 22);
		contentPane.add(udfrm_id);
		udfrm_id.setColumns(10);
		
		udfrm_sname = new JTextField();
		udfrm_sname.setBounds(78, 48, 189, 22);
		contentPane.add(udfrm_sname);
		udfrm_sname.setColumns(10);
		
		udfrm_fname = new JTextField();
		udfrm_fname.setBounds(78, 81, 189, 22);
		contentPane.add(udfrm_fname);
		udfrm_fname.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					oldTeacherDTO.setId(Integer.parseInt(udfrm_id.getText()));
				      
				    newTeacherDTO.setId(Integer.parseInt(udfrm_id.getText()));
				    newTeacherDTO.setSname(udfrm_sname.getText());
				    newTeacherDTO.setFname(udfrm_fname.getText());
				      
				    teacherService.updateTeacher(oldTeacherDTO, newTeacherDTO);
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				} catch (TeacherNotFoundException e1) {
					System.out.println(e1.getMessage());
				}	
			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					TeacherDTO teacherToDelete = new TeacherDTO();
					teacherToDelete.setId(Integer.parseInt(udfrm_id.getText()));
					teacherService.deleteTeacher(teacherToDelete);	
				}
				catch (SQLException e6){
					e6.printStackTrace();
				} catch (TeacherNotFoundException e1) {	
					e1.printStackTrace();
				}
			}
		});
		
		btnDelete.setBounds(9, 165, 89, 25);
		contentPane.add(btnDelete);
		btnUpdate.setBounds(91, 165, 92, 25);
		contentPane.add(btnUpdate);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TeachersApp.ekpaidSearchFrame.setEnabled(true);
				TeachersApp.ekpaidUpdateFrame.setVisible(false);	
			}
		});
		btnClose.setBounds(180, 165, 95, 25);
		contentPane.add(btnClose);
		
		JButton btnFirst = new JButton("");
		btnFirst.setIcon(new ImageIcon("C:\\THANASSIS\\AUEB\\MY_LECTURES\\JAVA\\Java-4-FullStackDev\\16x16\\First record.png"));
		
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					udfrm_id.setText(String.format("%s", teachers.get(0).getId()));
			        udfrm_sname.setText(teachers.get(0).getSname());
			        udfrm_fname.setText(teachers.get(0).getFname());
			} 
		});
		
		
		btnFirst.setBounds(9, 132, 39, 25);
		contentPane.add(btnFirst);
		
		JButton btnPrev = new JButton("");
		btnPrev.setIcon(new ImageIcon("C:\\THANASSIS\\AUEB\\MY_LECTURES\\JAVA\\Java-4-FullStackDev\\16x16\\Previous_record.png"));
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listPosition >= 1) {
					listPosition--;
					udfrm_id.setText(String.format("%s", teachers.get(listPosition).getId()));
			        udfrm_sname.setText(teachers.get(listPosition).getSname());
			        udfrm_fname.setText(teachers.get(listPosition).getFname());
				}
			}	
		});
		
		btnPrev.setBounds(44, 132, 39, 25);
		contentPane.add(btnPrev);
		
		JButton btnNext = new JButton("");
		btnNext.setIcon(new ImageIcon("C:\\THANASSIS\\AUEB\\MY_LECTURES\\JAVA\\Java-4-FullStackDev\\16x16\\Next_track.png"));
		
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listPosition <= teachers.size() - 2) {
					listPosition++;
					udfrm_id.setText(String.format("%s", teachers.get(listPosition).getId()));
			        udfrm_sname.setText(teachers.get(listPosition).getSname());
			        udfrm_fname.setText(teachers.get(listPosition).getFname());
				}
			}
		});
		
		btnNext.setBounds(78, 132, 46, 25);
		contentPane.add(btnNext);
		
		JButton btnLast = new JButton("");
		btnLast.setIcon(new ImageIcon("C:\\THANASSIS\\AUEB\\MY_LECTURES\\JAVA\\Java-4-FullStackDev\\16x16\\Last_Record.png"));
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					listPosition = listSize - 1;

					udfrm_id.setText(String.format("%s", teachers.get(listPosition).getId()));
			        udfrm_sname.setText(teachers.get(listPosition).getSname());
			        udfrm_fname.setText(teachers.get(listPosition).getFname());
			}
		});
		
		btnLast.setBounds(120, 132, 39, 25);
		contentPane.add(btnLast);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(9, 115, 257, 2);
		contentPane.add(separator);
	

	}
}
