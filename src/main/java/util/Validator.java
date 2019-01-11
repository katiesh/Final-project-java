package util;

import java.text.ParseException;
import java.util.Date;

public class Validator {
    private static final String NAME_SURNAME = "^[A-Z][a-zA-Z- ]{1,31}$";
    private static final String TEL_NUMBER = "^[+][1-9]{1}[0-9]{11}$";
    private static final String EMAIL = "^[A-Za-z1-9. _]{1,20}[@][A-Za-z_]{1,9}[.]{1}[A-Za-z]{1,3}$";
    private static final String CLASS_OF_ROOM = "^[A-Za-z][a-zA-Z ]{1,31}$";
    private static final String NUM_OF_PLACES = "^[1-8]$";
    private static final String STATUS = "^[A-Za-z][a-zA-Z ]{1,31}$";
    private static final String DATE = "^[0-9]{1,2}[.][0-9][0-2][.][2][0][1][8-9]$";


    public static boolean isCorrectNameSurname (String name){
        return name.matches(NAME_SURNAME);
    }

    public static boolean isCorrectTelNumber (String number){
        return number.matches(TEL_NUMBER);
    }

    public static boolean isCorrectEmail(String email){
        return email.matches(EMAIL);
    }

    public static boolean isCorrectClassOfRoom(String classOfRoom){
        return classOfRoom.matches(CLASS_OF_ROOM);
    }

    public static boolean isCorrectStatus(String status){
        return status.matches(STATUS);
    }

    public static boolean isCorrectNumOfPlaces(String numOfPlaces){
        return numOfPlaces.matches(NUM_OF_PLACES);
    }

    public static boolean isCorrectNumOfPlaces(int numOfPlaces){
        return (numOfPlaces > 0 && numOfPlaces < 6);
    }

    public static boolean isCorrectPrice(double price){
        return (price > 1 && price < 1000000);
    }

    public static Date isCorrectDate(String strDate){
        try {
            Date date = Parser.parseDateFromStringToUtil(strDate);
            //???????? ? ??????????? ????????? ?????
            long oneYear = 365*24*60*60*1000L;
            Date maxDate = new Date(new Date().getTime() + oneYear);
            if(date.compareTo(new Date()) >= 0 && date.compareTo(maxDate)<0){
                return date;
            } else{
                return null;
            }
        }catch (ParseException e){
            return null;
        }
    }

    public static Date isCorrectDate(String strDateFrom, String strDateTo){
        try {
            Date dateFrom = Parser.parseDateFromStringToUtil(strDateFrom);
            Date dateTo = Parser.parseDateFromStringToUtil(strDateTo);
            long oneYear = 365*24*60*60*1000L;
            Date maxDate = new Date(new Date().getTime() + oneYear);
            if(dateTo.after(dateFrom) && dateTo.compareTo(maxDate)<0){
            return dateTo;
            } else{
                return null;
            }
        }catch (ParseException e){
            return null;
        }
    }

    public static boolean isCorrectInt(String num){
        try{
            Integer.parseInt(num);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
