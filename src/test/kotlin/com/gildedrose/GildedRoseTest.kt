package com.gildedrose

import org.junit.Assert.assertEquals
import org.junit.Test


/*
    For Chris:
    - Once the sell by date has passed, Quality degrades twice as fast
    - The Quality of an item is never negative
    - “Aged Brie” actually increases in Quality the older it gets
    TODO: automate/parameterize it?
 */


class GildedRoseTest {

    @Test fun foo() {
        val items = arrayOf<Item>(Item("foo", 0, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals("foo", app.items[0].name)
    }

    @Test fun trivial() {
        val name = "bar"; val daysLeft = 1; val initQuality = 1
        val items = arrayOf<Item>(Item(name, daysLeft, initQuality))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(name, app.items[0].name)
        assertEquals(daysLeft-1, app.items[0].sellIn)
        assertEquals(initQuality-1, app.items[0].quality)
    }

    @Test fun `if sell by date passed, then degrades twice the rate`() {
        val name = "foo"; val daysLeft = 0; val initQuality = 4
        val items = arrayOf<Item>(Item(name, daysLeft, initQuality))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(initQuality-2, app.items[0].quality)
    }

    @Test fun nonNegativeQuality() {
        val name = "foo"; val daysLeft = 2; val initQuality = 0
        val items = arrayOf<Item>(Item(name, daysLeft, initQuality))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(0, app.items[0].quality)
    }

    @Test fun agedBrieQualityIncrease() {
        val name = "Aged Brie"; val daysLeft = 2; val initQuality = 1
        val items = arrayOf<Item>(Item(name, daysLeft, initQuality))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(initQuality+1, app.items[0].quality)
    }

}

