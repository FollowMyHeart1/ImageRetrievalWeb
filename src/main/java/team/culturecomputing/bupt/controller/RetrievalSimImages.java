package team.culturecomputing.bupt.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import team.culturecomputing.bupt.dao.SimImageInfo;
import team.culturecomputing.bupt.exception.ImageNotFoundException;
import team.culturecomputing.bupt.exception.ImageSizeLimitException;
import team.culturecomputing.bupt.exception.NotIsImageException;
import team.culturecomputing.bupt.exception.NumExceedLimitException;
import team.culturecomputing.bupt.exception.SocketFailureException;
import team.culturecomputing.bupt.util.SocketSend;

import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping(value="/imageretrievalvgg16",method=RequestMethod.POST, produces="application/json;charset=UTF-8")
public class RetrievalSimImages {
	private static final Logger LOGGER = LoggerFactory.getLogger(RetrievalSimImages.class);
	
	@Value("${queryImagePath}")
	private String queryImagePath;
	
	@RequestMapping(value="/simimages",method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public SimImageInfo  simimages(HttpServletRequest request, @RequestParam("imagefile")MultipartFile file,Integer topn) throws Exception {

		//要检索的图片路径（即上传的图片）
		//System.out.print(request);
		request.setCharacterEncoding("utf-8"); // 设置编码
		//如果topn参数没有，那么默认20
		if(topn==null)
		{
			topn=20;
		}
		else
		{	//如果topn参数不在范围内
			if(topn<0||topn>200)
			{
				LOGGER.error("设置的topn参数不在0-200之间");
				throw new NumExceedLimitException("设置的topn参数不在0-200之间");
			}
				
		}
		//如果没有接收到文件
		if(file==null)
		{
			LOGGER.error("没有接收到待检索的图片");
			throw new ImageNotFoundException("没有接收到待检索的图片");
		}
			
		else 
		{
			//接收到文件也要判断是否是图片
			//获取文件后缀名
			String fileName = file.getOriginalFilename();
			String kzm =fileName.substring(fileName.lastIndexOf(".")); 
			
			 if (!((".jpg").equals(kzm)||(".JPG").equals(kzm)||
					 (".jpeg").equals(kzm)||(".JPEG").equals(kzm)||
	                    (".PNG").equals(kzm)||(".png").equals(kzm)))
			 {
				 LOGGER.error("传输的文件不是图片或不支持该格式的图片");
				 throw new NotIsImageException("传输的文件不是图片或不支持该格式的图片");
	         }
			 else
			 {
				 //如果是图片，判断图片大小，不能超过50MB，图片大小限制可以在application.yml中设置
				 //System.out.println(file.getSize());
				 if (file.getSize() > 50*1024*1024)
				 {
					 LOGGER.error("传输的图片太大");
					 throw new ImageSizeLimitException("传输的图片太大");
				 }
					 
			 }
	     }

		
		String queryImageFile="";
		try {
				String fileName = file.getOriginalFilename();
				//根据uuid写文件名，保证不重复
				 String uuid = UUID.randomUUID().toString().replace("-", ""); 
				 String kzm =fileName.substring(fileName.lastIndexOf(".")); 
				 String filename = uuid + kzm;
				 //queryImagePath="/root/image_retrieval_vgg16/queryimage/"+filename;
				 queryImageFile=queryImagePath+filename;
				//把上传的图片放在这个路径下面
				File file1 = new File(queryImageFile);
				//System.out.println(file1);
				OutputStream out = new FileOutputStream(file1);
				out.write(file.getBytes());
				out.close();
		} catch (Exception e) {

		}

		
		SimImageInfo simImageInfo=new SimImageInfo();
		try {
			long startTime = System.currentTimeMillis();
			//使用socket通信
			SocketSend socketSend=new SocketSend();
			simImageInfo=socketSend.getSimImages(queryImageFile,topn);
			long endTime = System.currentTimeMillis();
			//System.out.println();    //输出程序运行时间
			LOGGER.info("程序运行时间：" + (endTime - startTime) + "ms");
			//删除检索图片
			 File queryimage = new File(queryImageFile);
			 if (queryimage.exists() && queryimage.isFile()) {
		          if (queryimage.delete()) {
		                //System.out.println("删除成功！");  
		                LOGGER.info("待检索图片"+queryImageFile+"在本地目录删除成功");
		        }
			 }
		} catch (SocketFailureException e) {
			throw new SocketFailureException("socket通信失败，请检查图片检索服务是否开启以及socket端口占用情况");
		}

		//return urls;
		return simImageInfo;
	}

}