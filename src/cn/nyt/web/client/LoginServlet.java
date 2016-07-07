package cn.nyt.web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.nyt.bean.User;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;

public class LoginServlet extends HttpServlet {
	private BusinessService service =DaoFactory.getInstance().createInstance(BusinessService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = service.findUser(username, password);
		if(user==null){
			request.setAttribute("message", "用户名或密码错误！");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return ;
		}
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
