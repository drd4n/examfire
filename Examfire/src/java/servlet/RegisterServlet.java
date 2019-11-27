/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.model.UsersJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import model.Users;

/**
 *
 * @author Dan
 */
public class RegisterServlet extends HttpServlet {
    @PersistenceUnit(name = "ExamfirePU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String password = request.getParameter("password");
        String cfpassword = request.getParameter("cfpassword");
        
        if(!(password.equals(cfpassword))){
            request.setAttribute("message", "Confirm your password do not match");
            getServletContext().getRequestDispatcher("/WEB-INF/Register.jsp").forward(request, response);
        }
        
        String userfullname = request.getParameter("userfullname");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        
        UsersJpaController usc = new UsersJpaController(utx, emf);
        
        if(username.equals(usc.findByUsername(username).getUsername())){
            request.setAttribute("message", "This user already exist");
            getServletContext().getRequestDispatcher("/WEB-INF/Register.jsp").forward(request, response);
            return;
        }
        
        Users u = new Users(username, password, userfullname, email);
        try {
            usc.create(u);
            request.setAttribute("message", "Register Sussesfully");
            getServletContext().getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("message", "Register Sus ses ful ly");
        getServletContext().getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/Register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
