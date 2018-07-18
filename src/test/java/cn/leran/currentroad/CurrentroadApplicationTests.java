package cn.leran.currentroad;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrentroadApplicationTests {

	@Test
	public void contextLoads() throws ParseException {
		Date date1 = new Date();
		//2017-11-5
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = dateFormat.parse("2017-11-5");
		int a = (int) ((date1.getTime() - date2.getTime()) / (1000*3600*24));
		System.out.println(a);
	}

}
