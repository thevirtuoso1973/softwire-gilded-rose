package com.gildedrose

class GildedRose(var items: Array<Item>, private val rate: Int = 1) {
    private fun isSulfuras(name: String): Boolean {
        return name.startsWith("Sulfuras")
    }

    private fun isAgedBrie(name: String): Boolean {
        return name.startsWith("Aged Brie")
    }

    private fun isBackstagePass(name: String): Boolean {
        return name.startsWith("Backstage passes")
    }

    private fun isConjured(name: String): Boolean {
        return name.startsWith("Conjured")
    }

    private fun newItemQuality(currQuality: Int, sellIn: Int): Int {
        val offset = if (sellIn < 0) 2 * rate else rate
        return maxOf(currQuality - offset, 0)
    }

    private fun newSulfurasQuality(currQuality: Int): Int {
        return currQuality
    }

    private fun newBrieQuality(currQuality: Int, sellIn: Int): Int {
        val offset = if (sellIn < 0) 2 * rate else rate
        return minOf(currQuality + offset, 50)
    }

    private fun newBackstagePassQuality(currQuality: Int, sellIn: Int): Int {
        return when {
            sellIn < 0 -> 0
            sellIn <= 5 -> minOf(currQuality + 3, 50)
            sellIn <= 10 -> minOf(currQuality + 2, 50)
            else -> minOf(currQuality + 1, 50)
        }
    }

    private fun newConjuredQuality(currQuality: Int, sellIn: Int): Int {
        val offset = if (sellIn < 0) 4 * rate else 2 * rate
        return minOf(currQuality - offset, 50)
    }

    fun updateQuality() {
        items.forEach { item ->
            when {
                isSulfuras(item.name) -> item.quality = newSulfurasQuality(item.quality)
                isAgedBrie(item.name) -> item.quality = newBrieQuality(item.quality, item.sellIn)
                isBackstagePass(item.name) -> item.quality = newBackstagePassQuality(item.quality, item.sellIn)
                isConjured(item.name) -> item.quality = newConjuredQuality(item.quality, item.sellIn)
                else -> item.quality = newItemQuality(item.quality, item.sellIn)
            }
            item.sellIn -= if (isSulfuras(item.name)) 0 else 1 // always decrease sellIn by one
        }
    }

    fun oldUpdateQuality() {
        //Decreasing quality before SellIn date is passed
        for (item in items) {
            when {
                item.name.startsWith("Sulfuras") -> {
                    item.quality = item.quality
                }
                item.name.startsWith("Aged Brie") -> {
                    item.quality = minOf(item.quality + rate, 50)
                }
                item.name.startsWith("Backstage passes") -> {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1
                    }
                    if (item.sellIn < 11) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1
                        }
                    }
                    if (item.sellIn < 6) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1
                        }
                    }
                    //Note:
                    //Rate of increase was not specified for other items, but it was for tickets
                }
                item.name.startsWith("Conjured") -> {
                    item.quality = maxOf(item.quality - 2 * rate, 0)
                }
                else -> {
                    item.quality = maxOf(item.quality - rate, 0)
                }


            }

            // Decreasing SellIn date except for Sulfuras
            if (!item.name.startsWith("Sulfuras")) {
                item.sellIn = item.sellIn - 1
            }

            //Extra decrease in quality after passing SellIn date
            if (item.sellIn < 0) {
                when {
                    item.name.startsWith("Sulfuras") -> {
                        item.quality = item.quality
                    }
                    item.name.startsWith("Aged Brie") -> {
                        item.quality = minOf(item.quality + rate, 50)
                    }
                    item.name.startsWith("Backstage passes") -> {
                        item.quality = 0
                    }
                    item.name.startsWith("Conjured") -> {
                        item.quality = maxOf(item.quality - 2 * rate, 0)
                    }
                    else -> {
                        item.quality = maxOf(item.quality - rate, 0)
                    }
                }
            }
        }
    }

}

