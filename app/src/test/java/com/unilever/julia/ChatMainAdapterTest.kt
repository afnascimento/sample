package com.unilever.julia

import org.junit.Test

class ChatMainAdapterTest {

    var mDataSet = arrayListOf("LOAD")

    @Test
    fun addItems_Test() {
        var value = "Item1"
        addItem(value)
        assert((mDataSet.size == 2) && (mDataSet[1] == value))

        value = "Item2"
        addItem(value)
        assert((mDataSet.size == 3) && (mDataSet[1] == value))
    }

    fun addItem(item: String) {
        if (mDataSet.size == 1) {
            mDataSet.add(item)
        } else {
            mDataSet.add(1, item)
        }
        val position = mDataSet.indexOf(item)
        System.out.println("addItem $position - $item")
    }
}