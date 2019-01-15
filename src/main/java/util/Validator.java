package util;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * validate data
 * @author Kateryna Shkulova
 */
public class Validator {
    private static final Logger logger = Logger.getLogger(Validator.class);
    /**
     * field regular expression for name and surname
     */
    private static final String NAME_SURNAME = "^[A-Z\u0410-\u042F][\u0430-\u044F\u0410-\u042Fa-zA-Z \\-]{1,31}$";
    /**
     * field regular expression for telephone number
     */
    private static final String TEL_NUMBER = "^[+][1-9][0-9]{11}$";
    /**
     * field regular expression for email
     */
    private static final String EMAIL = "^[A-Za-z1-9. _]{1,20}[@][A-Za-z_]{1,9}[.]{1}[A-Za-z]{1,3}$";
    /**
     * field regular expression for class of room
     */
    private static final String CLASS_OF_ROOM = "^[A-Za-z][a-zA-Z+ ]{1,31}$";
    /**
     * field regular expression for number of places
     */
    private static final String NUM_OF_PLACES = "^[1-8]$";

    /**
     * validate name or surname
     * @param name is a sting which should be validated
     * @return true if input is correct
     *  false if input is not correct
     */
    public static boolean isCorrectNameSurname(String name) {
        return name.matches(NAME_SURNAME);
    }

    /**
     * validate telephone number
     * @param number is a sting which should be validated
     * @return true if input is correct
     *  false if input is not correct
     */
    public static boolean isCorrectTelNumber(String number) {
        return number.matches(TEL_NUMBER);
    }

    /**
     * validate email
     * @param email is a sting which should be validated
     * @return true if input is correct
     *  false if input is not correct
     */
    public static boolean isCorrectEmail(String email) {
        return email.matches(EMAIL);
    }
    /**
     * validate class of room
     * @param classOfRoom is a sting which should be validated
     * @return true if input is correct
     *  false if input is not correct
     */
    public static boolean isCorrectClassOfRoom(String classOfRoom) {
        return classOfRoom.matches(CLASS_OF_ROOM);
    }

//    public static boolean isCorrectStatus(String status){
//        return status.matches(STATUS);
//    }
    /**
     * validate number of places
     * @param numOfPlaces is a sting which should be validated
     * @return true if input is correct
     *  false if input is not correct
     */
    public static boolean isCorrectNumOfPlaces(String numOfPlaces) {
        return numOfPlaces.matches(NUM_OF_PLACES);
    }

//    public static boolean isCorrectNumOfPlaces(int numOfPlaces){
//        return (numOfPlaces > 0 && numOfPlaces < 6);
//    }

//    public static boolean isCorrectPrice(double price){
//        return (price > 1 && price < 1000000);
//    }

    /**
     * validate date
     * <p>
     *     parse date to {@link Date} {@link Parser#parseDateFromStringToUtil(String)}
     *     check is the date after today and before (date+one year)
     * </p>
     * @param strDate is a string which should be validated
     * @return true if input is correct
     *  false if input is not correct
     */
    public static boolean isCorrectDate(String strDate) {
        Date date = Parser.parseDateFromStringToUtil(strDate);
        if(date!=null) {
            long oneYear = 365 * 24 * 60 * 60 * 1000L;
            Date maxDate = new Date(new Date().getTime() + oneYear);
            if (date.compareTo(new Date()) >= 0 && date.compareTo(maxDate) < 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    /**
     * validate date
     * <p>
     *     parse date to {@link Date} {@link Parser#parseDateFromStringToUtil(String)}
     *     check is the date after definite date and before (date+one year)
     * </p>
     * @param strDateTo is a string date after which should be validated date
     * @param strDateTo is a string which should be validated
     * @return true if input is correct
     *  false if input is not correct
     */
    public static boolean isCorrectDate(String strDateFrom, String strDateTo) {
        Date dateFrom = Parser.parseDateFromStringToUtil(strDateFrom);
        Date dateTo = Parser.parseDateFromStringToUtil(strDateTo);
        if (dateFrom != null && dateTo != null) {
            long oneYear = 365 * 24 * 60 * 60 * 1000L;
            Date maxDate = new Date(new Date().getTime() + oneYear);
            if (dateTo.after(dateFrom) && dateTo.compareTo(maxDate) < 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * validate integer
     * @param num is a string number
     * @return true if string is can be parsed to int  that more than 0
     * false if  it is impossible
     */
    public static boolean isCorrectInt(String num) {
        try {
            return Integer.parseInt(num)>0;
        } catch (NumberFormatException e) {
            logger.error(e);
            return false;
        }
    }
    /**
     * validate integer
     * @param num is a Object {@link Object}
     * @return true if object can be cast to int  that more than 0
     * false if  it is impossible
     */
    public static boolean isCorrectInt(Object num){
        try{
            int intNum = (Integer)num;
            return intNum>0;
        }catch (ClassCastException e){
            logger.error(e);
            return false;
        }
    }

    /**
     * validate double
     * @param num is a string number
     * @return true if string is can be parsed to double that more than 0
     * false if  it is impossible
     */
    public static boolean isCorrectDouble(String num) {
        try {
            return Double.parseDouble(num)>0;
        } catch (NumberFormatException e) {
            logger.error(e);
            return false;
        }
    }

}
