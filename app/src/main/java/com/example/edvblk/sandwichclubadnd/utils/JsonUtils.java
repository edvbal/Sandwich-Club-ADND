package com.example.edvblk.sandwichclubadnd.utils;

import com.example.edvblk.sandwichclubadnd.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {
    private JsonUtils() {
        // empty
    }

    public static Sandwich getSandwichFromJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject jsonParent = new JSONObject(json);
            JSONObject jsonName = jsonParent.getJSONObject("name");
            String mainName = jsonName.getString("mainName");
            List<String> additionalNames = getAdditionalNames(jsonName.getJSONArray("alsoKnownAs"));
            String placeOfOrigin = jsonParent.getString("placeOfOrigin");
            String description = jsonParent.getString("description");
            String image = jsonParent.getString("image");
            List<String> ingredients = getIngredients(jsonParent.getJSONArray("ingredients"));
            sandwich = new Sandwich(mainName, additionalNames, placeOfOrigin,
                    description, image, ingredients);
        } catch (JSONException cause) {
            cause.printStackTrace();
        }

        return sandwich;
    }

    private static List<String> getIngredients(JSONArray ingredientsArray) throws JSONException {
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsArray.length(); i++) {
            ingredients.add(ingredientsArray.getString(i));
        }
        return ingredients;
    }

    private static List<String> getAdditionalNames(JSONArray namesArray) throws JSONException {
        List<String> additionalNames = new ArrayList<>();
        for (int i = 0; i < namesArray.length(); i++) {
            additionalNames.add(namesArray.getString(i));
        }
        return additionalNames;
    }
}
