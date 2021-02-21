fun main(args: Array<String>) {
    var input = InputValidation(args)
    println("test Input:")
    println("${input.getConstraint().sortOrder},${input.getConstraint().maxItems},${input.getConstraint().maxWeight}")
    for(item in input.getItems()) {
        print("${item.id},")
        print("${item.length},")
        print("${item.quantity},")
        println("${item.weight},")
    }
}