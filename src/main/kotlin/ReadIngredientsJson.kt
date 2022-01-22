import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

class ReadIngredientsJson {

    companion object {
        const val JSON_PATH = "data/ingredients.json"
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
    }

}

fun main() {
    println("hi there")


    val ingredients = IngredientsJsonImporter().readIngredientsJson()
//    val ingredients = objectMapper.readValue(File("data/ingredients.json"), IngredientList::class.java).ingredients


    val mostExpensive = ingredients.maxByOrNull{ it.value }
    val heaviest = ingredients.maxByOrNull { it.weight }
    println("ingredients has ${ingredients.size} in it.")
    println("most expensive is ${mostExpensive?.name}, at ${mostExpensive?.value}.")
    println("heaviest is ${heaviest?.name}, at ${heaviest!!.weight}.")
}