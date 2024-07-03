package gr.aueb.elearn.teacherapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import gr.aueb.elearn.teacherapp.dao.ITeacherDAO;
import gr.aueb.elearn.teacherapp.dao.TeacherDAOImpl;
import gr.aueb.elearn.teacherapp.dto.TeacherDTO;
import gr.aueb.elearn.teacherapp.service.ITeacherService;
import gr.aueb.elearn.teacherapp.service.TeacherServiceImpl;
import gr.aueb.elearn.teacherapp.service.exceptions.TeacherIdAlreadyExistsException;


public class FrmEkpaideytesInsert extends JFrame {

	private static final long serialVersionUID = 1L;

	private ITeacherDAO teacherDAO = new TeacherDAOImpl();
	private ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);
	
	private JPanel contentPane;
	private JTextField frm_sname;
	private JTextField frm_fname;
	private JTextField frm_id;
	
	public FrmEkpaideytesInsert() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				frm_id.setText("");
				frm_sname.setText("");
				frm_fname.setText("");	
			}
		});
		setBackground(SystemColor.activeCaption);
		setTitle("Εισαγωγή Εκπαιδευτή");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 383, 270);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_ID = new JLabel("Κωδικός");
		lbl_ID.setForeground(new Color(153, 0, 0));
		lbl_ID.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl_ID.setBounds(58, 37, 56, 16);
		contentPane.add(lbl_ID);
		
		JLabel lbl_sname = new JLabel("Επίθετο");
		lbl_sname.setForeground(new Color(153, 0, 0));
		lbl_sname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_sname.setBounds(58, 72, 56, 16);
		contentPane.add(lbl_sname);
		
		JLabel lbl_fname = new JLabel("Όνομα");
		lbl_fname.setForeground(new Color(153, 0, 0));
		lbl_fname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_fname.setBounds(58, 107, 56, 16);
		contentPane.add(lbl_fname);
		
		frm_id = new JTextField();
		frm_id.setBounds(117, 37, 120, 22);
		contentPane.add(frm_id);
		frm_id.setColumns(12);
		
		frm_sname = new JTextField();
		frm_sname.setBounds(117, 71, 189, 22);
		contentPane.add(frm_sname);
		frm_sname.setColumns(10);
		
		frm_fname = new JTextField();
		frm_fname.setBounds(117, 107, 189, 22);
		contentPane.add(frm_fname);
		frm_fname.setColumns(10);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.setForeground(new Color(0, 0, 153));
		btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					TeacherDTO teacherDTO = new TeacherDTO();
					teacherDTO.setId(Integer.parseInt(frm_id.getText()));
					teacherDTO.setSname(frm_sname.getText());
					teacherDTO.setFname(frm_fname.getText());
					
					teacherService.insertTeacher(teacherDTO);		
					
				} catch (SQLException  e1) {		
					JOptionPane.showMessageDialog(null, "Error. Please try again", 
							"Error", JOptionPane.WARNING_MESSAGE);
					initFields();
				  } catch ( TeacherIdAlreadyExistsException e2) {
					  JOptionPane.showMessageDialog(null, "Inalid Key for TEACHER ID, Please try again", 
							  "Error", JOptionPane.WARNING_MESSAGE);
					  initFields();
				  }
			}
		});
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 170, 341, 2);
		contentPane.add(separator);
		btnInsert.setBounds(168, 185, 82, 25);
		contentPane.add(btnInsert);
		
		JButton btnClose = new JButton("Close");
		btnClose.setForeground(new Color(0, 0, 153));
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TeachersApp.ekpaidSearchFrame.setEnabled(true);
				TeachersApp.ekpaidInsertFrame.setVisible(false);	
			}
		});
		btnClose.setBounds(250, 185, 95, 25);
		contentPane.add(btnClose);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(18, 13, 329, 144);
		contentPane.add(panel);
	}
	
	private void initFields() {
		frm_id.setText("");
		frm_sname.setText("");
		frm_fname.setText("");
	}
}
