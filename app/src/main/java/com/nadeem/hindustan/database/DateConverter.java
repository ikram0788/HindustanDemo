package com.nadeem.hindustan.database;

/**
 * Created by ikram on 19/2/18.
 */

public class DateConverter {
    /*@TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromDestinationList(List<Destination> list) {

        if (list == null)
            return null;

        //Destination lang = list.get(0);
        //return list.isEmpty() ? null : new Gson().toJson(lang);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Destination>>() {
        }.getType();
        String json = gson.toJson(list, type);
        return json;
    }

    @TypeConverter
    public static List<Destination> toDestinationList(String destinationListString) {
        *//*Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(country, listType);*//*
        if (destinationListString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Destination>>() {
        }.getType();
        List<Destination> destinationList = gson.fromJson(destinationListString, type);
        return destinationList;
    }*/
}
