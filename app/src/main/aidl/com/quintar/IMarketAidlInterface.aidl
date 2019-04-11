// IMarketAidlInterface.aidl
package com.quintar;

// Declare any non-splash types here with import statements

interface IMarketAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    void pause();

    void destory();

    void send(String json,int type);

}
