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
    int examid;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        examid = Integer.parseInt(request.getParameter("examid"));
        HttpSession session = request.getSession(false);
        Users user = (Users) session.getAttribute("user");
        ExamJpaController ex = new ExamJpaController(utx, emf);
        Exam exam =ex.findExam(examid);
        ScoreController sc = new ScoreController();
        if(sc.findByUseridAndExamid(user, exam) == 0){
        request.setAttribute("Exam", exam);
        getServletContext().getRequestDispatcher("/WEB-INF/Exam.jsp").forward(request, response); 
            return;
        }
        response.sendRedirect("/Examfire/Score?examid=" + examid);
//        request.setAttribute("Exam", exam);
//        getServletContext().getRequestDispatcher("/WEB-INF/Exam.jsp").forward(request, response); 
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
        ExamJpaController xc = new ExamJpaController(utx, emf);
        Exam exam = xc.findExam(examid);
        int answer = 0;
        int score = 0;
        int choiceCount;
        int choiceid = exam.getChoicesetList().get(0).getChoiceList().get(0).getChoiceid();
        String sChoiceid;
        for (Choiceset set : exam.getChoicesetList()) {
            choiceCount =1;
            for (Choice c  : set.getChoiceList()) {
                sChoiceid = Integer.toString(choiceid);
                System.out.println(sChoiceid);
                answer = Integer.parseInt(request.getParameter(sChoiceid));
                System.out.println(answer);
                if(choiceCount==answer){
                    score++;
                }
                choiceid++;
                choiceCount++;
            }
        }
        System.out.println(score);
        //Send Score to Table
        HttpSession session = request.getSession(false);
        Users user = (Users) session.getAttribute("user");
        ScoreController sc = new ScoreController();
        sc.saveScore(user.getUserid(), score, examid);
        request.setAttribute("exam", exam);
        request.setAttribute("score", score);
        getServletContext().getRequestDispatcher("/WEB-INF/Score.jsp").forward(request, response);
        
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

    private String String(Integer choicesetid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
