package cn.nyt.web.client;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.nyt.bean.Book;
import cn.nyt.bean.Cart;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;

public class BuyServlet extends HttpServlet {
	
	BusinessService service =DaoFactory.getInstance().createInstance(BusinessService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		Book book=service.findBook(id);
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		if(cart==null){
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		cart.addBook(book);
		response.sendRedirect(request.getContextPath()+"/client/listcart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
