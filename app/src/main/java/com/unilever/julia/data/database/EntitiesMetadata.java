package com.unilever.julia.data.database;

public interface EntitiesMetadata {

    interface Customer {
        String tableName = "customer";
        String id = "_id";
        String code = "code";
        String territory = "territory";
        String name = "name";
        String state = "state";
        String city = "city";
        String district = "district";
        String textToFilter = "textToFilter";
    }

    interface Territory {
        String tableName = "territory";
        String id = "_id";
        String code = "code";
    }
}