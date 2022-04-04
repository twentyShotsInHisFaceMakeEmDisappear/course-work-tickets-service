package eu.senla.eventoservice.service.impl;

import eu.senla.eventoservice.repository.GenreRepository;
import eu.senla.eventoservice.service.GenreServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService implements GenreServiceInterface {

    private final GenreRepository genreRepository;

}
