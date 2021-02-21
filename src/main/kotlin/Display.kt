/*
Author: Michael Chand
 */

class Display(packList: MutableList<Pack>) {
    var packs = packList

    fun output() {
        for(pack in packs) {
            displayPackNumber(pack)

            for(item in pack.itemMap) {
                print("${item.value.id},")
                print("${item.value.length},")
                print("${item.value.quantity},")
                println("${item.value.weight}")
            }
            displayPackInfo(pack)
        }
    }

    private fun displayPackNumber(pack: Pack) {
        println("Pack Number: ${pack.id}")
    }

    private fun displayPackInfo(pack: Pack) {
        println("Pack Length: ${pack.length}, Pack Weight: ${String.format("%.3f", pack.totalWeight).toDouble()}")
        println("")
    }
}