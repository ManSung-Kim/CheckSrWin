package Sr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;

public class Sr {


    // reference
//    dptRsStnCd: 0030
//    arvRsStnCd: 0551
//    stlbTrnClsfCd: 05
//    psgNum: 2
//    seatAttCd: 015
//    isRequest: Y
//    dptRsStnCdNm: 익산
//    arvRsStnCdNm: 수서
//    dptDt: 20191206
//    dptTm: 160000
//    chtnDvCd: 1
//    psgInfoPerPrnb1: 2
//    psgInfoPerPrnb5: 0
//    psgInfoPerPrnb4: 0
//    psgInfoPerPrnb2: 0
//    psgInfoPerPrnb3: 0
//    locSeatAttCd1: 000
//    rqSeatAttCd1: 015
//    trnGpCd: 300

    private final HashMap<String,String> stationCode = new HashMap<String,String>() {
        {
            put("익산", "0030");
            put("수서", "0551");
            put("천안아산", "0502");
            put("동탄", "0552");
            put("지제", "0553");
            put("오송", "0297");
            put("김천(구미)", "0507");
            put("김천구미", "0507");
            put("대구", "0015");
            put("신경주", "0508");
            put("울산(통도사)", "0509");
            put("부산", "0020");
            put("공주", "0514");
            put("정읍", "0033");
            put("광주송정", "0036");
            put("나주", "0037");
            put("목포", "0041");
        }
    };

    private String dptRsStnCd = "0030"; // 출발지 코드
    private String arvRsStnCd = "0551"; // 도착지 코드
    private final String stlbTrnClsfCd = "05";
    private String psgNum = "2"; // passanger no.
    private final String seatAttCd = "015";
    private final String isRequest = "Y";
    private String dptRsStnCdNm = "익산"; // from
    private String arvRsStnCdNm = "수서"; // to
    private String dptDt = "20191206"; // depature date(YYYYMMDD)
    private String dptTm = "160000"; // depature time(HHMMSS)
    private final String chtnDvCd = "1";
    private String psgInfoPerPrnb1 = "2"; // same psgNum
    private final String psgInfoPerPrnb5 = "0";
    private final String psgInfoPerPrnb4 = "0";
    private final String psgInfoPerPrnb2 = "0";
    private final String psgInfoPerPrnb3 = "0";
    private final String locSeatAttCd1 = "000";
    private final String rqSeatAttCd1 = "015";
    private final String trnGpCd = "300";

    public void setReservationInfo(int numPassanger, String from, String to, String YYYYMMDD, String HHMMSS) {
        dptRsStnCd = stationCode.get(from);
        arvRsStnCd = stationCode.get(to);
        psgNum = numPassanger + "";
        psgInfoPerPrnb1 = psgNum;
        dptRsStnCdNm = convertToSerializedString(from);
        arvRsStnCdNm = convertToSerializedString(to);
        dptDt = YYYYMMDD;
        dptTm = HHMMSS;
    }

    private String convertToSerializedString(String text) {
        StringBuilder serializedStringBuilder = new StringBuilder();
        try {
            byte[] byteSreialized = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(text);
            byteSreialized = baos.toByteArray();
            String encoded = Base64.getEncoder().encodeToString(byteSreialized);

            // idx 0~6은 string object 및 length에 대한 정보
            for(int i = 7; i < byteSreialized.length; i++) {
                ///System.out.println("BBBBBBB " + "%" + String.format("%02x ", byteSreialized[i] & 0xff).toUpperCase());
                serializedStringBuilder.append("%");
                serializedStringBuilder.append(String.format("%02x", byteSreialized[i] & 0xff).toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serializedStringBuilder.toString();
    }


    public String getUrl() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://etk.srail.co.kr/hpg/hra/01/selectScheduleList.do?")
                .append("dptRsStnCd=").append(dptRsStnCd).append("&")
                .append("arvRsStnCd=").append(arvRsStnCd).append("&")
                .append("stlbTrnClsfCd=").append(stlbTrnClsfCd).append("&")
                .append("psgNum=").append(psgNum).append("&")
                .append("seatAttCd=").append(seatAttCd).append("&")
                .append("isRequest=").append(isRequest).append("&")
                .append("dptRsStnCdNm=").append(dptRsStnCdNm).append("&")
                .append("arvRsStnCdNm=").append(arvRsStnCdNm).append("&")
                .append("dptDt=").append(dptDt).append("&")
                .append("dptTm=").append(dptTm).append("&")
                .append("chtnDvCd=").append(chtnDvCd).append("&")
                .append("psgInfoPerPrnb1=").append(psgInfoPerPrnb1).append("&")
                .append("psgInfoPerPrnb5=").append(psgInfoPerPrnb5).append("&")
                .append("psgInfoPerPrnb4=").append(psgInfoPerPrnb4).append("&")
                .append("psgInfoPerPrnb2=").append(psgInfoPerPrnb2).append("&")
                .append("psgInfoPerPrnb3=").append(psgInfoPerPrnb3).append("&")
                .append("locSeatAttCd1=").append(locSeatAttCd1).append("&")
                .append("rqSeatAttCd1=").append(rqSeatAttCd1).append("&")
                .append("trnGpCd=").append(trnGpCd).append("&");
        return urlBuilder.toString();
    }
}
