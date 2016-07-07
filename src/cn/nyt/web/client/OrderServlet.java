package cn.nyt.web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.nyt.bean.Cart;
import cn.nyt.bean.User;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;

public class OrderServlet extends HttpServlet {

	private BusinessService service =DaoFactory.getInstance().createInstance(BusinessService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		if(user==null){
			request.setAttribute("message", "请先登陆！");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return ;
		}
		try {
			
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		/**
		 * #######
		 * if判断什么都没买的用户
		 * ##########
		 */
		service.saveOrder(cart, user);
		request.setAttribute("message", "订单生成成功！请等待收货！");
		} catch (Exception e) {
			request.setAttribute("message", "订单生成失败！");
			throw new RuntimeException(e);
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
