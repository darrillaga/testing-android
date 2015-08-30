package com.mooveit.android.testing.utils;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A helper class to load Json fixtures from the test assets
 */
public class FixturesLoader {

    private static final Map<String, JsonNode> fixtures = new HashMap<String, JsonNode>();

    /**
     *
     * @return
     */
    public static Map<String, JsonNode> getFixtures() {
        return fixtures;
    }

    /**
     * This method generates a Map matching an asset file name (without extension) to its content
     * parsed to a JsonNode.
     * The fixture assets have to be inside the folder referenced by fixtures_path string.
     * @param context
     * @param basePath
     * @return
     */
    public static Map<String, JsonNode> onLoad(Context context, String basePath) {
        Map<String, JsonNode> fixtures = new HashMap<String, JsonNode>();

        InputStream stream = null;
        try {

            String[] assetList = context.getAssets().list(basePath);

            for (String assetName : assetList) {
                stream = context.getAssets().open(basePath + "/" + assetName);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(stream);

                fixtures.put(
                        assetName.split("\\.")[0],
                        node
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not load fixtures");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException("[Warning] Could not close fixtures loader stream");
                }
            }
        }

        return fixtures;
    }
}
