package cn.nyt.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.nyt.bean.Book;
import cn.nyt.bean.Category;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;

import com.sun.org.apache.commons.beanutils.BeanUtils;

public class WebUtils {

	public static <T> T request2Bean(HttpServletRequest request,Class<T> beanClass){
		
		try {
			T t=beanClass.newInstance();
			Map map = request.getParameterMap();
			
			BeanUtils.populate(t, map);
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}
	}
	
	public static Book upLoad(HttpServletRequest request,String uploadPath){
		try {
			Book book = new Book();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding(request.getCharacterEncoding());
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				//普通输入项
				if(item.isFormField()){
					String fieldName = item.getFieldName();
					String value = item.getString("utf-8");
					//如果是分类输入项,则为书保存对应的分类对象
					if("category_id".equals(fieldName)){
						BusinessService service = DaoFactory.getInstance().createInstance(BusinessService.class);
						Category c = service.findCategory(value);
						book.setCategory(c);
					}else{
						BeanUtils.setProperty(book, fieldName, value);
					}
				}else{//不是普通输入项
					String fileName = item.getName();
					fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
					String savePath=uploadPath;
					String saveFileName=UUID.randomUUID().toString()+fileName;
					
					InputStream in = item.getInputStream();
					FileOutputStream out = new FileOutputStream(savePath+"\\"+saveFileName);
					int len=0;
					byte[] buffer=new byte[1024];
					while((len=in.read(buffer))>0){
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
					item.delete();
					book.setImage(saveFileName);
				}
			}
			return book;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
