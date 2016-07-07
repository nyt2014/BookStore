package cn.nyt.web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.nyt.bean.User;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;
import cn.nyt.service.impl.BusinessServiceImpl;
import cn.nyt.utils.WebUtils;

public class RegisterServlet extends HttpServlet {
	private BusinessService service =DaoFactory.getInstance().createInstance(BusinessService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			User user = WebUtils.request2Bean(request, User.class);
			service.addUser(user);
			request.setAttribute("message", "×¢²á³É¹¦£¡");
		} catch (Exception e) {
			request.setAttribute("message", "×¢²áÊ§°Ü£¡");
			throw new RuntimeException(e);
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
