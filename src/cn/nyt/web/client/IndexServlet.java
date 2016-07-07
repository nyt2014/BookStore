package cn.nyt.web.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.nyt.bean.Category;
import cn.nyt.bean.PageBean;
import cn.nyt.bean.QueryInfo;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;
import cn.nyt.utils.WebUtils;

/**
 *获取前台首页数据
 */
public class IndexServlet extends HttpServlet {

	private BusinessService service =DaoFactory.getInstance().createInstance(BusinessService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//1.获取前台首页展示需要的分页数据
		//用于封装请求带过来的分页数据
		QueryInfo info = WebUtils.request2Bean(request, QueryInfo.class);
		//获取分类下面的图书
		String category_id =request.getParameter("category_id");
		if(category_id!=null || category_id.trim().equals("")){
			info.setQueryname("category_id");
			info.setQueryvalue(category_id);
		}
		
		//2.获取前台首页展示需要的图书分类数据
		List<Category> categories = service.getAllCategory();
		System.out.println(categories);
		//获取符合条件的分页数据
		PageBean pageBean = service.bookPageQuery(info);
//		System.out.println(pageBean);
		
		//将数据存入request域中
		request.setAttribute("categories", categories);
		request.setAttribute("pagebean", pageBean);
		request.getRequestDispatcher("/client/cindex.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
