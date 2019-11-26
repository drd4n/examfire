/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.model.ExamJpaController;
import controller.model.ScoreController;
import java.io.IOException;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Exam;
import model.Users;

/**
 *
 * @author user
 */
public class ScoreServlet extends HttpServlet {
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
    
//    Users userid;
//    Exam examid;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
       HttpSession session = request.getSession(false);
        Users user = (Users) session.getAttribute("user");
        int examid = Integer.parseInt(request.getParameter("examid")) ;
        ScoreController sc = new ScoreController();
        ExamJpaController ex = new ExamJpaController(utx, emf);
        Exam exam =ex.findExam(examid);
        if(sc.findByUseridAndExamid(user, exam) == 0){
            request.setAttribute("examid", examid);
            response.sendRedirect("/Examfire/ExamServlet?examid=" + examid);
            return;
        }
        int score = sc.findByUseridAndExamid(user, exam);
        
//        request.setAttribute("user", user);
        request.setAttribute("exam", exam);
        request.setAttribute("score", score);
        getServletContext().getRequestDispatcher("/WEB-INF/Score.jsp").forward(request, response);
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
       processRequest(request, response);
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
