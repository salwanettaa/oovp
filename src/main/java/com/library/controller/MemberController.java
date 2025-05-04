package com.library.controller;

import com.library.model.Member;
import com.library.service.MemberService;
import com.library.service.impl.MemberServiceImpl;
import com.library.util.DateUtils;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for Member operations
 */
@WebServlet(name = "MemberController", urlPatterns = {"/members/*"})
public class MemberController extends HttpServlet {

    private final MemberService memberService;
    
    public MemberController() {
        this.memberService = new MemberServiceImpl();
    }
    
    /**
     * Handles the HTTP GET method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // List all members
            List<Member> members = memberService.getAllMembers();
            request.setAttribute("members", members);
            request.getRequestDispatcher("/WEB-INF/views/member/list.jsp").forward(request, response);
        } else if (pathInfo.equals("/add")) {
            // Show add member form
            request.getRequestDispatcher("/WEB-INF/views/member/add.jsp").forward(request, response);
        } else if (pathInfo.equals("/edit")) {
            // Show edit member form
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Member member = memberService.getMemberById(id);
                
                if (member != null) {
                    request.setAttribute("member", member);
                    request.getRequestDispatcher("/WEB-INF/views/member/edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/members");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/members");
            }
        } else if (pathInfo.equals("/search")) {
            // Handle search
            String name = request.getParameter("name");
            
            List<Member> members;
            
            if (name != null && !name.isEmpty()) {
                members = memberService.searchByName(name);
            } else {
                members = memberService.getAllMembers();
            }
            
            request.setAttribute("members", members);
            request.setAttribute("searchName", name);
            request.getRequestDispatcher("/WEB-INF/views/member/list.jsp").forward(request, response);
        } else {
            // Show member details
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Member member = memberService.getMemberById(id);
                
                if (member != null) {
                    request.setAttribute("member", member);
                    request.getRequestDispatcher("/WEB-INF/views/member/details.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/members");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/members");
            }
        }
    }

    /**
     * Handles the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Handle form submission for adding a member
            String name = request.getParameter("name");
            String dateRegisterStr = request.getParameter("dateRegister");
            
            try {
                Member member = new Member();
                member.setName(name);
                
                if (dateRegisterStr != null && !dateRegisterStr.isEmpty()) {
                    Date dateRegister = DateUtils.convertStringToSqlDate(dateRegisterStr);
                    member.setDateRegister(dateRegister);
                } else {
                    member.setDateRegister(DateUtils.getCurrentDate());
                }
                
                boolean success = memberService.addMember(member);
                
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/members");
                } else {
                    request.setAttribute("member", member);
                    request.setAttribute("error", "Failed to add member");
                    request.getRequestDispatcher("/WEB-INF/views/member/add.jsp").forward(request, response);
                }
            } catch (ParseException e) {
                request.setAttribute("error", "Invalid date format");
                request.getRequestDispatcher("/WEB-INF/views/member/add.jsp").forward(request, response);
            }
        } else if (pathInfo.equals("/update")) {
            // Handle form submission for updating a member
            String idStr = request.getParameter("id");
            String name = request.getParameter("name");
            String dateRegisterStr = request.getParameter("dateRegister");
            
            try {
                int id = Integer.parseInt(idStr);
                
                Member member = new Member();
                member.setId(id);
                member.setName(name);
                
                if (dateRegisterStr != null && !dateRegisterStr.isEmpty()) {
                    Date dateRegister = DateUtils.convertStringToSqlDate(dateRegisterStr);
                    member.setDateRegister(dateRegister);
                } else {
                    member.setDateRegister(DateUtils.getCurrentDate());
                }
                
                boolean success = memberService.updateMember(member);
                
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/members");
                } else {
                    request.setAttribute("member", member);
                    request.setAttribute("error", "Failed to update member");
                    request.getRequestDispatcher("/WEB-INF/views/member/edit.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/members");
            } catch (ParseException e) {
                request.setAttribute("error", "Invalid date format");
                request.getRequestDispatcher("/WEB-INF/views/member/edit.jsp").forward(request, response);
            }
        } else if (pathInfo.equals("/delete")) {
            // Handle member deletion
            String idStr = request.getParameter("id");
            
            try {
                int id = Integer.parseInt(idStr);
                memberService.deleteMember(id);
            } catch (NumberFormatException e) {
                // Ignore
            }
            
            response.sendRedirect(request.getContextPath() + "/members");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Member Controller Servlet";
    }
}