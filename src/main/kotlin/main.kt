/*
Author: Michael Chand.
*/
fun main(args: Array<String>) {
    val input = getValidatedInput(args)
    if(input != null) {
        val packs = createPack(input.getConstraint(), input.getItems())
        val display = Display(packs)
        display.output()
    }
}

fun getValidatedInput(args: Array<String>) : InputValidation? {
    lateinit var inputArray: Array<String>
    if(args.isNotEmpty()) {
        inputArray = args
    }
    else {
        val scannedInputList : MutableList<String> = mutableListOf()
        var read = true
        while(read){
            val line = readLine()
            if(!line.isNullOrBlank())
                scannedInputList.add(line)
            else
                read = false
        }
        inputArray = scannedInputList.toTypedArray()
    }
    if(inputArray.isEmpty())
        return null
    return InputValidation(inputArray)
}

fun createPack(constraint: Constraint, items: Array<Item>) : MutableList<Pack> {
    return when(constraint.sortOrder) {
        SortOrder.NATURAL -> naturalSorting(constraint, items)
        SortOrder.SHORT_TO_LONG -> shortToLongSorting(constraint, items)
        SortOrder.LONG_TO_SHORT -> longToShortSorting(constraint, items)
    }
}

// Keeps the original order of items when packing.
fun naturalSorting(constraint: Constraint, items: Array<Item>) : MutableList<Pack>  {
    val packs : MutableList<Pack> = mutableListOf()
    var pack = Pack()
    for(item in items) {
        val curItem = item.copy()
        while (curItem.quantity > 0) {
            if (pack.totalItems < constraint.maxItems && (pack.totalWeight + curItem.weight) < constraint.maxWeight) {
                if(!pack.itemMap.containsKey(curItem.id)) {
                    val newItem = curItem.copy()
                    newItem.quantity = 0
                    pack.itemMap[curItem.id] = newItem
                }
                pack.itemMap[curItem.id]!!.quantity++
                pack.totalItems++
                pack.totalWeight += curItem.weight
                pack.length = pack.length.coerceAtLeast(curItem.length)
                curItem.quantity--
            }
            else {
                pack.id = packs.size+1
                packs.add(pack)
                pack = Pack()
            }
        }
    }
    // Save the last pack, if any, which may only be partially full but no more items are left to pack.
    if(pack.totalItems > 0) {
        pack.id = packs.size+1
        packs.add(pack)
    }
    return packs
}

fun shortToLongSorting(constraint: Constraint, items: Array<Item>) : MutableList<Pack> {
    items.sortBy { item -> item.length }
    return naturalSorting(constraint, items)
}

fun longToShortSorting(constraint: Constraint, items: Array<Item>) : MutableList<Pack> {
    items.sortByDescending { item -> item.length }
    return naturalSorting(constraint, items)
}