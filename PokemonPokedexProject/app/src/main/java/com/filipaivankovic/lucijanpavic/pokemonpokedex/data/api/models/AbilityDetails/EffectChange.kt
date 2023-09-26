package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.AbilityDetails


import com.google.gson.annotations.SerializedName

data class EffectChange(
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntry>,
    @SerializedName("version_group")
    val versionGroup: VersionGroup
)