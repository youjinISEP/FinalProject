package com.royal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayImage
 */
@WebServlet("/DisplayImage")
public class DisplayImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String id=request.getParameter("id"); 
		
try {
	Class.forName("com.mysql.jdbc.Driver");

	    
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/learningmangament","root", "");
	    Statement st = con.createStatement();
	    ResultSet rs;
	    rs = st.executeQuery("select * from tbl_course where id=" + id );
	    if (rs.next()) {
	    	String mPath="F:\\uploads\\";
	    	
	    	 //String pic="F:/uploads/Screenshot_2016-12-21-13-44-04.png";
	        String pic=rs.getString("CourseImage");
	       
	        response.setContentType("image/jpeg");  
	        ServletOutputStream out;  
	        out = response.getOutputStream();  
	        FileInputStream fin;
	        
	       System.out.print("pic:"+pic);
	        
	        if(pic.equalsIgnoreCase(null)){
	        	//fin = new FileInputStream("F:\\uploads\\users.png"); 
	        	fin = new FileInputStream(mPath+"users.png");
	        	System.out.print("in null image");
	        }
	        else
	        {
	        fin = new FileInputStream(mPath+pic);  
	        } 
	        BufferedInputStream bin = new BufferedInputStream(fin);  
	        BufferedOutputStream bout = new BufferedOutputStream(out);  
	        int ch =0; ;  
	        while((ch=bin.read())!=-1)  
	        {  
	        bout.write(ch);  
	        }  
	          
	        bin.close();  
	        fin.close();  
	        bout.close();  
	        out.close();  
	        con.close();
	        
	    } 
		
		
	    
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
