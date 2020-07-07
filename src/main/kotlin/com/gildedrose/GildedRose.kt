package com.gildedrose

class GildedRose(var items: Array<Item>, private val rate: Int = 1) {

    fun updateQuality() {
        //Decreasing quality before SellIn date is passed
        for (i in items.indices) {
            when {
                items[i].name.startsWith("Sulfuras") -> {
                    items[i].quality = items[i].quality
                }
                items[i].name.startsWith("Aged Brie") -> {
                    items[i].quality = minOf(items[i].quality + rate,50)
                }
                items[i].name.startsWith("Backstage passes") -> {
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
                items[i].name.startsWith("Conjured") -> {
                    items[i].quality = maxOf(items[i].quality - 2*rate,0)
                }
                else -> {
                    items[i].quality = maxOf(items[i].quality - rate,0)
                }


            }

            // Decreasing SellIn date except for Sulfuras
            if (!items[i].name.startsWith("Sulfuras")) {
                items[i].sellIn = items[i].sellIn - 1
            }

            //Extra decrease in quality after passing SellIn date
            if (items[i].sellIn < 0) {
                when {
                    items[i].name.startsWith("Sulfuras") -> {
                        items[i].quality = items[i].quality
                    }
                    items[i].name.startsWith("Aged Brie") -> {
                        items[i].quality = minOf(items[i].quality + rate, 50)
                    }
                    items[i].name.startsWith("Backstage passes") -> {
                        items[i].quality = 0
                    }
                    items[i].name.startsWith("Conjured") -> {
                        items[i].quality = maxOf(items[i].quality - 2 * rate, 0)
                    }
                    else -> {
                        items[i].quality = maxOf(items[i].quality - rate, 0)
                    }
                }
            }
        }
    }

}

