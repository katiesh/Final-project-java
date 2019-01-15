package util;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;

/**
 * provides programm's parsers
 * @author Kateryna Shkulova
 */
public class Parser {
    /**
     * field logger
     */
    private static final Logger logger = Logger.getLogger(Parser.class);
    /**
     * parse date from type {@link java.util.Date} to type {@link java.sql.Date}
     * @param date is a date with type {@link java.util.Date}
     * @return date with type {@link java.sql.Date}
     */
    public static Date parseDateFromUtilToSql(java.util.Date date){
        return new Date(date.getTime());
    }

    /**
     * parse date from string in format{dd.MM.yyyy} to date with type {@link java.util.Date}
     * @param date is a string with date
     * @return date with type {@link java.util.Date}
     * @throws ParseException
     */
    public static java.util.Date parseDateFromStringToUtil(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try{
            return simpleDateFormat.parse(date);
        } catch (ParseException|NullPointerException e){
            logger.error(e);
            return null;
        }
    }

    /**
     * parse date from string date to {@link java.sql.Date}
     * <p>
     *     first parse date from string to {@link java.util.Date} {@link #parseDateFromStringToUtil(String)}
     *     then to @link java.sql.Date} {@link #parseDateFromUtilToSql(java.util.Date)}
     * </p>
     * @param date
     * @return
     */
    public static Date parseDateFromStringToSql(String date){
        return parseDateFromUtilToSql(parseDateFromStringToUtil(date));
    }

    /**
     * parse list to string
     * <p>parse list to string in format{(item1, item2, item3}
     * when list is empty then string will be null </p>
     * @param list is a list which will be parsed
     * @return resulted string
     */
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
