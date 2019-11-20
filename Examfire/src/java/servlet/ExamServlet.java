/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.model.ExamJpaController;
import controller.model.ScoreController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Choice;
import model.Choiceset;
import model.Exam;
import model.Users;

/**
 *
 * @author ZolyKana
 */
@WebServlet(name = "Exam", urlPatterns = {"/Exam"})
public class ExamServlet extends HttpServlet {
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ExamServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExamServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        int id = Integer.parseInt((String) request.getParameter("examid"));
        ExamJpaController xc = new ExamJpaController(utx, emf);
        Exam exam = xc.findExam(id);
        request.setAttribute("Exam", exam);
        getServletContext().getRequestDispatcher("/WEB-INF/Exam.jsp").forward(request, response); 
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
        int examid = Integer.parseInt(request.getParameter("examid"));
        ExamJpaController xc = new ExamJpaController(utx, emf);
        ScoreController sc = new ScoreController();
        Exam exam = xc.findExam(examid);
        int answer = 0;
        int score = 0;
        for (Choiceset set : exam.getChoicesetList()) {
            for (Choice c  : set.getChoiceList()) {
                answer = Integer.parseInt(request.getParameter("answers"+c.getChoicesetid()+"c"+set.getChoicesetid()));
                if(c.getChoiceid()==answer){
                    score++;
                }
            }
        }
        //Send Score to Table
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("users");
        sc.saveScore(user.getUserid(), score, examid);
        
        getServletContext().getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
        
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
