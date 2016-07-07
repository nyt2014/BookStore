package cn.nyt.web.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.nyt.bean.Order;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;

public class OrderServlet extends HttpServlet {

	BusinessService service=DaoFactory.getInstance().createInstance(BusinessService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if("getAll".equals(method)){
			getAll(request,response);
		}
		if("find".equals(method)){
			find(request,response);
		}
		if("update".equals(method)){
			update(request,response);
		}
		

	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			service.updateOrder(id, true);
			request.setAttribute("message", "订单已置为发货，请及时发货！");
		} catch (Exception e) {
			request.setAttribute("message", "出错！！");
			throw new RuntimeException(e);
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	private void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Order order = service.findOrder(id);
		request.setAttribute("order", order);
		request.getRequestDispatcher("/manager/orderdetail.jsp").forward(request, response);
		
	}

	private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String state = request.getParameter("state");
		List<Order> orders = service.getOrdersByState(Boolean.parseBoolean(state));
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/manager/listorders.jsp").forward(request, response);
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
