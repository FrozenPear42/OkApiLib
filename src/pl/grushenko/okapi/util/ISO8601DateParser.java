package pl.grushenko.okapi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ISO8601DateParser {

    public static Date parse( String input ) throws java.text.ParseException {

        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:sszzzzz");

        return df.parse( input );
        
    }

    public static String toString( Date date ) {
        
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:sszzzzz" );
  
        return df.format( date );        
    }

}
