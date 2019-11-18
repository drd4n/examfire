/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.model.ExamJpaController;
import controller.model.ScoreController;
import controller.model.UsersJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
 * @author Dan
 */
public class HomeServlet extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        Users user = (Users) session.getAttribute("user");
        ExamJpaController xc = new ExamJpaController(utx, emf);
        List<Exam> exams = xc.findExamEntities();
        request.setAttribute("exams", exams);
        
//        ArrayList<Integer> scores = new ArrayList<>();
//        for (int i = 0; i < exams.size(); i++) {
//            ScoreController sc = new ScoreController();
//            scores.add(sc.findByUseridAndExamid(user, exams.get(i)));
//        }
//        request.setAttribute("scores", scores);
        
        ScoreController sc = new ScoreController();
        ArrayList<Integer> scores =sc.findScoreEntitiesByUserid(user);
        request.setAttribute("scores", scores);
        
        ExamJpaController ec = new ExamJpaController(utx, emf);
        ArrayList<Exam> urexs = ec.findExamByUserid(user);
        request.setAttribute("urexs", urexs);
        getServletContext().getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
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
