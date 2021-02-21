fun main(args: Array<String>) {
    var input = InputValidation(args)
    println("test Input:")
    for(item in input.getItems()) {
        print("${item.id},")
        print("${item.length},")
        print("${item.quantity},")
        println("${item.weight},")
    }
}