package cn.nyt.web.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.nyt.bean.Category;
import cn.nyt.bean.User;
import cn.nyt.exception.MySecurityException;
import cn.nyt.factory.DaoFactory;
import cn.nyt.factory.ServiceFactory;
import cn.nyt.service.BusinessService;
import cn.nyt.utils.WebUtils;

public class CategoryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");
		if ("add".equals(method)) {
			add(request, response);
		}
		if ("getAll".equals(method)) {
			getAll(request, response);
		}
	}

	private void getAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BusinessService service = ServiceFactory.genInstance().createService(
				(User) request.getSession().getAttribute("user"));

		List<Category> list = service.getAllCategory();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manager/listcategory.jsp").forward(
				request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BusinessService service = ServiceFactory.genInstance().createService(
				(User) request.getSession().getAttribute("user"));
		try {
			Category bean = WebUtils.request2Bean(request, Category.class);
			service.addCategory(bean);
			request.setAttribute("message", "添加成功！");
		} catch (Exception e) {
			if (e.getCause() instanceof MySecurityException) {
				request.setAttribute("message", e.getCause().getMessage());
			} else {
				request.setAttribute("message", "添加失败！");
				throw new RuntimeException(e);
			}
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
