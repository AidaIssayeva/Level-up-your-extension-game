package com.aida.levelupyourextensiongame


fun DomesticAnimal.toAnimal(): Animal {
    return Animal(
        id = id,
        name = name,
        logo = logo,
        isDomestic = true,
        description = shortDescription,
        inRedList = false
    )
}

fun WildAnimal.toAnimal(): Animal {
    return Animal(
        id = id,
        name = name,
        logo = logo,
        isDomestic = false,
        description = shortDescription,
        inRedList = inRedList
    )
}

/**
 * This is not good example of extension function,
 * because you're extending common type `Charsequence` and exposing this method to
 * everything user types in.
 */
fun CharSequence.toAnimal(): Animal {
    return Animal(
        id = this.toString(),
        name = this.toString(),
        logo = "",
        isDomestic = false,
        inRedList = false,
        description = this.toString()
    )
}