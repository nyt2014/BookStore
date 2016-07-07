package cn.nyt.web.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.nyt.bean.Book;
import cn.nyt.bean.Category;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;
import cn.nyt.utils.WebUtils;

public class BookServlet extends HttpServlet {

	BusinessService service=DaoFactory.getInstance().createInstance(BusinessService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if("forAddUI".equals(method)){
			forAddUI(request,response);
		}
		if("add".equals(method)){
			add(request,response);
		}
		if("list".equals(method)){
			list(request,response);
		}

	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> list = service.getAllBook();
		//测试代码，数据库取出的数据查的是gb2312的码表，若是图片保存路径含有中文，则到jsp页面解析出现乱码
		/*for (Book book : list) {
			if(book.getName().equals("javase000")){
			System.out.println(book.getImage());
			}
			
		}*/
		request.setAttribute("books", list);
		request.getRequestDispatcher("/manager/listbooks.jsp").forward(request, response);
		
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Book book = WebUtils.upLoad(request, this.getServletContext().getRealPath("/images"));
			service.addBook(book);
			request.setAttribute("message", "添加成功！");
		} catch (Exception e) {
			request.setAttribute("message", "添加失败！");
			throw new RuntimeException(e);
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
		
	}

	private void forAddUI(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//找出所有的分类，再把分类信息带到图书添加界面
		List<Category> list = service.getAllCategory();
		request.setAttribute("categories", list);
		request.getRequestDispatcher("/manager/addbook.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
