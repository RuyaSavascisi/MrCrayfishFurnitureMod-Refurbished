---
sidebar_position: 4
sidebar_label: Frying Pan (Frying)
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Frying Pan (Frying)

The Frying Pan is a new block in the MrCrayfish's Furniture Mod: Refurbished and it allows you to fry food. It is placed on top of a powered Stove. Items need to be flipped half way through the cooking process with a Spatula. Along with the frying pan comes the new frying pan recipe type so you can add custom recipes. The Stove that heats the Frying Pan requires electrical power in order for it to function.

:::warning
The frying pan inherits all campfire recipes. You cannot modify those recipes through the frying pan recipe manager.
:::

## Recipe Manager
`<recipetype:refurbished_furniture:frying_pan_frying>`

## Custom Functions

### `addRecipe(name, ingredient, result[, time])`

Adds a new frying recipe to the frying pan

| Paramater  |                                          Type                                           | Required |                                                                                              Description                                                                                              |
| :--------: | :-------------------------------------------------------------------------------------: | :------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: |
|    name    |                                         string                                          |   Yes    |                                                                                The name of the recipe, must be unique.                                                                                |
| ingredient | [IIngredient](https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient) |   Yes    |                                                                                         The ingredient to fry                                                                                         |
|   result   |     [IItemStack](https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack)     |   Yes    |                                                                   The resulting item from frying the `ingredient`, can have a tag.                                                                    |
|    time    |                                           int                                           |    No    | The duration in ticks to fully cook the item. The item will cook for half this time, the player will then flip it with a Spatula, then it will complete cooking for the remaining time. Default `200` |

#### Example
<Tabs>
  <TabItem value="zenscript" label="ZenScript" default>
    ```ts title="%gamedir%/scripts/example.zs"
    <recipetype:refurbished_furniture:frying_pan_frying>.addRecipe(
        "frying/easy_diamonds",
        <item:minecraft:apple> | <item:minecraft:potato>, 
        <item:minecraft:diamond> * 64,
        500
    );
    ```
  </TabItem>
  <TabItem value="json" label="Datapack Equivelant">
    ```json title="(ZIP File) 🡢 /data/[namespace]/recipes/frying/easy_diamonds.json"
    {
        "type": "refurbished_furniture:frying_pan_frying",
        "ingredient": {
            "item": [
                "minecraft:apple",
                "minecraft:potato"
            ]
        },
        "result": {
            "item": "minecraft:diamond",
            "count": 64
        },
        "time": 500
    } 
    ```
  </TabItem>
</Tabs>

---

## Learn More

See **Recipe Managers** on the CraftTweaker [documentation](https://docs.blamejared.com/1.20.4/en/tutorial/Recipes/RecipeManagers) for all inbuilt functions.