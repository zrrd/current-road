package cn.leran.currentroad;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
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
    int a = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
    System.out.println(a);
  }

  @Test
  public void dateTest() {
    String date = "2018-2-28";
    boolean success;
    try {
      DateUtils.parseDateStrictly(date, "yyyy-MM-dd", "yyyy/MM/dd");
      System.out.println(true);
    } catch (ParseException e) {
      System.out.println(false);
    }
  }

}
