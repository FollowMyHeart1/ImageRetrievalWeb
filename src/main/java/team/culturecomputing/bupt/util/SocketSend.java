package team.culturecomputing.bupt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import team.culturecomputing.bupt.dao.SimImageInfo;
import team.culturecomputing.bupt.exception.SocketFailureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketSend {
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketSend.class);
	
	@Value("${sokcetPort}")
	private int socketPort;
	
	/**
	* 判断是否断开连接，断开返回true,没有返回false
	* @param socket
	* @return
	*/ 
	
	public SimImageInfo getSimImages(String queryimagepath, int topn) {
		SimImageInfo simImageInfo=new SimImageInfo();
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String host=addr.getHostName();
            //String ip=addr.getHostAddress().toString(); //获取本机ip
 
            // 初始化套接字，设置访问服务的主机和进程端口号，HOST是访问python进程的主机名称，可以是IP地址或者域名，PORT是python进程绑定的端口号
            Socket socket = new Socket(host,12345);
 
            // 获取输出流对象
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os);
            // 发送内容
            out.print( queryimagepath+"返回数目"+topn);
            
            // 告诉服务进程，内容发送完毕，可以开始处理
            out.print("over");
            socket.shutdownOutput();//关闭输出流
            
            // 获取服务进程的输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
            String tmp = null;
            StringBuilder sb = new StringBuilder();
            // 读取内容
            while((tmp=br.readLine())!=null)
                sb.append(tmp).append('\n');
            //System.out.println("接收到"+sb.toString());
            socket.shutdownInput();
            try {
            	
            	simImageInfo  = JSON.parseObject(sb.toString(),SimImageInfo.class);
                //System.out.println(simImageInfo.getImagePath());

              } catch (Exception e) {
            	  LOGGER.error(e.toString()+",请检查socket服务是否开启及端口占用情况");

              }
            os.close();
            is.close();
            br.close();
            socket.close();
            
        } catch (IOException e) {
        	LOGGER.error(e.toString()+",socket通信失败，请检查图片检索服务是否开启以及socket端口占用情况");
            throw new SocketFailureException("socket通信失败，请检查图片检索服务是否开启以及socket端口占用情况");
            
        }finally {
            LOGGER.info("socket通信完毕");
        }
        
        return simImageInfo;
    }

}
