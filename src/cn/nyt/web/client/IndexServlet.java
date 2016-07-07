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
 *��ȡǰ̨��ҳ����
 */
public class IndexServlet extends HttpServlet {

	private BusinessService service =DaoFactory.getInstance().createInstance(BusinessService.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//1.��ȡǰ̨��ҳչʾ��Ҫ�ķ�ҳ����
		//���ڷ�װ����������ķ�ҳ����
		QueryInfo info = WebUtils.request2Bean(request, QueryInfo.class);
		//��ȡ���������ͼ��
		String category_id =request.getParameter("category_id");
		if(category_id!=null || category_id.trim().equals("")){
			info.setQueryname("category_id");
			info.setQueryvalue(category_id);
		}
		
		//2.��ȡǰ̨��ҳչʾ��Ҫ��ͼ���������
		List<Category> categories = service.getAllCategory();
		System.out.println(categories);
		//��ȡ���������ķ�ҳ����
		PageBean pageBean = service.bookPageQuery(info);
//		System.out.println(pageBean);
		
		//�����ݴ���request����
		request.setAttribute("categories", categories);
		request.setAttribute("pagebean", pageBean);
		request.getRequestDispatcher("/client/cindex.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
