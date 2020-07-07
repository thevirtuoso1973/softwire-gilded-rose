package com.gildedrose

import org.junit.Assert.*
import org.junit.Test

class GildedRoseTest {

    @Test fun foo() {
        val items = arrayOf<Item>(Item("foo", 0, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("foo", app.items[0].name)
    }

    @Test fun trivial() {
        val name = "bar"
        val daysleft = 1
        val initquality = 1
        val items = arrayOf<Item>(Item(name, daysleft, initquality))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(name, app.items[0].name)
        assertEquals(daysleft-1, app.items[0].sellIn)
        assertEquals(initquality-1, app.items[0].quality)
    }

}

