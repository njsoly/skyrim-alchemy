package com.syntj.skyrim

import com.syntj.skyrim.Effect.*

@Suppress("unused")
enum class Ingredient (
    val description: String,
    val weight: Double? = null,
    val value: Double? = null,
    val image: String? = null,
    val effects: List<Effect?> = listOf(null, null, null, null),
    val plantable: Boolean? = null,
    val solstheimOnly: Boolean? = null,
    val khajitsOnly: Boolean? = null,
) {
    AbeceanLongfin(description = "Abecean Longfin", weight = 0.5, value = 15.0, image = "http://www.uesp.net/w/images/thumb/7/7a/SR-icon-ingredient-Abecean_Longfin.png/48px-SR-icon-ingredient-Abecean_Longfin.png",
        effects = listOf(WeaknessToFrost, FortifySneak, WeaknessToPoison, FortifyRestoration)
    ),
    BearClaws(
        description = "Bear Claws",
        weight = 0.1,
        value = 2.0,
        image = "http://www.uesp.net/w/images/thumb/0/04/SR-icon-ingredient-Bear_Claws.png/48px-SR-icon-ingredient-Bear_Claws.png",
        effects = listOf(RestoreStamina, FortifyHealth, FortifyOneHanded, DamageMagickaRegen)
    ),
    Bee(description = "Bee", weight = 0.1, value = 3.0, image = "http://www.uesp.net/w/images/thumb/b/b8/SR-icon-ingredient-Bee.png/48px-SR-icon-ingredient-Bee.png",
        effects = listOf(RestoreStamina, RavageStamina, RegenerateStamina, WeaknessToShock)
    ),
    BeehiveHusk(description = "Beehive Husk", weight = 1.0, value = 5.0, image = "http://www.uesp.net/w/images/thumb/8/84/SR-icon-ingredient-Beehive_Husk.png/48px-SR-icon-ingredient-Beehive_Husk.png",
        effects = listOf(ResistPoison, FortifyLightArmor, FortifySneak, FortifyDestruction)
    ),
    BleedingCrown(description = "Bleeding Crown", weight = 0.3, value = 10.0, image = "http://www.uesp.net/w/images/thumb/a/a6/SR-icon-ingredient-Bleeding_Crown.png/48px-SR-icon-ingredient-Bleeding_Crown.png",
        effects = listOf(WeaknessToFire, FortifyBlock, WeaknessToPoison, ResistMagic)
    ),
    Blisterwort(description = "Blisterwort", weight = 0.2, value = 12.0, image = "http://www.uesp.net/w/images/thumb/2/2b/SR-icon-ingredient-Blisterwort.png/48px-SR-icon-ingredient-Blisterwort.png",
        effects = listOf(DamageStamina, Frenzy, RestoreHealth, FortifySmithing)
    ),
    BlueButterflyWing(description = "Blue Butterfly Wing", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/1/16/SR-icon-ingredient-Blue_Butterfly_Wing.png/48px-SR-icon-ingredient-Blue_Butterfly_Wing.png",
        effects = listOf(DamageStamina, FortifyConjuration, DamageMagickaRegen, FortifyEnchanting)
    ),
    BlueDartwing(description = "Blue Dartwing", weight = 0.1, value = 1.0, image = "http://www.uesp.net/w/images/thumb/e/ee/SR-icon-ingredient-Blue_Dartwing.png/48px-SR-icon-ingredient-Blue_Dartwing.png",
        effects = listOf(ResistShock, FortifyPickpocket, RestoreHealth, Fear)
    ),
    BlueMountainFlower(
        description = "Blue Mountain Flower",
        weight = 0.1,
        value = 2.0,
        image = "http://www.uesp.net/w/images/thumb/3/38/SR-icon-ingredient-Blue_Mountain_Flower.png/48px-SR-icon-ingredient-Blue_Mountain_Flower.png",
        effects = listOf(RestoreHealth, FortifyConjuration, FortifyHealth, DamageMagickaRegen)
    ),
    BoneMeal(description = "Bone Meal", weight = 0.5, value = 5.0, image = "http://www.uesp.net/w/images/thumb/f/f9/SR-icon-ingredient-Bone_Meal.png/48px-SR-icon-ingredient-Bone_Meal.png",
        effects = listOf(DamageStamina, ResistFire, FortifyConjuration, RavageStamina)
    ),
    BriarHeart(description = "Briar Heart", weight = 0.5, value = 20.0, image = "http://www.uesp.net/w/images/thumb/6/6a/SR-icon-ingredient-Briar_Heart.png/48px-SR-icon-ingredient-Briar_Heart.png",
        effects = listOf(RestoreMagicka, FortifyBlock, Paralysis, FortifyMagicka)
    ),
    ButterflyWing(description = "Butterfly Wing", weight = 0.1, value = 3.0, image = "http://www.uesp.net/w/images/thumb/1/16/SR-icon-ingredient-Blue_Butterfly_Wing.png/48px-SR-icon-ingredient-Blue_Butterfly_Wing.png",
        effects = listOf(RestoreHealth, FortifyBarter, LingeringDamageStamina, DamageMagicka)
    ),
    CanisRoot(description = "Canis Root", weight = 0.1, value = 5.0, image = "http://www.uesp.net/w/images/thumb/f/f6/SR-icon-ingredient-Canis_Root.png/48px-SR-icon-ingredient-Canis_Root.png",
        effects = listOf(DamageStamina, FortifyOneHanded, FortifyMarksman, Paralysis)
    ),
    CharredSkeeverHide(description = "Charred Skeever Hide", weight = 0.5, value = 1.0, image = "http://www.uesp.net/w/images/thumb/7/71/SR-icon-ingredient-Charred_Skeever_Hide.png/48px-SR-icon-ingredient-Charred_Skeever_Hide.png",
        effects = listOf(RestoreStamina, CureDisease, ResistPoison, RestoreHealth)
    ),
    ChaurusEggs(description = "Chaurus Eggs", weight = 0.2, value = 10.0, image = "http://www.uesp.net/w/images/thumb/4/47/SR-icon-ingredient-Chaurus_Eggs.png/48px-SR-icon-ingredient-Chaurus_Eggs.png",
        effects = listOf(WeaknessToPoison, FortifyStamina, DamageMagicka, Invisibility)
    ),
    ChickensEgg(description = "Chicken's Egg", weight = 0.5, value = 2.0, image = "http://www.uesp.net/w/images/thumb/d/de/SR-icon-ingredient-Chicken%27s_Egg.png/48px-SR-icon-ingredient-Chicken%27s_Egg.png",
        effects = listOf(ResistMagic, DamageMagickaRegen, Waterbreathing, LingeringDamageStamina)
    ),
    CreepCluster(description = "Creep Cluster", weight = 0.2, value = 1.0, image = "http://www.uesp.net/w/images/thumb/1/15/SR-icon-ingredient-Creep_Cluster.png/48px-SR-icon-ingredient-Creep_Cluster.png",
        effects = listOf(RestoreMagicka, DamageStaminaRegen, FortifyCarryWeight, WeaknessToMagic)
    ),
    CrimsonNirnroot(description = "Crimson Nirnroot", weight = 0.2, value = 10.0, image = "http://www.uesp.net/w/images/thumb/5/55/SR-icon-ingredient-Crimson_Nirnroot.png/48px-SR-icon-ingredient-Crimson_Nirnroot.png",
        effects = listOf(DamageHealth, DamageStamina, Invisibility, ResistMagic)
    ),
    CyrodilicSpadetail(description = "Cyrodilic Spadetail", weight = 0.25, value = 15.0, image = "http://www.uesp.net/w/images/thumb/a/a9/SR-icon-ingredient-Cyrodilic_Spadetail.png/48px-SR-icon-ingredient-Cyrodilic_Spadetail.png",
        effects = listOf(DamageStamina, FortifyRestoration, Fear, RavageHealth)
    ),
    DaedraHeart(description = "Daedra Heart", weight = 0.5, value = 250.0, image = "http://www.uesp.net/w/images/thumb/b/b1/SR-icon-ingredient-Daedra_Heart.png/48px-SR-icon-ingredient-Daedra_Heart.png",
        effects = listOf(RestoreHealth, DamageStaminaRegen, DamageMagicka, Fear)
    ),
    Deathbell(description = "Deathbell", weight = 0.1, value = 4.0, image = "http://www.uesp.net/w/images/thumb/5/58/SR-icon-ingredient-Deathbell.png/48px-SR-icon-ingredient-Deathbell.png",
        effects = listOf(DamageHealth, RavageStamina, Slow, WeaknessToPoison)
    ),
    DragonsTongue(description = "Dragon's Tongue", weight = 0.1, value = 5.0, image = "http://www.uesp.net/w/images/thumb/0/0e/SR-icon-ingredient-Dragons_Tongue.png/48px-SR-icon-ingredient-Dragons_Tongue.png",
        effects = listOf(ResistFire, FortifyBarter, FortifyIllusion, FortifyTwoHanded)
    ),
    DwarvenOil(description = "Dwarven Oil", weight = 0.25, value = 15.0, image = "http://www.uesp.net/w/images/thumb/e/e5/SR-icon-ingredient-Dwarven_Oil.png/48px-SR-icon-ingredient-Dwarven_Oil.png",
        effects = listOf(WeaknessToMagic, FortifyIllusion, RegenerateMagicka, RestoreMagicka)
    ),
    Ectoplasm(description = "Ectoplasm", weight = 0.1, value = 25.0, image = "http://www.uesp.net/w/images/thumb/f/f1/SR-icon-ingredient-Ectoplasm.png/48px-SR-icon-ingredient-Ectoplasm.png",
        effects = listOf(RestoreMagicka, FortifyDestruction, FortifyMagicka, DamageHealth)
    ),
    ElvesEar(description = "Elves Ear", weight = 0.1, value = 10.0, image = "http://www.uesp.net/w/images/thumb/7/7a/SR-icon-ingredient-Elves_Ear.png/48px-SR-icon-ingredient-Elves_Ear.png",
        effects = listOf(RestoreMagicka, FortifyMarksman, WeaknessToFrost, ResistFire)
    ),
    EyeOfSabreCat(description = "Eye of Sabre Cat", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/4/4e/SR-icon-ingredient-Eye_of_Sabre_Cat.png/48px-SR-icon-ingredient-Eye_of_Sabre_Cat.png",
        effects = listOf(RestoreStamina, RavageHealth, DamageMagicka, RestoreHealth)
    ),
    FalmerEar(description = "Falmer Ear", weight = 0.2, value = 10.0, image = "http://www.uesp.net/w/images/thumb/6/6e/SR-icon-ingredient-Falmer_Ear.png/48px-SR-icon-ingredient-Falmer_Ear.png",
        effects = listOf(DamageHealth, Frenzy, ResistPoison, FortifyLockpicking)
    ),
    FireSalts(description = "Fire Salts", weight = 0.25, value = 50.0, image = "http://www.uesp.net/w/images/thumb/c/cc/SR-icon-ingredient-Fire_Salts.png/48px-SR-icon-ingredient-Fire_Salts.png",
        effects = listOf(WeaknessToFrost, ResistFire, RestoreMagicka, RegenerateMagicka)
    ),
    FlyAmanita(description = "Fly Amanita", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/7/76/SR-icon-ingredient-Fly_Amanita.png/48px-SR-icon-ingredient-Fly_Amanita.png",
        effects = listOf(ResistFire, FortifyTwoHanded, Frenzy, RegenerateStamina)
    ),
    FrostMirriam(description = "Frost Mirriam", weight = 0.1, value = 1.0, image = "http://www.uesp.net/w/images/thumb/9/99/SR-icon-ingredient-Frost_Mirriam.png/48px-SR-icon-ingredient-Frost_Mirriam.png",
        effects = listOf(ResistFrost, FortifySneak, RavageMagicka, DamageStaminaRegen)
    ),
    FrostSalts(description = "Frost Salts", weight = 0.25, value = 100.0, image = "http://www.uesp.net/w/images/thumb/5/58/SR-icon-ingredient-Frost_Salts.png/48px-SR-icon-ingredient-Frost_Salts.png",
        effects = listOf(WeaknessToFire, ResistFrost, RestoreMagicka, FortifyConjuration)
    ),
    Garlic(description = "Garlic", weight = 0.25, value = 1.0, image = "http://www.uesp.net/w/images/thumb/e/e5/SR-icon-ingredient-Garlic.png/48px-SR-icon-ingredient-Garlic.png",
        effects = listOf(ResistPoison, FortifyStamina, RegenerateMagicka, RegenerateHealth)
    ),
    GiantLichen(description = "Giant Lichen", weight = 0.25, value = 5.0, image = "http://www.uesp.net/w/images/thumb/8/8b/SR-icon-ingredient-Giant_Lichen.png/48px-SR-icon-ingredient-Giant_Lichen.png",
        effects = listOf(WeaknessToShock, RavageHealth, WeaknessToPoison, RestoreMagicka)
    ),
    GiantsToe(description = "Giant's Toe", weight = 1.0, value = 20.0, image = "http://www.uesp.net/w/images/thumb/b/bc/SR-icon-ingredient-Giants_Toe.png/48px-SR-icon-ingredient-Giants_Toe.png",
        effects = listOf(DamageStamina, FortifyHealth, FortifyCarryWeight, DamageStaminaRegen)
    ),
    GlowDust(description = "Glow Dust", weight = 0.5, value = 20.0, image = "http://www.uesp.net/w/images/thumb/f/f8/SR-icon-ingredient-Glow_Dust.png/48px-SR-icon-ingredient-Glow_Dust.png",
        effects = listOf(DamageMagicka, DamageMagickaRegen, FortifyDestruction, ResistShock)
    ),
    GlowingMushroom(description = "Glowing Mushroom", weight = 0.2, value = 5.0, image = "http://www.uesp.net/w/images/thumb/7/71/SR-icon-ingredient-Glowing_Mushroom.png/48px-SR-icon-ingredient-Glowing_Mushroom.png",
        effects = listOf(ResistShock, FortifyDestruction, FortifySmithing, FortifyHealth)
    ),
    GrassPod(description = "Grass Pod", weight = 0.1, value = 1.0, image = "http://www.uesp.net/w/images/thumb/7/78/SR-icon-ingredient-Grass_Pod.png/48px-SR-icon-ingredient-Grass_Pod.png",
        effects = listOf(ResistPoison, RavageMagicka, FortifyAlteration, RestoreMagicka)
    ),
    HagravenClaw(description = "Hagraven Claw", weight = 0.25, value = 20.0, image = "http://www.uesp.net/w/images/thumb/3/30/SR-icon-ingredient-Hagraven_Claw.png/48px-SR-icon-ingredient-Hagraven_Claw.png",
        effects = listOf(ResistMagic, LingeringDamageMagicka, FortifyEnchanting, FortifyBarter)
    ),
    HagravenFeathers(description = "Hagraven Feathers", weight = 0.1, value = 20.0, image = "http://www.uesp.net/w/images/thumb/d/d8/SR-icon-ingredient-Hagraven_Feathers.png/48px-SR-icon-ingredient-Hagraven_Feathers.png",
        effects = listOf(DamageMagicka, FortifyConjuration, Frenzy, WeaknessToShock)
    ),
    HangingMoss(description = "Hanging Moss", weight = 0.25, value = 1.0, image = "http://www.uesp.net/w/images/thumb/2/22/SR-icon-ingredient-Hanging_Moss.png/48px-SR-icon-ingredient-Hanging_Moss.png",
        effects = listOf(DamageMagicka, FortifyHealth, DamageMagickaRegen, FortifyOneHanded)
    ),
    HawkBeak(description = "Hawk Beak", weight = 0.25, value = 15.0, image = "http://www.uesp.net/w/images/thumb/6/69/SR-icon-ingredient-Hawk_Beak.png/48px-SR-icon-ingredient-Hawk_Beak.png",
        effects = listOf(RestoreStamina, ResistFrost, FortifyCarryWeight, ResistShock)
    ),
    HawkFeathers(description = "Hawk Feathers", weight = 0.1, value = 15.0, image = "http://www.uesp.net/w/images/thumb/5/5c/SR-icon-ingredient-Hawk_Feathers.png/48px-SR-icon-ingredient-Hawk_Feathers.png",
        effects = listOf(CureDisease, FortifyLightArmor, FortifyOneHanded, FortifySneak)
    ),
    Histcarp(description = "Histcarp", weight = 0.25, value = 6.0, image = "http://www.uesp.net/w/images/thumb/e/ec/SR-icon-ingredient-Histcarp.png/48px-SR-icon-ingredient-Histcarp.png",
        effects = listOf(RestoreStamina, FortifyMagicka, DamageStaminaRegen, Waterbreathing)
    ),
    Honeycomb(description = "Honeycomb", weight = 1.0, value = 5.0, image = "http://www.uesp.net/w/images/thumb/e/e0/SR-icon-ingredient-Honeycomb.png/48px-SR-icon-ingredient-Honeycomb.png",
        effects = listOf(RestoreStamina, FortifyBlock, FortifyLightArmor, RavageStamina)
    ),
    HumanFlesh(description = "Human Flesh", weight = 0.25, value = 1.0, image = "http://www.uesp.net/w/images/thumb/9/9d/SR-icon-ingredient-Human_Flesh.png/48px-SR-icon-ingredient-Human_Flesh.png",
        effects = listOf(DamageHealth, Paralysis, RestoreMagicka, FortifySneak)
    ),
    HumanHeart(description = "Human Heart", weight = 1.0, value = 0.0, image = "http://www.uesp.net/w/images/thumb/f/f6/SR-icon-ingredient-Human_Heart.png/48px-SR-icon-ingredient-Human_Heart.png",
        effects = listOf(DamageHealth, DamageMagicka, DamageMagickaRegen, Frenzy)
    ),
    IceWraithTeeth(description = "Ice Wraith Teeth", weight = 0.25, value = 30.0, image = "http://www.uesp.net/w/images/thumb/9/95/SR-icon-ingredient-Ice_Wraith_Teeth.png/48px-SR-icon-ingredient-Ice_Wraith_Teeth.png",
        effects = listOf(WeaknessToFrost, FortifyHeavyArmor, Invisibility, WeaknessToFire)
    ),
    ImpStool(description = "Imp Stool", weight = 0.3, value = 0.0, image = "http://www.uesp.net/w/images/thumb/7/77/SR-icon-ingredient-Imp_Stool.png/48px-SR-icon-ingredient-Imp_Stool.png",
        effects = listOf(DamageHealth, LingeringDamageHealth, Paralysis, RestoreHealth)
    ),
    JazbayGrapes(description = "Jazbay Grapes", weight = 0.2, value = 1.0, image = "http://www.uesp.net/w/images/thumb/5/50/SR-icon-ingredient-Jazbay_Grapes.png/48px-SR-icon-ingredient-Jazbay_Grapes.png",
        effects = listOf(WeaknessToMagic, FortifyMagicka, RegenerateMagicka, RavageHealth)
    ),
    JuniperBerries(description = "Juniper Berries", weight = 0.1, value = 1.0, image = "http://www.uesp.net/w/images/thumb/1/10/SR-icon-ingredient-Juniper_Berries.png/48px-SR-icon-ingredient-Juniper_Berries.png",
        effects = listOf(WeaknessToFire, FortifyMarksman, RegenerateHealth, DamageStaminaRegen)
    ),
    LargeAntlers(description = "Large Antlers", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/6/69/SR-icon-ingredient-Large_Antlers.png/48px-SR-icon-ingredient-Large_Antlers.png",
        effects = listOf(RestoreStamina, FortifyStamina, Slow, DamageStaminaRegen)
    ),
    Lavender(description = "Lavender", weight = 0.1, value = 1.0, image = "http://www.uesp.net/w/images/thumb/3/30/SR-icon-ingredient-Lavender.png/48px-SR-icon-ingredient-Lavender.png",
        effects = listOf(ResistMagic, FortifyStamina, RavageMagicka, FortifyConjuration)
    ),
    LunaMothWing(description = "Luna Moth Wing", weight = 0.1, value = 5.0, image = "http://www.uesp.net/w/images/thumb/f/f6/SR-icon-ingredient-Luna_Moth_Wing.png/48px-SR-icon-ingredient-Luna_Moth_Wing.png",
        effects = listOf(DamageMagicka, FortifyLightArmor, RegenerateHealth, Invisibility)
    ),
    MoonSugar(description = "Moon Sugar", weight = 0.25, value = 50.0, image = "http://www.uesp.net/w/images/thumb/4/48/SR-icon-ingredient-Moon_Sugar.png/48px-SR-icon-ingredient-Moon_Sugar.png",
        effects = listOf(WeaknessToFire, ResistFrost, RestoreMagicka, RegenerateMagicka)
    ),
    MoraTapinella(description = "Mora Tapinella", weight = 0.25, value = 4.0, image = "http://www.uesp.net/w/images/thumb/5/51/SR-icon-ingredient-Mora_Tapinella.png/48px-SR-icon-ingredient-Mora_Tapinella.png",
        effects = listOf(RestoreMagicka, LingeringDamageHealth, RegenerateStamina, FortifyIllusion)
    ),
    MudcrabChitin(description = "Mudcrab Chitin", weight = 0.25, value = 2.0, image = "http://www.uesp.net/w/images/thumb/7/74/SR-icon-ingredient-Mudcrab_Chitin.png/48px-SR-icon-ingredient-Mudcrab_Chitin.png",
        effects = listOf(RestoreStamina, CureDisease, ResistPoison, ResistFire)
    ),
    NamirasRot(description = "Namira's Rot", weight = 0.25, value = 0.0, image = "http://www.uesp.net/w/images/thumb/5/59/SR-icon-ingredient-Namira%27s_Rot.png/48px-SR-icon-ingredient-Namira%27s_Rot.png",
        effects = listOf(DamageMagicka, FortifyLockpicking, Fear, RegenerateHealth)
    ),
    Nightshade(description = "Nightshade", weight = 0.1, value = 8.0, image = "http://www.uesp.net/w/images/thumb/6/6f/SR-icon-ingredient-Nightshade.png/48px-SR-icon-ingredient-Nightshade.png",
        effects = listOf(DamageHealth, DamageMagickaRegen, LingeringDamageStamina, FortifyDestruction)
    ),
    Nirnroot(description = "Nirnroot", weight = 0.2, value = 10.0, image = "http://www.uesp.net/w/images/thumb/5/55/SR-icon-ingredient-Crimson_Nirnroot.png/48px-SR-icon-ingredient-Crimson_Nirnroot.png",
        effects = listOf(DamageHealth, DamageStamina, Invisibility, ResistMagic)
    ),
    NordicBarnacle(description = "Nordic Barnacle", weight = 0.2, value = 5.0, image = "http://www.uesp.net/w/images/thumb/7/75/SR-icon-ingredient-Nordic_Barnacle.png/48px-SR-icon-ingredient-Nordic_Barnacle.png",
        effects = listOf(DamageMagicka, Waterbreathing, RegenerateHealth, FortifyPickpocket)
    ),
    OrangeDartwing(description = "Orange Dartwing", weight = 0.1, value = 1.0, image = "http://www.uesp.net/w/images/thumb/e/e7/SR-icon-ingredient-Orange_Dartwing.png/48px-SR-icon-ingredient-Orange_Dartwing.png",
        effects = listOf(RestoreStamina, RavageMagicka, FortifyPickpocket, LingeringDamageHealth)
    ),
    Pearl(description = "Pearl", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/a/ad/SR-icon-ingredient-Pearl.png/48px-SR-icon-ingredient-Pearl.png",
        effects = listOf(RestoreStamina, FortifyBlock, RestoreMagicka, ResistShock)
    ),
    PineThrushEgg(description = "Pine Thrush Egg", weight = 0.5, value = 2.0, image = "http://www.uesp.net/w/images/thumb/0/0f/SR-icon-ingredient-Pine_Thrush_Egg.png/48px-SR-icon-ingredient-Pine_Thrush_Egg.png",
        effects = listOf(RestoreStamina, FortifyLockpicking, WeaknessToPoison, ResistShock)
    ),
    PowderedMammothTusk(description = "Powdered Mammoth Tusk", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/c/c9/SR-icon-ingredient-Powdered_Mammoth_Tusk.png/48px-SR-icon-ingredient-Powdered_Mammoth_Tusk.png",
        effects = listOf(RestoreStamina, FortifySneak, WeaknessToFire, Fear)
    ),
    PurpleMountainFlower(description = "Purple Mountain Flower", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/e/e1/SR-icon-ingredient-Purple_Mountain_Flower.png/48px-SR-icon-ingredient-Purple_Mountain_Flower.png",
        effects = listOf(RestoreStamina, FortifySneak, LingeringDamageMagicka, ResistFrost)
    ),
    RedMountainFlower(description = "Red Mountain Flower", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/b/bb/SR-icon-ingredient-Red_Mountain_Flower.png/48px-SR-icon-ingredient-Red_Mountain_Flower.png",
        effects = listOf(RestoreMagicka, RavageMagicka, FortifyMagicka, DamageHealth)
    ),
    RiverBetty(description = "River Betty", weight = 0.25, value = 15.0, image = "http://www.uesp.net/w/images/thumb/2/25/SR-icon-ingredient-River_Betty.png/48px-SR-icon-ingredient-River_Betty.png",
        effects = listOf(DamageHealth, FortifyAlteration, Slow, FortifyCarryWeight)
    ),
    RockWarblerEgg(description = "Rock Warbler Egg", weight = 0.5, value = 2.0, image = "http://www.uesp.net/w/images/thumb/f/fc/SR-icon-ingredient-Rock_Warbler_Egg.png/48px-SR-icon-ingredient-Rock_Warbler_Egg.png",
        effects = listOf(RestoreHealth, FortifyOneHanded, DamageStamina, WeaknessToMagic)
    ),
    SabreCatTooth(description = "Sabre Cat Tooth", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/1/1b/SR-icon-ingredient-Sabre_Cat_Tooth.png/48px-SR-icon-ingredient-Sabre_Cat_Tooth.png",
        effects = listOf(RestoreStamina, FortifyHeavyArmor, FortifySmithing, WeaknessToPoison)
    ),
    SaltPile(description = "Salt Pile", weight = 0.2, value = 2.0, image = "http://www.uesp.net/w/images/thumb/3/36/SR-icon-ingredient-Salt_Pile.png/48px-SR-icon-ingredient-Salt_Pile.png",
        effects = listOf(WeaknessToMagic, FortifyRestoration, Slow, RegenerateMagicka)
    ),
    ScalyPholiota(description = "Scaly Pholiota", weight = 0.25, value = 4.0, image = "http://www.uesp.net/w/images/thumb/1/18/SR-icon-ingredient-Scaly_Pholiota.png/48px-SR-icon-ingredient-Scaly_Pholiota.png",
        effects = listOf(WeaknessToMagic, FortifyIllusion, RegenerateStamina, FortifyCarryWeight)
    ),
    SilversidePerch(description = "Silverside Perch", weight = 0.25, value = 15.0, image = "http://www.uesp.net/w/images/thumb/3/32/SR-icon-ingredient-Silverside_Perch.png/48px-SR-icon-ingredient-Silverside_Perch.png",
        effects = listOf(RestoreStamina, DamageStaminaRegen, RavageHealth, ResistFrost)
    ),
    SkeeverTail(description = "Skeever Tail", weight = 0.2, value = 3.0, image = "http://www.uesp.net/w/images/thumb/d/dc/SR-icon-ingredient-Skeever_Tail.png/48px-SR-icon-ingredient-Skeever_Tail.png",
        effects = listOf(DamageStaminaRegen, RavageHealth, DamageHealth, FortifyLightArmor)
    ),
    SlaughterfishEgg(description = "Slaughterfish Egg", weight = 0.2, value = 3.0, image = "http://www.uesp.net/w/images/thumb/8/84/SR-icon-ingredient-Slaughterfish_Egg.png/48px-SR-icon-ingredient-Slaughterfish_Egg.png",
        effects = listOf(ResistPoison, FortifyPickpocket, LingeringDamageHealth, FortifyStamina)
    ),
    SlaughterfishScales(description = "Slaughterfish Scales", weight = 0.1, value = 3.0, image = "http://www.uesp.net/w/images/thumb/0/0f/SR-icon-ingredient-Slaughterfish_Scales.png/48px-SR-icon-ingredient-Slaughterfish_Scales.png",
        effects = listOf(ResistFrost, LingeringDamageHealth, FortifyHeavyArmor, FortifyBlock)
    ),
    SmallAntlers(description = "Small Antlers", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/4/4e/SR-icon-ingredient-Small_Antlers.png/48px-SR-icon-ingredient-Small_Antlers.png",
        effects = listOf(WeaknessToPoison, FortifyRestoration, LingeringDamageStamina, DamageHealth)
    ),
    SmallPearl(description = "Small Pearl", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/8/8f/SR-icon-ingredient-Small_Pearl.png/48px-SR-icon-ingredient-Small_Pearl.png",
        effects = listOf(RestoreStamina, FortifyOneHanded, FortifyRestoration, ResistFrost)
    ),
    Snowberries(description = "Snowberries", weight = 0.1, value = 4.0, image = "http://www.uesp.net/w/images/thumb/d/d3/SR-icon-ingredient-Snowberries.png/48px-SR-icon-ingredient-Snowberries.png",
        effects = listOf(ResistFire, FortifyEnchanting, ResistFrost, ResistShock)
    ),
    SpiderEgg(description = "Spider Egg", weight = 0.2, value = 5.0, image = "http://www.uesp.net/w/images/thumb/0/07/SR-icon-ingredient-Spider_Egg.png/48px-SR-icon-ingredient-Spider_Egg.png",
        effects = listOf(DamageStamina, DamageMagickaRegen, FortifyLockpicking, FortifyMarksman)
    ),
    SprigganSap(description = "Spriggan Sap", weight = 0.2, value = 15.0, image = "http://www.uesp.net/w/images/thumb/d/d6/SR-icon-ingredient-Spriggan_Sap.png/48px-SR-icon-ingredient-Spriggan_Sap.png",
        effects = listOf(DamageMagickaRegen, FortifyEnchanting, FortifySmithing, FortifyAlteration)
    ),
    SwampFungalPod(description = "Swamp Fungal Pod", weight = 0.25, value = 5.0, image = "http://www.uesp.net/w/images/thumb/9/92/SR-icon-ingredient-Swamp_Fungal_Pod.png/48px-SR-icon-ingredient-Swamp_Fungal_Pod.png",
        effects = listOf(ResistShock, LingeringDamageMagicka, Paralysis, RestoreHealth)
    ),
    Taproot(description = "Taproot", weight = 0.5, value = 15.0, image = "http://www.uesp.net/w/images/thumb/7/78/SR-icon-ingredient-Taproot.png/48px-SR-icon-ingredient-Taproot.png",
        effects = listOf(WeaknessToMagic, FortifyIllusion, RegenerateMagicka, RestoreMagicka)
    ),
    ThistleBranch(description = "Thistle Branch", weight = 0.1, value = 1.0, image = "http://www.uesp.net/w/images/thumb/b/bf/SR-icon-ingredient-Thistle_Branch.png/48px-SR-icon-ingredient-Thistle_Branch.png",
        effects = listOf(ResistFrost, RavageStamina, ResistPoison, FortifyHeavyArmor)
    ),
    TorchbugThorax(description = "Torchbug Thorax", weight = 0.1, value = 1.0, image = "http://www.uesp.net/w/images/thumb/9/99/SR-icon-ingredient-Torchbug_Thorax.png/48px-SR-icon-ingredient-Torchbug_Thorax.png",
        effects = listOf(RestoreStamina, LingeringDamageMagicka, WeaknessToMagic, FortifyStamina)
    ),
    TrollFat(description = "Troll Fat", weight = 1.0, value = 15.0, image = "http://www.uesp.net/w/images/thumb/8/8a/SR-icon-ingredient-Troll_Fat.png/48px-SR-icon-ingredient-Troll_Fat.png",
        effects = listOf(ResistPoison, FortifyTwoHanded, Frenzy, DamageHealth)
    ),
    TundraCotton(description = "Tundra Cotton", weight = 0.1, value = 1.0, image = "http://www.uesp.net/w/images/thumb/7/7e/SR-icon-ingredient-Tundra_Cotton.png/48px-SR-icon-ingredient-Tundra_Cotton.png",
        effects = listOf(ResistMagic, FortifyMagicka, FortifyBlock, FortifyBarter)
    ),
    VampireDust(description = "Vampire Dust", weight = 0.2, value = 25.0, image = "http://www.uesp.net/w/images/thumb/2/2f/SR-icon-ingredient-Vampire_Dust.png/48px-SR-icon-ingredient-Vampire_Dust.png",
        effects = listOf(Invisibility, RestoreMagicka, RegenerateHealth, CureDisease)
    ),
    VoidSalts(description = "Void Salts", weight = 0.2, value = 125.0, image = "http://www.uesp.net/w/images/thumb/7/77/SR-icon-ingredient-Void_Salts.png/48px-SR-icon-ingredient-Void_Salts.png",
        effects = listOf(WeaknessToShock, ResistMagic, DamageHealth, FortifyMagicka)
    ),
    Wheat(description = "Wheat", weight = 0.1, value = 5.0, image = "http://www.uesp.net/w/images/thumb/7/7f/SR-icon-ingredient-Wheat.png/48px-SR-icon-ingredient-Wheat.png",
        effects = listOf(RestoreHealth, FortifyHealth, DamageStaminaRegen, LingeringDamageMagicka)
    ),
    WhiteCap(description = "White Cap", weight = 0.3, value = 0.0, image = "http://www.uesp.net/w/images/thumb/f/fa/SR-icon-ingredient-White_Cap.png/48px-SR-icon-ingredient-White_Cap.png",
        effects = listOf(WeaknessToFrost, FortifyHeavyArmor, RestoreMagicka, RavageMagicka)
    ),
    WispWrappings(description = "Wisp Wrappings", weight = 0.1, value = 2.0, image = "http://www.uesp.net/w/images/thumb/9/9d/SR-icon-ingredient-Wisp_Wrappings.png/48px-SR-icon-ingredient-Wisp_Wrappings.png",
        effects = listOf(RestoreStamina, FortifyDestruction, FortifyCarryWeight, ResistMagic)
    ),

    // Solstheim
    AshenGrassPod(
        description = "Ashen Grass Pod",
        weight = 0.1,
        value = 1.0,
        effects = listOf(ResistFire, WeaknessToShock, null, FortifySneak),
        solstheimOnly = true,
    ),
    AshHopperJelly(
        description = "Ash Hopper Jelly",
        weight = 0.3,
        value = 20.0,
        effects = listOf(null, null, ResistShock, WeaknessToFrost),
        solstheimOnly = true,
    ),
    FelsaadTernFeathers(
        description = "Felsaad Tern Feathers",
        weight = 0.1,
        value = 15.0,
        effects = listOf(null, FortifyLightArmor, null, null),
        solstheimOnly = true,
    ),
    Scathecraw(
        description = "Scathecraw",
        weight = 0.1,
        value = 10.0,
        effects = listOf(RavageHealth, RavageStamina, RavageMagicka, LingeringDamageHealth),
        solstheimOnly = true,
    ),
    SpawnAsh(
        description = "Spawn Ash",
        weight = 0.1,
        value = 20.0,
        effects = listOf(RavageStamina, null, null, RavageMagicka),
        solstheimOnly = true,
    ),
    TramaRoot(
        description = "Trama Root",
        weight = 0.2,
        value = 1.0,
        effects = listOf(WeaknessToShock, null, DamageMagicka, Slow),
        solstheimOnly = true,
    ),

    // Khajit
    CorkbulbRoot(
        description = "Corkbulb Root",
        khajitsOnly = true
    ),
    FirePetal(
        description = "Fire Petal",
        khajitsOnly = true
    ),
    Saltrice(
        description = "Saltrice"
    ),
    ScribJelly(
        description = "Scrib Jelly",
        khajitsOnly = true
    ),
    SteelBlueEntoloma(
        description = "Steel-Blue Entoloma",
        khajitsOnly = true
    ),

}
