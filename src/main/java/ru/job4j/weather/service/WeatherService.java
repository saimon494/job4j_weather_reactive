package ru.job4j.weather.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.job4j.weather.model.Weather;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();

    {
        weathers.put(1, new Weather(1, "Msc", 23));
        weathers.put(2, new Weather(2, "SPb", 22));
        weathers.put(3, new Weather(3, "Bryansk", 23));
        weathers.put(4, new Weather(4, "Smolensk", 25));
        weathers.put(5, new Weather(5, "Kiev", 20));
        weathers.put(6, new Weather(6, "Minsk", 19));
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Mono<Weather> hottest() {
        int max = weathers.values().stream()
                .mapToInt(Weather::getTemperature)
                .max()
                .getAsInt();
        return Mono.justOrEmpty(weathers.values().stream()
                .filter(x -> x.getTemperature() == max)
                .findFirst());
    }

    public Flux<Weather> cityGreatThen(int temp) {
        return Flux.fromIterable(weathers.values().stream()
                .filter(x -> x.getTemperature() > temp)
                .collect(Collectors.toList()));
    }
}
