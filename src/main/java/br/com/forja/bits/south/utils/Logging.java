package br.com.forja.bits.south.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logging {

    private static Log log;

    public static Log getLog(){
        if (log == null)
            log = LogFactory.getLog(Log.class);

            return log;
    }


}
