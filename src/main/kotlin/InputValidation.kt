/*
Author: Michael Chand.
NOTE: variable "constraint" is always initialised before use due to class structure.
However this is not Thread safe and under threading, constraint could be accessed when null.
Therefore this is not a thread safe class.
 */
class InputValidation(args : Array<String>) {
    var input : Array<String> = args
    private lateinit var inputConstraint : Array<String>
    private lateinit var items : Array<Item>
    private var constraint: Constraint? = null


    init {
        validate()
    }

    private fun validate() {
        inputToArray(input)
    }

    fun getItems() : Array<Item> {
        return items
    }

    fun getConstraint() : Constraint {
        return constraint!!
    }

    private fun buildConstraint() {
        if(this.constraint == null) {
            this.constraint = Constraint()
            this.constraint!!.sortOrder = SortOrder.valueOf(inputConstraint[0].toUpperCase())
            this.constraint!!.maxItems = inputConstraint[1].toInt()
            this.constraint!!.maxWeight = inputConstraint[2].toDouble()
        }
    }

    // Check if any single item exceeds max weight for pack. Used for rejecting such an item as they cannot be part of
    // a back pack whose max weight is less than the items single piece weight.
    private fun getLegalWeightItems(item: Item) : Boolean {
        if(item.weight <= constraint!!.maxWeight)
            return true
        return false;
    }

    private fun inputToArray(inp : Array<String>)  {
        if(inp.size == 1) {
            //Everything arrived in a single line. Have to convert back to multi-line array first then call this again.
            inputToArray(flatToArray(inp[0]))
        }
        else {
            items = Array<Item>(inp.size-1){ Item() }
            for(i in 0 until inp.size) {
                if(i == 0) {
                    inputConstraint = inp[0].split(",").toTypedArray()
                    buildConstraint()
                }
                else {
                    var inputItems = inp[i].split(",").toTypedArray()
                    var item = Item()
                    item.id = inputItems[0].toInt()
                    item.length = inputItems[1].toInt()
                    item.quantity = inputItems[2].toInt()
                    item.weight = inputItems[3].toDouble()
                    if(getLegalWeightItems(item))
                        items[i-1] = item
                }
            }
        }
    }

    private fun flatToArray(inp : String): Array<String> {
        var inpArray = inp.split(",")
        var inputCriteria: String = inpArray[0]+","+ inpArray[1]+","+inpArray[2]
        var inputItems = Array<String>(1+(inpArray.size - 3)/4) { "it = $it" }
        var k: Int = 0
        inputItems[k++] = inputCriteria
        for(i in 3 until inpArray.size step 4) {
            var line : String = ""
            for(j in i until i+4) {
                line += inpArray[j] + if(j < i+3) "," else ""
            }
            inputItems[k++] = line
        }
        return inputItems
    }
}