package com.example.musicapp.models

import android.provider.BaseColumns

object EntityContract {
    const val DB_NAME = "db_example"
    const val DB_VERSION = 1

    // Table contents are grouped together in an anonymous object.
    object TaskEntry : BaseColumns {
        const val DB_TABLE = "my_table"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name" //fieldName
        const val COLUMN_QUANTITY = "quantity" //field2
        const val COLUMN_PRICE = "price" //field3
        const val COLUMN_STATUS = "status" //field4

    }
}