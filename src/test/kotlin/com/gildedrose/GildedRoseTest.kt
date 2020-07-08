package com.gildedrose

import org.junit.Assert.assertEquals
import org.junit.Test

class GildedRoseTest {
    private val testItems = arrayOf(
            Item("foo", -1, 4),
            Item("+5 Dexterity Vest", 10, 20), //
            Item("Aged Brie", 2, 0), //
            Item("Elixir of the Mongoose", 5, 7), //
            Item("Sulfuras, Hand of Ragnaros", 0, 50), //
            Item("Sulfuras, Hand of Ragnaros", -1, 50),
            Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            Item("Conjured Mana Cake", 3, 6),
            Item("Conjured Mana Cake", -1, 6)
    )

    //SellIn Tests:

    @Test
    fun sellInDecrease() {
        val qualityRate = 1
        for (item in testItems) {
            val app = GildedRose(arrayOf(item), qualityRate)
            val initSellIn = item.sellIn
            app.updateQuality()
            if (!item.name.startsWith("Sulfuras"))
            assertEquals(app.items[0].sellIn,initSellIn-1)
        }
    }

    //Quality Bound Tests:

    @Test
    fun nonNegativeQuality() {
        val qualityRate = 1
        for (item in testItems) {
            val app = GildedRose(arrayOf(item), qualityRate)
            app.updateQuality()
            assert(app.items[0].quality >= 0)
        }
    }

    @Test
    fun maxQuality50() {
        val qualityRate = 1
        for (item in testItems) {
            val app = GildedRose(arrayOf(item), qualityRate)
            app.updateQuality()
            assert(app.items[0].quality <= 50)
        }
    }

    //General Item Quality Decrease Tests

    @Test
    fun generalQualityDecreasePreSellIn() {
        val qualityRate = 1
        for (item in testItems) {
            if (
                    item.sellIn > 0
                    && !item.name.startsWith("Aged Brie")
                    && !item.name.startsWith("Sulfuras")
                    && !item.name.startsWith("Backstage")
                    && !item.name.startsWith("Conjured")
            ) {
                val initQuality = item.quality
                val app = GildedRose(arrayOf(item), qualityRate)
                app.updateQuality()
                assertEquals(maxOf(initQuality-qualityRate,0), app.items[0].quality)
            }
        }
    }

    @Test
    fun generalQualityDecreasePostSellIn() {
        val qualityRate = 1
        for (item in testItems) {
            if (
                    item.sellIn <= 0
                    && !item.name.startsWith("Aged Brie")
                    && !item.name.startsWith("Sulfuras")
                    && !item.name.startsWith("Backstage")
                    && !item.name.startsWith("Conjured")
            ) {
                val initQuality = item.quality
                val app = GildedRose(arrayOf(item), qualityRate)
                app.updateQuality()
                assertEquals(maxOf(initQuality-2*qualityRate,0), app.items[0].quality)
            }
        }
    }

    //Aged Brie Tests:

    @Test
    fun agedBrieQualityIncreasePostSellIn() {
        val qualityRate = 1
        for (item in testItems) {
            if (item.name.startsWith("Aged Brie")) {
                val initQuality = item.quality
                val app = GildedRose(arrayOf(item), qualityRate)
                app.updateQuality()
                if (item.sellIn < 0) {
                    assertEquals(minOf(initQuality+(2*qualityRate),50), app.items[0].quality)
                }
            }
        }
    }

    @Test
    fun agedBrieQualityIncreasePreSellIn() {
        val qualityRate = 1
        for (item in testItems) {
            if (item.name.startsWith("Aged Brie")) {
                val initQuality = item.quality
                val app = GildedRose(arrayOf(item), qualityRate)
                app.updateQuality()
                if (item.sellIn >= 0) {
                    assertEquals(minOf(initQuality+qualityRate,50), app.items[0].quality)
                }
            }
        }
    }

    //Sulfuras Tests:

    @Test
    fun sulfurasQualityNoChange() {
        val qualityRate = 1
        for (item in testItems) {
            if (item.name.startsWith("Sulfuras")) {
                val initQuality = item.quality
                val app = GildedRose(arrayOf(item),qualityRate)
                app.updateQuality()
                assert(app.items[0].quality == initQuality)
            }
        }
    }

    @Test
    fun sulfurasSellInNoChange() {
        val qualityRate = 1
        for (item in testItems) {
            if (item.name.startsWith("Sulfuras")) {
                val initSellIn = item.sellIn
                val app = GildedRose(arrayOf(item),qualityRate)
                app.updateQuality()
                assert(app.items[0].sellIn == initSellIn)
            }
        }
    }

    //Backstage Passes Tests:

    @Test
    fun backstagePassQualityWhenSellInMoreThan10Days() {
        val qualityRate = 1
        for (item in testItems) {
            if (item.name.startsWith("Backstage")) {
                val initQuality = item.quality
                val initSellin = item.sellIn
                if (initSellin > 10) {
                    val app = GildedRose(arrayOf(item),qualityRate)
                    app.updateQuality()
                    assertEquals(app.items[0].quality,minOf(initQuality+1,50))
                }
            }
        }
    }

    @Test
    fun backstagePassQualityWhenSellInBetween5to10Days() {
        val qualityRate = 1
        for (item in testItems) {
            if (item.name.startsWith("Backstage")) {
                val initQuality = item.quality
                val initSellin = item.sellIn
                if (initSellin in 5..10) {
                    val app = GildedRose(arrayOf(item),qualityRate)
                    app.updateQuality()
                    assertEquals(app.items[0].quality, minOf(initQuality + 2,50))
                }
            }
        }
    }

    @Test
    fun backstagePassQualityWhenSellInBetween0to5Days() {
        val qualityRate = 1
        for (item in testItems) {
            if (item.name.startsWith("Backstage")) {
                val initQuality = item.quality
                val initSellin = item.sellIn
                val app = GildedRose(arrayOf(item),qualityRate)
                app.updateQuality()
                if (initSellin in 0..5) {
                    assertEquals(app.items[0].quality, minOf(initQuality + 3,50))
                }
            }
        }
    }

    @Test
    fun backstagePassQualityWhenPostSellIn() {
        val qualityRate = 1
        for (item in testItems) {
            if (item.name.startsWith("Backstage")) {
                val app = GildedRose(arrayOf(item),qualityRate)
                app.updateQuality()
                if (app.items[0].sellIn < 0) {
                    assertEquals(app.items[0].quality,0)
                }
            }
        }
    }

    //Conjured Item Tests:

    @Test
    fun conjuredQualityPreSellIn() {
        val qualityRate = 1
        for (item in testItems) {
            if (item.name.startsWith("Conjured")) {
                val initQuality = item.quality
                val app = GildedRose(arrayOf(item),qualityRate)
                app.updateQuality()
                if (app.items[0].sellIn >= 0) {
                    assertEquals(app.items[0].quality, maxOf(initQuality - (2*qualityRate),0))
                }
            }
        }
    }

    @Test
    fun conjuredQualityPostSellIn() {
        val qualityRate = 1
        for (item in testItems) {
            if (item.name.startsWith("Conjured")) {
                val initQuality = item.quality
                val app = GildedRose(arrayOf(item),qualityRate)
                app.updateQuality()
                if (app.items[0].sellIn < 0) {
                    assertEquals(app.items[0].quality, maxOf(initQuality - (4*qualityRate),0))
                }
            }
        }
    }

}

