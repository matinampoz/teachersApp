package gr.aueb.elearn.teacherapp.dao;

import static gr.aueb.elearn.teacherapp.dao.dbutil.DBUtil.closeConnection;
import static gr.aueb.elearn.teacherapp.dao.dbutil.DBUtil.openConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import gr.aueb.elearn.teacherapp.model.Teacher;

public class TeacherDAOImpl implements ITeacherDAO {

	@Override
	public void insert(Teacher teacher) throws SQLException {
		String sql = "INSERT INTO TEACHERS VALUES ('" + teacher.getId() 
		+ "', '" + teacher.getSname() + "', '" + teacher.getFname() + "')";
		
		PreparedStatement pst = openConnection().prepareStatement(sql);
		int n = pst.executeUpdate(sql);
		
		JOptionPane.showMessageDialog(null, n + " Record inserted.", "INSERT", 
				JOptionPane.PLAIN_MESSAGE);
		
		pst.close();
		closeConnection();
	}

	@Override
	public void delete(Teacher teacher) throws SQLException {
		String sql = "DELETE FROM TEACHERS WHERE TEACHER_ID = " + teacher.getId();
		int dialogButton;
		
		PreparedStatement pst = openConnection().prepareStatement(sql);
		
        dialogButton = JOptionPane.showConfirmDialog (null, "Είστε σίγουρος;", 
      		  "Warning", JOptionPane.YES_NO_OPTION);

        if (dialogButton == JOptionPane.YES_OPTION){
      	  int numberOfRowsAffected = pst.executeUpdate();
      	  JOptionPane.showMessageDialog (null, numberOfRowsAffected + " rows deleted successfully", 
          		  "DELETE", JOptionPane.INFORMATION_MESSAGE);
        } else {
        	return;
        }
      	pst.close();
      	closeConnection();
	}

	@Override
	public void update(Teacher oldTeacher, Teacher newTeacher) throws SQLException {
		String sql = "UPDATE TEACHERS SET S_NAME = '" + newTeacher.getSname() + "', " + "F_NAME = '" 
				+ newTeacher.getFname() + "' WHERE TEACHER_ID = " + oldTeacher.getId();
		System.out.println(sql);
		
		PreparedStatement pst = openConnection().prepareStatement(sql);
		int numberOfRowsAffected = pst.executeUpdate();
	      
	      JOptionPane.showMessageDialog(null, numberOfRowsAffected + " rows affected", 
	    		  "UPDATE", JOptionPane.PLAIN_MESSAGE);
		  pst.close();
		  closeConnection();
	}

	@Override
	public List<Teacher> getTeachersBySurname(String surname) throws SQLException {
		String sql = "SELECT TEACHER_ID, S_NAME, F_Name FROM TEACHERS WHERE S_NAME LIKE '" 
				+ surname + "%'";
		
	    PreparedStatement pst =  openConnection().prepareStatement(sql);
	    ResultSet rs = pst.executeQuery(sql);
	    
	    List<Teacher> teachers = new ArrayList<>();
	    
	    rs.beforeFirst();
	    while (rs.next()) {
	    	Teacher teacher = new Teacher();
		    	
	    	teacher.setId(rs.getInt("TEACHER_ID"));
	    	teacher.setSname(rs.getString("S_NAME"));
	    	teacher.setFname(rs.getString("F_NAME"));
	    	teachers.add(teacher); 	
	    }
	    
	    closeConnection();
	    return teachers;
	}

	@Override
	public Teacher getTeacherById(int id) throws SQLException {
		String sql = "SELECT * FROM TEACHERS WHERE TEACHER_ID = " 
				+ id;
	    PreparedStatement pst =  openConnection().prepareStatement(sql);
	    ResultSet rs = pst.executeQuery(sql);
	    Teacher teacher = new Teacher();
	    if (rs.next()) {
	    	teacher.setId(rs.getInt("TEACHER_ID"));
	    	teacher.setSname(rs.getString("S_NAME"));
	    	teacher.setFname(rs.getString("F_NAME"));
	    	return teacher;
	    }
	    
	    return null;
	}	
}


