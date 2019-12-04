package Sr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SrManager {

    private int numPassanger = 1;
    private String from = "수서";
    private String to = "천안아산";
    private String date_YYYYMMDD = "20191205";
    private String time_HHMMSS = "070000";
    private String[] checkTime_HH_MM = new String[] {
            "09:40",
            "07:00"
    };

    public void reservateSRT() {
        //String url = "https://etk.srail.co.kr/hpg/hra/01/selectScheduleList.do?dptRsStnCd=0030&arvRsStnCd=0551&stlbTrnClsfCd=05&psgNum=1&seatAttCd=015&isRequest=Y&dptRsStnCdNm=%EC%9D%B5%EC%82%B0&arvRsStnCdNm=%EC%88%98%EC%84%9C&dptDt=20191206&dptTm=160000&chtnDvCd=1&psgInfoPerPrnb1=1&psgInfoPerPrnb5=0&psgInfoPerPrnb4=0&psgInfoPerPrnb2=0&psgInfoPerPrnb3=0&locSeatAttCd1=000&rqSeatAttCd1=015&trnGpCd=300";
        Sr sr = new Sr();
        sr.setReservationInfo(numPassanger,from,to,date_YYYYMMDD,time_HHMMSS);
        String url = sr.getUrl();
        System.out.println(url);

        try {
            Document doc = null;

            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements tblElements = doc.select("tbody tr");

            for(Element item : tblElements) {
                if (isContains(item.text(), checkTime_HH_MM)) {
                    System.out.print("found*********************");

                    if(item.text().contains("예약하기")) {
                        System.out.print("예약가능!!!!!!!!!\n");
                        Toolkit.getDefaultToolkit().beep();
                    }
                    else {
                        System.out.print("예약불가상태\n");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getReqTimes() {
        StringBuilder timesBuilder = new StringBuilder();
        for(int i = 0; i < checkTime_HH_MM.length; i++) {
            timesBuilder.append(checkTime_HH_MM[i]);
            timesBuilder.append(",");
        }
        return timesBuilder.toString();
    }

    private boolean isContains(String item, String[] findTime) {
        boolean isContain = false;
        for(int i = 0; i < findTime.length; i++) {
            isContain |= item.contains(findTime[i]);
            if(item.contains(findTime[i]))
                System.out.println(findTime[i] + "예약가능,");
            if(isContain) {
                break;
            }
        }
        return isContain;
    }
}
