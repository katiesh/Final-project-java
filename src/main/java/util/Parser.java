package util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;

public class Parser {
    public static Date parseDatefromUtilToSql(java.util.Date date){
        return new Date(date.getTime());
    }

    public static java.util.Date parseDateFromStringToUtil(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.parse(date);
    }

    public static Date parseDateFromStringToSql(String date) throws ParseException {
        return parseDatefromUtilToSql(parseDateFromStringToUtil(date));
    }

    public static String convertListToString (List list){
        StringBuffer convertedStr = new StringBuffer();
        convertedStr.append("(");
        ListIterator<Integer> listIterator = list.listIterator();
        if(list.size()!=0) {
            while (listIterator.hasNext()) {
                convertedStr.append(listIterator.next());
                if (listIterator.nextIndex() < list.size()) {
                    convertedStr.append(",");
                }
            }
        } else{
            return null;
        }
        convertedStr.append(")");
        return convertedStr.toString();
    }
}
