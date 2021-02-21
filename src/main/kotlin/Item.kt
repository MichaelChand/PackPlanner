class Item {
    var id: Int = 0
    var length: Int = 0
    var quantity: Int = 0
    var weight: Double = 0.0

    fun copy() : Item {
        var newItem = Item()
        newItem.id = this.id
        newItem.length = this.length
        newItem.quantity = this.quantity
        newItem.weight = this.weight

        return newItem
    }
}