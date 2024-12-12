package org.iesvdm.jsp_servlet_jdbc.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAO;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAOImpl;
import org.iesvdm.jsp_servlet_jdbc.model.Socio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "BorrarSociosServlet", value = "/BorrarSociosServlet")
public class BorrarSociosServlet extends HttpServlet {
    private SocioDAO socioDAO = new SocioDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        Integer value = null;
        try {
             value = Integer.parseInt(request.getParameter("codigo"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        if(value != null) {
            this.socioDAO.delete(value);
        }

        List<Socio> listado = this.socioDAO.getAll();
        request.setAttribute("listado", listado);
        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listadoSociosB.jsp");



        //SIEMPRE PARA HACER EFECTIVA UNA REDIRECCIÓN INTERNA DEL SERVIDOR
        //TENEMOS QUE HACER FORWARD CON LOS OBJETOS request Y response
        dispatcher.forward(request,response);

    }
}
