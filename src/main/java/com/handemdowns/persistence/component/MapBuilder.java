package com.handemdowns.persistence.component;

import com.handemdowns.persistence.dao.LocationRepository;
import com.handemdowns.persistence.dao.MapPlotRepository;
import com.handemdowns.persistence.model.Location;
import com.handemdowns.persistence.model.MapPlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapBuilder {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private LocationRepository locationRepository;
    private MapPlotRepository mapPlotRepository;

    public MapBuilder(LocationRepository locationRepository, MapPlotRepository mapPlotRepository) {
        this.locationRepository = locationRepository;
        this.mapPlotRepository = mapPlotRepository;
    }

    public void buildMap() {
        log.info("MapBuilder is running");
        buildCanadianMap();
        buildAmericanMap();
        log.info("MapBuilder is complete");
    }

    private void buildLocation(String locationCode, String country, String provState, String city, String area) {
        if (locationRepository.findOne(locationCode) == null) {
            log.info("Creating {}", locationRepository.save(Location.builder(locationCode, country, provState, city, area).build()));
        }
    }

    private void buildMapPlot(String locationCode, String postalCode, Double latitude, Double longitude) {
        if (mapPlotRepository.findOne(postalCode) == null) {
            log.info("Creating {}", mapPlotRepository.save(MapPlot.builder(postalCode, latitude, longitude, locationRepository.findOne(locationCode)).build()));
        }
    }

    /**
     * Build Canadian Map
     */
    private void buildCanadianMap() {
        /*
        *      Alberta
        */
        buildLocation("CLGRY", "Canada", "Alberta", "Calgary", null);
        buildMapPlot("CLGRY", "T2P",    51.071, -113.693);
        buildMapPlot("CLGRY", "T2K",    51.111, -114.048);
        buildMapPlot("CLGRY", "T2W",    50.951, -114.359);
        buildMapPlot("CLGRY", "T2X",    50.884, -114.033);
        buildMapPlot("CLGRY", "T3M",    50.88, -113.955);
        buildMapPlot("CLGRY", "T3N",    51.163, 113.954);
        buildMapPlot("CLGRY", "T3P",    51.207, -114.135);
        buildMapPlot("CLGRY", "T3R",    51.202, -114.245);
        buildMapPlot("CLGRY", "T1Y",    51.082, -113.958);
        buildMapPlot("CLGRY", "T2E",    51.088, -114.021);
        buildMapPlot("CLGRY", "T2H",    50.989, -114.052);
        buildMapPlot("CLGRY", "T2L",    51.105, -114.115);
        buildMapPlot("CLGRY", "T2N",    51.062, -114.116);
        buildMapPlot("CLGRY", "T2R",    51.041, -114.076);
        buildMapPlot("CLGRY", "T2S",    51.023, -114.071);
        buildMapPlot("CLGRY", "T2T",    51.024, -114.1);
        buildMapPlot("CLGRY", "T2Y",    50.909, -114.103);
        buildMapPlot("CLGRY", "T3C",    51.05, -114.139);
        buildMapPlot("CLGRY", "T3E",    50.99, -114.157);
        buildMapPlot("CLGRY", "T3J",    51.119, -113.947);
        buildMapPlot("CLGRY", "T3L",    51.147, -114.313);
        buildMapPlot("CLGRY", "T2C",    50.987, -113.963);
        buildMapPlot("CLGRY", "T2G",    51.027, -114.035);
        buildMapPlot("CLGRY", "T2M",    51.133, -113.856);
        buildMapPlot("CLGRY", "T2V",    50.982, -114.1);
        buildMapPlot("CLGRY", "T2Z",    50.928, 113.968);
        buildMapPlot("CLGRY", "T3G",    51.139, -114.201);
        buildMapPlot("CLGRY", "T3H",    51.156, -114.057);
        buildMapPlot("CLGRY", "T3S",    51.05, -114.085);
        buildMapPlot("CLGRY", "T2J",    50.848, -114.196);
        buildMapPlot("CLGRY", "T2A",    51.049, -113.956);
        buildMapPlot("CLGRY", "T2B",    51.021, -113.981);
        buildMapPlot("CLGRY", "T3A",    51.126, 114.142);
        buildMapPlot("CLGRY", "T3B",    51.091, -114.207);
        buildMapPlot("CLGRY", "T3K",    51.156, -114.057);

        buildLocation("EDMTN", "Canada", "Alberta", "Edmonton", null);
        buildMapPlot("EDMTN", "T5C",    53.6, -113.455);
        buildMapPlot("EDMTN", "T5J",    53.543, -113.497);
        buildMapPlot("EDMTN", "T5V",    53.585, -113.622);
        buildMapPlot("EDMTN", "T5W",    53.565, -113.402);
        buildMapPlot("EDMTN", "T5X",    53.631, -113.524);
        buildMapPlot("EDMTN", "T6J",    53.456, -113.521);
        buildMapPlot("EDMTN", "T6M",    53.459, -113.655);
        buildMapPlot("EDMTN", "T6N",    53.465, -113.478);
        buildMapPlot("EDMTN", "T6R",    53.457, -113.58);
        buildMapPlot("EDMTN", "T6T",    53.462, -113.371);
        buildMapPlot("EDMTN", "T6V",    53.611, -113.575);
        buildMapPlot("EDMTN", "T6W",    53.418,-113.579);
        buildMapPlot("EDMTN", "T6X",    53.415, -113.418);
        buildMapPlot("EDMTN", "T5T",    53.519, -113.658);
        buildMapPlot("EDMTN", "T5B",    53.735, -113.337);
        buildMapPlot("EDMTN", "T5K",    53.537, -113.51);
        buildMapPlot("EDMTN", "T5N",    53.544, -113.557);
        buildMapPlot("EDMTN", "T5P",    53.546, -113.596);
        buildMapPlot("EDMTN", "T5R",    53.518, -113.58);
        buildMapPlot("EDMTN", "T5S",    53.563, -113.67);
        buildMapPlot("EDMTN", "T5Y",    53.658, -113.361);
        buildMapPlot("EDMTN", "T5Z",    53.637, -113.467);
        buildMapPlot("EDMTN", "T6C",    53.522, -113.459);
        buildMapPlot("EDMTN", "T6E",    53.491, -113.48);
        buildMapPlot("EDMTN", "T6G",    53.521, -113.532);
        buildMapPlot("EDMTN", "T6K",    53.461, -113.452);
        buildMapPlot("EDMTN", "T6L",    53.459, -113.414);
        buildMapPlot("EDMTN", "T6S",    53.581, -113.337);
        buildMapPlot("EDMTN", "T5G",    53.571, -113.505);
        buildMapPlot("EDMTN", "T5L",    53.585, -113.553);
        buildMapPlot("EDMTN", "T5M",    53.563, -113.566);
        buildMapPlot("EDMTN", "T6P",    53.508, -113.372);
        buildMapPlot("EDMTN", "T6B",    53.513, -113.419);
        buildMapPlot("EDMTN", "T6H",    53.375, -113.458);
        buildMapPlot("EDMTN", "T5H",    53.551, -113.492);
        buildMapPlot("EDMTN", "T6A",    53.548, -113.432);
        buildMapPlot("EDMTN", "T5A",    53.593, -113.408);
        buildMapPlot("EDMTN", "T5E",    53.745, -113.447);

        buildLocation("FTMCM", "Canada", "Alberta", "Fort McMurray", null);
        buildMapPlot("FTMCM", "T9K",     56.754, -111.435);
        buildMapPlot("FTMCM", "T9J",     56.707, -111.377);
        buildMapPlot("FTMCM", "T9H",     56.664, -111.136);

        buildLocation("LTHBR", "Canada", "Alberta", "Lethbridge", null);
        buildMapPlot("LTHBR", "T1K",     49.651, -112.835);
        buildMapPlot("LTHBR", "T1H",     49.7, -112.819);
        buildMapPlot("LTHBR", "T1J",     49.658, -112.748);

        buildLocation("MEDHT", "Canada", "Alberta", "Medicine Hat", null);
        buildMapPlot("MEDHT", "T1C",     50.05, -110.668);
        buildMapPlot("MEDHT", "T1A",     50.082, -110.579);
        buildMapPlot("MEDHT", "T1B",     49.835, -110.52);

        buildLocation("PCERC", "Canada", "Alberta", "Peace River Country", null);
        buildMapPlot("PCERC", "T8S",     56.25, -117.286);

        buildLocation("REDDR", "Canada", "Alberta", "Red Deer", null);
        buildMapPlot("REDDR", "T4N",     52.232, -113.836);
        buildMapPlot("REDDR", "T4P",     52.295, -113.807);
        buildMapPlot("REDDR", "T4R",     52.242, -113.778);
        buildMapPlot("REDDR", "T4E",     52.277, -113.706);

        buildLocation("GRDPR", "Canada", "Alberta", "Grande Prairie", null);
        buildMapPlot("GRDPR", "T8V",     55.181, -118.91);
        buildMapPlot("GRDPR", "T8W",     55.13, -118.795);
        buildMapPlot("GRDPR", "T8X",     55.183, -118.746);

        buildLocation("STALT", "Canada", "Alberta", "St. Albert", null);
        buildMapPlot("STALT", "T8T",     53.633, -113.635);
        buildMapPlot("STALT", "T8N",     53.633,-113.635);

        buildLocation("AIRDE", "Canada", "Alberta", "Airdrie", null);
        buildMapPlot("AIRDE", "T4A",     51.28, -113.988);
        buildMapPlot("AIRDE", "T4B",     51.308, -114.04);

        /*
        *      British Columbia
        */
        buildLocation("CARIB", "Canada", "British Columbia", "Cariboo", null);
        buildMapPlot("CARIB", "V0K", 50.7372, -121.2713);
        buildMapPlot("CARIB", "V0L", 52.0829, -123.2779);
        buildMapPlot("CARIB", "V2J", 52.9287, -122.9965);
        buildMapPlot("CARIB", "V2G", 52.1417, -122.1417);

        buildLocation("CMXVL", "Canada", "British Columbia", "Comox Valley", null);
        buildMapPlot("CMXVL", "V9M", 49.6728, -124.947);
        buildMapPlot("CMXVL", "V9J", 49.8329, -125.1293);
        buildMapPlot("CMXVL", "V0R", 49.6188, -125.0313);
        buildMapPlot("CMXVL", "V9N", 49.6841, -124.9904);

        buildLocation("FRAVL", "Canada", "British Columbia", "Fraser Valley", "Chilliwack");
        buildMapPlot("FRAVL", "V2P",  49.184, -121.905);
        buildMapPlot("FRAVL", "V2R",  49.076, -121.988);
        buildMapPlot("FRAVL", "V4Z",  49.079, -121.61);

        buildLocation("FRAVL", "Canada", "British Columbia", "Fraser Valley", "Abbotsford");
        buildMapPlot("FRAVL", "V2S",  49.042, -122.283);
        buildMapPlot("FRAVL", "V2T",  49.038, -122.349);
        buildMapPlot("FRAVL", "V3G",  49.075, -122.178);
        buildMapPlot("FRAVL", "V4X",  49.086, -122.403);

        buildLocation("KAMLO", "Canada", "British Columbia", "Kamloops", null);
        buildMapPlot("KAMLO", "V1S", 50.6553, -120.3811);

        buildLocation("KLOKA", "Canada", "British Columbia", "Kelowna/Okanagan", null);
        buildMapPlot("KLOKA", "V1V", 49.929, -119.4676);
        buildMapPlot("KLOKA", "V1W", 49.842, -119.4903);
        buildMapPlot("KLOKA", "V1X", 49.8754, -119.3958);
        buildMapPlot("KLOKA", "V1Y", 49.8803, -119.5004);
        buildMapPlot("KLOKA", "V1Z", 49.88, -119.5355);
        buildMapPlot("KLOKA", "V0E", 50.9647, -119.1638);
        buildMapPlot("KLOKA", "V0H", 49.409, -119.005);

        buildLocation("KOOTE", "Canada", "British Columbia", "Kootenays", null);
        buildMapPlot("KOOTE", "V0B", 49.4539, -116.763);
        buildMapPlot("KOOTE", "V0G", 49.7332, -116.913);

        buildLocation("NANAI", "Canada", "British Columbia", "Nanaimo", null);
        buildMapPlot("NANAI", "V9R", 49.136, -123.9483);
        buildMapPlot("NANAI", "V9S", 49.174, -123.9422);
        buildMapPlot("NANAI", "V9T", 49.2079, -123.979);
        buildMapPlot("NANAI", "V9V", 49.2477, -124.0501);

        buildLocation("PRNGE", "Canada", "British Columbia", "Prince George", null);
        buildMapPlot("PRNGE", "V2L",  53.9112, -122.728);
        buildMapPlot("PRNGE", "V2K", 53.9313, -122.7823);
        buildMapPlot("PRNGE", "V2M", 53.928, -122.7878);
        buildMapPlot("PRNGE", "V2N", 53.9103, -122.7835);

        buildLocation("SKBUL", "Canada", "British Columbia", "Skeena/Bulkley", null);
        buildMapPlot("SKBUL", "V0V",  53.4242, -129.263);

        buildLocation("SUNCT", "Canada", "British Columbia", "Sunshine Coast", null);
        buildMapPlot("SUNCT", "V0N",  50.5899, -126.9517);

        buildLocation("VANBN", "Canada", "British Columbia", "Vancouver", "Burnaby/Newwest");
        buildMapPlot("VANBN", "V5A",  49.2869, -122.958);
        buildMapPlot("VANBN", "V5B",  49.2846, -122.9914);
        buildMapPlot("VANBN", "V5C",  49.2848, -123.0222);
        buildMapPlot("VANBN", "V5E",  49.2124, -122.9696);
        buildMapPlot("VANBN", "V5G",  49.2591, -123.0226);
        buildMapPlot("VANBN", "V5H",  49.2371, -123.0229);
        buildMapPlot("VANBN", "V5J",  49.2218, -123.022);
        buildMapPlot("VANBN", "V3N",  49.2201, -122.9478);

        buildLocation("VANDS", "Canada", "British Columbia", "Vancouver", "Delta");
        buildMapPlot("VANDS", "V4C",  49.1348, -122.9131);
        buildMapPlot("VANDS", "V4E",  49.0482, -122.9587);
        buildMapPlot("VANDS", "V4G",  49.1367, -123.0115);
        buildMapPlot("VANDS", "V4K",  49.0798, -123.0882);
        buildMapPlot("VANDS", "V4L",  49.0023, -123.0368);
        buildMapPlot("VANDS", "V4M",  49.0025, -123.0746);

        buildLocation("VANDS", "Canada", "British Columbia", "Vancouver", "Surrey");
        buildMapPlot("VANDS", "V3R",  49.1641, -122.8193);
        buildMapPlot("VANDS", "V3S",  49.1011, -122.8141);
        buildMapPlot("VANDS", "V3T",  49.1783, -122.8665);
        buildMapPlot("VANDS", "V3V",  49.1647, -122.8487);
        buildMapPlot("VANDS", "V3W",  49.0992, -122.8691);
        buildMapPlot("VANDS", "V3X",  49.1173, -122.8234);
        buildMapPlot("VANDS", "V4A",  49.0168, -122.7738);
        buildMapPlot("VANDS", "V4N",  49.1636, -122.7677);
        buildMapPlot("VANDS", "V4P",  49.0499, -122.804);
        buildMapPlot("VANDS", "V4N",  49.1636, -122.7677);

        buildLocation("VANDS", "Canada", "British Columbia", "Vancouver", "Langley");
        buildMapPlot("VANDS", "V2Y",  49.1175, -122.6684);
        buildMapPlot("VANDS", "V2Z",  49.0501, -122.6745);
        buildMapPlot("VANDS", "V3A",  49.0764, -122.6797);
        buildMapPlot("VANDS", "V4W",  49.1307, -122.5369);
        buildMapPlot("VANDS", "V1M",  49.164, -122.656);

        buildLocation("VANNS", "Canada", "British Columbia", "Vancouver", "North Shore");
        buildMapPlot("VANNS", "V7P",  49.3162, -123.0936);

        buildLocation("VANTP", "Canada", "British Columbia", "Vancouver", "Tricities/Pitt/Maple");
        buildMapPlot("VANTP", "V6B",  49.2836, -123.1041);

        buildLocation("VANRI", "Canada", "British Columbia", "Vancouver", "Richmond");
        buildMapPlot("VANRI", "V6V",  49.1699, -123.0912);
        buildMapPlot("VANRI", "V6W",  49.1261, -123.0897);
        buildMapPlot("VANRI", "V6X",  49.1701, -123.1438);
        buildMapPlot("VANRI", "V6Y",  49.1483, -123.1469);
        buildMapPlot("VANRI", "V7A",  49.1467, -123.1463);
        buildMapPlot("VANRI", "V7B",  49.178, -123.1701);
        buildMapPlot("VANRI", "V7C",  49.1745, -123.1978);
        buildMapPlot("VANRI", "V7E",  49.1476, -123.1897);

        buildLocation("VANCO", "Canada", "British Columbia", "Vancouver", "Vancouver");
        buildMapPlot("VANCO", "V5K",  49.293, -123.0489);
        buildMapPlot("VANCO", "V5L",  49.2835, -123.0786);
        buildMapPlot("VANCO", "V5M",  49.2695, -123.0556);
        buildMapPlot("VANCO", "V5N",  49.2699, -123.0765);
        buildMapPlot("VANCO", "V5P",  49.2393, -123.0729);
        buildMapPlot("VANCO", "V5R",  49.2499, -123.0556);
        buildMapPlot("VANCO", "V5S",  49.2286, -123.057);
        buildMapPlot("VANCO", "V5T",  49.2701, -123.1038);
        buildMapPlot("VANCO", "V5V",  49.2558, -123.1037);
        buildMapPlot("VANCO", "V5W",  49.2396, -123.0984);
        buildMapPlot("VANCO", "V5X",  49.2249, -123.1052);
        buildMapPlot("VANCO", "V5Y",  49.2702, -123.1017);
        buildMapPlot("VANCO", "V5Z",  49.2658, -123.1151);
        buildMapPlot("VANCO", "V6A",  49.2862, -123.0925);
        buildMapPlot("VANCO", "V6C",  49.2857, -123.1142);
        buildMapPlot("VANCO", "V6E",  49.2848, -123.1228);
        buildMapPlot("VANCO", "V6G",  49.289, -123.1294);
        buildMapPlot("VANCO", "V6H",  49.2661, -123.1276);
        buildMapPlot("VANCO", "V6J",  49.2768, -123.1469);
        buildMapPlot("VANCO", "V6K",  49.2738, -123.161);
        buildMapPlot("VANCO", "V6L",  49.2571, -123.1662);
        buildMapPlot("VANCO", "V6M",  49.2417, -123.1293);
        buildMapPlot("VANCO", "V6N",  49.2376, -123.1639);
        buildMapPlot("VANCO", "V6P",  49.2254, -123.1176);
        buildMapPlot("VANCO", "V6R",  49.273, -123.185);
        buildMapPlot("VANCO", "V6S",  49.2574, -123.1836);
        buildMapPlot("VANCO", "V6T",  49.2765, -123.2177);
        buildMapPlot("VANCO", "V6Z",  49.2814, -123.12);
        buildMapPlot("VANCO", "V7G",  49.304, -122.9689);
        buildMapPlot("VANCO", "V7H",  49.3011, -123.0205);
        buildMapPlot("VANCO", "V7J",  49.3016, -123.0309);
        buildMapPlot("VANCO", "V7K",  49.3322, -123.0518);
        buildMapPlot("VANCO", "V7L",  49.3042, -123.0651);
        buildMapPlot("VANCO", "V7M",  49.3111, -123.0798);
        buildMapPlot("VANCO", "V7N",  49.3325, -123.0674);
        buildMapPlot("VANCO", "V7R",  49.3328, -123.1043);
        buildMapPlot("VANCO", "V7S",  49.3585, -123.1186);
        buildMapPlot("VANCO", "V7T",  49.324, -123.1036);
        buildMapPlot("VANCO", "V7V",  49.3271, -123.1578);
        buildMapPlot("VANCO", "V7W",  49.3465, -123.238);
        buildMapPlot("VANCO", "V7X",  49.2935, -123.1162);
        buildMapPlot("VANCO", "V7Y",  49.2816, -123.1247);

        buildLocation("VICTO", "Canada", "British Columbia", "Victoria", null);
        buildMapPlot("VICTO", "V8T",  48.4278, -123.3574);
        buildMapPlot("VICTO", "V8V",  48.4192, -123.3856);
        buildMapPlot("VICTO", "V8W",  48.4202, -123.3671);

        buildLocation("WHSSQ", "Canada", "British Columbia", "Whistler/Squamish", null);
        buildMapPlot("WHSSQ", "V0N",  50.5899, -126.9517);
        buildMapPlot("WHSSQ", "V8B",  49.7497, -123.136);

        /*
        *      Manitoba
        */
        buildLocation("BRNDO", "Canada", "Manitoba", "Brandon", null);
        buildMapPlot("BRNDO", "R0K",  49.555, -99.715);
        buildMapPlot("BRNDO", "R7C",  49.882, -99.965);
        buildMapPlot("BRNDO", "R7A",  49.817, -99.957);
        buildMapPlot("BRNDO", "R7B",  49.842, -99.983);

        buildLocation("WINPG", "Canada", "Manitoba", "Winnipeg", null);
        buildMapPlot("WINPG", "R2C",  49.925, -96.956);
        buildMapPlot("WINPG", "R2R",  49.938, -97.216);
        buildMapPlot("WINPG", "R3S",  49.826, -97.293);
        buildMapPlot("WINPG", "R3W",  49.914, -97.04);
        buildMapPlot("WINPG", "R2G",  49.941, -97.057);
        buildMapPlot("WINPG", "R2H",  49.885,-97.119);
        buildMapPlot("WINPG", "R2J",  49.866, -97.063);
        buildMapPlot("WINPG", "R2K",  49.92, -97.083);
        buildMapPlot("WINPG", "R2L",  49.907, 97.1);
        buildMapPlot("WINPG", "R2M",  49.84, -97.115);
        buildMapPlot("WINPG", "R2P",  49.97, -97.155);
        buildMapPlot("WINPG", "R2V",  49.955, -97.111);
        buildMapPlot("WINPG", "R2W",  49.92, -97.135);
        buildMapPlot("WINPG", "R2X",  49.932, -97.173);
        buildMapPlot("WINPG", "R2Y",  49.907, -97.295);
        buildMapPlot("WINPG", "R3G",  49.888, -97.181);
        buildMapPlot("WINPG", "R3H",  49.903, -97.207);
        buildMapPlot("WINPG", "R3J",  49.897, -97.24);
        buildMapPlot("WINPG", "R3L",  49.867, -97.136);
        buildMapPlot("WINPG", "R3M",  49.863, -97.167);
        buildMapPlot("WINPG", "R3P",  49.842, -97.218);
        buildMapPlot("WINPG", "R3R",  49.855, -97.287);
        buildMapPlot("WINPG", "R3V",  49.746, -97.175);
        buildMapPlot("WINPG", "R3Y",  49.789, -97.215);
        buildMapPlot("WINPG", "R3E",  49.908, -97.178);
        buildMapPlot("WINPG", "R3T",  49.814, -97.153);
        buildMapPlot("WINPG", "R3X",  50.011, -97.218);
        buildMapPlot("WINPG", "R3A",  49.904, -97.149);
        buildMapPlot("WINPG", "R2N",  49.784, -97.097);
        buildMapPlot("WINPG", "R3B",  49.898, -97.14);
        buildMapPlot("WINPG", "R3N",  49.863, -97.196);

        /*
        *      New Brunswick
        */
        buildLocation("FRDRI", "Canada", "New Brunswick", "Fredericton", null);
        buildMapPlot("FRDRI", "E5L",   45.66, -66.614);
        buildMapPlot("FRDRI", "E3C",   45.902, -66.706);
        buildMapPlot("FRDRI", "E3B",   45.812, -66.676);
        buildMapPlot("FRDRI", "E3G",   45.945, -66.666);
        buildMapPlot("FRDRI", "E3A",   46.04, -66.386);

        buildLocation("MNCTN", "Canada", "New Brunswick", "Moncton", null);
        buildMapPlot("MNCTN", "E1C",    46.099, -64.804);
        buildMapPlot("MNCTN", "E1E",    46.077, -64.853);
        buildMapPlot("MNCTN", "E1A",    46.097, -64.709);
        buildMapPlot("MNCTN", "E1G",    46.18, -64.948);

        buildLocation("SAIJN", "Canada", "New Brunswick", "Saint John", null);
        buildMapPlot("SAIJN", "E2H",    45.35, -66.01);
        buildMapPlot("SAIJN", "E2J",    45.256, -65.943);
        buildMapPlot("SAIJN", "E2L",    45.272, -66.058);
        buildMapPlot("SAIJN", "E2M",    45.235, -66.175);
        buildMapPlot("SAIJN", "E2N",    45.318, -65.927);
        buildMapPlot("SAIJN", "E2P",    45.243, -65.985);
        buildMapPlot("SAIJN", "E2R",    46.037, -66.037);
        buildMapPlot("SAIJN", "E2S",    45.337, -65.813);
        buildMapPlot("SAIJN", "E2K",    45.273, -66.068);

        /*
        *      Newfoundland and Labrador
        */
        buildLocation("CRNBR", "Canada", "Newfoundland and Labrador", "Corner Brook", null);
        buildMapPlot("CRNBR", "A2H",    48.95, -57.952);

        buildLocation("STJON", "Canada", "Newfoundland and Labrador", "St. John's", null);
        buildMapPlot("STJON", "A1C",    47.512, -52.673);
        buildMapPlot("STJON", "A1E",   47.533, -52.759);
        buildMapPlot("STJON", "A1G",    47.506, 52.76);
        buildMapPlot("STJON", "A1H",   47.483, -52.841);
        buildMapPlot("STJON", "A1B",    47.57, -52.78);
        buildMapPlot("STJON", "A1A",    47.599, -52.693);

        /*
        *      Northwest Territories
        */
        buildLocation("YLWKN", "Canada", "Northwest Territories", "Yellowknife", null);
        buildMapPlot("YLWKN", "X0E",    62.4043, -110.7417);
        buildMapPlot("YLWKN", "X0G",    60.25, -123.41);
        buildMapPlot("YLWKN", "X1A",    62.4725, -114.3417);

        /*
        *      Nova Scotia
        */
        buildLocation("HLIFX", "Canada", "Nova Scotia", "Halifax", null);
        buildMapPlot("HLIFX", "B3L",   44.651, -63.615);
        buildMapPlot("HLIFX", "B3R",   44.594, -63.603);
        buildMapPlot("HLIFX", "B3S",   44.653, -63.667);
        buildMapPlot("HLIFX", "B3K",   44.662, -63.602);
        buildMapPlot("HLIFX", "B3M",   44.678, -63.658);
        buildMapPlot("HLIFX", "B3N",   44.631, -63.641);
        buildMapPlot("HLIFX", "B3P",   44.614, -63.595);
        buildMapPlot("HLIFX", "B3J",   44.645, -63.576);
        buildMapPlot("HLIFX", "B3H",   44.634, -63.582);

        buildLocation("SYDNY", "Canada", "Nova Scotia", "Sydney", null);
        buildMapPlot("SYDNY", "B1L",   46.063, -60.204);
        buildMapPlot("SYDNY", "B1M",   46.127, -60.07);
        buildMapPlot("SYDNY", "B1N",   46.197, -60.156);
        buildMapPlot("SYDNY", "B1R",   46.128, -60.292);
        buildMapPlot("SYDNY", "B1S",   46.111, -60.209);
        buildMapPlot("SYDNY", "B1P",   46.134, -60.175);
        buildMapPlot("SYDNY", "B2A",   46.117, -60.377);
        buildMapPlot("SYDNY", "B1V",   46.244, -60.231);

        /*
        *      Nunavut Territory
        */
        buildLocation("IQALU", "Canada", "Nunavut Territory", "Iqaluit", null);
        buildMapPlot("IQALU", "X0A",    70.4643, -68.4789);

        /*
        *      Ontario
        */
        buildLocation("BARRI", "Canada", "Ontario", "Barrie", null);
        buildMapPlot("BARRI", "L4M", 44.3885, -79.6886);
        buildMapPlot("BARRI", "L4N", 44.3891, -79.6901);
        buildMapPlot("BARRI", "L9S", 44.2871, -79.6703);

        buildLocation("BEGST", "Canada", "Ontario", "Bradford/E.G./Stouffville", null);
        buildMapPlot("BEGST", "L3Z", 44.1208, -79.5656);
        buildMapPlot("BEGST", "L0G", 44.1398, -79.4476);
        buildMapPlot("BEGST", "L4A", 43.9707, -79.2503);
        buildMapPlot("BEGST", "L9N", 44.1315, -79.4823);

        buildLocation("BELVL", "Canada", "Ontario", "Belleville", null);
        buildMapPlot("BELVL", "K8N", 44.1607, -77.369);
        buildMapPlot("BELVL", "K8P", 44.1605, -77.3846);
        buildMapPlot("BELVL", "K8R", 44.1312, -77.4521);

        buildLocation("BOWPH", "Canada", "Ontario", "Bowmanville/Port Hope", null);
        buildMapPlot("BOWPH", "L1A", 43.9427, -78.2944);
        buildMapPlot("BOWPH", "L1B", 43.8966, -78.6309);
        buildMapPlot("BOWPH", "L1C", 43.9014, -78.6755);
        buildMapPlot("BOWPH", "L1E", 43.914, -78.6925);
        buildMapPlot("BOWPH", "L0A", 44.1836, -78.5563);
        buildMapPlot("BOWPH", "L0B", 44.0286, -79.0015);

        buildLocation("BRAMP", "Canada", "Ontario", "Brampton", null);
        buildMapPlot("BRAMP", "L6P", 43.7794, -79.7284);
        buildMapPlot("BRAMP", "L6R", 43.7494, -79.7511);
        buildMapPlot("BRAMP", "L6S", 43.7153, -79.7321);
        buildMapPlot("BRAMP", "L6T", 43.6892, -79.7079);
        buildMapPlot("BRAMP", "L6V", 43.7074, -79.7853);
        buildMapPlot("BRAMP", "L6W", 43.6746, -79.724);
        buildMapPlot("BRAMP", "L6X", 43.6858, -79.7602);
        buildMapPlot("BRAMP", "L6Y", 43.6699, -79.7444);
        buildMapPlot("BRAMP", "L6Z", 43.7304, -79.8042);
        buildMapPlot("BRAMP", "L7A", 43.7023, -79.7909);

        buildLocation("BRTWO", "Canada", "Ontario", "Brantford/Woodstock", null);
        buildMapPlot("BRTWO", "N3P", 43.1884, -80.2422);
        buildMapPlot("BRTWO", "N3R", 43.1501, -80.2766);
        buildMapPlot("BRTWO", "N3S", 43.1242, -80.2412);
        buildMapPlot("BRTWO", "N3T", 43.1094, -80.275);
        buildMapPlot("BRTWO", "N3V", 43.1704, -80.2937);
        buildMapPlot("BRTWO", "N4S", 43.1277, -80.7743);
        buildMapPlot("BRTWO", "N4T", 43.1477, -80.7285);
        buildMapPlot("BRTWO", "N4V", 43.1127, -80.7368);

        buildLocation("BUROA", "Canada", "Ontario", "Burlington/Oakville", null);
        buildMapPlot("BUROA", "L7L", 43.3479, -79.7593);
        buildMapPlot("BUROA", "L7M", 43.3585, -79.8093);
        buildMapPlot("BUROA", "L7N", 43.3336, -79.7771);
        buildMapPlot("BUROA", "L7P", 43.3503, -79.8117);
        buildMapPlot("BUROA", "L7R", 43.3248, -79.7957);
        buildMapPlot("BUROA", "L7S", 43.304, -79.7991);
        buildMapPlot("BUROA", "L7T", 43.3018, -79.8497);
        buildMapPlot("BUROA", "L6H", 43.4543, -79.6921);
        buildMapPlot("BUROA", "L6J", 43.4427, -79.6664);
        buildMapPlot("BUROA", "L6K", 43.4401, -79.669);
        buildMapPlot("BUROA", "L6L", 43.4037, -79.6934);
        buildMapPlot("BUROA", "L6M", 43.4453, -79.7095);

        buildLocation("CALED", "Canada", "Ontario", "Caledon", null);
        buildMapPlot("CALED", "L7C", 43.7467, -79.8304);
        buildMapPlot("CALED", "L7E", 43.8628, -79.7147);
        buildMapPlot("CALED", "L7K", 43.8609, -79.9958);

        buildLocation("CHAKT", "Canada", "Ontario", "Chatham-Kent", null);
        buildMapPlot("CHAKT", "N7L", 42.4029, -82.1941);
        buildMapPlot("CHAKT", "N7M", 42.3997, -82.1996);

        buildLocation("CRNWL", "Canada", "Ontario", "Cornwall", null);
        buildMapPlot("CRNWL", "K6H", 45.0186, -74.7129);
        buildMapPlot("CRNWL", "K6J", 45.0149, -74.7279);
        buildMapPlot("CRNWL", "K6K", 45.0607, -74.7542);

        buildLocation("GORGI", "Canada", "Ontario", "Georgina", null);
        buildMapPlot("GORGI", "L4P", 44.2421, -79.4818);
        buildMapPlot("GORGI", "L0E", 44.2406, -79.357);

        buildLocation("GUELP", "Canada", "Ontario", "Guelph", null);
        buildMapPlot("GUELP", "N1C", 43.5036, -80.2394);
        buildMapPlot("GUELP", "N1E", 43.5749, -80.2688);
        buildMapPlot("GUELP", "N1G", 43.5325, -80.2531);
        buildMapPlot("GUELP", "N1H", 43.555, -80.2868);
        buildMapPlot("GUELP", "N1K", 43.5156, -80.2827);
        buildMapPlot("GUELP", "N1L", 43.5225, -80.2095);

        buildLocation("HAMIL", "Canada", "Ontario", "Hamilton", null);
        buildMapPlot("HAMIL", "L8E", 43.2318, -79.7696);
        buildMapPlot("HAMIL", "L8G", 43.2298, -79.7722);
        buildMapPlot("HAMIL", "L8H", 43.2369, -79.7991);
        buildMapPlot("HAMIL", "L8J", 43.1907, -79.7878);
        buildMapPlot("HAMIL", "L8K", 43.2424, -79.8192);
        buildMapPlot("HAMIL", "L8L", 43.2645, -79.8664);
        buildMapPlot("HAMIL", "L8M", 43.2522, -79.8489);
        buildMapPlot("HAMIL", "L8N", 43.2566, -79.8683);
        buildMapPlot("HAMIL", "L8P", 43.257, -79.8697);
        buildMapPlot("HAMIL", "L8R", 43.2574, -79.8676);
        buildMapPlot("HAMIL", "L8S", 43.2604, -79.8961);
        buildMapPlot("HAMIL", "L8T", 43.2365, -79.8338);
        buildMapPlot("HAMIL", "L8V", 43.2428, -79.8524);
        buildMapPlot("HAMIL", "L8W", 43.2141, -79.8626);
        buildMapPlot("HAMIL", "L9A", 43.241, -79.8452);
        buildMapPlot("HAMIL", "L9B", 43.2116, -79.8915);
        buildMapPlot("HAMIL", "L9C", 43.2432, -79.876);

        buildLocation("KINVA", "Canada", "Ontario", "King/Vaughan", null);
        buildMapPlot("KINVA", "L7B", 43.9327, -79.5104);
        buildMapPlot("KINVA", "L6A", 43.857, -79.514);
        buildMapPlot("KINVA", "L4K", 43.7848, -79.4811);
        buildMapPlot("KINVA", "L4J", 43.7964, -79.4278);
        buildMapPlot("KINVA", "L3T", 43.7984, -79.4186);
        buildMapPlot("KINVA", "L0J", 43.7788, -79.4991);
        buildMapPlot("KINVA", "L4H", 43.8084, -79.6089);

        buildLocation("KISTO", "Canada", "Ontario", "Kingston", null);
        buildMapPlot("KISTO", "K7K", 44.2322, -76.4799);
        buildMapPlot("KISTO", "K7L", 44.231, -76.4791);
        buildMapPlot("KISTO", "K7M", 44.2274, -76.5134);
        buildMapPlot("KISTO", "K7P", 44.2507, -76.5828);

        buildLocation("KIWLC", "Canada", "Ontario", "Kitchener/Waterloo/Cambridge", null);
        buildMapPlot("KIWLC", "N2A", 43.4353, -80.4527);
        buildMapPlot("KIWLC", "N2B", 43.448, -80.4589);
        buildMapPlot("KIWLC", "N2C", 43.4346, -80.4532);
        buildMapPlot("KIWLC", "N2E", 43.4236, -80.48);
        buildMapPlot("KIWLC", "N2G", 43.4497, -80.4893);
        buildMapPlot("KIWLC", "N2H", 43.4487, -80.4849);
        buildMapPlot("KIWLC", "N2J", 43.4613, -80.507);
        buildMapPlot("KIWLC", "N2K", 43.4801, -80.4801);
        buildMapPlot("KIWLC", "N2L", 43.4529, -80.5281);
        buildMapPlot("KIWLC", "N2M", 43.4422, -80.4968);
        buildMapPlot("KIWLC", "N2N", 43.4241, -80.5214);
        buildMapPlot("KIWLC", "N2P", 43.3938, -80.4443);
        buildMapPlot("KIWLC", "N2R", 43.3965, -80.4575);
        buildMapPlot("KIWLC", "N2T", 43.4511, -80.5572);
        buildMapPlot("KIWLC", "N2V", 43.5036, -80.5413);
        buildMapPlot("KIWLC", "N3A", 43.4161, -80.688);
        buildMapPlot("KIWLC", "N3B", 43.5852, -80.5662);
        buildMapPlot("KIWLC", "N3C", 43.4317, -80.3112);
        buildMapPlot("KIWLC", "N3E", 43.4244, -80.3364);
        buildMapPlot("KIWLC", "N3H", 43.4061, -80.3503);

        buildLocation("LONDO", "Canada", "Ontario", "London", null);
        buildMapPlot("LONDO", "N5V", 42.9927, -81.1686);
        buildMapPlot("LONDO", "N5W", 42.9778, -81.1941);
        buildMapPlot("LONDO", "N5X", 43.0303, -81.2676);
        buildMapPlot("LONDO", "N5Y", 43.0093, -81.21);
        buildMapPlot("LONDO", "N5Z", 42.9743, -81.1946);
        buildMapPlot("LONDO", "N6A", 42.9793, -81.2556);
        buildMapPlot("LONDO", "N6B", 42.9759, -81.229);
        buildMapPlot("LONDO", "N6C", 42.9799, -81.2609);
        buildMapPlot("LONDO", "N6E", 42.9419, -81.2475);
        buildMapPlot("LONDO", "N6G", 42.9943, -81.2623);
        buildMapPlot("LONDO", "N6H", 42.9899, -81.2607);
        buildMapPlot("LONDO", "N6J", 42.9797, -81.2639);
        buildMapPlot("LONDO", "N6K", 42.9627, -81.2948);
        buildMapPlot("LONDO", "N6L", 42.9344, -81.2802);
        buildMapPlot("LONDO", "N6M", 42.9922, -81.1398);
        buildMapPlot("LONDO", "N6N", 42.9324, -81.1916);
        buildMapPlot("LONDO", "N6P", 42.9114, -81.2999);

        buildLocation("MARKH", "Canada", "Ontario", "Markham", null);
        buildMapPlot("MARKH", "L3P", 43.8605, -79.3279);
        buildMapPlot("MARKH", "L3R", 43.8591, -79.3215);
        buildMapPlot("MARKH", "L3S", 43.831, -79.2768);

        buildLocation("MIHAL", "Canada", "Ontario", "Milton/Halton Hills", null);
        buildMapPlot("MIHAL", "L7J", 43.634, -80.0491);
        buildMapPlot("MIHAL", "L7G", 43.644, -79.8787);
        buildMapPlot("MIHAL", "L0P", 43.6373, -79.9803);
        buildMapPlot("MIHAL", "L9T", 43.5034, -79.8773);

        buildLocation("GTAMI", "Canada", "Ontario", "Mississauga", null);
        buildMapPlot("GTAMI", "L4V", 43.6879, -79.6072);
        buildMapPlot("GTAMI", "L4W", 43.6272, -79.6222);
        buildMapPlot("GTAMI", "L4X", 43.5996, -79.5664);
        buildMapPlot("GTAMI", "L4Y", 43.5854, -79.583);
        buildMapPlot("GTAMI", "L4Z", 43.6092, -79.6201);
        buildMapPlot("GTAMI", "L5A", 43.5701, -79.5985);
        buildMapPlot("GTAMI", "L5B", 43.5665, -79.6035);
        buildMapPlot("GTAMI", "L5C", 43.5591, -79.6186);
        buildMapPlot("GTAMI", "L5E", 43.571, -79.5668);
        buildMapPlot("GTAMI", "L5G", 43.5581, -79.5738);
        buildMapPlot("GTAMI", "L5H", 43.5472, -79.585);
        buildMapPlot("GTAMI", "L5J", 43.5146, -79.6063);
        buildMapPlot("GTAMI", "L5K", 43.5319, -79.6403);
        buildMapPlot("GTAMI", "L5L", 43.5372, -79.6667);
        buildMapPlot("GTAMI", "L5M", 43.5747, -79.7278);
        buildMapPlot("GTAMI", "L5N", 43.5892, -79.7239);
        buildMapPlot("GTAMI", "L5P", 43.6904, -79.6238);
        buildMapPlot("GTAMI", "L5R", 43.5974, -79.6402);
        buildMapPlot("GTAMI", "L5S", 43.6975, -79.6615);
        buildMapPlot("GTAMI", "L5T", 43.6578, -79.6607);
        buildMapPlot("GTAMI", "L5V", 43.6097, -79.704);
        buildMapPlot("GTAMI", "L5W", 43.6261, -79.729);

        buildLocation("NEWAU", "Canada", "Ontario", "Newmarket/Aurora", null);
        buildMapPlot("NEWAU", "L3X", 44.0464, -79.4874);
        buildMapPlot("NEWAU", "L3Y", 44.0414, -79.4534);
        buildMapPlot("NEWAU", "L4G", 43.9909, -79.4639);

        buildLocation("NIAGR", "Canada", "Ontario", "Niagara Region", null);
        buildMapPlot("NIAGR", "L0S", 43.0796, -79.199);
        buildMapPlot("NIAGR", "L2A", 42.8845, -78.9398);
        buildMapPlot("NIAGR", "L2E", 43.0939, -79.0699);
        buildMapPlot("NIAGR", "L2G", 43.0963, -79.074);
        buildMapPlot("NIAGR", "L2H", 43.1148, -79.1238);
        buildMapPlot("NIAGR", "L2J", 43.1155, -79.0916);
        buildMapPlot("NIAGR", "L2M", 43.2237, -79.2191);
        buildMapPlot("NIAGR", "L2N", 43.1751, -79.2389);
        buildMapPlot("NIAGR", "L2P", 43.1418, -79.2133);
        buildMapPlot("NIAGR", "L2R", 43.1719, -79.227);
        buildMapPlot("NIAGR", "L2S", 43.1275, -79.2631);
        buildMapPlot("NIAGR", "L2T", 43.1334, -79.1989);
        buildMapPlot("NIAGR", "L2V", 43.1017, -79.1997);
        buildMapPlot("NIAGR", "L2W", 43.1743, -79.2744);
        buildMapPlot("NIAGR", "L3B", 42.9859, -79.2232);
        buildMapPlot("NIAGR", "L3C", 42.9989, -79.2466);
        buildMapPlot("NIAGR", "L3K", 42.8754, -79.237);

        buildLocation("OSHWI", "Canada", "Ontario", "Oshawa/Whitby", null);
        buildMapPlot("OSHWI", "L1G", 43.898, -78.8656);
        buildMapPlot("OSHWI", "L1H", 43.8973, -78.8641);
        buildMapPlot("OSHWI", "L1J", 43.8587, -78.8341);
        buildMapPlot("OSHWI", "L1K", 43.9091, -78.8088);
        buildMapPlot("OSHWI", "L1L", 43.9527, -78.8795);
        buildMapPlot("OSHWI", "L1M", 43.9561, -78.9556);
        buildMapPlot("OSHWI", "L1N", 43.8581, -78.9319);
        buildMapPlot("OSHWI", "L1P", 43.8744, -78.9638);
        buildMapPlot("OSHWI", "L1R", 43.9018, -78.9347);

        buildLocation("OTTAW", "Canada", "Ontario", "Ottawa", null);
        buildMapPlot("OTTAW", "K1A", 45.4207, -75.7023);
        buildMapPlot("OTTAW", "K1B", 45.4325, -75.5624);
        buildMapPlot("OTTAW", "K1C", 45.4805, -75.5237);
        buildMapPlot("OTTAW", "K1E", 45.4882, -75.5199);
        buildMapPlot("OTTAW", "K1G", 45.4118, -75.6304);
        buildMapPlot("OTTAW", "K1H", 45.3938, -75.6639);
        buildMapPlot("OTTAW", "K1J", 45.422, -75.6303);
        buildMapPlot("OTTAW", "K1K", 45.4354, -75.6475);
        buildMapPlot("OTTAW", "K1L", 45.44, -75.6524);
        buildMapPlot("OTTAW", "K1M", 45.4461, -75.6744);
        buildMapPlot("OTTAW", "K1N", 45.3176, -75.895);
        buildMapPlot("OTTAW", "K1P", 45.423, -75.702);
        buildMapPlot("OTTAW", "K1R", 45.4, -75.7235);
        buildMapPlot("OTTAW", "K1S", 45.4127, -75.6742);
        buildMapPlot("OTTAW", "K1T", 45.352, -75.6421);
        buildMapPlot("OTTAW", "K1V", 45.3523, -75.6512);
        buildMapPlot("OTTAW", "K1W", 45.436, -75.5471);
        buildMapPlot("OTTAW", "K1X", 45.2884, -75.5992);
        buildMapPlot("OTTAW", "K1Y", 45.399, -75.7304);
        buildMapPlot("OTTAW", "K1Z", 45.3956, -75.7462);
        buildMapPlot("OTTAW", "K2A", 45.3778, -75.7632);
        buildMapPlot("OTTAW", "K2B", 45.3679, -75.7888);
        buildMapPlot("OTTAW", "K2C", 45.3594, -75.7523);
        buildMapPlot("OTTAW", "K2E", 45.3353, -75.7209);
        buildMapPlot("OTTAW", "K2G", 45.3286, -75.7703);
        buildMapPlot("OTTAW", "K2H", 45.3155, -75.837);
        buildMapPlot("OTTAW", "K2J", 45.2882, -75.7566);
        buildMapPlot("OTTAW", "K2K", 45.3339, -75.9098);
        buildMapPlot("OTTAW", "K2L", 45.3125, -75.8838);
        buildMapPlot("OTTAW", "K2M", 45.2884, -75.8648);
        buildMapPlot("OTTAW", "K2P", 45.4129, -75.6901);
        buildMapPlot("OTTAW", "K2R", 45.2776, -75.7902);
        buildMapPlot("OTTAW", "K2S", 45.2573, -75.9153);
        buildMapPlot("OTTAW", "K2T", 45.3121, -75.9217);
        buildMapPlot("OTTAW", "K2V", 45.3018, -75.9081);
        buildMapPlot("OTTAW", "K2W", 45.3564, -75.9445);
        buildMapPlot("OTTAW", "K4A", 45.4769, -75.4835);
        buildMapPlot("OTTAW", "K4B", 45.4251, -75.4288);
        buildMapPlot("OTTAW", "K4C", 45.5177, -75.4108);
        buildMapPlot("OTTAW", "K4M", 45.2289, -75.6817);
        buildMapPlot("OTTAW", "K4P", 45.258, -75.5762);

        buildLocation("OWNSO", "Canada", "Ontario", "Owen Sound", null);
        buildMapPlot("OWNSO", "N4K", 44.5519, -80.9385);
        buildMapPlot("OWNSO", "N4L", 44.6079, -80.5922);

        buildLocation("PICAJ", "Canada", "Ontario", "Pickering/Ajax", null);
        buildMapPlot("PICAJ", "L1V", 43.8087, -79.1307);
        buildMapPlot("PICAJ", "L1W", 43.8125, -79.0827);
        buildMapPlot("PICAJ", "L1X", 43.8449, -79.0996);
        buildMapPlot("PICAJ", "L1Y", 43.9903, -79.1004);
        buildMapPlot("PICAJ", "L1S", 43.8265, -78.9991);
        buildMapPlot("PICAJ", "L1T", 43.8603, -79.0434);
        buildMapPlot("PICAJ", "L1Z", 43.8627, -79.0136);

        buildLocation("PTRBO", "Canada", "Ontario", "Peterborough", null);
        buildMapPlot("PTRBO", "K9H", 44.299, -78.3145);
        buildMapPlot("PTRBO", "K9J", 44.2763, -78.313);
        buildMapPlot("PTRBO", "K9K", 44.279, -78.3659);
        buildMapPlot("PTRBO", "K9L", 44.3238, -78.303);

        buildLocation("RICHM", "Canada", "Ontario", "Richmond Hill", null);
        buildMapPlot("RICHM", "L4B", 43.8417, -79.4011);
        buildMapPlot("RICHM", "L4C", 43.8759, -79.4381);
        buildMapPlot("RICHM", "L4E", 43.9423, -79.4595);
        buildMapPlot("RICHM", "L4S", 43.8986, -79.4013);

        buildLocation("SRNIA", "Canada", "Ontario", "Sarnia", null);
        buildMapPlot("SRNIA", "N7S", 42.9607, -82.3718);
        buildMapPlot("SRNIA", "N7T", 42.971, -82.4084);
        buildMapPlot("SRNIA", "N7V", 42.9891, -82.399);
        buildMapPlot("SRNIA", "N7W", 42.9838, -82.3214);
        buildMapPlot("SRNIA", "N7X", 43.0147, -82.3417);

        buildLocation("SAUSM", "Canada", "Ontario", "Sault Ste Marie", null);
        buildMapPlot("SAUSM", "P6A", 46.5175, -84.3414);
        buildMapPlot("SAUSM", "P6B", 46.5105, -84.321);
        buildMapPlot("SAUSM", "P6C", 46.5245, -84.3768);

        buildLocation("SCUGO", "Canada", "Ontario", "Scugog/Brock", null);
        buildMapPlot("SCUGO", "L0C", 44.1065, -79.1427);

        buildLocation("SUDBU", "Canada", "Ontario", "Sudbury", null);
        buildMapPlot("SUDBU", "P0M", 46.1329, -80.8231);
        buildMapPlot("SUDBU", "P3A", 46.5076, -80.9872);
        buildMapPlot("SUDBU", "P3B", 46.4769, -80.9099);
        buildMapPlot("SUDBU", "P3C", 46.4727, -81.0291);
        buildMapPlot("SUDBU", "P3E", 46.4918, -80.9955);
        buildMapPlot("SUDBU", "P3G", 46.4106, -81.0517);
        buildMapPlot("SUDBU", "P3L", 46.5625, -80.8665);
        buildMapPlot("SUDBU", "P3N", 46.6191, -81.0356);
        buildMapPlot("SUDBU", "P3P", 46.6318, -81.0147);
        buildMapPlot("SUDBU", "P3Y", 46.4223, -81.1165);

        buildLocation("TORDC", "Canada", "Ontario", "Toronto", "Downtown/Central Toronto");
        buildMapPlot("TORDC", "M4N", 43.7168, -79.3998);
        buildMapPlot("TORDC", "M4P", 43.7066, -79.398);
        buildMapPlot("TORDC", "M4R", 43.7066, -79.3996);
        buildMapPlot("TORDC", "M4S", 43.6964, -79.3953);
        buildMapPlot("TORDC", "M4T", 43.6825, -79.3897);
        buildMapPlot("TORDC", "M4V", 43.6778, -79.3992);
        buildMapPlot("TORDC", "M4W", 43.6699, -79.3887);
        buildMapPlot("TORDC", "M4X", 43.6647, -79.3695);
        buildMapPlot("TORDC", "M4Y", 43.6618, -79.3847);
        buildMapPlot("TORDC", "M5A", 43.6369, -79.3505);
        buildMapPlot("TORDC", "M5B", 43.6543, -79.3796);
        buildMapPlot("TORDC", "M5C", 43.687, -79.5318);
        buildMapPlot("TORDC", "M5E", 43.639, -79.4499);
        buildMapPlot("TORDC", "M5G", 43.6519, -79.3874);
        buildMapPlot("TORDC", "M5H", 43.649, -79.3784);
        buildMapPlot("TORDC", "M5J", 43.6441, -79.3801);
        buildMapPlot("TORDC", "M5K", 43.6469, -79.3823);
        buildMapPlot("TORDC", "M5L", 43.6492, -79.3823);
        buildMapPlot("TORDC", "M5N", 43.7043, -79.4093);
        buildMapPlot("TORDC", "M5P", 43.6981, -79.3987);
        buildMapPlot("TORDC", "M5R", 43.6705, -79.3901);
        buildMapPlot("TORDC", "M5S", 43.6619, -79.3952);
        buildMapPlot("TORDC", "M5T", 43.6497, -79.3952);
        buildMapPlot("TORDC", "M5V", 43.6525, -79.3686);
        buildMapPlot("TORDC", "M5W", 43.6437, -79.3787);
        buildMapPlot("TORDC", "M5X", 43.6492, -79.3823);
        buildMapPlot("TORDC", "M6G", 43.6565, -79.4079);
        buildMapPlot("TORDC", "M4E", 43.6675, -79.296);
        buildMapPlot("TORDC", "M4J", 43.6713, -79.3412);
        buildMapPlot("TORDC", "M4K", 43.6668, -79.3501);
        buildMapPlot("TORDC", "M4L", 43.662, -79.3281);
        buildMapPlot("TORDC", "M4M", 43.6505, -79.3369);

        buildLocation("TOREY", "Canada", "Ontario", "Toronto", "East York");
        buildMapPlot("TOREY", "M4B", 43.6979, -79.2986);
        buildMapPlot("TOREY", "M4C", 43.68, -79.3218);
        buildMapPlot("TOREY", "M4G", 43.6918, -79.3708);
        buildMapPlot("TOREY", "M4H", 43.7018, -79.3578);

        buildLocation("TORET", "Canada", "Ontario", "Toronto", "Etobicoke");
        buildMapPlot("TORET", "M8V", 43.6305, -79.4762);
        buildMapPlot("TORET", "M8W", 43.5908, -79.5218);
        buildMapPlot("TORET", "M8X", 43.649, -79.4977);
        buildMapPlot("TORET", "M8Y", 43.6181, -79.4967);
        buildMapPlot("TORET", "M8Z", 43.6053, -79.5201);
        buildMapPlot("TORET", "M9A", 43.6434, -79.5297);
        buildMapPlot("TORET", "M9B", 43.6383, -79.5356);
        buildMapPlot("TORET", "M9C", 43.6088, -79.5574);
        buildMapPlot("TORET", "M9P", 43.6814, -79.5367);
        buildMapPlot("TORET", "M9R", 43.6808, -79.5438);
        buildMapPlot("TORET", "M9V", 43.73, -79.5542);
        buildMapPlot("TORET", "M9W", 43.6772, -79.5894);

        buildLocation("TORSC", "Canada", "Ontario", "Toronto", "Scarborough");
        buildMapPlot("TORSC", "M1B", 43.7976, -79.227);
        buildMapPlot("TORSC", "M1C", 43.7882, -79.1911);
        buildMapPlot("TORSC", "M1E", 43.7385, -79.2021);
        buildMapPlot("TORSC", "M1G", 43.7563, -79.2224);
        buildMapPlot("TORSC", "M1H", 43.7563, -79.2417);
        buildMapPlot("TORSC", "M1J", 43.7315, -79.246);
        buildMapPlot("TORSC", "M1K", 43.7025, -79.2656);
        buildMapPlot("TORSC", "M1L", 43.6905, -79.2857);
        buildMapPlot("TORSC", "M1M", 43.7041, -79.2446);
        buildMapPlot("TORSC", "M1N", 43.6748, -79.2764);
        buildMapPlot("TORSC", "M1P", 43.7422, -79.2818);
        buildMapPlot("TORSC", "M1R", 43.7293, -79.3038);
        buildMapPlot("TORSC", "M1S", 43.7807, -79.2855);
        buildMapPlot("TORSC", "M1T", 43.7719, -79.3213);
        buildMapPlot("TORSC", "M1V", 43.813, -79.2781);
        buildMapPlot("TORSC", "M1W", 43.7822, -79.3261);
        buildMapPlot("TORSC", "M1X", 43.8275, -79.2437);

        buildLocation("TORNY", "Canada", "Ontario", "Toronto", "North York");
        buildMapPlot("TORNY", "M5M", 43.7248, -79.4033);
        buildMapPlot("TORNY", "M2H", 43.7895, -79.3735);
        buildMapPlot("TORNY", "M2J", 43.7685, -79.3584);
        buildMapPlot("TORNY", "M2K", 43.7657, -79.3835);
        buildMapPlot("TORNY", "M2L", 43.7352, -79.3818);
        buildMapPlot("TORNY", "M2M", 43.784, -79.4263);
        buildMapPlot("TORNY", "M2N", 43.7521, -79.4202);
        buildMapPlot("TORNY", "M2P", 43.7393, -79.4005);
        buildMapPlot("TORNY", "M2R", 43.7648, -79.4325);
        buildMapPlot("TORNY", "M3A", 43.7358, -79.328);
        buildMapPlot("TORNY", "M3B", 43.7363, -79.3498);
        buildMapPlot("TORNY", "M3C", 43.7122, -79.3237);
        buildMapPlot("TORNY", "M3H", 43.7387, -79.4337);
        buildMapPlot("TORNY", "M3J", 43.7496, -79.4886);
        buildMapPlot("TORNY", "M3K", 43.7271, -79.4666);
        buildMapPlot("TORNY", "M3L", 43.7183, -79.5119);
        buildMapPlot("TORNY", "M3M", 43.72, -79.5085);
        buildMapPlot("TORNY", "M3N", 43.7387, -79.5166);
        buildMapPlot("TORNY", "M4A", 43.7159, -79.3037);
        buildMapPlot("TORNY", "M6A", 43.7193, -79.43);
        buildMapPlot("TORNY", "M6B", 43.7054, -79.4272);

        buildLocation("TORYO", "Canada", "Ontario", "Toronto", "York");
        buildMapPlot("TORYO", "M6C", 43.683, -79.4184);
        buildMapPlot("TORYO", "M6E", 43.6797, -79.4358);
        buildMapPlot("TORYO", "M6M", 43.6815, -79.4668);
        buildMapPlot("TORYO", "M6N", 43.668, -79.4515);
        buildMapPlot("TORYO", "M9N", 43.7087, -79.5287);
        buildMapPlot("TORYO", "M6S", 43.6358, -79.4668);

        buildLocation("UXBRI", "Canada", "Ontario", "Uxbridge", null);
        buildMapPlot("UXBRI", "L9P", 44.1065, -79.1427);

        buildLocation("THUND", "Canada", "Ontario", "Thunder Bay", null);
        buildMapPlot("THUND", "P7A", 48.4578, -89.1885);
        buildMapPlot("THUND", "P7B", 48.4349, -89.2192);
        buildMapPlot("THUND", "P7C", 48.3852, -89.242);
        buildMapPlot("THUND", "P7E", 48.3775, -89.2704);
        buildMapPlot("THUND", "P7G", 48.4511, -89.273);
        buildMapPlot("THUND", "P7J", 48.3187, -89.3415);
        buildMapPlot("THUND", "P7K", 48.3959, -89.3556);

        buildLocation("TIMMI", "Canada", "Ontario", "Timmins", null);
        buildMapPlot("TIMMI", "P0N", 48.4466, -80.8161);
        buildMapPlot("TIMMI", "P4N", 48.4757, -81.3366);
        buildMapPlot("TIMMI", "P4P", 48.4951, -81.3513);
        buildMapPlot("TIMMI", "P4R", 48.473, -81.3765);

        buildLocation("WINSO", "Canada", "Ontario", "Windsor", null);
        buildMapPlot("WINSO", "N8N", 42.3326, -82.8926);
        buildMapPlot("WINSO", "N8P", 42.3391, -82.9279);
        buildMapPlot("WINSO", "N8R", 42.3136, -82.9338);
        buildMapPlot("WINSO", "N8S", 42.3307, -82.9752);
        buildMapPlot("WINSO", "N8T", 42.3188, -82.965);
        buildMapPlot("WINSO", "N8V", 42.2679, -82.9699);
        buildMapPlot("WINSO", "N8W", 42.3062, -83.0017);
        buildMapPlot("WINSO", "N8X", 42.3039, -83.0308);
        buildMapPlot("WINSO", "N8Y", 42.3251, -83.0171);
        buildMapPlot("WINSO", "N9A", 42.3159, -83.0393);
        buildMapPlot("WINSO", "N9B", 42.3158, -83.0568);
        buildMapPlot("WINSO", "N9C", 42.3077, -83.0724);
        buildMapPlot("WINSO", "N9E", 42.2736, -83.0416);
        buildMapPlot("WINSO", "N9G", 42.2581, -82.9988);
        buildMapPlot("WINSO", "N9H", 42.2351, -82.998);
        buildMapPlot("WINSO", "N9J", 42.247, -83.1);
        buildMapPlot("WINSO", "N9K", 42.049, -83.1032);
        buildMapPlot("WINSO", "N9V", 42.1106, -83.1115);

        /*
        *      Prince Edward Island
        */
        buildLocation("CHART", "Canada", "Prince Edward Island", "Charlottetown", null);
        buildMapPlot("CHART", "C1A",   46.2318, -63.1192);
        buildMapPlot("CHART", "C1C",   46.2688, -63.1097);
        buildMapPlot("CHART", "C1E",   46.2607, -63.16);
        buildMapPlot("CHART", "C1B",   46.2067, -63.0729);
        buildMapPlot("CHART", "C0A",   46.1668, -62.6487);
        buildMapPlot("CHART", "C0B",  46.3182, -63.5586);

        buildLocation("SUMME", "Canada", "Prince Edward Island", "Summerside", null);
        buildMapPlot("SUMME", "C1N",   46.3907, -63.7868);

        /*
        *      Quebec
        */
        buildLocation("GATNE", "Canada", "Quebec", "Gatineau", null);
        buildMapPlot("GATNE", "J8P",   45.487, -75.616);
        buildMapPlot("GATNE", "J8R",   45.529, -75.609);
        buildMapPlot("GATNE", "J8T",   45.478, -75.706);
        buildMapPlot("GATNE", "J8V",   45.571, -75.761);

        buildLocation("MNTRE", "Canada", "Quebec", "Montreal", null);
        buildMapPlot("MNTRE", "H4X",   45.453, -73.649);
        buildMapPlot("MNTRE", "H1B",   45.632, -73.508);
        buildMapPlot("MNTRE", "H1G",   45.611, -73.621);
        buildMapPlot("MNTRE", "H1H",   45.59, -73.639);
        buildMapPlot("MNTRE", "H2Z",   45.505, -73.562);
        buildMapPlot("MNTRE", "H3A",   45.504,-73.575);
        buildMapPlot("MNTRE", "H3B",   45.501, -73.568);
        buildMapPlot("MNTRE", "H3G",   45.4997,-73.579);
        buildMapPlot("MNTRE", "H3H",   45.501,-73.588);
        buildMapPlot("MNTRE", "H2Y",   45.506, -73.555);

        buildLocation("QUEBE", "Canada", "Quebec", "Quebec City", null);
        buildMapPlot("QUEBE", "G1L",   46.83, -71.246);
        buildMapPlot("QUEBE", "G1P",   46.81, -71.31);
        buildMapPlot("QUEBE", "G1R",   46.807,-71.218);
        buildMapPlot("QUEBE", "G1S",   46.793, -71.245);
        buildMapPlot("QUEBE", "G2C",   46.829, -71.334);
        buildMapPlot("QUEBE", "G1J",   46.814, -71.219);
        buildMapPlot("QUEBE", "G1M",   46.818, -71.271);
        buildMapPlot("QUEBE", "G1N",   46.804, -71.264);
        buildMapPlot("QUEBE", "G1T",   46.774, -71.261);
        buildMapPlot("QUEBE", "G2J",   46.84, -71.278);
        buildMapPlot("QUEBE", "G2K",   46.854, -71.304);
        buildMapPlot("QUEBE", "G1J",   46.838, -71.223);

        buildLocation("SAGUE", "Canada", "Quebec", "Saguenay", null);
        buildMapPlot("SAGUE", "G0V",    50.172, -70.628);

        buildLocation("SHEBK", "Canada", "Quebec", "Sherbrooke", null);
        buildMapPlot("SHEBK", "J1E",    45.423, -71.872);
        buildMapPlot("SHEBK", "J1G",    45.402, -71.848);
        buildMapPlot("SHEBK", "J1H",    45.389, -71.899);
        buildMapPlot("SHEBK", "J1K",    45.382, -71.933);
        buildMapPlot("SHEBK", "J1L",    45.411, -71.959);
        buildMapPlot("SHEBK", "J1M",    45.366, -71.842);
        buildMapPlot("SHEBK", "J1J",    45.4, -71.899);

        buildLocation("TRSRV", "Canada", "Quebec", "Trois-Rivieres", null);
        buildMapPlot("TRSRV", "G8Y",   46.367, -72.617);
        buildMapPlot("TRSRV", "G8Z",   46.346, -72.572);
        buildMapPlot("TRSRV", "G9A",   46.37, -72.679);
        buildMapPlot("TRSRV", "G9B",   46.316, -72.683);
        buildMapPlot("TRSRV", "G9C",   46.392, -72.673);

        buildLocation("CHICO", "Canada", "Quebec", "Chicoutimi - Jonquière", null);
        buildMapPlot("CHICO", "G7G",   48.445, -71.103);
        buildMapPlot("CHICO", "G7H",   48.419, -71.042);
        buildMapPlot("CHICO", "G7J",   48.417, -71.103);
        buildMapPlot("CHICO", "G7K",   48.369, -71.118);
        buildMapPlot("CHICO", "G7S",   48.429, -71.177);
        buildMapPlot("CHICO", "G7T",   48.398, -71.153);
        buildMapPlot("CHICO", "G7X",   48.32, -71.415);
        buildMapPlot("CHICO", "G7Y",   48.37, -71.236);
        buildMapPlot("CHICO", "G7Z",   48.438, -71.264);
        buildMapPlot("CHICO", "G8A",   48.423, -71.363);

        buildLocation("STSRI", "Canada", "Quebec", "Saint-Jean-sur-Richelieu", null);
        buildMapPlot("STSRI", "J2X",   45.309, -73.219);
        buildMapPlot("STSRI", "J2Y",   45.311, -73.356);
        buildMapPlot("STSRI", "J3B",   45.283, -73.279);
        buildMapPlot("STSRI", "J3A",   45.307, -73.263);

        buildLocation("CHATGU", "Canada", "Quebec", "Châteauguay", null);
        buildMapPlot("CHATGU", "J6J",   45.3811, -73.7280);

        buildLocation("DRUMVI", "Canada", "Quebec", "Drummondville", null);
        buildMapPlot("DRUMVI", "J2C",   45.889, -72.506);
        buildMapPlot("DRUMVI", "J2E",   45.91, -72.529);
        buildMapPlot("DRUMVI", "J2A",   45.815, -72.403);
        buildMapPlot("DRUMVI", "J2B",   45.906, -72.593);

        buildLocation("STJER", "Canada", "Quebec", "Saint-Jérôme", null);
        buildMapPlot("STJER", "J5L",    45.799, -74.073);
        buildMapPlot("STJER", "J7Z",    45.779, -73.983);
        buildMapPlot("STJER", "J7Y",    45.78, -74.004);

        buildLocation("GRANY", "Canada", "Quebec", "Granby", null);
        buildMapPlot("GRANY", "J2G",     45.405, -72.72);
        buildMapPlot("GRANY", "J2H",     45.394, -72.7);
        buildMapPlot("GRANY", "J2J",     45.401, -72.783);

        buildLocation("BELIL", "Canada", "Quebec", "Beloeil", null);
        buildMapPlot("BELIL", "J3G",     45.595, -73.228);
        buildMapPlot("BELIL", "J3H",     45.553, -73.175);

        buildLocation("STHYT", "Canada", "Quebec", "Saint-Hyacinthe", null);
        buildMapPlot("STHYT", "J2R",     45.657, -72.924);
        buildMapPlot("STHYT", "J2S",     45.614, -72.991);
        buildMapPlot("STHYT", "J2T",     45.597, 72.937);

        buildLocation("SHGAN", "Canada", "Quebec", "Shawinigan", null);
        buildMapPlot("SHGAN", "G9N",     46.556, -72.72);
        buildMapPlot("SHGAN", "G9P",     46.507, -72.744);
        buildMapPlot("SHGAN", "G9R",     46.61, -72.827);

        buildLocation("JOLTE", "Canada", "Quebec", "Joliette", null);
        buildMapPlot("JOLTE", "J6E",     46.017, -73.449);

        buildLocation("VICVI", "Canada", "Quebec", "Victoriaville", null);
        buildMapPlot("VICVI", "G6P",     46.053, -71.948);
        buildMapPlot("VICVI", "G6R",     46.016, -71.956);
        buildMapPlot("VICVI", "G6S",     46.026, -71.872);
        buildMapPlot("VICVI", "G6T",     46.083, -71.973);

        /*
        *      Saskatchewan
        */
        buildLocation("MOOJA", "Canada", "Saskatchewan", "Moose Jaw", null);
        buildMapPlot("MOOJA", "S6H",   50.39, -105.558);
        buildMapPlot("MOOJA", "S6J",   50.418, -105.539);
        buildMapPlot("MOOJA", "S6K",   50.454, -105.642);

        buildLocation("PRNAL", "Canada", "Saskatchewan", "Prince Albert", null);
        buildMapPlot("PRNAL", "S6V",    54.493, -104.305);
        buildMapPlot("PRNAL", "S6W",    53.179, -105.774);
        buildMapPlot("PRNAL", "S6X",    53.194, -105.702);

        buildLocation("REGIN", "Canada", "Saskatchewan", "Regina", null);
        buildMapPlot("REGIN", "S4L",   50.413, -104.273);
        buildMapPlot("REGIN", "S4P",   50.419, -104.677);
        buildMapPlot("REGIN", "S4R",   50.486, -104.616);
        buildMapPlot("REGIN", "S4T",   50.451, -104.665);
        buildMapPlot("REGIN", "S4V",   50.425, -104.539);
        buildMapPlot("REGIN", "S4W",   50.408, -104.654);
        buildMapPlot("REGIN", "S4X",   50.506, -104.675);
        buildMapPlot("REGIN", "S4Y",   50.477, -104.699);
        buildMapPlot("REGIN", "S4Z",   50.45, -104.532);
        buildMapPlot("REGIN", "S4N",   50.467, -104.541);
        buildMapPlot("REGIN", "S4S",   50.415, -104.61);

        buildLocation("SASKA", "Canada", "Saskatchewan", "Saskatoon", null);
        buildMapPlot("SASKA", "S7H",    52.117, -106.635);
        buildMapPlot("SASKA", "S7J",    52.096, -106.625);
        buildMapPlot("SASKA", "S7K",    52.011, -106.796);
        buildMapPlot("SASKA", "S7L",    52.156, -106.687);
        buildMapPlot("SASKA", "S7M",    52.113, -106.723);
        buildMapPlot("SASKA", "S7N",    52.14, -106.608);
        buildMapPlot("SASKA", "S7R",    52.159, -106.716);
        buildMapPlot("SASKA", "S7S",    52.161, -106.586);
        buildMapPlot("SASKA", "S7T",    52.04, -106.66);
        buildMapPlot("SASKA", "S7V",    52.098, -106.555);
        buildMapPlot("SASKA", "S7P",    52.275, -106.501);
        buildMapPlot("SASKA", "S7W",    52.117, -106.635);

        /*
        *      Yukon Territory
        */
        buildLocation("WHTHS", "Canada", "Yukon Territory", "Whitehorse", null);
        buildMapPlot("WHTHS", "Y1A",    60.7227, -135.0534);
    }



    /**
     * Build American Map
     */
    private void buildAmericanMap() {
        /*
        *      Alabama
        */
        buildLocation("AUBRN", "United States", "Alabama", "Auburn", null);
        buildLocation("BIRMH", "United States", "Alabama", "Birmingham", null);
        buildLocation("DOTHN", "United States", "Alabama", "Dothan", null);
        buildLocation("FLOMS", "United States", "Alabama", "Florence/Muscle Shoals", null);
        buildLocation("GDSNA", "United States", "Alabama", "Gadsden-Anniston", null);
        buildLocation("HTVLD", "United States", "Alabama", "Huntsville/Decatur", null);
        buildLocation("MOBIL", "United States", "Alabama", "Mobile", null);
        buildLocation("MNTGM", "United States", "Alabama", "Montgomery", null);
        buildLocation("TUSCA", "United States", "Alabama", "Tuscaloosa", null);

        /*
        *      Alaska
        */
        buildLocation("ANCHO", "United States", "Alaska", "Anchorage/Mat-Su", null);
        buildLocation("FRBNK", "United States", "Alaska", "Fairbanks", null);
        buildLocation("KENAI", "United States", "Alaska", "Kenai Peninsula", null);
        buildLocation("SOUAL", "United States", "Alaska", "Southeast Alaska", null);

        /*
        *      Arizona
        */
        buildLocation("FLAGS", "United States", "Arizona", "Flagstaff/Sedona", null);
        buildLocation("MOHVE", "United States", "Arizona", "Mohave County", null);
        buildLocation("PHOCS", "United States", "Arizona", "Phoenix", "Central/South Phoenix");
        buildLocation("PHOEV", "United States", "Arizona", "Phoenix", "East Valley");
        buildLocation("PHONO", "United States", "Arizona", "Phoenix", "North Phoenix");
        buildLocation("PHOWV", "United States", "Arizona", "Phoenix", "West Valley");
        buildLocation("PRESC", "United States", "Arizona", "Prescott", null);
        buildLocation("SHOLO", "United States", "Arizona", "Show Low", null);
        buildLocation("SIERV", "United States", "Arizona", "Sierra Vista", null);
        buildLocation("TUSCO", "United States", "Arizona", "Tucson", null);
        buildLocation("YUMAA", "United States", "Arizona", "Yuma", null);

        /*
        *      Arkansas
        */
        buildLocation("FAYET", "United States", "Arkansas", "Fayetteville", null);
        buildLocation("FRTSM", "United States", "Arkansas", "Fort Smith", null);
        buildLocation("JONES", "United States", "Arkansas", "Jonesboro", null);
        buildLocation("LTLRK", "United States", "Arkansas", "Little Rock", null);
        buildLocation("TEXAR", "United States", "Arkansas", "Texarkana", null);

        /*
        *      California
        */
        buildLocation("BAKER", "United States", "California", "Bakersfield", null);
        buildLocation("CHICO", "United States", "California", "Chico", null);
        buildLocation("FREMA", "United States", "California", "Fresno/Madera", null);
        buildLocation("GOLDC", "United States", "California", "Gold Country", null);
        buildLocation("HANCO", "United States", "California", "Hanford/Corcoran", null);
        buildLocation("HUMBO", "United States", "California", "Humboldt County", null);
        buildLocation("IMPRC", "United States", "California", "Imperial County", null);
        buildLocation("INEMP", "United States", "California", "Inland Empire", null);
        buildLocation("LAANT", "United States", "California", "Los Angeles", "Antelope Valley");
        buildLocation("LACEN", "United States", "California", "Los Angeles", "Central LA");
        buildLocation("LALBF", "United States", "California", "Los Angeles", "Long Beach/562");
        buildLocation("LASFV", "United States", "California", "Los Angeles", "San Fernando Valley");
        buildLocation("LASGV", "United States", "California", "Los Angeles", "San Gabriel Valley");
        buildLocation("LAWSB", "United States", "California", "Los Angeles", "Westside/South Bay");
        buildLocation("MENDO", "United States", "California", "Mendocino County", null);
        buildLocation("MERCE", "United States", "California", "Merced", null);
        buildLocation("MODES", "United States", "California", "Modesto", null);
        buildLocation("MNTBY", "United States", "California", "Monterey Bay", null);
        buildLocation("ORNGE", "United States", "California", "Orange County", null);
        buildLocation("PLMSP", "United States", "California", "Palm Springs", null);
        buildLocation("REDDI", "United States", "California", "Redding", null);
        buildLocation("SACRA", "United States", "California", "Sacramento", null);
        buildLocation("SDCIT", "United States", "California", "San Diego", "City of San Diego");
        buildLocation("SDEAS", "United States", "California", "San Diego", "East SD County");
        buildLocation("SDNOR", "United States", "California", "San Diego", "North SD County");
        buildLocation("SDSOU", "United States", "California", "San Diego", "South SD County");
        buildLocation("SFCIT", "United States", "California", "San Francisco Bay Area", "City of San Francisco");
        buildLocation("SFEAS", "United States", "California", "San Francisco Bay Area", "East Bay");
        buildLocation("SFNOR", "United States", "California", "San Francisco Bay Area", "North Bay");
        buildLocation("SFPEN", "United States", "California", "San Francisco Bay Area", "Peninsula");
        buildLocation("SFSAN", "United States", "California", "San Francisco Bay Area", "Santa Cruz");
        buildLocation("SFSOU", "United States", "California", "San Francisco Bay Area", "South Bay");
        buildLocation("SLOBI", "United States", "California", "San Luis Obispo", null);
        buildLocation("SNTBA", "United States", "California", "Santa Barbara", null);
        buildLocation("SNTMA", "United States", "California", "Santa Maria", null);
        buildLocation("SISKI", "United States", "California", "Siskiyou County", null);
        buildLocation("STOCK", "United States", "California", "Stockton", null);
        buildLocation("SUSAN", "United States", "California", "Susanville", null);
        buildLocation("VNTCO", "United States", "California", "Ventura County", null);
        buildLocation("VISAL", "United States", "California", "Visalia-Tulare", null);
        buildLocation("YUBAS", "United States", "California", "Yuba Sutter", null);

        /*
        *      Colorado
        */
        buildLocation("BOULD", "United States", "Colorado", "Boulder", null);
        buildLocation("COLSP", "United States", "Colorado", "Colorado Springs", null);
        buildLocation("DENVR", "United States", "Colorado", "Denver", null);
        buildLocation("EACOL", "United States", "Colorado", "Eastern Colorado", null);
        buildLocation("FTCNO", "United States", "Colorado", "Fort Collins/North Colorado", null);
        buildLocation("HIGHR", "United States", "Colorado", "High Rockies", null);
        buildLocation("PUEBL", "United States", "Colorado", "Pueblo", null);
        buildLocation("WSTSL", "United States", "Colorado", "Western Slope", null);

        /*
        *      Connecticut
        */
        buildLocation("ESTCT", "United States", "Connecticut", "Eastern Connecticut", null);
        buildLocation("HARTF", "United States", "Connecticut", "Hartford", null);
        buildLocation("NWHVN", "United States", "Connecticut", "New Haven", null);
        buildLocation("NWECT", "United States", "Connecticut", "Northwest Connecticut", null);

        /*
        *      Delaware
        */
        buildLocation("DELAW", "United States", "Delaware", "Delaware", null);

        /*
        *      DC
        */
        buildLocation("WSHDC", "United States", "DC", "Washington", "Washington, DC");
        buildLocation("WSHNV", "United States", "DC", "Washington", "Northern Virginia, DC");
        buildLocation("WSHMA", "United States", "DC", "Washington", "Maryland, DC");

        /*
        *      Florida
        */
        buildLocation("BROCO", "United States", "Florida", "Broward County", null);
        buildLocation("DAYBE", "United States", "Florida", "Daytona Beach", null);
        buildLocation("FLKEY", "United States", "Florida", "Florida Keys", null);
        buildLocation("FTLAU", "United States", "Florida", "Fort Lauderdale", null);
        buildLocation("SWFLC", "United States", "Florida", "South West FL/Fort Myers", "Lee County");
        buildLocation("SWFCH", "United States", "Florida", "South West FL/Fort Myers", "Charlotte County");
        buildLocation("SFLCO", "United States", "Florida", "South West FL/Fort Myers", "Collier County");
        buildLocation("GAINE", "United States", "Florida", "Gainesville", null);
        buildLocation("HRTLD", "United States", "Florida", "Heartland", null);
        buildLocation("JCKSO", "United States", "Florida", "Jacksonville", null);
        buildLocation("LAKEL", "United States", "Florida", "Lakeland", null);
        buildLocation("MIAMI", "United States", "Florida", "Miami", null);
        buildLocation("NOCFL", "United States", "Florida", "North Central Florida", null);
        buildLocation("OCALA", "United States", "Florida", "Ocala", null);
        buildLocation("OKALO", "United States", "Florida", "Okaloosa/Walton", null);
        buildLocation("PLMBE", "United States", "Florida", "Palm Beach County", null);
        buildLocation("PENSA", "United States", "Florida", "Pensacola", null);
        buildLocation("SARAS", "United States", "Florida", "Sarasota/Bradenton", null);
        buildLocation("SOUFL", "United States", "Florida", "South Florida", null);
        buildLocation("SPCCO", "United States", "Florida", "Space Coast", null);
        buildLocation("STAUG", "United States", "Florida", "St. Augustine", null);
        buildLocation("TALLA", "United States", "Florida", "Tallahassee", null);
        buildLocation("TBHER", "United States", "Florida", "Tampa Bay", "Hernando County");
        buildLocation("TBHIL", "United States", "Florida", "Tampa Bay", "Hillsborough County");
        buildLocation("TBPAS", "United States", "Florida", "Tampa Bay", "Pasco County");
        buildLocation("TBPIN", "United States", "Florida", "Tampa Bay", "Pinellas County");
        buildLocation("TRSCO", "United States", "Florida", "Treasure Coast", null);

        /*
        *      Georgia
        */
        buildLocation("ALBAN", "United States", "Georgia", "Albany", null);
        buildLocation("ATHNS", "United States", "Georgia", "Athens", null);
        buildLocation("ATLEA", "United States", "Georgia", "Atlanta", "Atlanta East");
        buildLocation("ATLNO", "United States", "Georgia", "Atlanta", "Atlanta North");
        buildLocation("ALTSO", "United States", "Georgia", "Atlanta", "Atlanta South");
        buildLocation("ATLWE", "United States", "Georgia", "Atlanta", "Atlanta West");
        buildLocation("AUGUS", "United States", "Georgia", "Augusta", null);
        buildLocation("BRUNS", "United States", "Georgia", "Brunswick", null);
        buildLocation("COLUM", "United States", "Georgia", "Columbus", null);
        buildLocation("MACWA", "United States", "Georgia", "Macon/Warner Robins", null);
        buildLocation("NWEGA", "United States", "Georgia", "Northwest Georgia", null);
        buildLocation("SAVHI", "United States", "Georgia", "Savannah/Hinesville", null);
        buildLocation("STATE", "United States", "Georgia", "Statesboro", null);
        buildLocation("VALDO", "United States", "Georgia", "Valdosta", null);

        /*
        *      Hawaii
        */
        buildLocation("BIGIS", "United States", "Hawaii", "Big Island", null);
        buildLocation("KAUAI", "United States", "Hawaii", "Kauai", null);
        buildLocation("HMAUI", "United States", "Hawaii", "Maui", null);
        buildLocation("MOLOK", "United States", "Hawaii", "Molokai", null);
        buildLocation("HOAHU", "United States", "Hawaii", "Oahu", null);

        /*
        *      Idaho
        */
        buildLocation("BOISE", "United States", "Idaho", "Boise", null);
        buildLocation("EASID", "United States", "Idaho", "East Idaho", null);
        buildLocation("LEWCL", "United States", "Idaho", "Lewiston/Clarkston", null);
        buildLocation("TWNFL", "United States", "Idaho", "Twin Falls", null);

        /*
        *      Illinois
        */
        buildLocation("BLMNO", "United States", "Illinois", "Bloomington-Normal", null);
        buildLocation("CHMPA", "United States", "Illinois", "Champaign Urbana", null);
        buildLocation("CHICT", "United States", "Illinois", "Chicago", "City of Chicago");
        buildLocation("CHINC", "United States", "Illinois", "Chicago", "North Chicagoland");
        buildLocation("CHINI", "United States", "Illinois", "Chicago", "Northwest Indiana");
        buildLocation("CHINS", "United States", "Illinois", "Chicago", "Northwest Suburbs");
        buildLocation("CHISC", "United States", "Illinois", "Chicago", "South Chicagoland");
        buildLocation("CHIWS", "United States", "Illinois", "Chicago", "West Chicagoland");
        buildLocation("DECAT", "United States", "Illinois", "Decatur", null);
        buildLocation("LASAL", "United States", "Illinois", "La Salle County", null);
        buildLocation("MATCH", "United States", "Illinois", "Mattoon/Charleston", null);
        buildLocation("PEORI", "United States", "Illinois", "Peoria", null);
        buildLocation("RCKFD", "United States", "Illinois", "Rockford", null);
        buildLocation("SOUIL", "United States", "Illinois", "Southern Illinois", null);
        buildLocation("SPNGF", "United States", "Illinois", "Springfield", null);
        buildLocation("WSTIL", "United States", "Illinois", "Western Illinois", null);

        /*
        *      Indiana
        */
        buildLocation("BLOOM", "United States", "Indiana", "Bloomington", null);
        buildLocation("EVNVL", "United States", "Indiana", "Evansville", null);
        buildLocation("FTWYN", "United States", "Indiana", "Fort Wayne", null);
        buildLocation("INDAP", "United States", "Indiana", "Indianapolis", null);
        buildLocation("KOKOM", "United States", "Indiana", "Kokomo", null);
        buildLocation("LAFAY", "United States", "Indiana", "Lafayette", null);
        buildLocation("MUNAN", "United States", "Indiana", "Muncie/Anderson", null);
        buildLocation("RCHMN", "United States", "Indiana", "Richmond", null);
        buildLocation("SBMIC", "United States", "Indiana", "South Bend/Michiana", null);
        buildLocation("TERHA", "United States", "Indiana", "Terre Haute", null);

        /*
        *      Iowa
        */
        buildLocation("AMESI", "United States", "Iowa", "Ames", null);
        buildLocation("CEDAR", "United States", "Iowa", "Cedar Rapids", null);
        buildLocation("DSMOI", "United States", "Iowa", "Des Moines", null);
        buildLocation("DUBUQ", "United States", "Iowa", "Dubuque", null);
        buildLocation("FTDOD", "United States", "Iowa", "Fort Dodge", null);
        buildLocation("IOCTY", "United States", "Iowa", "Iowa City", null);
        buildLocation("MSCTY", "United States", "Iowa", "Mason City", null);
        buildLocation("QDCTS", "United States", "Iowa", "Quad Cities", null);
        buildLocation("SIOUX", "United States", "Iowa", "Sioux City", null);
        buildLocation("SEIOW", "United States", "Iowa", "Southeast Iowa", null);
        buildLocation("WTRLO", "United States", "Iowa", "Waterloo/Cedar Falls", null);

        /*
        *      Kansas
        */
        buildLocation("LAWRE", "United States", "Kansas", "Lawrence", null);
        buildLocation("MANKS", "United States", "Kansas", "Manhattan", null);
        buildLocation("NORKS", "United States", "Kansas", "Northwest Kansas", null);
        buildLocation("SLINA", "United States", "Kansas", "Salina", null);
        buildLocation("SESKS", "United States", "Kansas", "Southeast Kansas", null);
        buildLocation("SWSKS", "United States", "Kansas", "Southwest Kansas", null);
        buildLocation("TOPEK", "United States", "Kansas", "Topeka", null);
        buildLocation("WICHI", "United States", "Kansas", "Wichita", null);

        /*
        *      Kentucky
        */
        buildLocation("BOWGR", "United States", "Kentucky", "Bowling Green", null);
        buildLocation("EASTK", "United States", "Kentucky", "Eastern Kentucky", null);
        buildLocation("LEXIN", "United States", "Kentucky", "Lexington", null);
        buildLocation("LOIVL", "United States", "Kentucky", "Louisville", null);
        buildLocation("OWNBR", "United States", "Kentucky", "Owensboro", null);
        buildLocation("WSTKY", "United States", "Kentucky", "Western Kentucky", null);

        /*
        *      Louisiana
        */
        buildLocation("BTNRO", "United States", "Louisiana", "Baton Rouge", null);
        buildLocation("CENLA", "United States", "Louisiana", "Central Louisiana", null);
        buildLocation("HOUMA", "United States", "Louisiana", "Houma", null);
        buildLocation("LAFET", "United States", "Louisiana", "Lafayette", null);
        buildLocation("LKCHR", "United States", "Louisiana", "Lake Charles", null);
        buildLocation("MONRO", "United States", "Louisiana", "Monroe", null);
        buildLocation("NWORL", "United States", "Louisiana", "New Orleans", null);
        buildLocation("SHREV", "United States", "Louisiana", "Shreveport", null);

        /*
        *      Maine
        */
        buildLocation("MAINE", "United States", "Maine", "Maine", null);

        /*
        *      Maryland
        */
        buildLocation("ANNAP", "United States", "Maryland", "Annapolis", null);
        buildLocation("BALTI", "United States", "Maryland", "Baltimore", null);
        buildLocation("ESSHO", "United States", "Maryland", "Eastern Shore", null);
        buildLocation("FREDR", "United States", "Maryland", "Frederick", null);
        buildLocation("SOUMD", "United States", "Maryland", "Southern Maryland", null);
        buildLocation("WESMD", "United States", "Maryland", "Western Maryland", null);

        /*
        *      Massachusetts
        */
        buildLocation("BOSOX", "United States", "Massachusetts", "Boston", "Boston/Cambridge/Brookline");
        buildLocation("BOSMW", "United States", "Massachusetts", "Boston", "Metro West");
        buildLocation("BOSME", "United States", "Massachusetts", "Boston", "Northwest/Merrimack");
        buildLocation("BOSNS", "United States", "Massachusetts", "Boston", "North Shore");
        buildLocation("BOSSS", "United States", "Massachusetts", "Boston", "South Shore");
        buildLocation("CAPEI", "United States", "Massachusetts", "Cape Cod/Islands", null);
        buildLocation("SOUCS", "United States", "Massachusetts", "South Coast", null);
        buildLocation("WESMA", "United States", "Massachusetts", "Western MA", null);
        buildLocation("WORCE", "United States", "Massachusetts", "Worcester/Central MA", null);

        /*
        *      Michigan
        */
        buildLocation("ANNAR", "United States", "Michigan", "Ann Arbor", null);
        buildLocation("BTLCR", "United States", "Michigan", "Battle Creek", null);
        buildLocation("CENMI", "United States", "Michigan", "Central Michigan", null);
        buildLocation("DETMC", "United States", "Michigan", "Detroit", "Macomb County");
        buildLocation("DETOA", "United States", "Michigan", "Detroit", "Oakland County");
        buildLocation("DETWC", "United States", "Michigan", "Detroit", "Wayne County");
        buildLocation("FLINT", "United States", "Michigan", "Flint", null);
        buildLocation("GRNRA", "United States", "Michigan", "Grand Rapids", null);
        buildLocation("HOLLA", "United States", "Michigan", "Holland", null);
        buildLocation("JAKSO", "United States", "Michigan", "Jackson", null);
        buildLocation("KLAMA", "United States", "Michigan", "Kalamazoo", null);
        buildLocation("LANSI", "United States", "Michigan", "Lansing", null);
        buildLocation("MONRO", "United States", "Michigan", "Monroe", null);
        buildLocation("MUSKG", "United States", "Michigan", "Muskegon", null);
        buildLocation("NORMI", "United States", "Michigan", "Northern Michigan", null);
        buildLocation("PRTHU", "United States", "Michigan", "Port Huron", null);
        buildLocation("SMBAY", "United States", "Michigan", "Saginaw/Midland/Baycity", null);
        buildLocation("SOUWE", "United States", "Michigan", "Southwest Michigan", null);
        buildLocation("THUMB", "United States", "Michigan", "The Thumb", null);
        buildLocation("UPPEN", "United States", "Michigan", "Upper Peninsula", null);

        /*
        *      Minnesota
        */
        buildLocation("BEMID", "United States", "Minnesota", "Bemidji", null);
        buildLocation("BRAIN", "United States", "Minnesota", "Brainerd", null);
        buildLocation("DULSU", "United States", "Minnesota", "Duluth/Superior", null);
        buildLocation("MANKA", "United States", "Minnesota", "Mankato", null);
        buildLocation("MINAN", "United States", "Minnesota", "Minneapolis", "Anoka/Chisago/Isanti");
        buildLocation("MINCS", "United States", "Minnesota", "Minneapolis", "Carver/Sherburne/Wright");
        buildLocation("MINDS", "United States", "Minnesota", "Minneapolis", "Dakota/Scott");
        buildLocation("MINHE", "United States", "Minnesota", "Minneapolis", "Hennepin County");
        buildLocation("MINRA", "United States", "Minnesota", "Minneapolis", "Ramsey County");
        buildLocation("MINWA", "United States", "Minnesota", "Minneapolis", "Washington County");
        buildLocation("ROCHE", "United States", "Minnesota", "Rochester", null);
        buildLocation("SWMIN", "United States", "Minnesota", "Southwest Minnesota", null);
        buildLocation("STCLD", "United States", "Minnesota", "St. Cloud", null);

        /*
        *      Mississippi
        */
        buildLocation("GULFP", "United States", "Mississippi", "Gulfport/Biloxi", null);
        buildLocation("HTBRG", "United States", "Mississippi", "Hattiesburg", null);
        buildLocation("JCKSN", "United States", "Mississippi", "Jackson", null);
        buildLocation("MERID", "United States", "Mississippi", "Meridian", null);
        buildLocation("NORMS", "United States", "Mississippi", "North MS", null);
        buildLocation("SOUMS", "United States", "Mississippi", "Southwest MS", null);

        /*
        *      Missouri
        */
        buildLocation("COLJC", "United States", "Missouri", "Columbia/Jeff City", null);
        buildLocation("JPOLI", "United States", "Missouri", "Joplin", null);
        buildLocation("KANCI", "United States", "Missouri", "Kansas City", null);
        buildLocation("KIRKS", "United States", "Missouri", "Kirksville", null);
        buildLocation("OZARK", "United States", "Missouri", "Lake of the Ozarks", null);
        buildLocation("SOUMO", "United States", "Missouri", "Southeast MO", null);
        buildLocation("SPRGF", "United States", "Missouri", "Springfield", null);
        buildLocation("STJOS", "United States", "Missouri", "St. Joseph", null);
        buildLocation("STLUS", "United States", "Missouri", "St. Louis", null);

        /*
        *      Montana
        */
        buildLocation("BILLI", "United States", "Montana", "Billings", null);
        buildLocation("BOZEM", "United States", "Montana", "Bozeman", null);
        buildLocation("BUTTE", "United States", "Montana", "Butte", null);
        buildLocation("EASMT", "United States", "Montana", "Eastern MT", null);
        buildLocation("GRTFL", "United States", "Montana", "Great Falls", null);
        buildLocation("HLENA", "United States", "Montana", "Helena", null);
        buildLocation("KALSP", "United States", "Montana", "Kalispell", null);
        buildLocation("MISOL", "United States", "Montana", "Missoula", null);

        /*
        *      Nebraska
        */
        buildLocation("GRNIS", "United States", "Nebraska", "Grand Island", null);
        buildLocation("LINCO", "United States", "Nebraska", "Lincoln", null);
        buildLocation("NORPA", "United States", "Nebraska", "North Platte", null);
        buildLocation("OMAHA", "United States", "Nebraska", "Omaha/Council Bluffs", null);
        buildLocation("SCOTB", "United States", "Nebraska", "Scottsbluff/Panhandle", null);

        /*
        *      Nevada
        */
        buildLocation("ELKO1", "United States", "Nevada", "Elko", null);
        buildLocation("LSVEG", "United States", "Nevada", "Las Vegas", null);
        buildLocation("RENTO", "United States", "Nevada", "Reno/Tahoe", null);

        /*
        *      New Hampshire
        */
        buildLocation("NEWHA", "United States", "New Hampshire", "New Hampshire", null);

        /*
        *      New Jersey
        */
        buildLocation("CENNJ", "United States", "New Jersey", "Central NJ", null);
        buildLocation("JERSH", "United States", "New Jersey", "Jersey Shore", null);
        buildLocation("NOJER", "United States", "New Jersey", "North Jersey", null);
        buildLocation("SOJER", "United States", "New Jersey", "South Jersey", null);

        /*
        *      New Mexico
        */
        buildLocation("ALBUQ", "United States", "New Mexico", "Albuquerque", null);
        buildLocation("CLOVS", "United States", "New Mexico", "Clovis/Portales", null);
        buildLocation("FRMIN", "United States", "New Mexico", "Farmington", null);
        buildLocation("LSCRU", "United States", "New Mexico", "Las Cruces", null);
        buildLocation("ROSWE", "United States", "New Mexico", "Roswell/Carlsbad", null);
        buildLocation("SANFE", "United States", "New Mexico", "Santa Fe/Taos", null);

        /*
        *      New York
        */
        buildLocation("ALBNY", "United States", "New York", "Albany", null);
        buildLocation("BINGH", "United States", "New York", "Binghamton", null);
        buildLocation("BUFLO", "United States", "New York", "Buffalo", null);
        buildLocation("CATSK", "United States", "New York", "Catskills", null);
        buildLocation("CHAUT", "United States", "New York", "Chautauqua", null);
        buildLocation("ELMIR", "United States", "New York", "Elmira/Corning", null);
        buildLocation("FNGLK", "United States", "New York", "Finger Lakes", null);
        buildLocation("GLNFL", "United States", "New York", "Glens Falls", null);
        buildLocation("HUDVL", "United States", "New York", "Hudson Valley", null);
        buildLocation("ITHAC", "United States", "New York", "Ithaca", null);
        buildLocation("NYBRK", "United States", "New York", "New York City", "Brooklyn");
        buildLocation("NYBRX", "United States", "New York", "New York City", "Bronx");
        buildLocation("NYFAI", "United States", "New York", "New York City", "Fairfield");
        buildLocation("NYLNG", "United States", "New York", "New York City", "Long Island");
        buildLocation("NYMAN", "United States", "New York", "New York City", "Manhattan");
        buildLocation("NYNJR", "United States", "New York", "New York City", "New Jersey");
        buildLocation("NYQUE", "United States", "New York", "New York City", "Queens");
        buildLocation("NYSTA", "United States", "New York", "New York City", "Staten Island");
        buildLocation("NYWES", "United States", "New York", "New York City", "Westchester");
        buildLocation("ONEON", "United States", "New York", "Oneonta", null);
        buildLocation("PLATT", "United States", "New York", "Plattsburgh/Adirondacks", null);
        buildLocation("POTCA", "United States", "New York", "Potsdam/Canton/Massena", null);
        buildLocation("ROCST", "United States", "New York", "Rochester", null);
        buildLocation("SYRAC", "United States", "New York", "Syracuse", null);
        buildLocation("TWINT", "United States", "New York", "Twin Tiers NY/PA", null);
        buildLocation("UTICA", "United States", "New York", "Utica/Rome/Oneida", null);
        buildLocation("WATER", "United States", "New York", "Watertown", null);

        /*
        *      North Carolina
        */
        buildLocation("ASHEV", "United States", "North Carolina", "Asheville", null);
        buildLocation("BOONE", "United States", "North Carolina", "Boone", null);
        buildLocation("CHARL", "United States", "North Carolina", "Charlotte", null);
        buildLocation("EASNC", "United States", "North Carolina", "Eastern NC", null);
        buildLocation("FAYET", "United States", "North Carolina", "Fayetteville", null);
        buildLocation("GREEN", "United States", "North Carolina", "Greensboro", null);
        buildLocation("HICKO", "United States", "North Carolina", "Hickory/Lenoir", null);
        buildLocation("JCKVI", "United States", "North Carolina", "Jacksonville", null);
        buildLocation("OUTBA", "United States", "North Carolina", "Outer Banks", null);
        buildLocation("RALDU", "United States", "North Carolina", "Raleigh/Durham/Chapel Hill", null);
        buildLocation("WILMG", "United States", "North Carolina", "Wilmington", null);
        buildLocation("WINSL", "United States", "North Carolina", "Winston-Salem", null);

        /*
        *      North Dakota
        */
        buildLocation("BISMA", "United States", "North Dakota", "Bismarck", null);
        buildLocation("FARGO", "United States", "North Dakota", "Fargo/Moorhead", null);
        buildLocation("GRNFO", "United States", "North Dakota", "Grand Forks", null);
        buildLocation("NORDA", "United States", "North Dakota", "North Dakota", null);

        /*
        *      Ohio
        */
        buildLocation("AKCAN", "United States", "Ohio", "Akron/Canton", null);
        buildLocation("ASHTA", "United States", "Ohio", "Ashtabula", null);
        buildLocation("ATHEN", "United States", "Ohio", "Athens", null);
        buildLocation("CHILI", "United States", "Ohio", "Chillicothe", null);
        buildLocation("CINCI", "United States", "Ohio", "Cincinnati", null);
        buildLocation("CLEVE", "United States", "Ohio", "Cleveland", null);
        buildLocation("COLUM", "United States", "Ohio", "Columbus", null);
        buildLocation("DATSP", "United States", "Ohio", "Dayton/Springfield", null);
        buildLocation("LIMAF", "United States", "Ohio", "Lima/Findlay", null);
        buildLocation("MANSF", "United States", "Ohio", "Mansfield", null);
        buildLocation("SANDU", "United States", "Ohio", "Sandusky", null);
        buildLocation("TOLED", "United States", "Ohio", "Toledo", null);
        buildLocation("TUSCO", "United States", "Ohio", "Tuscarawas County", null);
        buildLocation("YONTO", "United States", "Ohio", "Youngstown", null);
        buildLocation("ZANES", "United States", "Ohio", "Zanesville/Cambridge", null);

        /*
        *      Oklahoma
        */
        buildLocation("LAWTO", "United States", "Oklahoma", "Lawton", null);
        buildLocation("NOROK", "United States", "Oklahoma", "Northwest OK", null);
        buildLocation("OKLCI", "United States", "Oklahoma", "Oklahoma City", null);
        buildLocation("STILL", "United States", "Oklahoma", "Stillwater", null);
        buildLocation("TULSA", "United States", "Oklahoma", "Tulsa", null);

        /*
        *      Oregon
        */
        buildLocation("BEND1", "United States", "Oregon", "Bend", null);
        buildLocation("CORVL", "United States", "Oregon", "Corvallis/Albany", null);
        buildLocation("EASOR", "United States", "Oregon", "East OR", null);
        buildLocation("EUGNE", "United States", "Oregon", "Eugene", null);
        buildLocation("KLAMA", "United States", "Oregon", "Klamath Falls", null);
        buildLocation("MEDFO", "United States", "Oregon", "Medford/Ashland", null);
        buildLocation("ORCOA", "United States", "Oregon", "Oregon Coast", null);
        buildLocation("PTCLC", "United States", "Oregon", "Portland", "Clackamas County");
        buildLocation("PTCLR", "United States", "Oregon", "Portland", "Clark/Cowlitz");
        buildLocation("PTCOL", "United States", "Oregon", "Portland", "Columbia Gorge");
        buildLocation("PTMUL", "United States", "Oregon", "Portland", "Multnomah County");
        buildLocation("PTNOC", "United States", "Oregon", "Portland", "North Coast");
        buildLocation("PTWAS", "United States", "Oregon", "Portland", "Washington County");
        buildLocation("PTYAM", "United States", "Oregon", "Portland", "Yamhill County");
        buildLocation("ROSEB", "United States", "Oregon", "Roseburg", null);
        buildLocation("SALEM", "United States", "Oregon", "Salem", null);

        /*
        *      Pennsylvania
        */
        buildLocation("ALTOO", "United States", "Pennsylvania", "Altoona/Johnstown", null);
        buildLocation("CMBVL", "United States", "Pennsylvania", "Cumberland Valley", null);
        buildLocation("ERIE1", "United States", "Pennsylvania", "Erie", null);
        buildLocation("HARRI", "United States", "Pennsylvania", "Harrisburg", null);
        buildLocation("LANCA", "United States", "Pennsylvania", "Lancaster", null);
        buildLocation("LHVLY", "United States", "Pennsylvania", "Lehigh Valley", null);
        buildLocation("MEDVL", "United States", "Pennsylvania", "Meadville", null);
        buildLocation("PHILI", "United States", "Pennsylvania", "Philadelphia", null);
        buildLocation("PITTS", "United States", "Pennsylvania", "Pittsburgh", null);
        buildLocation("POCON", "United States", "Pennsylvania", "Poconos", null);
        buildLocation("READI", "United States", "Pennsylvania", "Reading", null);
        buildLocation("SCRAN", "United States", "Pennsylvania", "Scranton/Wilkes-Barre", null);
        buildLocation("STECL", "United States", "Pennsylvania", "State College", null);
        buildLocation("WILSP", "United States", "Pennsylvania", "Williamsport", null);
        buildLocation("YORK1", "United States", "Pennsylvania", "York", null);

        /*
        *      Rhode Island
        */
        buildLocation("", "United States", "Rhode Island", "Rhode Island", null);

        /*
        *      South Carolina
        */
        buildLocation("CHARL", "United States", "South Carolina", "Charleston", null);
        buildLocation("COLUM", "United States", "South Carolina", "Columbia", null);
        buildLocation("FLORE", "United States", "South Carolina", "Florence", null);
        buildLocation("GRUPS", "United States", "South Carolina", "Greenville/Upstate", null);
        buildLocation("HILTO", "United States", "South Carolina", "Hilton Head", null);
        buildLocation("MYRTL", "United States", "South Carolina", "Myrtle Beach", null);

        /*
        *      South Dakota
        */
        buildLocation("NORSD", "United States", "South Dakota", "Northeast SD", null);
        buildLocation("PIESD", "United States", "South Dakota", "Pierre/Central SD", null);
        buildLocation("RAPID", "United States", "South Dakota", "Rapid City/West SD", null);
        buildLocation("SIOUX", "United States", "South Dakota", "Sioux Falls/Southeast SD", null);
        buildLocation("SOUDA", "United States", "South Dakota", "South Dakota", null);

        /*
        *      Tennessee
        */
        buildLocation("CHATT", "United States", "Tennessee", "Chattanooga", null);
        buildLocation("CLKSV", "United States", "Tennessee", "Clarksville", null);
        buildLocation("COOKE", "United States", "Tennessee", "Cookeville", null);
        buildLocation("JKSON", "United States", "Tennessee", "Jackson", null);
        buildLocation("KNOXV", "United States", "Tennessee", "Knoxville", null);
        buildLocation("MEMPH", "United States", "Tennessee", "Memphis", null);
        buildLocation("NASHV", "United States", "Tennessee", "Nashville", null);
        buildLocation("TRICI", "United States", "Tennessee", "Tri-Cities", null);

        /*
        *      Texas
        */
        buildLocation("ABILE", "United States", "Texas", "Abilene", null);
        buildLocation("AMARI", "United States", "Texas", "Amarillo", null);
        buildLocation("AUSTI", "United States", "Texas", "Austin", null);
        buildLocation("BEAPO", "United States", "Texas", "Beaumont/Port Arthur", null);
        buildLocation("BROWN", "United States", "Texas", "Brownsville", null);
        buildLocation("COLST", "United States", "Texas", "College Station", null);
        buildLocation("CORPU", "United States", "Texas", "Corpus Christi", null);
        buildLocation("DALAS", "United States", "Texas", "Dallas", "Dallas");
        buildLocation("DLFTW", "United States", "Texas", "Dallas", "Fort Worth");
        buildLocation("DLMID", "United States", "Texas", "Dallas", "Mid Cities");
        buildLocation("DLNOR", "United States", "Texas", "Dallas", "North Dallas");
        buildLocation("DLSOU", "United States", "Texas", "Dallas", "South Dallas");
        buildLocation("DEATX", "United States", "Texas", "Deep East Texas", null);
        buildLocation("DELRI", "United States", "Texas", "Del Rio/Eagle Pass", null);
        buildLocation("ELPSO", "United States", "Texas", "El Paso", null);
        buildLocation("GALVE", "United States", "Texas", "Galveston", null);
        buildLocation("HOUST", "United States", "Texas", "Houston", null);
        buildLocation("KILLE", "United States", "Texas", "Killeen/Temple/Fort Hood", null);
        buildLocation("LARED", "United States", "Texas", "Laredo", null);
        buildLocation("LUBBO", "United States", "Texas", "Lubbock", null);
        buildLocation("MCALL", "United States", "Texas", "McAllen/Edinburg", null);
        buildLocation("ODESS", "United States", "Texas", "Odessa/Midland", null);
        buildLocation("SANAG", "United States", "Texas", "San Angelo", null);
        buildLocation("SANAN", "United States", "Texas", "San Antonio", null);
        buildLocation("SANMA", "United States", "Texas", "San Marcos", null);
        buildLocation("SWTEX", "United States", "Texas", "Southwest TX", null);
        buildLocation("TEXOM", "United States", "Texas", "Texoma", null);
        buildLocation("TYLER", "United States", "Texas", "Tyler/East TX", null);
        buildLocation("VICTO", "United States", "Texas", "Victoria", null);
        buildLocation("WACO1", "United States", "Texas", "Waco", null);
        buildLocation("WICHI", "United States", "Texas", "Wichita Falls", null);

        /*
        *      Utah
        */
        buildLocation("LOGAN", "United States", "Utah", "Logan", null);
        buildLocation("ODGEN", "United States", "Utah", "Ogden/Clearfield", null);
        buildLocation("PROVO", "United States", "Utah", "Provo/Orem", null);
        buildLocation("SALTL", "United States", "Utah", "Salt Lake City", null);
        buildLocation("STGEO", "United States", "Utah", "St. George", null);

        /*
        *      Vermont
        */
        buildLocation("VERMO", "United States", "Vermont", "Vermont", null);

        /*
        *      Virginia
        */
        buildLocation("CHALO", "United States", "Virginia", "Charlottesville", null);
        buildLocation("DANVI", "United States", "Virginia", "Danville", null);
        buildLocation("FREDE", "United States", "Virginia", "Fredericksburg", null);
        buildLocation("HAMPT", "United States", "Virginia", "Hampton Roads", null);
        buildLocation("HARRI", "United States", "Virginia", "Harrisonburg", null);
        buildLocation("LYNCH", "United States", "Virginia", "Lynchburg", null);
        buildLocation("NEWRI", "United States", "Virginia", "New River Valley", null);
        buildLocation("RICHM", "United States", "Virginia", "Richmond", null);
        buildLocation("ROANO", "United States", "Virginia", "Roanoke", null);
        buildLocation("SOUVA", "United States", "Virginia", "Southwest VA", null);
        buildLocation("WINCH", "United States", "Virginia", "Winchester", null);

        /*
        *      Washington
        */
        buildLocation("BELLI", "United States", "Washington", "Bellingham", null);
        buildLocation("KENNE", "United States", "Washington", "Kennewick/Pasco/Richland", null);
        buildLocation("MOSES", "United States", "Washington", "Moses Lake", null);
        buildLocation("OLYMP", "United States", "Washington", "Olympic Peninsula", null);
        buildLocation("PULLM", "United States", "Washington", "Pullman/Moscow", null);
        buildLocation("SEEAS", "United States", "Washington", "Seattle", "East Side");
        buildLocation("SEKIT", "United States", "Washington", "Seattle", "Kitsap County");
        buildLocation("SEOLY", "United States", "Washington", "Seattle", "Olympia");
        buildLocation("SEATL", "United States", "Washington", "Seattle", "Seattle");
        buildLocation("SESNO", "United States", "Washington", "Seattle", "Snohomish County");
        buildLocation("SESKI", "United States", "Washington", "Seattle", "South King");
        buildLocation("SETAC", "United States", "Washington", "Seattle", "Tacoma");
        buildLocation("SKAGI", "United States", "Washington", "Skagit/San Juan Island", null);
        buildLocation("SPOKA", "United States", "Washington", "Spokane/Coeur D'Alene", null);
        buildLocation("WENAT", "United States", "Washington", "Wenatchee", null);
        buildLocation("YAKIM", "United States", "Washington", "Yakima", null);

        /*
        *      West Virginia
        */
        buildLocation("CHLRT", "United States", "West Virginia", "Charleston", null);
        buildLocation("EAPAN", "United States", "West Virginia", "Eastern Panhandle", null);
        buildLocation("HUNTI", "United States", "West Virginia", "Huntington/Ashland", null);
        buildLocation("MORGA", "United States", "West Virginia", "Morgantown", null);
        buildLocation("NOPAN", "United States", "West Virginia", "Northern Panhandle", null);
        buildLocation("PARKE", "United States", "West Virginia", "Parkersburg/Marietta", null);
        buildLocation("SOUWV", "United States", "West Virginia", "Southern WV", null);

        /*
        *      Wisconsin
        */
        buildLocation("APPLE", "United States", "Wisconsin", "Appleton/Oshkosh/Fond Du Lac", null);
        buildLocation("EAUCL", "United States", "Wisconsin", "Eau Claire", null);
        buildLocation("GRNBA", "United States", "Wisconsin", "Green Bay", null);
        buildLocation("JNSVL", "United States", "Wisconsin", "Janesville", null);
        buildLocation("KENOS", "United States", "Wisconsin", "Kenosha/Racine", null);
        buildLocation("LACRO", "United States", "Wisconsin", "La Crosse", null);
        buildLocation("MADIS", "United States", "Wisconsin", "Madison", null);
        buildLocation("MILWA", "United States", "Wisconsin", "Milwaukee", null);
        buildLocation("NORWI", "United States", "Wisconsin", "Northern WI", null);
        buildLocation("SHEBO", "United States", "Wisconsin", "Sheboygan", null);
        buildLocation("WASAU", "United States", "Wisconsin", "Wausau", null);

        /*
        *      Wyoming
        */
        buildLocation("WYOMI", "United States", "Wyoming", "Wyoming", null);

        /*
        *      Territories
        */
        buildLocation("GUAMI", "United States", "Territories", "Guam/Micronesia", null);
        buildLocation("PUERT", "United States", "Territories", "Puerto Rico", null);
        buildLocation("USVIR", "United States", "Territories", "U.S. Virgin Islands", null);
    }
}