/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.model.UsersJpaController;
import controller.model.exceptions.RollbackFailureException;
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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Users;

/**
 *
 * @author Dan
 */
public class EditPasswordServlet extends HttpServlet {
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
        HttpSession ses =request.getSession(false);
        Users user = (Users) ses.getAttribute("user");
        
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("password");
        String cfpassword = request.getParameter("cfpassword");
        
        if(!(newpassword.equals(cfpassword))){
            request.setAttribute("message", "Your new password not match");
            getServletContext().getRequestDispatcher("/WEB-INF/Profile.jsp").forward(request, response);
        }
        
        if(user.getPassword().equals(oldpassword)){
            if(cfpassword.equals(oldpassword)){
            request.setAttribute("message", "Your new password and old password cannot be the same");
            getServletContext().getRequestDispatcher("/WEB-INF/Profile.jsp").forward(request, response);
            return;
        }
            try {
                user.setPassword(cfpassword);
                UsersJpaController uc = new UsersJpaController(utx, emf);
                uc.edit(user);
                ses.setAttribute("user", user);
                request.setAttribute("message", "Password Changed");
                ses.invalidate();
                getServletContext().getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
                return;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(EditPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(EditPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.setAttribute("message", "Your old password Wrong!");
        getServletContext().getRequestDispatcher("/WEB-INF/Profile.jsp").forward(request, response);
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
        getServletContext().getRequestDispatcher("/WEB-INF/Profile.jsp").forward(request, response);
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
