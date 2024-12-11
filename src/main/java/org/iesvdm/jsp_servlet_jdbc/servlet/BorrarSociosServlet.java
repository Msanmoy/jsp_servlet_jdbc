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

        //CÓDIGO DE VALIDACIÓN ENCAPSULADO EN UN MÉTODO DE UTILERÍA
        // SI OK ==> OPTIONAL CON SOCIO                 |
        // SI FAIL ==> EMPTY OPTIONAL                   |
        //                                              V
        Optional<Socio> optionalSocio = UtilServlet.validaBorrar(request);

        //SI OPTIONAL CON SOCIO PRESENTE <--> VALIDA OK
        if (optionalSocio.isPresent()) {

            //ACCEDO AL VALOR DE OPTIONAL DE SOCIO
            Socio socio = optionalSocio.get();

            //PERSITO EL SOCIO NUEVO EN BBDD
            this.socioDAO.delete(socio.getSocioId());

            //CARGO TODO EL LISTADO DE SOCIOS DE BBDD CON EL NUEVO
            List<Socio> listado = this.socioDAO.getAll();

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listadoSociosB.jsp");
        } else {

            //El OPTIONAL ESTÁ VACÍO (EMPTY)
            //PREPARO MENSAJE DE ERROR EN EL ÁMBITO DEL REQUEST PARA LA VISTA JSP
            //                                |
            //                                V
            request.setAttribute("error", "Error de validación!");

            //POR ÚLTIMO, REDIRECCIÓN INTERNA PARA LA URL /BorrarSocioServlet A formularioSocio.jsp
            //                                                                      |
            //                                                                      V
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioSocioB.jsp");
        }


        //SIEMPRE PARA HACER EFECTIVA UNA REDIRECCIÓN INTERNA DEL SERVIDOR
        //TENEMOS QUE HACER FORWARD CON LOS OBJETOS request Y response
        dispatcher.forward(request,response);

    }
}
