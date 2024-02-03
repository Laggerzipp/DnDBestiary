package com.example.example

import com.google.gson.annotations.SerializedName

data class Monster(
  val index: String,
  val name: String,
  val size: String,
  val type: String,
  val alignment: String,
  @SerializedName("armor_class") val armorClass: List<ArmorClass>,
  @SerializedName("hit_points") val hitPoints: Int,
  @SerializedName("hit_dice") val hitDice: String,
  @SerializedName("hit_points_roll") val hitPointsRoll: String,
  val speed: Speed,
  val strength: Int,
  val dexterity: Int,
  val constitution: Int,
  val intelligence: Int,
  val wisdom: Int,
  val charisma: Int,
  val proficiencies: List<Proficiency>,
  @SerializedName("damage_vulnerabilities") val damageVulnerabilities: List<String>,
  @SerializedName("damage_resistances") val damageResistances: List<String>,
  @SerializedName("damage_immunities") val damageImmunities: List<String>,
  @SerializedName("condition_immunities") val conditionImmunities: List<String>,
  val senses: Senses,
  val languages: String,
  @SerializedName("challenge_rating") val challengeRating: Int,
  @SerializedName("proficiency_bonus") val proficiencyBonus: Int,
  val xp: Int,
  @SerializedName("special_abilities") val specialAbilities: List<SpecialAbility>,
  val actions: List<Action>,
  @SerializedName("legendary_actions") val legendaryActions: List<LegendaryAction>,
  val image: String,
  val url: String
)

data class ArmorClass(
  val type: String,
  val value: Int
)

data class Speed(
  val walk: String,
  val swim: String
)

data class Proficiency(
  val value: Int,
  val proficiency: ProficiencyDetails
)

data class ProficiencyDetails(
  val index: String,
  val name: String,
  val url: String
)

data class Senses(
  val darkvision: String,
  @SerializedName("passive_perception") val passivePerception: Int
)

data class SpecialAbility(
  val name: String,
  val desc: String,
  val dc: Dc? = null
)

data class Dc(
  @SerializedName("dc_type") val dcType: DcType,
  @SerializedName("dc_value") val dcValue: Int,
  @SerializedName("success_type") val successType: String
)

data class DcType(
  val index: String,
  val name: String,
  val url: String
)

data class Action(
  val name: String,
  val desc: String,
  @SerializedName("attack_bonus") val attackBonus: Int? = null,
  val dc: Dc? = null,
  val damage: List<Damage>,
  val actions: List<Action>? = null
)

data class Damage(
  @SerializedName("damage_type") val damageType: DamageType,
  @SerializedName("damage_dice") val damageDice: String
)

data class DamageType(
  val index: String,
  val name: String,
  val url: String
)

data class LegendaryAction(
  val name: String,
  val desc: String,
  @SerializedName("attack_bonus") val attackBonus: Int? = null,
  val damage: List<Damage>? = null
)
