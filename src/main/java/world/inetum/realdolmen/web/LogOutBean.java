package world.inetum.realdolmen.web;

import javax.enterprise.inject.Model;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Model
public class LogOutBean {

    public String logOut(HttpServletRequest request) throws ServletException {
        request.logout();
        request.getSession().invalidate();
        return "/time-registrations?faces-redirect=true";
    }
}
