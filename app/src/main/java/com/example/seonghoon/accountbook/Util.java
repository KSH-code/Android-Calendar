package com.example.seonghoon.accountbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by seonghoon.kim on 2017-09-15.
 */

class Util {
    private static final Util ourInstance = new Util();

    private Util() {
    }

    static Util getInstance() {
        return ourInstance;
    }

    /**
     * time(ms)를 yyyyMM으로 바꿔서 출력해준다.
     *
     * @param time
     * @return yyyyMM
     */
    public String getDefaultDateFormat(long time) {
        return getDateFormat(time, "yyyyMM");
    }

    /**
     * time(ms)를 format으로 바꿔서 출력해준다.
     *
     * @param time
     * @return format
     */
    public String getDateFormat(long time, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date date = new Date(time);
        return df.format(date);
    }

}
