package com.gildedrose

import kotlin.concurrent.fixedRateTimer
import kotlin.math.max

class GildedRose(var items: Array<Item>, val rate: Int = 1) {

    fun updateQuality() {
        //Decreasing quality before SellIn date is passed
        for (i in items.indices) {
            when (items[i].name) {
                "Sulfuras, Hand of Ragnaros" -> {
                    items[i].quality = items[i].quality
                }
                "Aged Brie" -> {
                    items[i].quality = maxOf(items[i].quality + rate,50)
                }
                "Backstage passes to a TAFKAL80ETC concert" -> {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1
                    }
                    if (items[i].sellIn < 11) {
                        if (items[i].quality < 50) {
                            items[i].quality = items[i].quality + 1
                        }
                    }
                    if (items[i].sellIn < 6) {
                        if (items[i].quality < 50) {
                            items[i].quality = items[i].quality + 1
                        }
                    }
                    //Note:
                    //Rate of increase was not specified for other items, but it was for tickets
                }
                "Conjured Mana Cake" -> {
                    items[i].quality = minOf(items[i].quality - 2*rate,0)
                }
                else -> {
                    items[i].quality = minOf(items[i].quality - rate,0)
                }


            }

            // Decreasing SellIn date except for Sulfuras
            if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                items[i].sellIn = items[i].sellIn - 1
            }

            //Extra decrease in quality after passing SellIn date
            if (items[i].sellIn < 0) {
                when (items[i].name) {
                    "Sulfuras, Hand of Ragnaros" -> {
                        items[i].quality = items[i].quality
                    }
                    "Aged Brie" -> {
                        items[i].quality = maxOf(items[i].quality + rate, 50)
                    }
                    "Backstage passes to a TAFKAL80ETC concert" -> {
                        items[i].quality = 0
                    }
                    "Conjured Mana Cake" -> {
                        items[i].quality = minOf(items[i].quality - 2 * rate, 0)
                    }
                    else -> {
                        items[i].quality = minOf(items[i].quality - rate, 0)
                    }
                }
            }
        }
    }

}

