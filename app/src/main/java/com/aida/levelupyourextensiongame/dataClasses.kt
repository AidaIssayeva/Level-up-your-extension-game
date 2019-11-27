package com.aida.levelupyourextensiongame


data class DomesticAnimal(
    val id: String,
    val name: String,
    val farmLocation: String,
    val type: Type,
    val logo: String,
    val longDescription: String,
    val shortDescription: String
)

data class WildAnimal(
    val id: String,
    val name: String,
    val logo: String,
    val inRedList: Boolean,
    val location: Biome,
    val longDescription: String,
    val shortDescription: String
)

data class Animal(
    val id: String,
    val name: String,
    val isDomestic: Boolean,
    val inRedList: Boolean,
    val description: String,
    val logo: String
)

enum class Type {
    CAMEL,
    CATTLE,
    SHEEP,
    GOAT,
    HORSE
}

enum class Biome {
    STEPPE,
    FOREST,
    MOUNTAIN
}