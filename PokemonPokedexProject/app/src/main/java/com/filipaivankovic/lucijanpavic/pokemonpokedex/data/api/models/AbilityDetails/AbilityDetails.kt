package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.AbilityDetails


import com.google.gson.annotations.SerializedName

data class AbilityDetails(
    @SerializedName("effect_changes")
    val effectChanges: List<EffectChange>,
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntry>,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<Any>,
    @SerializedName("generation")
    val generation: Generation,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_main_series")
    val isMainSeries: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("names")
    val names: List<Any>,
    @SerializedName("pokemon")
    val pokemon: List<Any>
)