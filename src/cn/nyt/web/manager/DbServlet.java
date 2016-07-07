package cn.nyt.web.manager;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.nyt.bean.DbBak;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;

public class DbServlet extends HttpServlet {

	BusinessService service = DaoFactory.getInstance().createInstance(
			BusinessService.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("bakup".equals(method)) {
			bakup(request, response);
		}
		if ("list".equals(method)) {
			list(request, response);
		}
		if ("restore".equals(method)) {
			restore(request, response);
		}
		if ("remove".equals(method)) {
			remove(request, response);
		}

	}

	private void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			DbBak bak = service.findBak(id);
			File file = new File(bak.getFilename());
			if (file.exists()) {
				file.delete();
			}
			if (id != null) {
				service.deleteBak(id);
			}
			request.setAttribute("message", "删除成功！");
		} catch (Exception e) {
			request.setAttribute("message", "删除失败！");
			throw new RuntimeException(e);
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	private void restore(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			DbBak bak = service.findBak(id);
			String filename = bak.getFilename();

			File file = new File(filename);
			if (file.exists()) {
				String command = "cmd /c mysql -uroot -proot day22_myBookStore<"
						+ filename;
				Runtime.getRuntime().exec(command);
				request.setAttribute("message", "恢复成功！");
			}else{
				request.setAttribute("message", "恢复失败！该备份文件不存在！");
			}
		} catch (Exception e) {
			request.setAttribute("message", "恢复失败！");
			throw new RuntimeException(e);
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);

	}

	private void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<DbBak> baks = service.getAllBak();
		request.setAttribute("baks", baks);
		request.getRequestDispatcher("/manager/listbak.jsp").forward(request,
				response);

	}

	// 1.备份当前数据库
	private void bakup(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			// 备份存放的目录
			String bakpath = this.getServletContext().getRealPath("/bakup");
			// 以当前时间的毫秒值作为备份的文件名
			String filename = System.currentTimeMillis() + ".sql";

			// Windows DOS命令 备份操作
			// windows命令备份：mysqldump -uroot -p mydb22>e:\mydb22.sql
			String command = "cmd /c mysqldump -uroot -proot day22_myBookStore>"
					+ bakpath + "\\" + filename;
			Runtime.getRuntime().exec(command);

			// 将备份的信息封装到javabean中，并把javabean存入数据库
			DbBak bak = new DbBak();
			bak.setBaktime(new Date());
			bak.setDescription(request.getParameter("description"));
			bak.setFilename(bakpath + "\\" + filename);
			service.addBak(bak);
			request.setAttribute("message", "备份成功！");
		} catch (Exception e) {
			request.setAttribute("message", "备份失败！");
			throw new RuntimeException(e);
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
