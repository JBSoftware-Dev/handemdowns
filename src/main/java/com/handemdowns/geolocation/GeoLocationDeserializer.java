package com.handemdowns.geolocation;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class GeoLocationDeserializer extends StdDeserializer<GeoLocation> {
    public GeoLocationDeserializer() {
        this(null);
    }

    public GeoLocationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public GeoLocation deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode root = parser.getCodec().readTree(parser);
        String status = root.get("status").asText();
        if (!status.equals("OK")) {
            throw new JsonParseException(parser, "Bad status");
        }

        GeoLocation.GeoLocationBuilder builder = GeoLocation.builder();

        JsonNode resultNode = root.path("results");
        if (resultNode.isMissingNode() || !resultNode.isArray()) {
            throw new JsonParseException(parser, "Error parsing results");
        }
        for (JsonNode result : resultNode) {
            JsonNode addressComponentsNode = result.path("address_components");
            if (addressComponentsNode.isMissingNode() || !addressComponentsNode.isArray()) {
                throw new JsonParseException(parser, "Error parsing address_components");
            }
            for (JsonNode addressComponent : addressComponentsNode) {
                JsonNode typesNode = addressComponent.path("types");
                if (typesNode.isMissingNode()) {
                    throw new JsonParseException(parser, "Error parsing types");
                }

                if (typesNode.toString().contains("locality")) {
                    JsonNode longNameNode = addressComponent.path("long_name");
                    builder.city(longNameNode.asText());
                }

                if (typesNode.toString().contains("postal_code")) {
                    JsonNode longNameNode = addressComponent.path("long_name");
                    builder.postalCode(longNameNode.asText());
                }
            }

            JsonNode geometryNode = result.path("geometry");
            if (geometryNode.isMissingNode()) {
                throw new JsonParseException(parser, "Error parsing geometry");
            }

            JsonNode locationNode = geometryNode.path("location");
            if (locationNode.isMissingNode()) {
                throw new JsonParseException(parser, "Error parsing location");
            }
            builder.latitude((Double) locationNode.get("lat").numberValue());
            builder.longitude((Double) locationNode.get("lng").numberValue());
        }
        return builder.build();
    }
}