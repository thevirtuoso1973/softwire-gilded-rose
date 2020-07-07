package com.gildedrose

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        //Decreasing quality before SellIn date is passed
        for (i in items.indices) {
            when (items[i].name) {
                "Sulfuras, Hand of Ragnaros" -> {items[i].quality = items[i].quality}
                "Aged Brie" -> {if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1
                }}
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
                }
                else -> {if (items[i].quality > 0) {
                    items[i].quality = items[i].quality - 1
                }}
            }

            // Decreasing SellIn date except for Sulfuras
            if (items[i].name != "Sulfuras, Hand of Ragnaros") {
                items[i].sellIn = items[i].sellIn - 1
            }

            //Extra decrease in quality after passing SellIn date
            if (items[i].sellIn < 0) {
                when (items[i].name) {
                    "Aged Brie" -> {if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1
                    }}
                    "Backstage passes to a TAFKAL80ETC concert" -> {items[i].quality = 0}
                    "Sulfuras, Hand of Ragnaros" -> {items[i].quality = items[i].quality}
                    else -> {if (items[i].quality > 0) {
                        items[i].quality = items[i].quality - 1
                    }}
                }
            }
        }
    }

}

