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
			request.setAttribute("message", "ɾ���ɹ���");
		} catch (Exception e) {
			request.setAttribute("message", "ɾ��ʧ�ܣ�");
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
				request.setAttribute("message", "�ָ��ɹ���");
			}else{
				request.setAttribute("message", "�ָ�ʧ�ܣ��ñ����ļ������ڣ�");
			}
		} catch (Exception e) {
			request.setAttribute("message", "�ָ�ʧ�ܣ�");
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

	// 1.���ݵ�ǰ���ݿ�
	private void bakup(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			// ���ݴ�ŵ�Ŀ¼
			String bakpath = this.getServletContext().getRealPath("/bakup");
			// �Ե�ǰʱ��ĺ���ֵ��Ϊ���ݵ��ļ���
			String filename = System.currentTimeMillis() + ".sql";

			// Windows DOS���� ���ݲ���
			// windows����ݣ�mysqldump -uroot -p mydb22>e:\mydb22.sql
			String command = "cmd /c mysqldump -uroot -proot day22_myBookStore>"
					+ bakpath + "\\" + filename;
			Runtime.getRuntime().exec(command);

			// �����ݵ���Ϣ��װ��javabean�У�����javabean�������ݿ�
			DbBak bak = new DbBak();
			bak.setBaktime(new Date());
			bak.setDescription(request.getParameter("description"));
			bak.setFilename(bakpath + "\\" + filename);
			service.addBak(bak);
			request.setAttribute("message", "���ݳɹ���");
		} catch (Exception e) {
			request.setAttribute("message", "����ʧ�ܣ�");
			throw new RuntimeException(e);
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
