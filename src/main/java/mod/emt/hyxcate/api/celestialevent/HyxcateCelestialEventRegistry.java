package mod.emt.hyxcate.api.celestialevent;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HyxcateCelestialEventRegistry {
    private static class Registration<T> {
        private final String id;
        private final Integer priority;
        private final Function<CapabilityCelestialEvent, T> factory;

        private Registration(String id, Integer priority, Function<CapabilityCelestialEvent, T> factory) {
            this.id = id;
            this.priority = priority;
            this.factory = factory;
        }

        public String getId() {
            return id;
        }

        public Integer getPriority() {
            return priority;
        }

        public Function<CapabilityCelestialEvent, T> getFactory() {
            return factory;
        }
    }

    private static final List<Registration<HyxcateLunarEvent>> lunarEvents = new ArrayList<>();
    private static final List<Registration<HyxcateSolarEvent>> solarEvents = new ArrayList<>();

    public static void registerLunar(String id, Function<CapabilityCelestialEvent, HyxcateLunarEvent> factory) {
        registerLunar(id, null, factory);
    }

    public static void registerLunar(String id, int priority, Function<CapabilityCelestialEvent, HyxcateLunarEvent> factory) {
        registerLunar(id, Integer.valueOf(priority), factory);
    }

    private static void registerLunar(String id, Integer priority, Function<CapabilityCelestialEvent, HyxcateLunarEvent> factory) {
        validateId(id);
        if (lunarEvents.stream().anyMatch(event -> event.getId().equals(id))) {
            throw new IllegalArgumentException("Duplicate Lunar Event id: " + id);
        }
        lunarEvents.add(new Registration<>(id, priority, factory));
    }


    public static void registerSolar(String id, Function<CapabilityCelestialEvent, HyxcateSolarEvent> factory) {
        registerSolar(id, null, factory);
    }

    public static void registerSolar(String id, int priority, Function<CapabilityCelestialEvent, HyxcateSolarEvent> factory) {
        registerSolar(id, Integer.valueOf(priority), factory);
    }

    private static void registerSolar(String id, Integer priority, Function<CapabilityCelestialEvent, HyxcateSolarEvent> factory) {
        validateId(id);
        if (solarEvents.stream().anyMatch(event -> event.getId().equals(id))) {
            throw new IllegalArgumentException("Duplicate Solar Event id: " + id);
        }
        solarEvents.add(new Registration<>(id, priority, factory));
    }

    public static List<HyxcateLunarEvent> createLunarEvents(CapabilityCelestialEvent world) {
        return lunarEvents.stream()
                .sorted((a, b) -> {
                    int priorityA = a.getPriority() == null ? 0 : a.getPriority();
                    int priorityB = b.getPriority() == null ? 0 : b.getPriority();
                    return Integer.compare(priorityB, priorityA);
                })
                .map(event -> event.getFactory().apply(world))
                .collect(Collectors.toList());
    }

    public static List<HyxcateSolarEvent> createSolarEvents(CapabilityCelestialEvent world) {
        return solarEvents.stream()
                .sorted((a, b) -> {
                    int priorityA = a.getPriority() == null ? 0 : a.getPriority();
                    int priorityB = b.getPriority() == null ? 0 : b.getPriority();
                    return Integer.compare(priorityB, priorityA);
                })
                .map(event -> event.getFactory().apply(world))
                .collect(Collectors.toList());
    }


    private static void validateId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Celestial Event id cannot be null or empty!");
        }
    }
}