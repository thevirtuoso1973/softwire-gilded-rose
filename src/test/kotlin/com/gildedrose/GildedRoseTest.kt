package com.gildedrose

import org.junit.Assert.assertEquals
import org.junit.Test


/*
    For Chris:
    1. Once the sell by date has passed, Quality degrades twice as fast
    2. The Quality of an item is never negative
    3. “Aged Brie” actually increases in Quality the older it gets

    TODO:
     - automate/parameterize it somehow?
     - add conjure support (last bullet point)
     - add rate attribute to GildedRose (avoid literal)
 */


class GildedRoseTest {
    private val testItems = arrayOf(Item("foo", -1, 4))

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
        val qualityRate = 1
        for (item in testItems) {
            if (
                    item.sellIn < 0
                    && !item.name.startsWith("Aged Brie")
                    && !item.name.startsWith("Sulfuras")
            ) {
                val initQuality = item.quality
                val app = GildedRose(arrayOf(item), qualityRate)
                app.updateQuality()
                assertEquals(initQuality-(2*qualityRate), app.items[0].quality)
            }
        }
    }

    @Test fun nonNegativeQuality() {
        val qualityRate = 1
        for (item in testItems) {
            val app = GildedRose(arrayOf(item), qualityRate)
            app.updateQuality()
            assert(app.items[0].quality >= 0)
        }
    }

    @Test fun agedBrieQualityIncrease() {
        val name = "Aged Brie"; val daysLeft = 2; val initQuality = 1
        val items = arrayOf<Item>(Item(name, daysLeft, initQuality))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(initQuality+1, app.items[0].quality)
    }

}

