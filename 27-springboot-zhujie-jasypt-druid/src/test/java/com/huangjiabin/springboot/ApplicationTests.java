package com.huangjiabin.springboot;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class ApplicationTests {
	@Autowired
	private StringEncryptor stringEncryptor;
	@Test
	void contextLoads() {
		//用jdk13，jdk8的话会报错提示JCE缺少的让你安装
		//加密
		String username = stringEncryptor.encrypt("root");
		System.out.println("username:"+username);
		//解密，在配置文件中这样写防止别人看到数据库密码啥的，解密写:
		// spring.datasource.username=ENC(Nvq13J34VccYl+4+WJUrlbg2ZhwWmy9tWwkcj6XVC9EJzknxeHqrHc5NCkSOzkid)
		//也可以以不写ENC，自己在配置文件中配置，前缀后缀
		String decusername=stringEncryptor.decrypt(username);
		System.out.println(decusername);


		String password = stringEncryptor.encrypt("1220.dbggl.h");
		System.out.println("password:"+password);
		String decpassword=stringEncryptor.decrypt(password);
		System.out.println(decpassword);

	}

}
